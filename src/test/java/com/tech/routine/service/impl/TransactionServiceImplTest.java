package com.tech.routine.service.impl;

import com.tech.routine.domain.TransactionType;
import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.exception.BaseException;
import com.tech.routine.mapper.TransactionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Spy
    private TransactionMapper transactionMapper;
    @InjectMocks
    TransactionServiceImpl transactionService;


    @Test
    void create_expectSuccess() {
        var accountId = UUID.randomUUID();
        var transaction = TransactionDTO.builder()
                .accountId(accountId)
                .amount(new BigDecimal(10))
                .type(TransactionType.WITHDRAWL).build();
        StepVerifier.create(transactionService.create(transaction))
                .assertNext(res -> {
                    Assertions.assertEquals(accountId, res.accountId());
                }).verifyComplete();
    }

    @Test
    void create_expectAlreadyExistsError() {
        var accountId = UUID.randomUUID();
        var transaction = TransactionDTO.builder()
                .accountId(accountId)
                .amount(new BigDecimal(10))
                .type(TransactionType.WITHDRAWL).build();
        StepVerifier.create(transactionService.create(transaction))
                .expectError(BaseException.class)
                .verify();
    }
}

