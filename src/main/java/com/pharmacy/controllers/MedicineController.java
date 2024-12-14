package com.pharmacy.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/medicines")
@AllArgsConstructor
public class MedicineController {

    //GET
    //1 getMedicineByName - tabel extins cu join
    //2 getMedicineBySubstanceName -tabel extins cu join
    //3 getByCategoryId - supliment, antibiotic, etc

    //UPDATE
    //1 increaseStockByIdWith - in spate face get apoi creste cu x
    //2 decreaseStckByIdWith - ce se intampla daca ajunge la 0? sau sa nu iasa negativ
    //3 changePriceByIdWith -

    //ADD
    //1 addNewMedicine - mesaje potrivite daca nu gaseste id-ul de supplier

    //DELETE
    //1 deleteMedicine by id

}
