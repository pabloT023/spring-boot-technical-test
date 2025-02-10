package com.bank.payment_service.exception;

public class OrderCanceledException extends RuntimeException{

    public OrderCanceledException(String message){
        super(message);
    }
}
