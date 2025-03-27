package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TodayAttendanceResponseDto {

    private final Long memberId;
    private final LocalDateTime today;
    private final List<TodayAttendanceStatus> students;
    private final Long totalAttendance;
    private final Long totalEarlyLeave;
    private final Long totalLate;
    private final Long totalAbsence;


    public static TodayAttendanceResponseDto from(Long memberId, List<TodayAttendanceStatus> students, Long totalAttendance,
                                                  Long totalEarly, Long totalLate, Long totalAbsence) {
        return TodayAttendanceResponseDto.builder()
                .memberId(memberId)
                .today(LocalDateTime.now())
                .students(students)
                .totalAttendance(totalAttendance)
                .totalEarlyLeave(totalEarly)
                .totalLate(totalLate)
                .totalAbsence(totalAbsence)
                .build();
    }
}
