package com.tech.routine.service.impl;

import com.tech.routine.dto.AccountDTO;
import com.tech.routine.mapper.AccountMapper;
import com.tech.routine.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;

    @Override
    public Mono<AccountDTO> create(AccountDTO account) {
        return null;
    }

    @Override
    public Mono<AccountDTO> getById(UUID id) {
        return null;
    }
}
