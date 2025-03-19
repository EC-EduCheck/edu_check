package org.example.educheck.global.common.exception.custom;

import org.example.educheck.global.common.exception.ErrorCode;
import org.example.educheck.global.common.exception.GlobalException;

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
