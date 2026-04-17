package com.example.firstapi.Exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Order with id " + orderId + " was not found");
    }
}

