package org.example.educheck.global.common.exception.custom.common;

import org.example.educheck.global.common.exception.ErrorCode;

public class ResourceOwnerMismatchException extends GlobalException {
    public ResourceOwnerMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceOwnerMismatchException(String message) {
        super(ErrorCode.MISMATCHED_RESOURCE, message);
    }

    public ResourceOwnerMismatchException() {
        super(ErrorCode.MISMATCHED_RESOURCE);
    }
}
