package org.example.educheck.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.dto.SignUpRequestDto;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.student.entity.Status;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.member.student.repository.StudentRepository;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.global.security.jwt.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;
    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public Member signUp(SignUpRequestDto requestDto) {

        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String formattedBirthDate = requestDto.getFormattedBirthDate();
        String encodedPassword = passwordEncoder.encode(formattedBirthDate);

        Member member = memberRepository.save(requestDto.toEntity(encodedPassword));
        Student student = Student.builder()
                .member(member)
                .status(Status.NORMAL)
                .courseParticipationStatus('Y')
                .build();
        Student savedStudent = studentRepository.save(student);
        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과정입니다."));

        Registration registration = Registration.builder()
                .student(savedStudent)
                .course(course)
                .status(org.example.educheck.domain.registration.entity.Status.PREVIOUS)
                .build();
        registrationRepository.save(registration);


        return member;
    }

}
