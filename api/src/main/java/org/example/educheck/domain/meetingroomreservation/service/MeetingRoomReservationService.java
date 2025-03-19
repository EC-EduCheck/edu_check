package org.example.educheck.domain.meetingroomreservation.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingRoomReservationService {

    private final MeetingRoomReservationRepository meetingRoomReservationRepository;
    private final MemberRepository memberRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    @Transactional
    public void createReservation(User user, Long campusId, MeetingRoomReservationRequestDto requestDto) {

        //회의실이 존재하는지 검증 및 엔티티를 저장시 사용해야함(예외 처리가 예쁘지 않음)
        MeetingRoom meetingRoom = meetingRoomRepository.findById(requestDto.getMeetingRoomId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회의실이 존재하지 않습니다."));

        //사용자 캠퍼스랑 회의실 캠퍼스가 일치하는지 검증
        validateUserCampusMatchMeetingRoom(campusId, meetingRoom);

        //유효한 예약 시간대인지 검증
        validateReservationTime(requestDto.getStartTime(), requestDto.getEndTime());


        //해당 시간대에 다른 예약이 존재하는지 검증
        validateReservableTime(meetingRoom, requestDto.getStartTime(), requestDto.getEndTime());

        //예약 처리
        Member findMember = memberRepository.findByEmail(user.getUsername());
        MeetingRoomReservation meetingRoomReservation = requestDto.toEntity(findMember, meetingRoom);
        meetingRoomReservationRepository.save(meetingRoomReservation);
    }

    /**
     * 예약은 9시부터 22시까지 가능
     */
    private void validateReservationTime(LocalDateTime startTime, LocalDateTime endTime) {

        LocalTime startOfDay = LocalTime.of(9, 0);
        LocalTime endOfDay = LocalTime.of(22, 0);


        if (startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }

        if (endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("종료 시간이 시작 시간보다 빠를 수 없습니다.");
        }

        if (ChronoUnit.MINUTES.between(startTime, endTime) < 15) {
            throw new IllegalArgumentException("최소 예약 시간은 15분입니다.");
        }

        if (startTime.getMinute() % 15 != 0 || endTime.getMinute() % 15 != 0) {
            throw new IllegalArgumentException("예약은 15분 단위로만 가능합니다.");
        }

        if (startTime.toLocalTime().isBefore(startOfDay) || endTime.toLocalTime().isAfter(endOfDay)) {
            throw new IllegalArgumentException("예약 가능 시간은 오전 9시부터 오후 10시까지입니다.");
        }

    }

    private void validateReservableTime(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate date = startTime.toLocalDate();
        boolean result = meetingRoomReservationRepository.existsOverlappingReservation(meetingRoom,
                date, startTime, endTime);

        if (!result) {
            throw new IllegalArgumentException("중복 예약이 발생했습니다.");
        }
    }

    //TODO: 쿼리 발생하는거 확인 후, FETCH JOIN 처리 등 고려 하기
    //해당 회의실이 캠퍼스에 속한지 확인한다.
    private void validateUserCampusMatchMeetingRoom(Long campusId, MeetingRoom meetingRoom) {

        if (!campusId.equals(meetingRoom.getCampusId())) {
            throw new IllegalArgumentException("해당 회의실은 캠퍼스내 회의실이 아닙니다.");
        }
    }
}
