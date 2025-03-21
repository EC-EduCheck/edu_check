package org.example.educheck.global.common.exception.custom.reservation;

import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.common.GlobalException;

public class ReservationConflictException extends GlobalException {
    public ReservationConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReservationConflictException() {
        super(ErrorCode.RESERVATION_CONFLICT, ErrorCode.RESERVATION_CONFLICT.getMessage());
    }
}
