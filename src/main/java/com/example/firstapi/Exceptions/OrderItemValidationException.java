package com.example.firstapi.Exceptions;

public class OrderItemValidationException extends RuntimeException {
    public OrderItemValidationException(String message) {
        super(message);
    }

    public static OrderItemValidationException invalidDates() {
        return new OrderItemValidationException("Order item start date cannot be after end date");
    }
}
