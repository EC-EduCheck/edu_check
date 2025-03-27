package org.example.educheck.domain.absenceattendance.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonPropertyOrder({"absenceAttendanceId", "startDate", "endDate", "isApprove", "category"})
public class MyAbsenceAttendanceResponseDto {

    private Long absenceAttendanceId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Character isApprove;
    private String category;

    public MyAbsenceAttendanceResponseDto(Long id, LocalDate startTime, LocalDate endTime,
                                          Character isApprove, String category) {
        this.absenceAttendanceId = id;
        this.startDate = startTime;
        this.endDate = endTime;
        this.isApprove = isApprove;
        this.category = category;
    }

}
