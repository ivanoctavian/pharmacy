package com.pharmacy.modules.inventory.service;


import com.pharmacy.config.ApiException;
import com.pharmacy.config.FieldValidator;
import com.pharmacy.modules.inventory.model.Medicine;
import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.repository.MedicineRepository;
import com.pharmacy.payload.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final FieldValidator fieldValidator;

    public Response<List<Medicine>> getMedicinesByCategoryId(Long categoryId){
        if(categoryId == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "CategoryId is null.");
        }
        log.info("CategoryId to search for: " + categoryId);
        List<Medicine> medicines = medicineRepository.findAllByCategory_Id(categoryId);
        if(medicines.isEmpty()){
            Response<List<Medicine>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No medicines found.");
            return response;
        }
        Response<List<Medicine>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(medicines);
        return response;
    }

    public Response<List<Medicine>> getMedicinesBySubstanceName(String substanceName){
        if(substanceName == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "SubstanceName is null.");
        }
        log.info("Substance name to search for: " + substanceName);
        List<Medicine> medicines = medicineRepository.findAllBySubstanceName(substanceName);
        if(medicines.isEmpty()){
            Response<List<Medicine>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No medicines found.");
            return response;
        }
        Response<List<Medicine>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(medicines);
        return response;

    }

    public Response<?> getMedicines(Optional<String> medicineName){
        if(medicineName.isPresent() && !medicineName.get().trim().isEmpty()){
            return getMedicineByName(medicineName.get());
        }
        return getAllMedicines();
    }
    private Response<List<Medicine>> getAllMedicines(){
        log.info("Get all medicines start.");
        List<Medicine> medicines = medicineRepository.findAll();
        log.info("List of medicines found: " + medicines);
        if(medicines.isEmpty()){
            Response<List<Medicine>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No medicines found.");
            return response;
        }
        Response<List<Medicine>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(medicines);
        return response;
    }
    private Response<Medicine> getMedicineByName(String medicineName){
        log.info("MedicineName is present. Medicine to search for: " + medicineName);
        try{
            Optional<Medicine> medicine = medicineRepository.findByName(medicineName);
            if(medicine.isPresent()){
                log.info("Medicine found in table. Medicine: " + medicine.get());
                Response<Medicine> response =new Response<>();
                response.setStatus(Response.Status.SUCCESS);
                response.setData(medicine.get());
                return response;
            }else{
                String errMessage = String.format("Medicine %s was not found.",medicineName);
                log.info(errMessage);
                Response<Medicine> response =new Response<>();
                response.setStatus(Response.Status.NOT_FOUND);
                response.setMessage(errMessage);
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("Could not get entity from database.");
            Response<Medicine> response = new Response<>();
            response.setStatus(Response.Status.ERROR);
            response.setMessage("Could not get entity from database.");
            return response;
        }
    }
}
