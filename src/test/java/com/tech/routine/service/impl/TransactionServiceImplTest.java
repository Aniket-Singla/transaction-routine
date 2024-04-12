package com.tech.routine.service.impl;

import com.tech.routine.dto.AccountDTO;
import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.enums.TransactionType;
import com.tech.routine.exception.BaseException;
import com.tech.routine.exception.ValidationException;
import com.tech.routine.mapper.TransactionMapperImpl;
import com.tech.routine.repository.TransactionRepository;
import com.tech.routine.service.AccountService;
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

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Spy
    private TransactionMapperImpl transactionMapper;
    @Mock
    private AccountService accountService;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    TransactionServiceImpl transactionService;


    @Test
    void create__whenDebit_expectSuccess() {
        var accountId = UUID.randomUUID();
        var transaction = TransactionDTO.builder()
                .accountId(accountId)
                .amount(new BigDecimal(-10))
                .type(TransactionType.WITHDRAWL).build();
        Mockito.when(accountService.getById(any(UUID.class))).thenReturn(Mono.just(AccountDTO.builder().build()));
        Mockito.when(transactionRepository.save(any()))
                .thenAnswer(invocation -> {
                    var obj = invocation.getArguments()[0];
                    return Mono.just(obj);
                });
        StepVerifier.create(transactionService.create(transaction))
                .assertNext(res -> {
                    Assertions.assertEquals(accountId, res.accountId());
                }).verifyComplete();
    }

    @Test
    void create_whenNegativeForCreditVoucher_exceptValidationError() {
        var accountId = UUID.randomUUID();
        var transaction = TransactionDTO.builder()
                .accountId(accountId)
                .amount(new BigDecimal(-10))
                .type(TransactionType.CREDIT_VOUCHER).build();
        StepVerifier.create(transactionService.create(transaction))
                .expectError(ValidationException.class)
                .verify();
    }

    @Test
    void create_whenPositiveForPurchase_exceptValidationError() {
        var accountId = UUID.randomUUID();
        var transaction = TransactionDTO.builder()
                .accountId(accountId)
                .amount(new BigDecimal(10))
                .type(TransactionType.PURCHASE_WITH_INSTALLMENTS).build();
        StepVerifier.create(transactionService.create(transaction))
                .expectError(ValidationException.class)
                .verify();
    }

    @Test
    void create_expectMissingAccountException() {
        var accountId = UUID.randomUUID();
        var transaction = TransactionDTO.builder()
                .accountId(accountId)
                .amount(new BigDecimal(10))
                .type(TransactionType.CREDIT_VOUCHER).build();
        Mockito.when(accountService.getById(any(UUID.class))).thenReturn(Mono.empty());
        StepVerifier.create(transactionService.create(transaction))
                .expectError(BaseException.class)
                .verify();
    }
}

