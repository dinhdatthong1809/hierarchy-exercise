package com.thong.employee.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TECHNICAL_ERROR("Server internal error"),
    RESOURCE_NOT_FOUND("Resource not found"),
    INVALID_INPUT(""),

    MULTIPLE_ROOT_MANAGERS("Should not contain more than 1 root manager in the employee tree. Root manager ids are %s"),
    DETECTED_EMPLOYEE_CYCLE("The employee tree has cycle. Please check the cycle list: %s"),
    ;

    private final String messageTemplate;

}
