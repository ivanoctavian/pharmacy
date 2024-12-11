package com.pharmacy.modules.approved_medicines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "spl_product_data_elements",
    "active_ingredient",
    "purpose",
    "indications_and_usage",
    "warnings",
    "do_not_use",
    "ask_doctor",
    "ask_doctor_or_pharmacist",
    "when_using",
    "stop_use",
    "pregnancy_or_breast_feeding",
    "keep_out_of_reach_of_children",
    "dosage_and_administration",
    "storage_and_handling",
    "inactive_ingredient",
    "questions",
    "package_label_principal_display_panel",
    "set_id",
    "id",
    "effective_time",
    "version",
    "openfda"
})
@Data
public class Result {
    @JsonProperty("spl_product_data_elements")
    private List<String> splProductDataElements;

    @JsonProperty("active_ingredient")
    private List<String> activeIngredient;

    @JsonProperty("purpose")
    private List<String> purpose;

    @JsonProperty("indications_and_usage")
    private List<String> indicationsAndUsage;

    @JsonProperty("warnings")
    private List<String> warnings;

    @JsonProperty("do_not_use")
    private List<String> doNotUse;

    @JsonProperty("ask_doctor")
    private List<String> askDoctor;

    @JsonProperty("ask_doctor_or_pharmacist")
    private List<String> askDoctorOrPharmacist;

    @JsonProperty("when_using")
    private List<String> whenUsing;

    @JsonProperty("stop_use")
    private List<String> stopUse;

    @JsonProperty("pregnancy_or_breast_feeding")
    private List<String> pregnancyOrBreastFeeding;

    @JsonProperty("keep_out_of_reach_of_children")
    private List<String> keepOutOfReachOfChildren;

    @JsonProperty("dosage_and_administration")
    private List<String> dosageAndAdministration;

    @JsonProperty("storage_and_handling")
    private List<String> storageAndHandling;

    @JsonProperty("inactive_ingredient")
    private List<String> inactiveIngredient;

    @JsonProperty("questions")
    private List<String> questions;

    @JsonProperty("package_label_principal_display_panel")
    private List<String> packageLabelPrincipalDisplayPanel;

    @JsonProperty("set_id")
    private String setId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("effective_time")
    private String effectiveTime;

    @JsonProperty("version")
    private String version;

    @JsonProperty("openfda")
    private Openfda openfda;
}
