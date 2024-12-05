package com.pharmacy.modules.inventory.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pharmacy.config.ApiException;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum MedicineCategory {
    SUPPLEMENTS("SUPPLEMENTS"),
    ANTIBIOTICS("ANTIBIOTICS"),
    REGULAR_MEDICINES("REGULAR_MEDICINES");
    private final String name;
    MedicineCategory(String name){
        this.name = name;
    }

    @JsonCreator
    public static MedicineCategory fromString(String value) {
        try {
            return MedicineCategory.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid medicineCategory: " + value);
        }
    }
}
