package org.example.educheck.domain.absenceattendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.absenceattendanceattachmentfile.dto.response.AttachmentFileReposeDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AbsenceAttendanceResponseDto {

    //유고결석신청번호
    private Long absenceAttendanceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private String category;
    private String reason;
    private LocalDateTime approveDate;
    private Character isApprove;
    private List<AttachmentFileReposeDto> files;

    public static AbsenceAttendanceResponseDto from(AbsenceAttendance absenceAttendance, List<AttachmentFileReposeDto> files) {
        return AbsenceAttendanceResponseDto.builder()
                .absenceAttendanceId(absenceAttendance.getId())
                .startDate(absenceAttendance.getStartTime())
                .endDate(absenceAttendance.getEndTime())
                .createdAt(absenceAttendance.getCreatedAt())
                .category(absenceAttendance.getCategory())
                .reason(absenceAttendance.getReason())
                .approveDate(absenceAttendance.getApproveDate())
                .isApprove(absenceAttendance.getIsApprove())
                .files(files)
                .build();
    }
}
