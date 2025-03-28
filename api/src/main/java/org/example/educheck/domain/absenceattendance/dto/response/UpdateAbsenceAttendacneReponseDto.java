package org.example.educheck.domain.absenceattendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UpdateAbsenceAttendacneReponseDto {

    private Long absenceAttendanceId;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private Character isApprove;
    private LocalDateTime approveDate;

    public static UpdateAbsenceAttendacneReponseDto from(AbsenceAttendance absenceAttendance) {
        return UpdateAbsenceAttendacneReponseDto.builder()
                .absenceAttendanceId(absenceAttendance.getId())
                .reason(absenceAttendance.getReason())
                .startDate(absenceAttendance.getStartTime())
                .endDate(absenceAttendance.getEndTime())
                .category(absenceAttendance.getCategory())
                .isApprove(absenceAttendance.getIsApprove())
                .approveDate(absenceAttendance.getApproveDate())
                .build();
    }


}
