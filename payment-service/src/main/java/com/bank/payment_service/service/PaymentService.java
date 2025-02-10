package com.bank.payment_service.service;

import com.bank.payment_service.dto.PaymentResponseDTO;
import com.bank.payment_service.model.Payment;

public interface PaymentService {
    PaymentResponseDTO createPayment(Payment payment);
}
