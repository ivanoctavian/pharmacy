package com.pharmacy.config;

import com.pharmacy.payload.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException apiException, WebRequest request) {
        Response<Object> response = new Response<>(Response.Status.ERROR, apiException.getMessage());
       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .contentType(MediaType.APPLICATION_JSON)
               .body(response);
    }
}