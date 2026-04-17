package com.example.firstapi.Exceptions;

public class RoleValidationException extends RuntimeException {
    public RoleValidationException(String message) {
        super(message);
    }
}
