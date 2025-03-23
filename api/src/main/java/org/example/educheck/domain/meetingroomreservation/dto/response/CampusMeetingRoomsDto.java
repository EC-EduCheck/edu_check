package org.example.educheck.domain.meetingroomreservation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CampusMeetingRoomsDto {

    private Long campusId;
    private LocalDate date;
    private List<MeetingRoomDto> meetingRooms;
}
