package com.example.firstapi.Controllers.dtos;

import com.example.firstapi.Utilities.ToolStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class ToolDTO {
    public record GetToolDTO(
            Long id,
            String name,
            Long price,
            String callbackData,
            ToolStatus toolStatus,
            Instant createdAt
    ) {
    }

    public record PostToolDTO(
            @NotBlank String name,
            @NotNull @Min(0) Long price,
            String callbackData,
            @NotNull ToolStatus toolStatus
    ) {
    }

    public record UpdateToolDTO(
            @NotNull @Positive Long id,
            @NotBlank String name,
            @NotNull @Min(0) Long price,
            String callbackData,
            @NotBlank String toolStatus
    ) {
    }

    public record UpdateToolStatus(
            @NotNull @Positive Long id,
            @NotBlank String status
    ){}

}
