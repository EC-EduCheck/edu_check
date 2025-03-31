package org.example.educheck.domain.studentCourseAttendance.dto.response;

import java.time.LocalDate;

public interface AttendanceStatsProjection {
    Long getStudentId();

    Long getMemberId();

    String getMemberName();

    Long getCourseId();

    Integer getProgressCount();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getAttendanceCount();

    Integer getLateCount();

    Integer getEarlyLeaveCount();

    Integer getAbsentCount();

    Double getAccumulatedAbsence();


}
