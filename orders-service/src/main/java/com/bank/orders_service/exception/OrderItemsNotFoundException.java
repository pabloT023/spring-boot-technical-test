package com.bank.orders_service.exception;

public class OrderItemsNotFoundException extends RuntimeException{
    public OrderItemsNotFoundException(String message){
        super(message);
    }
}
