package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import org.example.educheck.domain.attendance.entity.Attendance;

import java.time.LocalDate;

@Builder
public class StudentAttendanceResponseDto {

    private final LocalDate date;
    private final String status;

    public static StudentAttendanceResponseDto from(Attendance attendance) {
        return StudentAttendanceResponseDto.builder()
                .date(attendance.getLecture().getDate())
                .status(attendance.getAttendanceStatus().toString())
                .build();
    }
}
