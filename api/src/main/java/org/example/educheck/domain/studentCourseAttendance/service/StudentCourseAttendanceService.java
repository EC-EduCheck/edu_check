package org.example.educheck.domain.studentCourseAttendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.attendance.dto.response.MyAttendanceRecordListResponseDto;
import org.example.educheck.domain.attendance.dto.response.MyAttendanceRecordResponseDto;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.domain.staffcourse.repository.StaffCourseRepository;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordListResponseDto;
import org.example.educheck.domain.studentCourseAttendance.dto.response.AttendanceRecordResponseDto;
import org.example.educheck.domain.studentCourseAttendance.entity.StudentCourseAttendance;
import org.example.educheck.domain.studentCourseAttendance.repository.StudentCourseAttendanceRepository;
import org.example.educheck.global.common.exception.custom.common.ForbiddenException;
import org.example.educheck.global.common.exception.custom.common.InvalidRequestException;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentCourseAttendanceService {

    private final StudentCourseAttendanceRepository studentCourseAttendanceRepository;
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;
    private final StaffCourseRepository staffCourseRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    private static void validateDates(Integer year, Integer month) {
        if (year != null && (year < 2000 || year > 2026)) {
            throw new InvalidRequestException("유효하지 않은 연도입니다.");
        }

        if (month != null && (month < 1 || month > 12)) {
            throw new InvalidRequestException("유효하지 않은 월입니다.");
        }

        if (month != null && year != null) {
            throw new InvalidRequestException("월 선택시 연도 선택은 필수입니다.");
        }
    }

    public AttendanceRecordListResponseDto getStudentAttendanceRecordLists(Member member, Long studentId, Long courseId, Pageable pageable) {

        Course validCourse = getValidCourse(courseId);
        validateStaffAuthorizationInCourse(member, courseId);
        Member validStudent = getValidStudentInCourse(studentId, courseId);

        Page<StudentCourseAttendance> attendanceRecordList = studentCourseAttendanceRepository.findByIdStudentIdAndIdCourseId(studentId, courseId, pageable);

        List<AttendanceRecordResponseDto> list = attendanceRecordList
                .stream()
                .map(AttendanceRecordResponseDto::from)
                .toList();

        return AttendanceRecordListResponseDto.builder()
                .requesterId(member.getId())
                .requesterName(member.getName())
                .studentId(studentId)
                .studentName(validStudent.getName())
                .studentPhoneNumber(validStudent.getPhoneNumber())
                .courseId(courseId)
                .courseName(validCourse.getName())
                .attendanceRecordList(list)
                .totalPages(attendanceRecordList.getTotalPages())
                .hasNext(attendanceRecordList.hasNext())
                .hasPrevious(attendanceRecordList.hasPrevious())
                .build();
    }

    public MyAttendanceRecordListResponseDto getMyAttendanceRecordLists(Member member, Long courseId, Integer year, Integer month, Pageable pageable) {
        Long studentId = member.getStudentId();

        Course validCourse = getValidCourse(courseId);
        validateStudentRegistrationInCourse(courseId, studentId);
        validateDates(year, month);

        Page<StudentCourseAttendance> attendanceList = studentCourseAttendanceRepository.findByStudentAndCourse(studentId, courseId, year, month, pageable);

        //respnseDTO로 말아서 전달하기
        List<MyAttendanceRecordResponseDto> attendances = attendanceList
                .stream()
                .map(MyAttendanceRecordResponseDto::from)
                .toList();

        return MyAttendanceRecordListResponseDto.builder()
                .attendanceList(attendances)
                .userId(member.getId())
                .name(member.getName())
                .courseName(validCourse.getName())
                .totalPages(attendanceList.getTotalPages())
                .hasNext(attendanceList.hasNext())
                .hasPrevious(attendanceList.hasPrevious())
                .build();

    }

    private void validateStudentRegistrationInCourse(Long courseId, Long studentId) {
        if (!registrationRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new ForbiddenException("출석부 조회는 수강 중이거나 수강했던 과정에 대해서만 가능합니다.");
        }
    }

    private Member getValidStudentInCourse(Long studentId, Long courseId) {
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 학생입니다."));

        if (!registrationRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new ForbiddenException("해당 교육 과정에 수강중인 학생이 아닙니다.");
        }

        return student;
    }

    private Course getValidCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 교육 과정이 존재하지 않습니다."));
    }

    private void validateStaffAuthorizationInCourse(Member member, Long courseId) {
        Long staffId = member.getStaff().getId();
        if (!staffCourseRepository.existsByStaffIdAndCourseId(staffId, courseId)) {
            throw new ForbiddenException("출석부 조회는 해당 과정의 관리자만 조회 가능합니다.");
        }
    }
}
