package org.example.educheck.domain.attendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;

@Getter
@AllArgsConstructor
public class AttendanceStatusResponseDto {
    private AttendanceStatus attendanceStatus;
}
