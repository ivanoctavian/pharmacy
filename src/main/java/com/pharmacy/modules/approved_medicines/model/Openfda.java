package com.pharmacy.modules.approved_medicines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "application_number",
    "brand_name",
    "generic_name",
    "manufacturer_name",
    "product_ndc",
    "product_type",
    "route",
    "substance_name",
    "rxcui",
    "spl_id",
    "spl_set_id",
    "package_ndc",
    "is_original_packager",
    "upc",
    "nui",
    "pharm_class_moa",
    "pharm_class_cs",
    "pharm_class_epc",
    "unii"
})
@Data
public class Openfda {

    @JsonProperty("application_number")
    private List<String> applicationNumber;

    @JsonProperty("brand_name")
    private List<String> brandName;

    @JsonProperty("generic_name")
    private List<String> genericName;

    @JsonProperty("manufacturer_name")
    private List<String> manufacturerName;

    @JsonProperty("product_ndc")
    private List<String> productNdc;

    @JsonProperty("product_type")
    private List<String> productType;

    @JsonProperty("route")
    private List<String> route;

    @JsonProperty("substance_name")
    private List<String> substanceName;

    @JsonProperty("rxcui")
    private List<String> rxcui;

    @JsonProperty("spl_id")
    private List<String> splId;

    @JsonProperty("spl_set_id")
    private List<String> splSetId;

    @JsonProperty("package_ndc")
    private List<String> packageNdc;

    @JsonProperty("is_original_packager")
    private List<Boolean> isOriginalPackager;

    @JsonProperty("upc")
    private List<String> upc;

    @JsonProperty("nui")
    private List<String> nui;

    @JsonProperty("pharm_class_moa")
    private List<String> pharmClassMoa;

    @JsonProperty("pharm_class_cs")
    private List<String> pharmClassCs;

    @JsonProperty("pharm_class_epc")
    private List<String> pharmClassEpc;

    @JsonProperty("unii")
    private List<String> unii;

}
