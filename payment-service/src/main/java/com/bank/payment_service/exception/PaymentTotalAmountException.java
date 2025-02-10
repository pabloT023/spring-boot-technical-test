package com.bank.payment_service.exception;

public class PaymentTotalAmountException extends RuntimeException{
    public PaymentTotalAmountException(String message){
        super(message);
    }
}
