package org.example.educheck.domain.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.dto.LoginRequestDto;
import org.example.educheck.domain.member.dto.LoginResponseDto;
import org.example.educheck.domain.member.dto.SignUpRequestDto;
import org.example.educheck.domain.member.service.AuthService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.example.educheck.global.common.exception.custom.LoginValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        authService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("회원가입 성공", "CREATED", null));
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
}
