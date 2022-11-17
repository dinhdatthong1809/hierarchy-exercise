package com.thong.employee.exception;

import com.thong.employee.exception.enums.ErrorCode;

public class ResourceNotFoundException extends BusinessException {

    protected ResourceNotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND.getMessageTemplate());
    }

    public static ResourceNotFoundException of() {
        return new ResourceNotFoundException();
    }

}
