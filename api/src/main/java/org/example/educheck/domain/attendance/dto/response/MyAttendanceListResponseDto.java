package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyAttendanceListResponseDto {

    private Long userId;
    private String name;
    private String courseName;
    private List<MyAttendanceResponseDto> attendanceList;


}
