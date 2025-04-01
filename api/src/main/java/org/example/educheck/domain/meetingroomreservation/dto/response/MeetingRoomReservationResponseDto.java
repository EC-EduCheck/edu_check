package org.example.educheck.domain.meetingroomreservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.meetingroomreservation.entity.MeetingRoomReservation;
import org.example.educheck.domain.meetingroomreservation.entity.ReservationStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MeetingRoomReservationResponseDto {
    private Long reservationId;
    private Long reserverId;
    private String reserverName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long meetingRoomId;
    private String meetingRoomName;
    private ReservationStatus status;

    public static MeetingRoomReservationResponseDto from(MeetingRoomReservation reservation) {
        return MeetingRoomReservationResponseDto.builder()
                .reservationId(reservation.getId())
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
