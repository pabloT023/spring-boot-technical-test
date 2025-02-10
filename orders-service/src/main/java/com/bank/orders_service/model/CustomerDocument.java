package com.bank.orders_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.bank.orders_service.model.CustomerDocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customer_documents")
public class CustomerDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_document_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "customer_document_type_id", nullable = false)
    private CustomerDocumentType documentType;

    @Column(name = "document_number")
    private String documentNumber;
}
