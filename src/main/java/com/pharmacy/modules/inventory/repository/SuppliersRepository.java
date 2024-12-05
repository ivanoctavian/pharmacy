package com.pharmacy.modules.inventory.repository;

import com.pharmacy.modules.inventory.model.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends JpaRepository<Suppliers, Integer>{
}
