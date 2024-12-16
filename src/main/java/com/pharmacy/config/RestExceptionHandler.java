package com.pharmacy.config;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.pharmacy.payload.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
        log.error("EX: " + ex);
        Response<Object> response = new Response<>(Response.Status.ERROR, "Unauthorized");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex, WebRequest request) {
        log.error("EX: " + ex);
        String message;
        switch (ex.getSQLState()){
            case "23505" -> message = "Duplicates are not allowed.";
            default -> message = "Database exception.";
        }
        Response<Object> response = new Response<>(Response.Status.ERROR, message);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("EX: " + ex);
        String message = String.format("Parameter '%s' should be of type '%s', not '%s'.",
                ex.getParameter().getParameterName(),
                ex.getRequiredType(),
                ex.getParameter().getParameterType());
        Response<Object> response = new Response<>(Response.Status.ERROR, message);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        Response<Object> response = new Response<>(Response.Status.ERROR, error);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpMessageNotReadableException {}", ex.getMessage());
        String error = ex.getMessage();
        Throwable cause = ex.getCause();

        if (ex.getCause().getCause() instanceof ApiException) {
            ApiException apiException = (ApiException) ex.getCause().getCause();
            Response<Object> response = new Response<>(Response.Status.ERROR, apiException.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatEx = (InvalidFormatException) cause;
            if (invalidFormatEx.getTargetType().equals(LocalDate.class) || invalidFormatEx.getTargetType().equals(LocalDateTime.class)) {
                String fieldName = "";
                for (JsonMappingException.Reference reference : invalidFormatEx.getPath()) {
                    fieldName = reference.getFieldName();
                }
                Response<Object> response = new Response<>(Response.Status.ERROR, "Invalid date format for field: " + fieldName + ". Expected: yyyy-MM-dd ");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
            }
        }
        Response<Object> response = new Response<>(Response.Status.ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException apiException, WebRequest request) {
        Response<Object> response = new Response<>(Response.Status.ERROR, apiException.getMessage());
       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .contentType(MediaType.APPLICATION_JSON)
               .body(response);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<Object> handleInvalidDataAccess(InvalidDataAccessResourceUsageException apiException, WebRequest request) {
        log.error("Error: " + apiException.getMessage());
        Response<Object> response = new Response<>(Response.Status.ERROR, "Database exception.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}