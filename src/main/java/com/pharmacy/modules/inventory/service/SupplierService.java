package com.pharmacy.modules.inventory.service;

import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.repository.SupplierRepository;
import com.pharmacy.payload.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Get suppliers methods
     */
    public Response<Supplier> getSuppliers(Optional<String> supplierName){
        if(supplierName.isPresent() && !supplierName.get().trim().isEmpty()){
            return getSupplierByName(supplierName.get());
        }
        return getAllSuppliers();
    }

    public Response<Supplier> getAllSuppliers(){


    }

    public Response<Supplier> getSupplierByName(String supplierName){

    }
}
