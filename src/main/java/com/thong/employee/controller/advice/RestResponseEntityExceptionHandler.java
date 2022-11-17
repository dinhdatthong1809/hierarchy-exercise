package com.thong.employee.controller.advice;

import com.thong.employee.controller.advice.ErrorResponse.ErrorInfo;
import com.thong.employee.exception.BusinessException;
import com.thong.employee.exception.ResourceNotFoundException;
import com.thong.employee.exception.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorInfo> errorInfoList = ex.getBindingResult().getAllErrors().stream()
                .map(error -> new ErrorInfo(ErrorCode.INVALID_INPUT, error.getDefaultMessage()))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(errorInfoList);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getErrorCode(), ex.getErrorCode().getMessageTemplate());
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessException(BusinessException ex) {
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable ex) {
        log.error(ErrorCode.TECHNICAL_ERROR.getMessageTemplate(), ex);
        return new ErrorResponse(ErrorCode.TECHNICAL_ERROR, ex.getMessage());
    }

}
