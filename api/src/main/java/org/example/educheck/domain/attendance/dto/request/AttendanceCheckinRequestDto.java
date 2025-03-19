package org.example.educheck.domain.attendance.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCheckinRequestDto {

    @Min(value = -180, message = "경도는 -180도부터 180도 사이여야 합니다")
    @Max(value = 180, message = "경도는 -180도부터 180도 사이여야 합니다")
    private double longitude;

    @Min(value = -90, message = "위도는 -90도부터 90도 사이여야 합니다")
    @Max(value = 90, message = "위도는 -90도부터 90도 사이여야 합니다")
    private double latitude;
}
