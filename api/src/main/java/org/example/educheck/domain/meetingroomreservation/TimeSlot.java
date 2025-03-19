package org.example.educheck.domain.meetingroomreservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.meetingroomreservation.dto.request.MeetingRoomReservationRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    public static TimeSlot from(MeetingRoomReservationRequestDto requestDto) {
        return TimeSlot.builder()
                .startTime(requestDto.getStartTime().toLocalTime())
                .endTime(requestDto.getEndTime().toLocalTime())
                .date(requestDto.getStartTime().toLocalDate())
                .build();
    }

}
