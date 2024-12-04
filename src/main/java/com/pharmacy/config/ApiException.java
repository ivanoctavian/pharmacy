package com.pharmacy.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiException extends RuntimeException {
    protected LocalDateTime timeStamp = LocalDateTime.now();

    private HttpStatus status;
    private String message;
    private String statusCode;

    public ApiException() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiException(HttpStatus status, String message, String statusCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }

    public ApiException(HttpStatus status, String message, Throwable exception) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ApiException(String message, String statusCode) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return null;
    }

    @Override
    public String getLocalizedMessage() {
        return null;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "timeStamp=" + timeStamp +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }
}