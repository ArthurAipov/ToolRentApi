package com.example.firstapi.Exceptions;

public class OrderModificationNotAllowedException extends RuntimeException {
    public OrderModificationNotAllowedException(String message) {
        super(message);
    }

    public static OrderModificationNotAllowedException approvedOrder(Long orderId) {
        return new OrderModificationNotAllowedException(
                "Approved order with id " + orderId + " cannot be modified"
        );
    }
}
