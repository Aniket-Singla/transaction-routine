package com.tech.routine.controller;

import com.tech.routine.dto.AccountDTO;
import com.tech.routine.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@Tag(name = "Accounts Resource Controller")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "Create Account By Providing Required Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDTO> createAccount(@RequestBody @Valid AccountDTO account){
        return accountService.create(account);
    }

    @Operation(summary = "Get Account By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    }
    )
    @GetMapping("{id}")
    public Mono<AccountDTO> getById(@PathVariable UUID id) {
        return accountService.getById(id);
    }
}
