package com.tech.routine.service;

import com.tech.routine.dto.AccountDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountService {

    Mono<AccountDTO> create(AccountDTO account);

    Mono<AccountDTO> getById(UUID id);
}
