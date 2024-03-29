package com.tech.routine.service.impl;

import com.tech.routine.dto.AccountDTO;
import com.tech.routine.exception.BaseException;
import com.tech.routine.exception.NotFoundException;
import com.tech.routine.mapper.AccountMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Spy
    private AccountMapperImpl accountMapper;
    @InjectMocks
    AccountServiceImpl accountService;


    @Test
    void create_expectSuccess() {
        var documentNumber = "testNumber";
        var account = AccountDTO.builder()
                .documentNumber(documentNumber)
                .build();
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
        StepVerifier.create(accountService.create(account))
                .expectError(BaseException.class)
                .verify();
    }

    @Test
    void getById_expectResult() {
        var accountId = UUID.randomUUID();
        var res = AccountDTO.builder()
                .id(accountId)
                .documentNumber("testNumber")
                .build();
        StepVerifier.create(accountService.getById(accountId))
                .expectNext(res)
                .verifyComplete();
    }

    @Test
    void getById_expectNotFound() {
        var accountId = UUID.randomUUID();
        StepVerifier.create(accountService.getById(accountId))
                .expectError(NotFoundException.class)
                .verify();
    }

}

