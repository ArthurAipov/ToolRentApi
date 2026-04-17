package com.example.firstapi.Exceptions;

import java.time.LocalDate;

public class ToolAlreadyReservedException extends RuntimeException {
    public ToolAlreadyReservedException(String message) {
        super(message);
    }

    public ToolAlreadyReservedException(Long toolId, LocalDate startDate, LocalDate endDate) {
        super("Tool with id " + toolId + " is already reserved for period from " + startDate + " to " + endDate);
    }
}
