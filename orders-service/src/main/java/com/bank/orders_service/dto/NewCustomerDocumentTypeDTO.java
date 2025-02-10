package com.bank.orders_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewCustomerDocumentTypeDTO {

    private Long id;
    private String name;
    private Boolean isActive;

}
