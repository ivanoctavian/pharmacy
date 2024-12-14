package com.pharmacy.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Response<T> {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("timeStamp")
    protected LocalDateTime timeStamp = LocalDateTime.now();

    @JsonProperty("status")
    protected Status status;

    @JsonProperty("message")
    protected String message;

    @JsonProperty("data")
    protected T data;

    public Response(Status status, String message) {
        this.timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
    public Response(Status status, T data) {
        this.status = status;
        this.data = data;
    }
    public enum Status {
        SUCCESS, ERROR, FAIL, NOT_FOUND
    }
}