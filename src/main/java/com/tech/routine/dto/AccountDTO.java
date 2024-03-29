package com.tech.routine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public record AccountDTO(
        UUID id,
        @NotBlank
        @NotNull
        String documentNumber
) {
}
