package org.example.educheck.domain.absenceattendance.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAbsenceAttendacneRequestDto {

    @NotEmpty
    private String reason;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotEmpty
    private AttendanceStatus category;

    public void updateEntity(AbsenceAttendance entity) {
        entity.setReason(reason);
        entity.setStartTime(startDate);
        entity.setEndTime(endDate);
        entity.setCategory(category);
    }

    @AssertTrue(message = "시작일은 종료일 이후일 수 없습니다.")
    public boolean isValidDateRange() {
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }

}
