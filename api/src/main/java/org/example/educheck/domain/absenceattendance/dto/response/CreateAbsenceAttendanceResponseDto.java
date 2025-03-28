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
public class CreateAbsenceAttendanceResponseDto {

    private Long absenceAttendanceId;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    //    생성 시 밑에 2개는 항시 의미 없는 데이터인데 줘야 할까요?
    private Character isApprove;
    private LocalDateTime approveDate;

    public static CreateAbsenceAttendanceResponseDto from(AbsenceAttendance absenceAttendance) {
        return CreateAbsenceAttendanceResponseDto.builder()
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
