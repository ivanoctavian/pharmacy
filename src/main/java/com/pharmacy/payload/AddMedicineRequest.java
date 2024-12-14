package com.pharmacy.payload;

import com.pharmacy.config.Mandatory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddMedicineRequest {

    @Mandatory
    private String name;

    @Mandatory
    private LocalDate expirationDate;

    @Mandatory
    private Long stock;

    @Mandatory
    private Long supplierId;

    @Mandatory
    private Long categoryId;


    @Mandatory
    private String producer;

    @Mandatory
    private BigDecimal price;
}
