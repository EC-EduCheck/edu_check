package org.example.educheck.domain.absenceattendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAbsenceAttendancesResponseDto {
    private Long userId;
    private Long courseId;
    private List<AbsenceAttendancesDto> absenceAttendances;

    private long totalPages;
    private boolean hasNext;
    private boolean hasPrevious;


    public static GetAbsenceAttendancesResponseDto from(Page<AbsenceAttendance> absenceAttendances, Member member) {

        return GetAbsenceAttendancesResponseDto.builder()
                .userId(member.getId())
                .courseId(absenceAttendances.getContent().getFirst().getCourse().getId())
                .absenceAttendances(absenceAttendances.getContent().stream().map(AbsenceAttendancesDto::from).toList())
                .totalPages(absenceAttendances.getTotalPages())
                .hasNext(absenceAttendances.hasNext())
                .hasPrevious(absenceAttendances.hasPrevious())
                .build();


    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AbsenceAttendancesDto {
        private Long studentId;
        private String studentName;
        private Boolean status;
        private boolean isAttached;
        private LocalDate startDate;
        private LocalDate endDate;


        public static AbsenceAttendancesDto from(AbsenceAttendance absenceAttendance) {

            return AbsenceAttendancesDto.builder()
                    .studentId(absenceAttendance.getStudent().getId())
                    .studentName(absenceAttendance.getStudent().getMember().getName())
                    .status(absenceAttendance.getIsApprove() == null ? null : (absenceAttendance.getIsApprove() == 'T'))
                    .isAttached(!absenceAttendance.getAbsenceAttendanceAttachmentFiles().isEmpty())
                    .startDate(absenceAttendance.getStartTime().toLocalDate())
                    .endDate(absenceAttendance.getEndTime().toLocalDate())
                    .build();
        }
    }
}
