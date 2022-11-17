package com.thong.employee.controller.advice;

import com.thong.employee.exception.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private LocalDateTime timestamp = LocalDateTime.now();
    private List<ErrorInfo> errorInfoList = new ArrayList<>();

    public ErrorResponse(ErrorCode errorCode, String message) {
        this.errorInfoList.add(new ErrorInfo(errorCode, message));
    }

    public ErrorResponse(List<ErrorInfo> errorInfoList) {
        this.errorInfoList.addAll(errorInfoList);
    }

    @Getter
    @Setter
    public static class ErrorInfo {
        private ErrorCode errorCode;
        private String message;

        public ErrorInfo(ErrorCode errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }

}