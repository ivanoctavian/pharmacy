package com.pharmacy.modules.inventory.repository;

import com.pharmacy.modules.inventory.model.Supplier;
import org.springframework.stereotype.Service;

@Service
public class SupplierRepositoryHelper {
    protected Supplier createNewSupplier(){
        Supplier supplier = new Supplier();
        supplier.setName("newSupplier" + System.currentTimeMillis());
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
