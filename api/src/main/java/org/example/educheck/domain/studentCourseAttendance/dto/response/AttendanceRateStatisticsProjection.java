package org.example.educheck.domain.studentCourseAttendance.dto.response;

import java.math.BigDecimal;

public interface AttendanceRateStatisticsProjection {
    Long getStudentId();

    Long getMemberId();

    String getMemberName();

    Long getCourseId();

    String getCourseName();

    BigDecimal getAttendanceRateUntilToday();

    BigDecimal getTotalAttendanceRate();

    BigDecimal getCourseProgressRate();
}
