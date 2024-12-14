package com.pharmacy.controllers;

import com.pharmacy.modules.inventory.service.MedicineService;
import com.pharmacy.payload.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/medicines")
@AllArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    //GET
    //1 getMedicineByName - tabel extins cu join
    //2 getMedicineBySubstanceName -tabel extins cu join
    //3 getByCategoryId - supliment, antibiotic, etc
    @GetMapping(value = "")
    public ResponseEntity<Response<?>> getMedicines(@RequestParam Optional<String> medicineName){
        return new ResponseEntity<>(medicineService.getMedicines(medicineName), HttpStatus.OK);
    }
    @GetMapping(value = "/by-substance")
    public ResponseEntity<Response<?>> getMedicinesBySubstanceName(@RequestParam String substanceName){
        return new ResponseEntity<>(medicineService.getMedicinesBySubstanceName(substanceName), HttpStatus.OK);
    }
    @GetMapping(value = "/by-category")
    public ResponseEntity<Response<?>> getMedicinesByCategoryId(@RequestParam Long categoryId){
        return new ResponseEntity<>(medicineService.getMedicinesByCategoryId(categoryId), HttpStatus.OK);
    }


    //UPDATE
    //1 increaseStockByIdWith - in spate face get apoi creste cu x
    //2 decreaseStckByIdWith - ce se intampla daca ajunge la 0? sau sa nu iasa negativ
    //3 changePriceByIdWith -

    //ADD
    //1 addNewMedicine - mesaje potrivite daca nu gaseste id-ul de supplier

    //DELETE
    //1 deleteMedicine by id

}
