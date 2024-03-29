package com.tech.routine.dto;

import java.util.UUID;

public record AccountDTO(
        UUID id,
        String documentNumber
) {
}
