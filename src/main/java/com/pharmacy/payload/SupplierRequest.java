package com.pharmacy.payload;


import com.pharmacy.config.Mandatory;
import lombok.Data;

@Data
public class SupplierRequest {
    @Mandatory
    private String supplierName;
}
