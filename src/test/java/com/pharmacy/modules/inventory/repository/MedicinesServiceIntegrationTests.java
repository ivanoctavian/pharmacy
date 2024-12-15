package com.pharmacy.modules.inventory.repository;


import com.pharmacy.config.ApiException;
import com.pharmacy.modules.inventory.model.Category;
import com.pharmacy.modules.inventory.model.Medicine;
import com.pharmacy.modules.inventory.model.MedicineCategory;
import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.service.MedicineService;
import com.pharmacy.payload.AddMedicineRequest;
import com.pharmacy.payload.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicinesServiceIntegrationTests {
    @Autowired
    private MedicineService medicineService;

    @MockitoBean
    private MedicineRepository medicineRepository;

    @MockitoBean
    private SupplierRepository supplierRepository;

    @MockitoBean
    private CategoryRepository categoryRepository;

    @Test
    void testAddNewMedicine_correct() {
        AddMedicineRequest request = new AddMedicineRequest();
        request.setName("ibuprofen");
        request.setExpirationDate(LocalDate.of(2025, 10, 10));
        request.setPrice(BigDecimal.valueOf(22.45));
        request.setStock(100L);
        request.setCategoryId(1L);
        request.setSupplierId(2L);

        Category category = new Category();
        category.setName(MedicineCategory.REGULAR_MEDICINES);
        category.setId(1L);

        Supplier supplier = new Supplier();
        supplier.setName("SmartDrugs impex srl");
        supplier.setId(2L);

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.existsById(2L)).thenReturn(true);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(supplierRepository.findById(2L)).thenReturn(Optional.of(supplier));

        Response<?> response = medicineService.addNewMedicine(request);
        assertEquals(Response.Status.SUCCESS, response.getStatus());
        verify(medicineRepository).save(any(Medicine.class));
    }

    @Test
    void testAddNewMedicine_insufficient_stock() {
        AddMedicineRequest request = new AddMedicineRequest();
        request.setName("ibuprofen");
        request.setExpirationDate(LocalDate.of(2025, 10, 10));
        request.setPrice(BigDecimal.valueOf(22.45));
        request.setStock(-2L);
        request.setCategoryId(1L);
        request.setSupplierId(2L);

        Category category = new Category();
        category.setName(MedicineCategory.REGULAR_MEDICINES);
        category.setId(1L);

        Supplier supplier = new Supplier();
        supplier.setName("SmartDrugs impex srl");
        supplier.setId(2L);

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.existsById(2L)).thenReturn(true);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(supplierRepository.findById(2L)).thenReturn(Optional.of(supplier));

        ApiException exception = assertThrows(ApiException.class, () -> medicineService.addNewMedicine(request));

        assertEquals("Stock must be at least 1.", exception.getMessage());
    }

    @Test
    void testAddNewMedicine_expired() {
        AddMedicineRequest request = new AddMedicineRequest();
        request.setName("ibuprofen");
        request.setExpirationDate(LocalDate.of(2024, 10, 10));
        request.setPrice(BigDecimal.valueOf(22.45));
        request.setStock(223L);
        request.setCategoryId(1L);
        request.setSupplierId(2L);

        Category category = new Category();
        category.setName(MedicineCategory.REGULAR_MEDICINES);
        category.setId(1L);

        Supplier supplier = new Supplier();
        supplier.setName("SmartDrugs impex srl");
        supplier.setId(2L);

        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.existsById(2L)).thenReturn(true);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(supplierRepository.findById(2L)).thenReturn(Optional.of(supplier));

        ApiException exception = assertThrows(ApiException.class, () -> medicineService.addNewMedicine(request));

        assertEquals("Medicine is expired.", exception.getMessage());
    }
}
