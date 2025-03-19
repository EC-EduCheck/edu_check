package org.example.educheck.domain.meetingroomreservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.educheck.domain.meetingroomreservation.TimeSlot;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.global.common.exception.custom.ReservationConflictException;
import org.example.educheck.global.common.exception.custom.ResourceNotFoundException;
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

@Slf4j
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

        Member findMember = memberRepository.findByEmail(user.getUsername()).orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사용자입니다."));

        MeetingRoom meetingRoom = meetingRoomRepository.findById(requestDto.getMeetingRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 회의실이 존재하지 않습니다."));

        validateUserCampusMatchMeetingRoom(campusId, meetingRoom);

        validateReservationTime(requestDto.getStartTime(), requestDto.getEndTime());

        TimeSlot timeSlot = TimeSlot.from(requestDto);

        //신버전
        if (!isAvailable(meetingRoom.getId(), timeSlot)) {
            throw new ReservationConflictException();
        }

        //RDB에 예약 -> Redis 슬롯 처리
        MeetingRoomReservation meetingRoomReservation = requestDto.toEntity(findMember, meetingRoom);
        meetingRoomReservationRepository.save(meetingRoomReservation);

        updateRedisSlots(meetingRoom.getId(), timeSlot, true);

        log.info("createReservation 메서드, 예약 성공");
    }

    private void updateRedisSlots(Long roomId, TimeSlot timeSlot, boolean isReserved) {

        String redisKey = generateSlotKey(roomId, timeSlot.getDate());

        Boolean[] slots = (Boolean[]) redisTemplate.opsForValue().get(redisKey);

        if (slots == null) {
            initDailyReservationsSlots(timeSlot.getDate());
            slots = (Boolean[]) redisTemplate.opsForValue().get(redisKey);
        }

        int startSlotIndex = calculateSlotIndex(timeSlot.getStartTime());
        int endSlotIndex = calculateSlotIndex(timeSlot.getEndTime());

        for (int i = startSlotIndex; i <= endSlotIndex; i++) {
            slots[i] = isReserved;
        }

        redisTemplate.opsForValue().set(redisKey, slots);

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
