package com.tech.routine.service.impl;

import com.tech.routine.domain.Account;
import com.tech.routine.dto.AccountDTO;
import com.tech.routine.exception.AlreadyExistException;
import com.tech.routine.exception.NotFoundException;
import com.tech.routine.mapper.AccountMapper;
import com.tech.routine.repository.AccountRepository;
import com.tech.routine.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Override
    public Mono<AccountDTO> create(AccountDTO account) {
        var domain = accountMapper.toDomain(account, true);
        domain.setId(UUID.randomUUID());
        return accountRepository.findByDocumentNumber(account.documentNumber())
                .flatMap(existing -> Mono.error(new AlreadyExistException(Account.class.getSimpleName())))
                .switchIfEmpty(Mono.defer(() -> accountRepository.save(domain)))
                .cast(Account.class)
                .map(accountMapper::toDTO)
                .doOnError(error -> log.error("Error while saving Account", error));
    }

    @Override
    public Mono<AccountDTO> getById(UUID id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(Account.class.getSimpleName())))
                .map(accountMapper::toDTO);
    }
}
