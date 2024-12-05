package com.pharmacy.modules.inventory.repository;

import com.pharmacy.config.ApiException;
import com.pharmacy.config.FieldValidator;
import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.service.SupplierService;
import com.pharmacy.payload.Response;
import com.pharmacy.payload.SupplierRequest;
import com.pharmacy.payload.UpdateSupplierRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles(value = "dev")
@Slf4j
class SupplierServiceTests {
    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    SupplierHelper supplierCreator;

    @InjectMocks
    SupplierService supplierService;

    @Mock
    FieldValidator validator;


    @Test
    void update_supplier_existing(){
        Supplier initialMock = supplierCreator.mockSupplier();
        Supplier updatedMock = supplierCreator.createSupplier_updated(); // TEST_1234
        UpdateSupplierRequest request = supplierCreator.createUpdateRequest_existing();
        //1.
        //when searching for the name  return true
        //mocking that this record already exists in table
        when(supplierRepository.existsByName(request.getSupplierName()))
                .thenReturn(true) // first time when searching if the supplier already exists.
                .thenReturn(false); // second when checking the NEW NAME if exists.
        //2.
        when(supplierRepository.findByName(request.getSupplierName()))
                .thenReturn(Optional.of(initialMock));
        //3.
        //when save, return the mocked
        when(supplierRepository.save(any()))
                .thenReturn(updatedMock);//TEST_1234
        try {
            Response<?> response = supplierService.updateSupplier(request);
            assertEquals(Response.Status.SUCCESS, response.getStatus());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void update_supplier_not_existing(){
        Supplier initialMock = supplierCreator.mockSupplier();
        Supplier updatedMock = supplierCreator.createSupplier_updated(); // TEST_1234
        UpdateSupplierRequest request = supplierCreator.createUpdateRequest_existing();
        //1.
        //when searching for the name  return true
        //mocking that this record already exists in table
        when(supplierRepository.existsByName(request.getSupplierName()))
                .thenReturn(false) // first time when searching if the supplier already exists.
                .thenReturn(false); // second when checking the NEW NAME if exists.
        //2.
        when(supplierRepository.findByName(request.getSupplierName()))
                .thenReturn(Optional.of(initialMock));
        //3.
        //when save, return the mocked
        when(supplierRepository.save(any()))
                .thenReturn(updatedMock);//TEST_1234
        try {
            Response<?> response = supplierService.updateSupplier(request);
        }catch (ApiException ex){
            String expectedMessage = "Supplier " + request.getSupplierName() + " does not exists.";
            assertEquals(expectedMessage, ex.getMessage());
        }
    }


    //save
    @Test
    void save_new_supplier_unique_name(){
        when(supplierRepository.save(any()))
                .thenReturn(supplierCreator.supplierMock());
        SupplierRequest request = supplierCreator.createNewSupplier();
        Response response= supplierService.addNewSupplier(request);
        assertEquals(Response.Status.SUCCESS, response.getStatus());
    }

    @Test
    void save_supplier_existing_name(){
        Supplier supplierMockExpectToGet = supplierCreator.supplierMock();
        SupplierRequest request = supplierCreator.createNewSupplier();
        //when save, return the mocked
        when(supplierRepository.save(any()))
                .thenReturn(supplierMockExpectToGet);
        //when searching for the name which should be unique, return true
        //mocking that this record already exists in table
        when(supplierRepository.existsByName(request.getSupplierName()))
                .thenReturn(true);
        try {
            supplierService.addNewSupplier(request);
        }catch (ApiException ex){
            String message = "Supplier '" +request.getSupplierName() + "' already exists.";
            assertEquals(message, ex.getMessage());
        }
    }



}
