package org.example.educheck.domain.studentCourseAttendance.dto.response;

public interface AttendanceStatsProjection {
    Long getStudentId();

    Long getMemberId();

    String getMemberName();

    Long getCourseId();

    Integer getProgressCount();

    Integer getAttendanceCount();

    Integer getLateCount();

    Integer getEarlyLeaveCount();

    Integer getAbsentCount();

    Double getAccumulatedAbsence();


}
