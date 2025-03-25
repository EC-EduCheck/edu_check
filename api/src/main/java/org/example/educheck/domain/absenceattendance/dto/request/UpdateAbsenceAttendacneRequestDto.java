package org.example.educheck.domain.absenceattendance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.educheck.domain.absenceattendance.entity.AbsenceAttendance;

import java.time.LocalDate;

@Setter
@NoArgsConstructor
public class UpdateAbsenceAttendacneRequestDto {

    @NotEmpty
    private String resean;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotEmpty
    private String category;

    public void updateEntity(AbsenceAttendance entity) {
        entity.setReason(resean);
        entity.setStartTime(startDate);
        entity.setEndTime(endDate);
        entity.setCategory(category);
    }

}
