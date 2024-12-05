package com.pharmacy.modules.inventory.service;

import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.repository.SupplierRepository;
import com.pharmacy.payload.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     Get suppliers methods
     */
    public Response<?> getSuppliers(Optional<String> supplierName){
        if(supplierName.isPresent() && !supplierName.get().trim().isEmpty()){
            return getSupplierByName(supplierName.get());
        }
        return getAllSuppliers();
    }

    public Response<List<Supplier>> getAllSuppliers(){
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

    public Response<Supplier> getSupplierByName(String supplierName){
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
                String errMessage = String.format("Supplier with name %S was not found.",supplierName);
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
