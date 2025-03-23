package org.example.educheck.domain.meetingroomreservation.dto.response;

import java.time.LocalDateTime;


public interface MeetingRoomReservationsProjections {
    Long getMeetingRoomId();

    String getMeetingRoomName();

    Long getCampusId();

    Long getMeetingRoomReservationId();

    Long getMemberId();

    String getMemberName();

    String getReservationStatus();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();
}

