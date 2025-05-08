package com.example.JobWebsite.exceptionhandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppException extends RuntimeException {
    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public AppException(HttpStatus httpStatus, ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    private ErrorCode errorCode;
    private HttpStatus httpStatus;
}
