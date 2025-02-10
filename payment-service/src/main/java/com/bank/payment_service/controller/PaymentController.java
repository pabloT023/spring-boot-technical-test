package com.bank.payment_service.controller;

import com.bank.payment_service.dto.PaymentResponseDTO;
import com.bank.payment_service.model.Payment;
import com.bank.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    // Endpoint to pay the order
    @PostMapping
    public PaymentResponseDTO createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }
}
