package org.example.educheck.global.common.exception.custom.attendance;

import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.custom.common.GlobalException;

public class AttendanceAlreadyException extends GlobalException {
    public AttendanceAlreadyException() {
        super(ErrorCode.ALREADY_ATTENDANCE);
    }

}
