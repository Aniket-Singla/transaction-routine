package com.tech.routine.service.impl;

import com.tech.routine.domain.Account;
import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.enums.EntryDirection;
import com.tech.routine.exception.NotFoundException;
import com.tech.routine.exception.ValidationException;
import com.tech.routine.mapper.TransactionMapper;
import com.tech.routine.repository.TransactionRepository;
import com.tech.routine.service.AccountService;
import com.tech.routine.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public Mono<TransactionDTO> create(TransactionDTO transactionDTO) {
        return validateDirection(transactionDTO)
                .flatMap(ig -> accountService.getById(transactionDTO.accountId()))
                .switchIfEmpty(Mono.error(new NotFoundException(Account.class.getSimpleName())))
                .flatMap(ig -> {
                    var transaction = transactionMapper.toDomain(transactionDTO, true);
                    transaction.setId(UUID.randomUUID());
                    return transactionRepository.save(transaction);
                })
                .map(transactionMapper::toDTO);

    }

    private Mono<TransactionDTO> validateDirection(TransactionDTO transactionDTO) {
        // Not sure if client will always push signed amount. Currently considering the same and validating.
        var expectedDirection = transactionDTO.type().getDirection();
        if (EntryDirection.POSITIVE.equals(expectedDirection) && BigDecimal.ZERO.compareTo(transactionDTO.amount()) > 0) {
            return Mono.error(new ValidationException(transactionDTO.type().getDescription() + " Should have positive amount"));
        }
        if (EntryDirection.NEGATIVE.equals(expectedDirection) && BigDecimal.ZERO.compareTo(transactionDTO.amount()) < 0) {
            return Mono.error(new ValidationException(transactionDTO.type().getDescription() + " Should have negative amount"));
        }
        return Mono.just(transactionDTO);
    }
}
