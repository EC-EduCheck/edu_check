package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import org.example.educheck.domain.attendance.entity.Attendance;

import java.time.LocalDateTime;

@Builder
public class StudentAttendanceResponseDto {

    private final LocalDateTime date;
    private final String status;

    public static StudentAttendanceResponseDto from(Attendance attendance) {
        return StudentAttendanceResponseDto.builder()
                .date(attendance.getLecture().getDate())
                .status(attendance.getStatus().toString())
                .build();
    }
}
