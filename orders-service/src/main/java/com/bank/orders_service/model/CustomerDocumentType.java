package com.bank.orders_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_document_types")
public class CustomerDocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_document_type_id")
    private Long id;

    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

}
