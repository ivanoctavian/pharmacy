package com.pharmacy.modules.inventory.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@DataJpaTest
@ActiveProfiles(value = "dev")
@Slf4j
class SupplierRepositoryTests {
    @MockitoBean
    private SupplierRepository supplierRepository;

    @Autowired
    SupplierRepositoryHelper supplierCreator;


    @Test
    void save_supplier_unique_name(){

    }

    @Test
    void save_supplier_existing_name(){

    }



}
