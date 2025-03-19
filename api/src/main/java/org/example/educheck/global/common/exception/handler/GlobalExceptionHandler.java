package org.example.educheck.global.common.exception.handler;

import org.example.educheck.global.common.dto.ApiResponse;
import org.example.educheck.global.common.exceptin.custom.LoginValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginValidationException(LoginValidationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse
                        .error("로그인에 실패했습니다.", "Unauthorized"));
    }
}
