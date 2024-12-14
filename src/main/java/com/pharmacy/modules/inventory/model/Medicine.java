package com.pharmacy.modules.inventory.model;

import com.pharmacy.payload.AddMedicineRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Medicine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Medicine {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Medicine_seq")
    @SequenceGenerator(name="Medicine_seq", sequenceName="Medicine_seq", allocationSize=1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "substance_name")
    private String substanceName;

    @Column(name = "producer")
    private String producer;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "supplier_id", foreignKey = @ForeignKey(name = "FK_SUPPLIER_ID"))
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_ID"))
    private Category category;

    public Medicine(AddMedicineRequest addMedicineRequest){
        this.name = addMedicineRequest.getName();
        this.expirationDate = addMedicineRequest.getExpirationDate();
        this.stock = addMedicineRequest.getStock();
        this.producer = addMedicineRequest.getProducer();
        this.price = addMedicineRequest.getPrice();
    }
}
