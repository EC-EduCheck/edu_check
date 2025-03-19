package org.example.educheck.domain.meetingroomreservation.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.educheck.domain.meetingroomreservation.TimeSlot;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingRoomReservationService {

    private final MeetingRoomReservationRepository meetingRoomReservationRepository;
    private final MemberRepository memberRepository;
    private final MeetingRoomRepository meetingRoomRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void createReservation(UserDetails user, Long campusId, MeetingRoomReservationRequestDto requestDto) {

        Member findMember = memberRepository.findByEmail(user.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 member입니다."));

        MeetingRoom meetingRoom = meetingRoomRepository.findById(requestDto.getMeetingRoomId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회의실이 존재하지 않습니다."));

        validateUserCampusMatchMeetingRoom(campusId, meetingRoom);

        validateReservationTime(requestDto.getStartTime(), requestDto.getEndTime());

//        if(!isAvailable(meetingRoom.getId(), TimeSlot.from(requestDto))) {
//            throw new
//        }

        validateReservableTime(meetingRoom, requestDto.getStartTime(), requestDto.getEndTime());

        MeetingRoomReservation meetingRoomReservation = requestDto.toEntity(findMember, meetingRoom);
        meetingRoomReservationRepository.save(meetingRoomReservation);
    }

    /**
     * 예약은 9시부터 22시까지 가능
     */
    private void validateReservationTime(LocalDateTime startTime, LocalDateTime endTime) {

        LocalTime startOfDay = LocalTime.of(9, 0);
        LocalTime endOfDay = LocalTime.of(22, 0);


        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("종료 시간이 시작 시간보다 빠를 수 없습니다.");
        }

        if (ChronoUnit.MINUTES.between(startTime, endTime) < 15) {
            throw new IllegalArgumentException("최소 예약 시간은 15분입니다.");
        }

        if (startTime.toLocalTime().isBefore(startOfDay) || endTime.toLocalTime().isAfter(endOfDay)) {
            throw new IllegalArgumentException("예약 가능 시간은 오전 9시부터 오후 10시까지입니다.");
        }

    }

    private void validateReservableTime(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate date = startTime.toLocalDate();
        boolean result = meetingRoomReservationRepository.existsOverlappingReservation(meetingRoom,
                date, startTime, endTime);

        if (result) {
            throw new IllegalArgumentException("중복 예약이 발생했습니다.");
        }
    }

    //TODO: 쿼리 발생하는거 확인 후, FETCH JOIN 처리 등 고려 하기
    private void validateUserCampusMatchMeetingRoom(Long campusId, MeetingRoom meetingRoom) {

        if (!campusId.equals(meetingRoom.getCampusId())) {
            throw new IllegalArgumentException("해당 회의실은 캠퍼스내 회의실이 아닙니다.");
        }
    }

    private String generateSlotKey(Long roomId, LocalDate date) {
        return String.format("mettingRoom:%d:date:%s", roomId, date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    private int calculateSlots(int startHour, int endHour, int slotDurationMinutes) {
        int totalMinutes = (endHour - startHour) * 60;
        return totalMinutes / slotDurationMinutes;

    }

    private void initDailyReservationsSlots(LocalDate date) {
        List<MeetingRoom> roomList = meetingRoomRepository.findAll();

        for (MeetingRoom room : roomList) {
            String redisKey = generateSlotKey(room.getId(), date);

            int slotsCount = calculateSlots(9, 22, 15);
            Boolean[] slots = new Boolean[slotsCount];
            for (int i = 0; i < slotsCount; i++) {
                slots[i] = false;
            }

            redisTemplate.opsForValue().set(redisKey, slots);
        }
    }

    public boolean isAvailable(Long meetingRoomId, TimeSlot timeSlot) {
        String redisKey = generateSlotKey(meetingRoomId, timeSlot.getDate());
        Boolean[] slots = (Boolean[]) redisTemplate.opsForValue().get(redisKey);

        if (slots == null) {
            initDailyReservationsSlots(timeSlot.getDate());
            slots = (Boolean[]) redisTemplate.opsForValue().get(redisKey);
        }

        int startSlotIndex = calculateSlotIndex(timeSlot.getStartTime());
        int endSlotIndex = calculateSlotIndex(timeSlot.getStartTime());

        for (int i = startSlotIndex; i <= endSlotIndex; i++) {
            if (slots[i]) {
                return false;
            }
        }

        return true;
    }

    private int calculateSlotIndex(LocalTime localTime) {
        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        return (hour - 9) * 4 + (minute / 15);

    }
}
