package org.example.educheck.domain.studentCourseAttendance.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class AttendanceRateStatisticsDto {
    private BigDecimal attendanceRateUntilToday;
    private BigDecimal totalAttendanceRate;
    private BigDecimal courseProgressRate;

    public static AttendanceRateStatisticsDto from(AttendanceRateStatisticsProjection projection) {
        return AttendanceRateStatisticsDto.builder()
                .attendanceRateUntilToday(projection.getAttendanceRateUntilToday())
                .totalAttendanceRate(projection.getTotalAttendanceRate())
                .courseProgressRate(projection.getCourseProgressRate())
                .build();
    }
}
