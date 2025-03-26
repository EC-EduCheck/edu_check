package org.example.educheck.domain.studentCourseAttendance.dto.response;


import lombok.Builder;
import lombok.Getter;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;

import java.time.LocalDate;

@Getter
@Builder
public class AttendanceRecordResponseDto {

    private LocalDate lectureDateTime;
    private String attendanceStatus;
    private Long lectureSession;

    public static AttendanceRecordResponseDto from(StudentCourseAttendance entity) {
        return AttendanceRecordResponseDto.builder()
                .lectureDateTime(entity.getLectureDate())
                .attendanceStatus(entity.getAttendanceStatus())
                .lectureSession(entity.getLectureSession())
                .build();
    }
}
