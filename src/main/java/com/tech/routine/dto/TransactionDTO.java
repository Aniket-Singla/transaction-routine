package com.tech.routine.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tech.routine.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public record TransactionDTO(
        UUID id,
        @NotNull
        UUID accountId,
        @NotNull
        BigDecimal amount,
        @NotNull
        TransactionType type,
        Instant createdAt
) {
}
