package com.tech.routine.service;

import com.tech.routine.dto.AccountDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountService {

    /**
     * Create Account for given Data
     *
     * @param account AccountDTO
     * @return AccountDTO
     */
    Mono<AccountDTO> create(AccountDTO account);

    /**
     * Get Account by Id
     *
     * @param id Identifier
     * @return Account
     */
    Mono<AccountDTO> getById(UUID id);
}
