package com.pharmacy.modules.inventory.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Category_seq")
    @SequenceGenerator(name="Category_seq", sequenceName="Category_seq", allocationSize=1)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private MedicineCategory name;

    @Column(name = "require_recipe")
    private boolean requiresRecipe = false;
}
