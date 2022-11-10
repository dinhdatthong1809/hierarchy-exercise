package com.thong.employee.exception;

public class BusinessException extends RuntimeException {

    private BusinessException(String message) {
        super(message);
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

}
