package com.pharmacy.controllers;

import com.pharmacy.modules.approved_medicines.model.FdaApprovedMedicine;
import com.pharmacy.modules.approved_medicines.service.ApiFdaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/fda-api/")
@RequiredArgsConstructor
public class TestFdaSearchController {
    private final ApiFdaService apiFdaService;

    @GetMapping("/search")
    public ResponseEntity<FdaApprovedMedicine> searchFdaByName(String medicineName){
        return new ResponseEntity<>(apiFdaService.searchForMedicineByName(medicineName), HttpStatus.OK);
    }
}
