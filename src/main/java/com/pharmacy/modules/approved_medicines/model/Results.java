package com.pharmacy.modules.approved_medicines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "skip",
    "limit",
    "total"
})
@Data
public class Results {
    @JsonProperty("skip")
    private Integer skip;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("total")
    private Integer total;

}
