package com.tech.routine.controller;

import com.tech.routine.dto.AccountDTO;
import com.tech.routine.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public Mono<AccountDTO> createAccount(@RequestBody @Valid AccountDTO account){
        return accountService.create(account);
    }

    @GetMapping("{id}")
    public Mono<AccountDTO> getById(@PathVariable UUID id) {
        return accountService.getById(id);
    }
}
