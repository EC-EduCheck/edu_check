package org.example.educheck.global.common.exception.custom.common;

import org.example.educheck.global.common.exception.ErrorCode;

public class ResourceNotFoundException extends GlobalException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException(String customMessage) {
        super(ErrorCode.RESOURCE_NOT_FOUND, customMessage);
    }

    public ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }

}
