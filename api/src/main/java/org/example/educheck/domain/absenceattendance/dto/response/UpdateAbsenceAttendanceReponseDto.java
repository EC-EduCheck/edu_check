package org.example.educheck.domain.absenceattendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UpdateAbsenceAttendanceReponseDto {

    private Long absenceAttendanceId;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private AttendanceStatus category;
    private Character isApprove;
    private LocalDateTime approveDateTime;

    public static UpdateAbsenceAttendanceReponseDto from(AbsenceAttendance absenceAttendance) {
        return UpdateAbsenceAttendanceReponseDto.builder()
                .absenceAttendanceId(absenceAttendance.getId())
                .reason(absenceAttendance.getReason())
                .startDate(absenceAttendance.getStartTime())
                .endDate(absenceAttendance.getEndTime())
                .category(absenceAttendance.getCategory())
                .isApprove(absenceAttendance.getIsApprove())
                .approveDateTime(absenceAttendance.getApproveDateTime())
                .build();
    }


}
