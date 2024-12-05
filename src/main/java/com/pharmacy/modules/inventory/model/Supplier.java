package com.pharmacy.modules.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Supplier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Supplier_seq")
    @SequenceGenerator(name="Supplier_seq", sequenceName="Supplier_seq", allocationSize=1)
    private Long id;

    @Column(name = "name")
    private String name;
}
