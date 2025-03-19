package org.example.educheck.global.common.exception.custom;

import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.GlobalException;

public class ReservationConflictException extends GlobalException {
    public ReservationConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReservationConflictException() {
        super(ErrorCode.RESERVATION_CONFLICT, ErrorCode.RESERVATION_CONFLICT.getMessage());
    }
}
