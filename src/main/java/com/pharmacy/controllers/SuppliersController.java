package com.pharmacy.controllers;

import com.pharmacy.modules.inventory.service.SupplierService;
import com.pharmacy.payload.AddSupplierRequest;
import com.pharmacy.payload.Response;
import com.pharmacy.payload.UpdateSupplierRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/add")
    public ResponseEntity<Response<?>> addSupplier(@RequestBody AddSupplierRequest addSupplierRequest){
        return new ResponseEntity<>(supplierService.addNewSupplier(addSupplierRequest), HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Response<?>> updateSupplier(@RequestBody UpdateSupplierRequest updateSupplierRequest){
        return new ResponseEntity<>(supplierService.updateSupplier(updateSupplierRequest), HttpStatus.OK);
    }
    @DeleteMapping(value = "")
    public ResponseEntity<Response<?>> deleteSupplier(@RequestParam String supplierName){
        return new ResponseEntity<>(supplierService.deleteSupplier(supplierName), HttpStatus.OK);
    }
}
