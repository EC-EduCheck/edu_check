package org.example.educheck.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //0000 회원
    //1000 예약
    RESERVATION_CONFLICT(HttpStatus.CONFLICT, "1000", "해당 시간에는 이미 예약이 있습니다. 다른 시간을 선택해주세요."),

    //2000 출석
    ALREADY_ATTENDANCE(HttpStatus.BAD_REQUEST, "2000", "이미 출석 처리되었습니다."),

    //예시
    MEETINGROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "3000", "회의실을 찾을 수 없습니다."),

    //4000 5000 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "4000", "입력값이 올바르지 않습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "해당 리소스가 존재하지 않습니다."),
    MISMATCHED_RESOURCE(HttpStatus.BAD_REQUEST, "4008", "요청한 리소스가 일치하지 않습니다."),
    NOT_OWNER(HttpStatus.FORBIDDEN, "4003", "본인만 수정 및 삭제 가능합니다."),
    MAX_UPLOAD_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, "4009", "파일 크기가 5MB를 초과할 수 없습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4010", "인증에 실패했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "4030", "접근 권한이 없습니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "서버 내부에서 오류가 발생했습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "파일 업로드 중 오류가 발생했습니다."),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5002", "파일 삭제 중 오류가 발생했습니다");


    private final HttpStatus status;
    private final String code;
    private final String message;

}
