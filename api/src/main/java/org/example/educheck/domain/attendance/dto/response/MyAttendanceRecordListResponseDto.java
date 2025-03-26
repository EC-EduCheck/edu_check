package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyAttendanceRecordListResponseDto {

    private Long userId;
    private String name;
    private String courseName;
    private List<MyAttendanceRecordResponseDto> attendanceList;

    private long totalPages;
    private boolean hasNext;
    private boolean hasPrevious;


}
