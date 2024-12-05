package com.pharmacy.modules.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Supplier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Supplier_seq")
    @SequenceGenerator(name="Supplier_seq", sequenceName="Supplier_seq", allocationSize=1)
    private Long id;

    @Column(name = "name")
    private String name;
}
