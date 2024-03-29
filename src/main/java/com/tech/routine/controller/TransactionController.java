package com.tech.routine.controller;

import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionDTO> createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.create(transactionDTO);
    }
}
