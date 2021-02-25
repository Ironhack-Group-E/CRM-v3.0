package com.ironhack.salesRepservice.service.exceptions;

public class SalesRepNotFoundException extends RuntimeException{

    public SalesRepNotFoundException(String message) {
        super(message);
    }

    public SalesRepNotFoundException(Throwable cause) {
        super(cause);
    }

    public SalesRepNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
