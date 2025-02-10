package com.bank.orders_service.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
