package org.example.educheck.domain.absenceattendance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessAbsenceAttendanceRequestDto {
    @JsonProperty("isApprove")
    private boolean isApprove;
}
