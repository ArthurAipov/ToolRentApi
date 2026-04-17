package com.example.firstapi.Exceptions;

public class DuplicateToolInOrderException extends RuntimeException {
    public DuplicateToolInOrderException(String message) {
        super(message);
    }

    public DuplicateToolInOrderException(Long orderId, Long toolId) {
        super("Tool with id " + toolId + " is already added to order with id " + orderId);
    }
}
