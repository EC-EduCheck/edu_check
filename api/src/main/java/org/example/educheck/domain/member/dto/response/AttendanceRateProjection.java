package org.example.educheck.domain.member.dto.response;

public interface AttendanceRateProjection {

    Long getMemberId();

    Long getStudentId();

    Integer getProgressingCount();

    Integer getTotalAttendanceCount();

    Integer getPresentCount();

    Integer getLateCount();

    Integer getEarlyLeaveCount();

    Integer getAdjustedAbsenceCount();

    Integer getAbsenceCount();

    Integer getTotalAbsenceCount();

    Integer getRecognizedAttendanceCount();

    Double getTodayAttendanceRate();

    Double getOverallAttendanceRate();

    Double getCourseProgressRate();
}
