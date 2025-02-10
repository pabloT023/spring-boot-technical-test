package com.bank.payment_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentMethodResponseDTO {

    private Long id;
    private String name;
    private String description;
}
