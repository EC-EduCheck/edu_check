package org.example.educheck.domain.studentCourseAttendance.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttendanceStatsResponseDto {
    private Long studentId;
    private Long memberId;
    private String memberName;
    private Long courseId;
    private Integer lateCount;
    private Integer earlyLeaveCount;
    private Integer absentCount;
    private Double accumulatedAbsence;
    private Double attendanceRate;

    public static AttendanceStatsResponseDto from(AttendanceStatsProjection projection) {
        return AttendanceStatsResponseDto.builder()
                .studentId(projection.getStudentId())
                .memberId(projection.getMemberId())
                .memberName(projection.getMemberName())
                .courseId(projection.getCourseId())
                .lateCount(projection.getLateCount())
                .earlyLeaveCount(projection.getEarlyLeaveCount())
                .absentCount(projection.getAbsentCount())
                .accumulatedAbsence(projection.getAccumulatedAbsence())
                .attendanceRate(projection.getAttendanceRate())
                .build();
    }
}
