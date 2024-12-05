package com.pharmacy.payload;

import com.pharmacy.config.Mandatory;
import lombok.Data;

@Data
public class UpdateSupplierRequest {
    @Mandatory
    private String supplierName;
    @Mandatory
    private String newSupplierName;
}
