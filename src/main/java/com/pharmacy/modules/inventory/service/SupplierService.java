package com.pharmacy.modules.inventory.service;

import com.pharmacy.config.ApiException;
import com.pharmacy.config.FieldValidator;
import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.repository.SupplierRepository;
import com.pharmacy.payload.AddSupplierRequest;
import com.pharmacy.payload.Response;
import com.pharmacy.payload.UpdateSupplierRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * If the supplier name is provided, then will return one unique entity.
 * Else, if the supplier name is null or empty, will return all suppliers found in table.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final FieldValidator fieldValidator;

    public Response<?> deleteSupplier(Long id){
        log.info("Delete supplier start.");
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if(supplier.isPresent()){
            supplierRepository.delete(supplier.get());
            log.info("Supplier deleted successfully");
            Response<Supplier> response =new Response<>();
            response.setStatus(Response.Status.SUCCESS);
            return response;
        }else{
            String errMessage = String.format("Supplier doesn't exists.");
            log.info(errMessage);
            Response<Supplier> response =new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage(errMessage);
            return response;
        }
    }

    public Response<?> updateSupplier(UpdateSupplierRequest request){
        log.info("UpdateSupplier start. Request: " + request);
        if(request == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "UpdateSupplierRequest is null");
        }
        fieldValidator.validateMandatoryFields(request);
        Long supplierId = request.getId();
        String newSupplierName = request.getNewSupplierName();

        if(!supplierRepository.existsById(supplierId)) {
            log.error("Supplier doesn't exists.");
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("Supplier %s does not exists.", supplierId));
        }

        Supplier supplier = supplierRepository.findById(supplierId).get();
        supplier.setName(newSupplierName);
        supplierRepository.save(supplier);
        log.info("Supplier {} was updated successfully!", supplier);
        Response<String> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        return response;
    }

    public Response<?> addNewSupplier(AddSupplierRequest request){
        log.info("AddNewSupplier start. Request: " + request);
        fieldValidator.validateMandatoryFields(request);
        if(request == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "AddSupplierRequest is null");
        }
        String supplierName = request.getSupplierName();

        Supplier supplier = new Supplier();
        supplier.setName(supplierName);

        String message = String.format("Supplier %s added successfully.", supplierName);
        supplierRepository.save(supplier);
        log.info(message);
        Response<String> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setMessage(message);
        return response;
    }

    /**
     Get suppliers methods
     */
    public Response<?> getSuppliers(Optional<Long> supplierId){
        if(supplierId.isPresent() && supplierId.get() != 0) {
            return getSupplierByName(supplierId.get());
        }
        return getAllSuppliers();
    }
    private Response<List<Supplier>> getAllSuppliers(){
        log.info("Get all suppliers start.");
        List<Supplier> suppliers = supplierRepository.findAll();
        log.info("List of suppliers found: " + suppliers);
        if(suppliers.isEmpty()){
            log.info("No suppliers found.");
            Response<List<Supplier>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No suppliers found.");
            response.setData(suppliers);
            return response;
        }
        Response<List<Supplier>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(suppliers);
        return response;
    }
    private Response<Supplier> getSupplierByName(Long id){
        log.info("SupplierId param is present. Supplier to search for: " + id);

        Optional<Supplier> supplier = supplierRepository.findById(id);
        if(supplier.isPresent()){
            log.info("Supplier found in table. Supplier: " + supplier.get());
            Response<Supplier> response =new Response<>();
            response.setStatus(Response.Status.SUCCESS);
            response.setData(supplier.get());
            return response;
        }else {
            String errMessage = String.format("Supplier doesn't exist.");
            log.info(errMessage);
            Response<Supplier> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage(errMessage);
            return response;
        }
    }
}
