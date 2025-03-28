package org.example.educheck.domain.member.staff.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.staff.dto.request.UpdateStudentRegistrationStatusRequestDto;
import org.example.educheck.domain.member.staff.dto.response.UpdateStudentRegistrationStatusResponseDto;
import org.example.educheck.domain.member.staff.entity.Staff;
import org.example.educheck.domain.member.repository.StaffRepository;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.domain.staffcourse.repository.StaffCourseRepository;
import org.example.educheck.global.common.exception.custom.common.ForbiddenException;
import org.example.educheck.global.common.exception.custom.common.InvalidRequestException;
import org.example.educheck.global.common.exception.custom.common.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 1. 관리자 권한 검증
        Staff staff = staffRepository.findByMember(member)
                .orElseThrow(() -> new ResourceNotFoundException("관리자 정보를 찾을 수 없습니다."));

        // 2. 관리자가 해당 코스를 관리하는지 확인
        validateStaffManageCourse(staff.getId(), courseId);

        // 3. 코스 정보 조회
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 교육 과정을 찾을 수 없습니다."));

        // 4. 학생 정보 조회
        Member studentMember = memberRepository.findByStudent_Id(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생을 찾을 수 없습니다."));
        Student student = studentMember.getStudent();

        // 5. 수강 정보 조회
        Registration registration = registrationRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생의 수강 정보를 찾을 수 없습니다."));

        // 6. 수강 상태 업데이트
        registration.setStatus(requestDto.getStatus());

        // 7. 응답 DTO 생성 및 반환
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
            throw new ForbiddenException("관리자가 관리하는 교육 과정에 대해서만 수정 가능합니다.");
        }
    }
}
