package com.bank.payment_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaymentTransactionResponseDTO {

    private Long id;
    private BigDecimal amount;
    private PaymentMethodResponseDTO paymentMethod;
}
