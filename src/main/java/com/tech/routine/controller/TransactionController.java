package com.tech.routine.controller;

import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionDTO> createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.create(transactionDTO);
    }
}
