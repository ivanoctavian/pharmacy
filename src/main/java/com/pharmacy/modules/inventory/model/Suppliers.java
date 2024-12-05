package com.pharmacy.modules.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Suppliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Suppliers {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Suppliers_seq")
    @SequenceGenerator(name="Suppliers_seq", sequenceName="Suppliers_seq", allocationSize=1)
    private Long id;


    @Column(name = "name")
    private String name;
}
