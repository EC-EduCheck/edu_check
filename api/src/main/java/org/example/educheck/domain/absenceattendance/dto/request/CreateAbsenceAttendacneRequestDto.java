package org.example.educheck.domain.absenceattendance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.educheck.domain.attendance.entity.AttendanceStatus;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class CreateAbsenceAttendacneRequestDto {

    @NotEmpty
    private String reason;
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
    @NotEmpty
    private AttendanceStatus category;
}
