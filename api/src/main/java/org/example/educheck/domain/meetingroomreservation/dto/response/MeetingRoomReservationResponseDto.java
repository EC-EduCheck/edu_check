package org.example.educheck.domain.meetingroomreservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.entity.ReservationStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class MeetingRoomReservationResponseDto {
    private Long reserverId;
    private String reserverName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long meetingRoomId;
    private String meetingRoomName;
    private ReservationStatus status;

    public static MeetingRoomReservationResponseDto from(MeetingRoomReservation reservation) {
        return MeetingRoomReservationResponseDto.builder()
                .reserverId(reservation.getMember().getId())
                .reserverName(reservation.getMember().getName())
                .startDateTime(reservation.getStartTime())
                .endDateTime(reservation.getEndTime())
                .meetingRoomId(reservation.getMeetingRoom().getId())
                .meetingRoomName(reservation.getMeetingRoom().getName())
                .status(reservation.getStatus())
                .build();
    }

}
