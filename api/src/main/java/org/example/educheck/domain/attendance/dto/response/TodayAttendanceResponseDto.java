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
    private final TodayAttendanceSummary summary;
    private final List<TodayAttendanceStatus> students;

    public static TodayAttendanceResponseDto from(Long memberId, List<TodayAttendanceStatus> students, TodayAttendanceSummary summary) {
        return TodayAttendanceResponseDto.builder()
                .memberId(memberId)
                .today(LocalDateTime.now())
                .students(students)
                .summary(summary)
                .build();
    }
}
