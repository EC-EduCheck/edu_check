package org.example.educheck.domain.member.dto.response;

import java.math.BigDecimal;

public interface AttendanceRateProjection {
    Long getStudentId();

    Long getMemberId();

    BigDecimal getTotalAttendance();

    BigDecimal getAttendanceCount();

    BigDecimal getLateCount();

    BigDecimal getEarlyLeaveCount();

    BigDecimal getAbsenceCount();

    BigDecimal getAdjustedAbsenceCount();

    BigDecimal getAttendanceRate();
}
