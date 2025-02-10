package com.bank.payment_service.exception;

public class OrderPaidException extends RuntimeException{

    public OrderPaidException(String message){
        super(message);
    }
}
