package com.thong.employee.controller.advice;

import com.thong.employee.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessException(BusinessException ex) {
        return new ErrorMessage(ErrorMessage.ErrorCode.BUSINESS_ERROR, ex.getMessage());
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleThrowable(Throwable ex) {
        return new ErrorMessage(ErrorMessage.ErrorCode.TECHNICAL_ERROR, ex.getMessage());
    }

}

@Getter
@Setter
class ErrorMessage {

    public enum ErrorCode {
        BUSINESS_ERROR,
        TECHNICAL_ERROR,
        ;
    }

    private ErrorCode errorCode;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public ErrorMessage(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}