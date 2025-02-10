package com.bank.payment_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PaymentTransaction> paymentTransactions;

    @Column(name = "is_completed")
    private Boolean isCompleted;

}
