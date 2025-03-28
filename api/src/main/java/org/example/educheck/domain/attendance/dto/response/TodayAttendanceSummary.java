package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodayAttendanceSummary {

    private final Long totalAttendance;
    private final Long totalEarlyLeave;
    private final Long totalLate;
    private final Long totalAbsence;

    public static TodayAttendanceSummary from(Long totalAttendance,
                                              Long totalEarlyLeave, Long totalLate, Long totalAbsence) {

        return TodayAttendanceSummary.builder()
                .totalAttendance(totalAttendance)
                .totalEarlyLeave(totalEarlyLeave)
                .totalLate(totalLate)
                .totalAbsence(totalAbsence)
                .build();
    }
}
