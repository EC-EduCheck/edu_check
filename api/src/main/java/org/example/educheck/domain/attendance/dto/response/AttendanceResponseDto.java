package org.example.educheck.domain.attendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.attendance.entity.Attendance;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class AttendanceResponseDto {

    private final Long studentId;
    private final String studentName;
    private final String status;

    public static AttendanceResponseDto from(Attendance attendance) {
        return AttendanceResponseDto.builder()
                .studentId(attendance.getStudent().getId())
                .studentName(attendance.getStudent().getMember().getName())
                .status(attendance.getStatus().toString())
                .build();
    }
}
