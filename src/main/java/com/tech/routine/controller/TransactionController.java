package com.tech.routine.controller;

import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
@Tag(name = "Transaction Resource Controller")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Create Transaction By Providing Required Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.create(transactionDTO);
    }
}
