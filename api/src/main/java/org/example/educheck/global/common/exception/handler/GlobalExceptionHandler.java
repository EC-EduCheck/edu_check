package org.example.educheck.global.common.exception.handler;

import org.example.educheck.global.common.dto.ApiResponse;
import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.GlobalException;
import org.example.educheck.global.common.exception.custom.LoginValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginValidationException(LoginValidationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse
                        .error("로그인에 실패했습니다.", "Unauthorized"));
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiResponse<Object>> exceptionHandler(GlobalException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(ApiResponse
                        .error(ex.getErrorCode().getMessage(), ex.getErrorCode().getCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> methodArgumentNotValidHandler(
            MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String errorMessage = errorMessages.isEmpty()
                ? ErrorCode.INVALID_INPUT.getMessage()
                : errorMessages.getFirst();

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.error(errorMessage,
                        ErrorCode.INVALID_INPUT.getCode()));
    }
}
