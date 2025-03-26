package org.example.educheck.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.response.MyAttendanceListResponseDto;
import org.example.educheck.domain.attendance.dto.response.MyAttendanceResponseDto;
import org.example.educheck.domain.attendance.dto.response.StudentAttendanceListResponseDto;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;
import org.example.educheck.domain.studentCourseAttendance.repository.StudentCourseAttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffAttendanceService {

    private final StudentCourseAttendanceRepository studentCourseAttendanceRepository;

    public StudentAttendanceListResponseDto getStudentAttendancList(Member member, Long courseId, Long studentId) {

        List<StudentCourseAttendance> attendanceList = studentCourseAttendanceRepository.findByIdStudentIdAndIdCourseId(studentId, courseId);

    }

    public MyAttendanceListResponseDto getMyAttendances(Member member, Long courseId, Integer year, Integer month) {

        Long studentId = member.getStudentId();

        validateExistCourse(courseId);
        validateStudentRegistrationInCourse(courseId, studentId);
        validateDates(year, month);

        Course course = getCourse(courseId);

        List<StudentCourseAttendance> attendanceList = studentCourseAttendanceRepository.findByIdStudentIdAndIdCourseId(studentId, courseId);

        //respnseDTO로 말아서 전달하기
        List<MyAttendanceResponseDto> attendances = attendanceList
                .stream()
                .map(MyAttendanceResponseDto::from)
                .toList();

        return MyAttendanceListResponseDto.builder()
                .attendanceList(attendances)
                .userId(member.getId())
                .name(member.getName())
                .courseName(course.getName())
                .build();

    }
}
