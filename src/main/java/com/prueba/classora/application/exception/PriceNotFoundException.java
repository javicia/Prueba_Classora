package com.prueba.classora.application.exception;

public class PriceNotFoundException extends RuntimeException{
    public PriceNotFoundException(String message) {
        super(message);
    }
}
