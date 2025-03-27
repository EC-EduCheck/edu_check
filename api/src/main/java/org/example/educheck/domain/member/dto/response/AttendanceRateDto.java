package org.example.educheck.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class AttendanceRateDto {

    private final Long memberId;
    private final Long studentId;
    private final Integer progressingCount;
    private final Integer totalAttendanceCount;
    private final Integer presentCount;
    private final Integer lateCount;
    private final Integer earlyLeaveCount;
    private final Integer adjustedAbsenceCount;
    private final Integer absenceCount;
    private final Integer totalAbsenceCount;
    private final Integer recognizedAttendanceCount;
    private final BigDecimal todayAttendanceRate;
    private final BigDecimal overallAttendanceRate;
    private final BigDecimal courseProgressRate;

    public static AttendanceRateDto from(AttendanceRateProjection projection) {
        return AttendanceRateDto.builder()
                .memberId(projection.getMemberId())
                .studentId(projection.getStudentId())
                .progressingCount(projection.getProgressingCount())
                .totalAttendanceCount(projection.getTotalAttendanceCount())
                .presentCount(projection.getPresentCount())
                .lateCount(projection.getLateCount())
                .earlyLeaveCount(projection.getEarlyLeaveCount())
                .adjustedAbsenceCount(projection.getAdjustedAbsenceCount())
                .absenceCount(projection.getAbsenceCount())
                .totalAbsenceCount(projection.getTotalAbsenceCount())
                .recognizedAttendanceCount(projection.getRecognizedAttendanceCount())
                .todayAttendanceRate(BigDecimal.valueOf(projection.getTodayAttendanceRate()))
                .overallAttendanceRate(BigDecimal.valueOf(projection.getOverallAttendanceRate()))
                .courseProgressRate(BigDecimal.valueOf(projection.getCourseProgressRate()))
                .build();
    }

}
