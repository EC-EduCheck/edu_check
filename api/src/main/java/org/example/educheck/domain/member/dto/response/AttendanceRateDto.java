package org.example.educheck.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class AttendanceRateDto {

    private final Long studentId;
    private final Long memberId;
    private final BigDecimal totalAttendance;
    private final BigDecimal attendanceCount;
    private final BigDecimal lateCount;
    private final BigDecimal earlyLeaveCount;
    private final BigDecimal absenceCount;
    private final BigDecimal adjustedAbsenceCount;
    private final BigDecimal attendanceRate;

    public static AttendanceRateDto from(AttendanceRateProjection projection) {
        return AttendanceRateDto.builder()
                .studentId(projection.getStudentId())
                .memberId(projection.getMemberId())
                .totalAttendance(projection.getTotalAttendance())
                .attendanceCount(projection.getAttendanceCount())
                .lateCount(projection.getLateCount())
                .earlyLeaveCount(projection.getEarlyLeaveCount())
                .absenceCount(projection.getAbsenceCount())
                .adjustedAbsenceCount(projection.getAdjustedAbsenceCount())
                .attendanceRate(projection.getAttendanceRate())
                .build();
    }

    @Override
    public String toString() {
        return "AttendanceRateDto{" +
                "studentId=" + studentId +
                ", memberId=" + memberId +
                ", totalAttendance=" + totalAttendance +
                ", attendanceCount=" + attendanceCount +
                ", lateCount=" + lateCount +
                ", earlyLeaveCount=" + earlyLeaveCount +
                ", absenceCount=" + absenceCount +
                ", adjustedAbsenceCount=" + adjustedAbsenceCount +
                ", attendanceRate=" + attendanceRate +
                '}';
    }
}
