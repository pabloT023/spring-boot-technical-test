package com.bank.orders_service.exception;

public class OrderPaidException extends RuntimeException{

    public OrderPaidException(String message){
        super(message);
    }
}
