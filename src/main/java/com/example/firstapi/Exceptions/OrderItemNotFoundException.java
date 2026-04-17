package com.example.firstapi.Exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(Long id) {
        super("OrderItem with id " + id + " was not found");
    }
}
