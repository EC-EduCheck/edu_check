package org.example.educheck.global.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.educheck.global.common.dto.ApiResponse;
import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.LoginValidationException;
import org.example.educheck.global.common.exception.custom.auth.EmailNotFoundException;
import org.example.educheck.global.common.exception.custom.common.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
                        .error(ex.getMessage(), ex.getErrorCode().getCode()));
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getStatus())
                .body(ApiResponse.error(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getMessage(),
                        ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getCode()));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT.getMessage(),
                        ErrorCode.INVALID_INPUT.getCode()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(String.format("잘못된 요청: '%s' 값은 %s 타입이어야 합니다.", ex.getName(), ex.getRequiredType().getSimpleName()), ErrorCode.INVALID_INPUT.getCode())
                );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity
                .status(ErrorCode.API_ENDPOINT_NOT_FOUND.getStatus())
                .body(ApiResponse.error(ErrorCode.API_ENDPOINT_NOT_FOUND.getMessage(),
                        ErrorCode.API_ENDPOINT_NOT_FOUND.getCode()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
        return ResponseEntity
                .status(ErrorCode.RESOURCE_NOT_FOUND.getStatus())
                .body(ApiResponse.error(ErrorCode.RESOURCE_NOT_FOUND.getMessage(),
                        ErrorCode.RESOURCE_NOT_FOUND.getCode()));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailNotFoundException(EmailNotFoundException ex) {
        return ResponseEntity
                .status(ErrorCode.EMAIL_NOT_FOUND.getStatus())
                .body(ApiResponse.error(ErrorCode.EMAIL_NOT_FOUND.getMessage(),
                        ErrorCode.EMAIL_NOT_FOUND.getCode()));
    }
}
