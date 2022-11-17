package com.thong.employee.exception;

import com.thong.employee.exception.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static BusinessException of(ErrorCode errorCode, Object... parameters) {
        return new BusinessException(errorCode, String.format(errorCode.getMessageTemplate(), parameters));
    }

}
