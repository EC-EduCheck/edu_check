package org.example.educheck.domain.member.staff.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.staff.dto.request.UpdateStudentRegistrationStatusRequestDto;
import org.example.educheck.domain.member.staff.dto.response.GetStudentsResponseDto;
import org.example.educheck.domain.member.staff.dto.response.UpdateStudentRegistrationStatusResponseDto;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.student.dto.response.StudentInfoResponseDto;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.domain.staffcourse.repository.StaffCourseRepository;
import org.example.educheck.global.common.exception.custom.common.ForbiddenException;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffService {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;
    private final StaffCourseRepository staffCourseRepository;

    @Transactional
    public UpdateStudentRegistrationStatusResponseDto updateStudentRegistrationStatus(Member member, Long courseId, Long studentId, UpdateStudentRegistrationStatusRequestDto requestDto) {

        Staff staff = staffRepository.findByMember(member)
                .orElseThrow(() -> new ResourceNotFoundException("관리자 정보를 찾을 수 없습니다."));

        validateStaffManageCourse(staff.getId(), courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 교육 과정을 찾을 수 없습니다."));

        Member studentMember = memberRepository.findByStudent_Id(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생을 찾을 수 없습니다."));
        Student student = studentMember.getStudent();

        Registration registration = registrationRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생의 수강 정보를 찾을 수 없습니다."));

        registration.setStatus(requestDto.getStatus());

        return UpdateStudentRegistrationStatusResponseDto.from(
                studentId,
                courseId,
                studentMember.getName(),
                course.getName(),
                requestDto.getStatus()
        );
    }

    private void validateStaffManageCourse(Long staffId, Long courseId) {
        boolean isCorrect = staffCourseRepository.existsByStaffIdAndCourseId(staffId, courseId);
        if (!isCorrect) {
            throw new ForbiddenException("관리자가 속해있는 교육 과정에 대해서만 관리 가능합니다.");
        }
    }

    public GetStudentsResponseDto getStudentsByCourse(Member member, Long courseId) {
        Staff staff = staffRepository.findByMember(member)
                .orElseThrow(() -> new ResourceNotFoundException("관리자 정보를 찾을 수 없습니다."));

        validateStaffManageCourse(staff.getId(), courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 교육 과정을 찾을 수 없습니다."));

        List<Registration> registrations = registrationRepository.findByCourseId(courseId);

        List<StudentInfoResponseDto> students = registrations.stream()
                .map(registration -> {
                    Student student = registration.getStudent();
                    Member studentInfo = student.getMember();

                    return StudentInfoResponseDto.builder()
                            .studentId(student.getId())
                            .studentName(studentInfo.getName())
                            .studentEmail(studentInfo.getEmail())
                            .studentPhoneNumber(studentInfo.getPhoneNumber())
                            .registrationStatus(registration.getStatus()) // 수강 상태
                            .build();
                })
                .collect(Collectors.toList());


        return GetStudentsResponseDto.from(
                courseId,
                course.getName(),
                students
        );

    }
}
