package com.example.products_service.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
