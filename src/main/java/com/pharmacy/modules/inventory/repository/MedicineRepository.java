package com.pharmacy.modules.inventory.repository;

import com.pharmacy.modules.inventory.model.Category;
import com.pharmacy.modules.inventory.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByName(String name);
    List<Medicine> findAllBySubstanceName(String name);
    List<Medicine> findAllByCategory_Id(Long id);
    List<Medicine> findAllByCategory(Category category);
}
