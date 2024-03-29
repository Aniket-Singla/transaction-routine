package com.tech.routine.service.impl;

import com.tech.routine.domain.Account;
import com.tech.routine.dto.AccountDTO;
import com.tech.routine.exception.AlreadyExistException;
import com.tech.routine.exception.NotFoundException;
import com.tech.routine.mapper.AccountMapperImpl;
import com.tech.routine.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Spy
    private AccountMapperImpl accountMapper;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    AccountServiceImpl accountService;


    @Test
    void create_expectSuccess() {
        var documentNumber = "testNumber";
        var account = AccountDTO.builder()
                .documentNumber(documentNumber)
                .build();
        Mockito.when(accountRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.empty());
        Mockito.when(accountRepository.save(any()))
                .thenAnswer(invocation -> {
                    var toSave = invocation.getArguments()[0];
                    return Mono.just(toSave);
                });
        StepVerifier.create(accountService.create(account))
                .assertNext(res -> {
                    Assertions.assertEquals(documentNumber, res.documentNumber());
                })
                .verifyComplete();
    }

    @Test
    void create_expectAlreadyExistsError() {
        var documentNumber = "testNumber";
        var account = AccountDTO.builder()
                .documentNumber(documentNumber)
                .build();
        Mockito.when(accountRepository.findByDocumentNumber(any()))
                .thenReturn(Mono.just(new Account(UUID.randomUUID(), documentNumber, false)));
        StepVerifier.create(accountService.create(account))
                .expectError(AlreadyExistException.class)
                .verify();
    }

    @Test
    void getById_expectResult() {
        var accountId = UUID.randomUUID();
        Mockito.when(accountRepository.findById(any(UUID.class)))
                .thenReturn(Mono.just(new Account(UUID.randomUUID(), "testNumber", false)));
        StepVerifier.create(accountService.getById(accountId))
                .assertNext(res -> {
                    Assertions.assertEquals("testNumber", res.documentNumber());
                })
                .verifyComplete();
    }

    @Test
    void getById_expectNotFound() {
        var accountId = UUID.randomUUID();
        Mockito.when(accountRepository.findById(any(UUID.class))).thenReturn(Mono.empty());
        StepVerifier.create(accountService.getById(accountId))
                .expectError(NotFoundException.class)
                .verify();
    }

}

