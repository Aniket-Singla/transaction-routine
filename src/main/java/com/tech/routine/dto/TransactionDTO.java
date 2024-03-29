package com.tech.routine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public record TransactionDTO(
        UUID id,
        @NotNull
        UUID accountId,
        @NotNull
        Double amount // see if we want to use bigdecimal or float here.
) {
}
