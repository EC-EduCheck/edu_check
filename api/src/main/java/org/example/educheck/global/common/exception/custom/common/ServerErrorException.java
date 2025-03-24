package org.example.educheck.global.common.exception.custom.common;

import org.example.educheck.global.common.exception.ErrorCode;

public class ServerErrorException extends GlobalException {
    public ServerErrorException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ServerErrorException() {
        super(ErrorCode.SERVER_ERROR, ErrorCode.SERVER_ERROR.getMessage());
    }

    public ServerErrorException(String message) {
        super(ErrorCode.SERVER_ERROR, message);
    }
}
