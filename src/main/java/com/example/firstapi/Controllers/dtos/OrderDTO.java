package com.example.firstapi.Controllers.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.time.LocalDate;

public class OrderDTO {
    public record GetOrderDTO(
            Long id,
            Long userId,
            Boolean approve,
            LocalDate startDate,
            LocalDate endDate,
            Instant createdAt
    ) {
    }

    public record PostOrderDTO(
            @NotNull @Positive Long userId,
            @NotNull Boolean approve,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate
    ) {
    }

    public record UpdateOrderDTO(
            @NotNull @Positive Long id,
            @NotNull @Positive Long userId,
            @NotNull Boolean approve,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate
    ) {
    }
}
