package org.example.educheck.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //0000 회원
    //1000 예약
    RESERVATION_CONFLICT(HttpStatus.CONFLICT, "2000", "해당 시간에는 이미 예약이 있습니다. 다른 시간을 선택해주세요."),

    //2000 

    //예시
    MEETINGROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "3000", "회의실을 찾을 수 없습니다."),

    //4000 5000 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "4000", "입력값이 올바르지 않습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "해당 리소스가 존재하지 않습니다."),
    MISMATCHED_RESOURCE(HttpStatus.BAD_REQUEST, "4008", "요청한 리소스가 일치하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "4010", "인증에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
