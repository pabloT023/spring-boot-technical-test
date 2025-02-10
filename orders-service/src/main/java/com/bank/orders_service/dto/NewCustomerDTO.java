package com.bank.orders_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewCustomerDTO {


    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String address;

    private List<NewCustomerDocumentDTO> customerDocuments;

}
