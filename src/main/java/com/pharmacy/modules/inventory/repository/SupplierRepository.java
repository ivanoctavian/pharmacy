package com.pharmacy.modules.inventory.repository;

import com.pharmacy.modules.inventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
    Optional<Supplier> findByName(String name);
    boolean existsByName(String name);
}
