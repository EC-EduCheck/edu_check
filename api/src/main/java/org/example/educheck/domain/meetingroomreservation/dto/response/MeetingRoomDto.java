package org.example.educheck.domain.meetingroomreservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MeetingRoomDto {
    private Long meetingRoomId;
    private String meetingRoomName;
    private List<ReservationDto> reservations;
}
