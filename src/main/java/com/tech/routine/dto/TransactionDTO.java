package com.tech.routine.dto;

import java.util.UUID;

public record TransactionDTO(
        UUID id,
        UUID accountId,
        Double amount // see if we want to use bigdecimal or float here.
) {
}
