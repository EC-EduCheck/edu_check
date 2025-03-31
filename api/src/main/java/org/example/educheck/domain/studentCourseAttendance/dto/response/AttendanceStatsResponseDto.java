package org.example.educheck.domain.studentCourseAttendance.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AttendanceStatsResponseDto {
    private Long memberId;
    private String memberName;
    private Long courseId;
    private Integer lateCount;
    private Integer earlyLeaveCount;
    private Integer absentCount;
    private Double accumulatedAbsence;
    private Double attendanceRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public static AttendanceStatsResponseDto from(AttendanceStatsProjection projection, double attendanceRate) {
        return AttendanceStatsResponseDto.builder()
                .memberId(projection.getMemberId())
                .memberName(projection.getMemberName())
                .courseId(projection.getCourseId())
                .lateCount(projection.getLateCount())
                .earlyLeaveCount(projection.getEarlyLeaveCount())
                .absentCount(projection.getAbsentCount())
                .accumulatedAbsence(projection.getAccumulatedAbsence())
                .startDate(projection.getStartDate())
                .endDate(projection.getEndDate())
                .attendanceRate(attendanceRate)
                .build();
    }

}
