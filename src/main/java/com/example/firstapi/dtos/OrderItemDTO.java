package com.example.firstapi.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class OrderItemDTO {
    public record GetOrderItemDTO(
            Long id,
            Long orderId,
            Long toolId,
            LocalDate startDate,
            LocalDate endDate,
            Long price
    ) {
    }

    public record PostOrderItemDTO(
            @NotNull @Positive Long orderId,
            @NotNull @Positive Long toolId,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @NotNull @Min(0) Long price
    ) {
    }

    public record UpdateOrderItemDTO(
            @NotNull @Positive Long id,
            @NotNull @Positive Long orderId,
            @NotNull @Positive Long toolId,
            @NotNull LocalDate startDate,
            @NotNull LocalDate endDate,
            @NotNull @Min(0) Long price
    ) {
    }

}
