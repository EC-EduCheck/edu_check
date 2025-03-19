package org.example.educheck.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.educheck.domain.member.dto.SignUpRequestDto;
import org.example.educheck.domain.member.service.AuthService;
import org.example.educheck.global.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> singUp(@RequestBody @Valid SignUpRequestDto requestDto) {
        authService.signUp(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("회원가입 성공", "CREATED", null));
    }
}
