package org.example.educheck.domain.meetingroomreservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MeetingRoomReservationResponseDto {
    private String reserverId;
    private String reserverName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long meetingRoomId;
    private String meetingRoomName;

}
