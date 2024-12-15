package com.pharmacy.modules.inventory.repository;

import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.payload.AddSupplierRequest;
import com.pharmacy.payload.UpdateSupplierRequest;
import org.springframework.stereotype.Service;

@Service
public class SupplierHelper {
    protected UpdateSupplierRequest createUpdateRequest_existing(){
        UpdateSupplierRequest request = new UpdateSupplierRequest();
        request.setId(1234L);
        request.setNewSupplierName("NEW_123");
        return request;
    }
    protected Supplier createSupplier_updated(){
        Supplier supplier = new Supplier();
        supplier.setName("NEW_123");
        return supplier;
    }

    protected Supplier mockSupplier(){
        Supplier supplier = new Supplier();
        supplier.setName("TEST_1234");
        return supplier;
    }


    protected AddSupplierRequest createNewSupplier(){
       AddSupplierRequest addSupplierRequest = new AddSupplierRequest();
       addSupplierRequest.setSupplierName("TEST_1234");
       return addSupplierRequest;
    }
    protected Supplier supplierMock(){
        Supplier supplier = new Supplier();
        supplier.setName("TEST_1234");
        return supplier;
    }

    protected Supplier createPfizerSupplier(){
        Supplier supplier = new Supplier();
        supplier.setName("Pfizer");
        return supplier;
    }

    protected Supplier createBioNTechSupplier(){
        Supplier supplier = new Supplier();
        supplier.setName("Pfizer");
        return supplier;
    }
}
