package com.pharmacy.controllers;

import com.pharmacy.modules.inventory.service.MedicineService;
import com.pharmacy.payload.AddMedicineRequest;
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

    @GetMapping(value = "")
    public ResponseEntity<Response<?>> getMedicines(@RequestParam Optional<Long> medicineId){
        return new ResponseEntity<>(medicineService.getMedicines(medicineId), HttpStatus.OK);
    }
    @GetMapping(value = "/by-substance")
    public ResponseEntity<Response<?>> getMedicinesBySubstanceName(@RequestParam String substanceName){
        return new ResponseEntity<>(medicineService.getMedicinesBySubstanceName(substanceName), HttpStatus.OK);
    }
    @GetMapping(value = "/by-category")
    public ResponseEntity<Response<?>> getMedicinesByCategoryId(@RequestParam Long categoryId){
        return new ResponseEntity<>(medicineService.getMedicinesByCategoryId(categoryId), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Response<?>> addnewMedicine(@RequestBody AddMedicineRequest request){
        return new ResponseEntity<>(medicineService.addNewMedicine(request), HttpStatus.OK);
    }
    @PostMapping(value = "/increase-stock")
    public ResponseEntity<Response<?>> increaseStock(@RequestParam Long medicineId, @RequestParam Long increaseBy){
        return new ResponseEntity<>(medicineService.increaseStock(medicineId, increaseBy), HttpStatus.OK);
    }
    @PostMapping(value = "/decrease-stock")
    public ResponseEntity<Response<?>> decreaseStock(@RequestParam Long medicineId, @RequestParam Long decreaseBy){
        return new ResponseEntity<>(medicineService.decreaseStock(medicineId, decreaseBy), HttpStatus.OK);
    }

    @PostMapping(value = "/change-price")
    public ResponseEntity<Response<?>> changePrice(@RequestParam Long medicineId, @RequestParam Long newPrice){
        return new ResponseEntity<>(medicineService.changePrice(medicineId, newPrice), HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Response<?>> deleteMedicine(@RequestParam Long medicineId){
        return new ResponseEntity<>(medicineService.deleteMedicine(medicineId), HttpStatus.OK);
    }

}
