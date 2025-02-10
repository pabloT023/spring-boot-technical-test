package com.bank.payment_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long id;
    private List<PaymentTransactionResponseDTO> transactions;
    private Boolean isCompleted;
}
