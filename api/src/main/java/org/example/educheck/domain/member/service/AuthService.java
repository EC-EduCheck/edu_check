package org.example.educheck.domain.member.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.course.entity.Course;
import org.example.educheck.domain.course.repository.CourseRepository;
import org.example.educheck.domain.member.dto.EmailCheckResponseDto;
import org.example.educheck.domain.member.dto.LoginRequestDto;
import org.example.educheck.domain.member.dto.LoginResponseDto;
import org.example.educheck.domain.member.dto.SignUpRequestDto;
import org.example.educheck.domain.member.entity.Member;
import org.example.educheck.domain.member.repository.MemberRepository;
import org.example.educheck.domain.member.student.entity.Status;
import org.example.educheck.domain.member.student.entity.Student;
import org.example.educheck.domain.member.student.repository.StudentRepository;
import org.example.educheck.domain.registration.entity.Registration;
import org.example.educheck.domain.registration.repository.RegistrationRepository;
import org.example.educheck.global.common.exception.custom.LoginValidationException;
import org.example.educheck.global.security.CustomUserDetailsService;
import org.example.educheck.global.security.jwt.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    private final CustomUserDetailsService customUserDetailsService;

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
                .courseParticipationStatus('T')
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

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(), requestDto.getPassword()
                )
        );


        String accessToken = jwtTokenUtil.createAccessToken(authenticate);
        response.setHeader("Authorization", "Bearer " + accessToken);

        String refreshToken = jwtTokenUtil.createRefreshToken(authenticate);
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh");
        response.addCookie(cookie);


        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다.")
                );

        LoginResponseDto loginResponseDto = roleBasedLogin(member);


        member.setLastLoginDate(LocalDateTime.now());

        return loginResponseDto;
    }

    private LoginResponseDto roleBasedLogin(Member member) {

        return switch (member.getRole()) {
            case STUDENT -> memberRepository.studentLoginResponseDtoByMemberId(member.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원이다."));
            case MIDDLE_ADMIN -> memberRepository.staffLoginResponseDtoByMemberId(member.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원이다."));
            default -> throw new IllegalArgumentException("존재하지 않는 회원이다.");
        };
    }

    public EmailCheckResponseDto emailCheck(String email) {

        return new EmailCheckResponseDto(!memberRepository.existsByEmail(email));
    }


    public LoginResponseDto refreshTokenRotation(HttpServletResponse response, String email) {

        UserDetails userDetails = customUserDetailsService.loadUserByEmail(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        String accessToken = jwtTokenUtil.createAccessToken(authentication);
        response.setHeader("Authorization", "Bearer " + accessToken);

        String refreshToken = jwtTokenUtil.createRefreshToken(authentication);
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh");
        response.addCookie(cookie);

        Member member = memberRepository.findByEmail(email).orElseThrow(
                LoginValidationException::new);

        return roleBasedLogin(member);


    }

    public ResponseEntity<Void> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth/refresh");
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
