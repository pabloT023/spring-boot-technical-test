package com.bank.payment_service.exception;

public class PaymentMethodNotFoundException extends RuntimeException{
    public PaymentMethodNotFoundException(String message){
        super(message);
    }
}

