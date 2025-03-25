package org.example.educheck.domain.absenceattendance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@NoArgsConstructor
public class ModifyAbsenceAttendacneRequestDto {

    @NotEmpty
    private String resean;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotEmpty
    private String category;

    private List<String> deleteFileS3Keys;
}
