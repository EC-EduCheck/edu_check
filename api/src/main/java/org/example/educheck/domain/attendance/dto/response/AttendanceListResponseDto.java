package org.example.educheck.domain.attendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.attendance.entity.Attendance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class AttendanceListResponseDto {

    private final Long userId;
    private final Long attence;
    private final Long early;
    private final Long late;
    private final Long absence;
    private final LocalDateTime today;
    private final List<AttendanceResponseDto> students;

    public static AttendanceListResponseDto from(
            Long userId,
            List<AttendanceResponseDto> responseDtos,
            long attence,
            long earlyLeave,
            long late,
            long absence

    ) {
        return AttendanceListResponseDto.builder()
                .userId(userId)
                .attence(attence)
                .early(earlyLeave)
                .late(late)
                .absence(absence)
                .today(LocalDateTime.now())
                .students(responseDtos)
                .build();
    }
}
