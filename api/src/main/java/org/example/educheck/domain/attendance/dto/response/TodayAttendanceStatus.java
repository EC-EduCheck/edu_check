package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;

import java.time.LocalDateTime;

@Getter
@Builder
public class TodayAttendanceStatus {

    private final Long studentId;
    private final String studentName;
    private final String status;
    private final LocalDateTime checkInTime;

    public static TodayAttendanceStatus from(StudentCourseAttendance attendance) {
        return TodayAttendanceStatus.builder()
                .studentId(attendance.getMemberId())
                .studentName(attendance.getMemberName())
                .status(attendance.getAttendanceStatus())
                .checkInTime(attendance.getCheckInTimestamp())
                .build();
    }
}
