package org.example.educheck.domain.attendance.dto.response;

import lombok.Builder;
import org.example.educheck.domain.attendance.entity.Attendance;
import org.example.educheck.domain.member.student.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class StudentAttendanceListResponseDto {

    private final Long studentId;
    private final String studentName;
    private final String studentPhone;
    private final long attendanceRateByToday;
    private final long overallAttendanceRate;
    private final long courseProgressRate;
    private final List<StudentAttendanceResponseDto> attendanceHistories;

    public static StudentAttendanceListResponseDto from(
            Student student,
            List<Attendance> attendances,
            Long attendanceRateByToday,
            Long overallAttendanceRate,
            long courseProgressRate
            ) {
        return StudentAttendanceListResponseDto.builder()
                .studentId(student.getId())
                .studentName(student.getMember().getName())
                .studentPhone(student.getMember().getPhoneNumber())
                .attendanceRateByToday(attendanceRateByToday)
                .overallAttendanceRate(overallAttendanceRate)
                .courseProgressRate(courseProgressRate)
                .attendanceHistories(attendances.stream().map(StudentAttendanceResponseDto::from).collect(Collectors.toList()).reversed())
                .build();
    }
}
