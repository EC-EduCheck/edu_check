package org.example.educheck.domain.meetingroomreservation.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.meetingroom.entity.MeetingRoom;
import org.example.educheck.domain.meetingroom.repository.MeetingRoomRepository;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;
import org.example.educheck.domain.meetingroomreservation.dto.response.MeetingRoomReservationResponseDto;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.entity.ReservationStatus;
import org.example.educheck.domain.meetingroomreservation.repository.MeetingRoomReservationRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.global.common.exception.custom.common.ResourceMismatchException;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.example.educheck.global.common.exception.custom.common.ResourceOwnerMismatchException;
import org.example.educheck.global.common.exception.custom.reservation.ReservationConflictException;
import org.springframework.security.core.userdetails.UserDetails;
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

    private static void validateResourceOwner(Member authenticatedMember, MeetingRoomReservation meetingRoomReservation) {
        if (!authenticatedMember.getId().equals(meetingRoomReservation.getMember().getId())) {
            throw new ResourceOwnerMismatchException();
        }
    }

    @Transactional
    public void createReservation(UserDetails user, Long campusId, MeetingRoomReservationRequestDto requestDto) {

        Member findMember = getAuthenticatedMember(user);

        MeetingRoom meetingRoom = meetingRoomRepository.findById(requestDto.getMeetingRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 회의실이 존재하지 않습니다."));

        validateUserCampusMatchMeetingRoom(campusId, meetingRoom);

        validateReservationTime(requestDto.getStartTime(), requestDto.getEndTime());


        validateReservableTime(meetingRoom, requestDto.getStartTime(), requestDto.getEndTime());

        MeetingRoomReservation meetingRoomReservation = requestDto.toEntity(findMember, meetingRoom);
        meetingRoomReservationRepository.save(meetingRoomReservation);
    }

    private Member getAuthenticatedMember(UserDetails user) {
        return memberRepository.findByEmail(user.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 member입니다."));
    }

    private void validateReservationTime(LocalDateTime startTime, LocalDateTime endTime) {

        LocalTime startOfDay = LocalTime.of(9, 0);
        LocalTime endOfDay = LocalTime.of(22, 0);


        if (endTime.isBefore(startTime)) {
            throw new ReservationConflictException("시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }

        if (startTime.isAfter(endTime)) {
            throw new ReservationConflictException("종료 시간이 시작 시간보다 빠를 수 없습니다.");
        }

        if (ChronoUnit.MINUTES.between(startTime, endTime) < 15) {
            throw new ReservationConflictException("최소 예약 시간은 15분입니다.");
        }

        if (startTime.toLocalTime().isBefore(startOfDay) || endTime.toLocalTime().isAfter(endOfDay)) {
            throw new ReservationConflictException("예약 가능 시간은 오전 9시부터 오후 10시까지입니다.");
        }

    }

    private void validateReservableTime(MeetingRoom meetingRoom, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDate date = startTime.toLocalDate();
        boolean result = meetingRoomReservationRepository.existsOverlappingReservation(meetingRoom,
                date, startTime, endTime, ReservationStatus.ACTIVE);

        if (result) {
            throw new ReservationConflictException();
        }
    }

    private void validateUserCampusMatchMeetingRoom(Long campusId, MeetingRoom meetingRoom) {

        if (!campusId.equals(meetingRoom.getCampusId())) {
            throw new ResourceMismatchException("해당 회의실은 캠퍼스내 회의실이 아닙니다.");
        }
    }

    public MeetingRoomReservationResponseDto getMeetingRoomReservationById(Long reservationId) {
        MeetingRoomReservation meetingRoomReservation = meetingRoomReservationRepository.findByIdWithDetails(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 예약 내역이 존재하지 않습니다."));

        return MeetingRoomReservationResponseDto.from(meetingRoomReservation);


    }

    @Transactional
    public void cancelReservation(UserDetails userDetails, Long meetingRoomReservationId) {

        MeetingRoomReservation meetingRoomReservation = meetingRoomReservationRepository.findByStatusAndById(meetingRoomReservationId, ReservationStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("해당 예약 내역이 존재하지 않습니다."));

        Member authenticatedMember = getAuthenticatedMember(userDetails);
        validateResourceOwner(authenticatedMember, meetingRoomReservation);

        meetingRoomReservation.cancelReservation();
        meetingRoomReservationRepository.save(meetingRoomReservation);
    }
}
