package org.example.educheck.global.common.exception.handler;

import org.example.educheck.global.common.dto.ApiResponse;
import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.LoginValidationException;
import org.example.educheck.global.common.exception.custom.common.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginValidationException(LoginValidationException ex) {

        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(ApiResponse
                        .error(ex.getErrorCode().getMessage(), ex.getErrorCode().getCode()));
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

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingRequestCookieException(MissingRequestCookieException ex) {

        return ResponseEntity
                .status(ErrorCode.UNAUTHORIZED.getStatus())
                .body(ApiResponse.error(ErrorCode.UNAUTHORIZED.getMessage(),
                        ErrorCode.UNAUTHORIZED.getCode()));

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT.getMessage(),
                        ErrorCode.INVALID_INPUT.getCode()));
    }
}
