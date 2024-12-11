package com.pharmacy.modules.approved_medicines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "disclaimer",
    "terms",
    "license",
    "last_updated",
    "results"
})
@Data
public class Meta {
    @JsonProperty("disclaimer")
    private String disclaimer;

    @JsonProperty("terms")
    private String terms;

    @JsonProperty("license")
    private String license;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("results")
    private Results results;
}
