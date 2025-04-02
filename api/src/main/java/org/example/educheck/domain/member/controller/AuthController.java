package org.example.educheck.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.dto.EmailCheckResponseDto;
import org.example.educheck.domain.member.dto.LoginRequestDto;
import org.example.educheck.domain.member.dto.LoginResponseDto;
import org.example.educheck.domain.member.dto.SignUpRequestDto;
import org.example.educheck.domain.member.dto.response.RegisteredMemberResponseDto;
import org.example.educheck.domain.member.service.AuthService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.example.educheck.global.common.exception.custom.LoginValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<RegisteredMemberResponseDto>> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("회원가입 성공", "CREATED", authService.signUp(requestDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody @Valid LoginRequestDto requestDto,
                                                               HttpServletResponse response) {

        try {
            return ResponseEntity.ok(
                    ApiResponse.ok("로그인 성공", "OK",
                            authService.login(requestDto, response))

            );
        } catch (BadCredentialsException ex) {
            throw new LoginValidationException();
        }
    }

    @GetMapping("/email-check")
    public ResponseEntity<ApiResponse<EmailCheckResponseDto>> emailCheck(@RequestParam("email") String email) {

        EmailCheckResponseDto emailCheckResult = authService.emailCheck(email);

        if (emailCheckResult.isAvailable()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.ok("사용 가능한 이메일입니다. ", "OK", emailCheckResult)
            );
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.ok("이미 사용 중인 이메일입니다.", "CONFLICT", emailCheckResult)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshTokenRotation(
            @CookieValue(value = "refresh_token", required = true) String refreshToken,
            HttpServletResponse response) {


        LoginResponseDto loginResponseDto = authService.refreshTokenRotation(response, refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("토큰 재발급 성공", "OK", loginResponseDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {

        return authService.logout(request, response);
    }
}
