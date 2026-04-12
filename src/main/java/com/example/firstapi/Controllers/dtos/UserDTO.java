package com.example.firstapi.Controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class UserDTO {
    public record GetUserDTO(
            Long id,
            String alias,
            String name,
            String surname,
            String role,
            String chatId,
            Boolean blackList,
            Boolean approve,
            Instant createdAt
    ) {
    }

    public record PostUserDTO(
            @NotBlank String alias,
            @NotBlank String name,
            @NotBlank String surname,
            @NotNull @Positive Long roleId,
            @NotBlank String chatId
    ) {
    }

    public record UpdateUserDTO(
            @NotNull @Positive Long id,
            @NotBlank String alias,
            @NotBlank String name,
            @NotBlank String surname,
            @NotNull Boolean blackList,
            @NotNull Boolean approve
    ) {
    }

}
