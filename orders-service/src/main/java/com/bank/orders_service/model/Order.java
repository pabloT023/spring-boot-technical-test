package com.bank.orders_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "is_canceled")
    private Boolean isCanceled;

    // PrePersist was used to set values to these statuses
    @PrePersist
    public void prePersist() {
        if (isPaid == null) {
            isPaid = false;
        }

        if (isCanceled == null) {
            isCanceled = false;
        }
    }


}
