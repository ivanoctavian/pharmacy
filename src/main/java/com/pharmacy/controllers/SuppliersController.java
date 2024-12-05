package com.pharmacy.controllers;

import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.service.SupplierService;
import com.pharmacy.payload.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/suppliers")
@AllArgsConstructor
public class SuppliersController {
    private final SupplierService supplierService;

    @GetMapping(value = "")
    public ResponseEntity<Response<?>> getSuppliers(@RequestParam Optional<String> supplierName){
        return new ResponseEntity<>(supplierService.getSuppliers(supplierName), HttpStatus.OK);
    }
}
