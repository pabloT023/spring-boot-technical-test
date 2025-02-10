package com.bank.orders_service.exception;

public class OrderCanceledException extends RuntimeException{

    public OrderCanceledException(String message){
        super(message);
    }
}
