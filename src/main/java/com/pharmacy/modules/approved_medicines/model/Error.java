package com.pharmacy.modules.approved_medicines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "message"
})
@Data
public class Error {
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;


}