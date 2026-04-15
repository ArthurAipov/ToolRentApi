package com.example.firstapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RoleDTO {
    public record GetRoleDTO(
            Long id,
            String name
    ) {
    }

    public record PostRoleDTO(
            @NotBlank
            String name
    ) {
    }

    public record UpdateRoleDTO(
            @NotNull
            @Positive
            Long id,
            @NotBlank
            String name
    ) {
    }

}
