package com.example.firstapi.Exceptions;

public class ToolNotFoundException extends RuntimeException {
    public ToolNotFoundException(Long id) {
        super("Tool with id " + id + " was not found");
    }
}
