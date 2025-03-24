package org.example.educheck.domain.absenceattendance.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
public class CreateAbsenceAttendacneRequestDto {

    @NotEmpty
    private String resean;
    @NotEmpty
    private LocalDate startDate;
    @NotEmpty
    private LocalDate endDate;
    @NotEmpty
    private String category;
    private MultipartFile file;
}
