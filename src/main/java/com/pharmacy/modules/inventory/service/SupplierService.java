package com.pharmacy.modules.inventory.service;

import com.pharmacy.config.ApiException;
import com.pharmacy.config.FieldValidator;
import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.repository.SupplierRepository;
import com.pharmacy.payload.Response;
import com.pharmacy.payload.SupplierRequest;
import com.pharmacy.payload.UpdateSupplierRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * If the supplier name is provided, then will return one unique entity.
 * Else, if the supplier name is null or empty, will return all suppliers found in table.
 */
@Service
@Slf4j
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    FieldValidator fieldValidator;

    public Response<?> updateSupplier(UpdateSupplierRequest request){
        if(request == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "SupplierRequest is null");
        }
        fieldValidator.validateMandatoryFields(request);
        String supplierName = request.getSupplierName();
        String newSupplierName = request.getNewSupplierName();

        if(!supplierRepository.existsByName(supplierName)) {
           throw new ApiException(HttpStatus.BAD_REQUEST, String.format("Supplier %s does not exists.", supplierName));
        }
        if(supplierRepository.existsByName(newSupplierName)){
           throw new ApiException(HttpStatus.BAD_REQUEST, String.format("New supplier name must be unique. '%s' already exists.", newSupplierName));
        }
        try {
            Supplier supplier = supplierRepository.findByName(supplierName).get();
            supplier.setName(newSupplierName);
            supplierRepository.save(supplier);
            log.info("Supplier {} was updated successfully!", supplier);
            Response<String> response = new Response<>();
            response.setStatus(Response.Status.SUCCESS);
            return response;
        }catch (Exception e){
            log.error("Exception catched: " + e.getMessage());
            log.error("Supplier was not updated to database.");
            Response<Supplier> response = new Response<>();
            response.setStatus(Response.Status.ERROR);
            response.setMessage("Supplier was not updated to database.");
            return response;
        }

    }

    public Response<?> addNewSupplier(SupplierRequest request){
        if(request == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "SupplierRequest is null");
        }
        String supplierName = request.getSupplierName();
        if(supplierName == null || supplierName.trim().isEmpty()){
            throw new ApiException(HttpStatus.BAD_REQUEST, "SupplierName is missing.");
        }
        if(supplierRepository.findByName(supplierName).isPresent()){
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("Supplier '%s' already exists.", supplierName));
        }

        Supplier supplier = new Supplier();
        supplier.setName(supplierName);
        try {
            String message = String.format("Supplier %s added successfully.", supplierName);
            supplierRepository.save(supplier);
            log.info(message);
            Response<String> response = new Response<>();
            response.setStatus(Response.Status.SUCCESS);
            response.setMessage(message);
            return response;
        }catch (Exception e){
            log.error("Exception catched: " + e.getMessage());
            log.error("Supplier was not added to database.");
            Response<Supplier> response = new Response<>();
            response.setStatus(Response.Status.ERROR);
            response.setMessage("Supplier was not added to database.");
            return response;
        }
    }

    /**
     Get suppliers methods
     */
    public Response<?> getSuppliers(Optional<String> supplierName){
        if(supplierName.isPresent() && !supplierName.get().trim().isEmpty()){
            return getSupplierByName(supplierName.get());
        }
        return getAllSuppliers();
    }
    private Response<List<Supplier>> getAllSuppliers(){
        log.info("Get all suppliers start.");
        List<Supplier> suppliers = supplierRepository.findAll();
        log.info("List of suppliers found: " + suppliers);
        if(suppliers.isEmpty()){
            Response<List<Supplier>> response = new Response<>();
            response.setStatus(Response.Status.FAIL);
            response.setMessage("No suppliers found.");
            return response;
        }
        Response<List<Supplier>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(suppliers);
        return response;

    }
    private Response<Supplier> getSupplierByName(String supplierName){
        log.info("SupplierName param is present. Supplier to search for: " + supplierName);
        try{
            Optional<Supplier> supplier = supplierRepository.findByName(supplierName);
            if(supplier.isPresent()){
                log.info("Supplier found in table. Supplier: " + supplier.get());
                Response<Supplier> response =new Response<>();
                response.setStatus(Response.Status.SUCCESS);
                response.setData(supplier.get());
                return response;
            }else{
                String errMessage = String.format("Supplier with name %s was not found.",supplierName);
                log.info(errMessage);
                Response<Supplier> response =new Response<>();
                response.setStatus(Response.Status.FAIL);
                response.setMessage(errMessage);
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("Could not get entity from database.");
            Response<Supplier> response = new Response<>();
            response.setStatus(Response.Status.ERROR);
            response.setMessage("Could not get entity from database.");
            return response;
        }
    }
}
