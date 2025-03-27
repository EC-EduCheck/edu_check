package org.example.educheck.domain.meetingroomreservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationDto {

    private Long meetingRoomReservationId;
    private Long reserverId;
    private String reserverName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
