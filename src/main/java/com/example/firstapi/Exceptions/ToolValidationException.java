package com.example.firstapi.Exceptions;

public class ToolValidationException extends RuntimeException {
    public ToolValidationException(String message) {
        super(message);
    }
}
