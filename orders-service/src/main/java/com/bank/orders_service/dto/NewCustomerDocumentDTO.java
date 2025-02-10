package com.bank.orders_service.dto;

import com.bank.orders_service.model.Customer;
import com.bank.orders_service.model.CustomerDocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewCustomerDocumentDTO {

    private Long id;
    private NewCustomerDocumentTypeDTO documentType;
    private String number;
}
