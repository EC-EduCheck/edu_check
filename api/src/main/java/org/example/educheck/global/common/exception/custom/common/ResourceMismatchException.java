package org.example.educheck.global.common.exception.custom.common;


import org.example.educheck.global.common.exception.ErrorCode;

public class ResourceMismatchException extends GlobalException {
    public ResourceMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceMismatchException(String customMessage) {
        super(ErrorCode.MISMATCHED_RESOURCE, customMessage);
    }

    public ResourceMismatchException() {
        super(ErrorCode.MISMATCHED_RESOURCE, ErrorCode.MISMATCHED_RESOURCE.getMessage());
    }
}
