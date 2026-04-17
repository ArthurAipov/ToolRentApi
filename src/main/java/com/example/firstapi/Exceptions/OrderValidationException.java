package com.example.firstapi.Exceptions;

public class OrderValidationException extends RuntimeException {
    public OrderValidationException(String message) {
        super(message);
    }

    public static OrderValidationException invalidDates() {
        return new OrderValidationException("Order start date cannot be after end date");
    }
}
