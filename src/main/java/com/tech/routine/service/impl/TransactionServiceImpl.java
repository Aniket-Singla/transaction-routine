package com.tech.routine.service.impl;

import com.tech.routine.domain.Account;
import com.tech.routine.domain.Transaction;
import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.enums.EntryDirection;
import com.tech.routine.enums.TransactionType;
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
import java.util.ArrayList;
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
                    if(TransactionType.CREDIT_VOUCHER.equals(transactionDTO.type())) {
                        return getUpdatedTransactionDTOAfterDischargingDebits(transactionDTO);
                    }
                    else {
                        return Mono.just(transactionDTO.withBalance(transactionDTO.amount()));
                    }
                })
                .flatMap(updatedTransaction -> {
                    var transaction = transactionMapper.toDomain(updatedTransaction, true);
                    transaction.setId(UUID.randomUUID());
                    return transactionRepository.save(transaction);
                })
                .map(transactionMapper::toDTO);
    }

    /**
     *
     * @param transactionDTO
     * @return
     */
    private Mono<TransactionDTO> getUpdatedTransactionDTOAfterDischargingDebits(TransactionDTO transactionDTO) {
        // 1. transactions to balance exists , or not exists.
        return transactionRepository.getTransactionWithBalanceForGivenAccount(transactionDTO.accountId())
                .collectList()
                .flatMap(transactionList -> {
                    var toBalance = transactionDTO.amount();
                    var participatingTransactions = new ArrayList<Transaction>();
                    for(int transactionIndex=0; transactionIndex<transactionList.size(); transactionIndex++) {
                        var transactionToUpdate = transactionList.get(transactionIndex);
                        var amountUtilized = getUtilizableBalance(transactionToUpdate.getBalance(), toBalance);
                        transactionToUpdate.setBalance(transactionToUpdate.getBalance().add(amountUtilized));
                        participatingTransactions.add(transactionToUpdate);
                        toBalance = toBalance.subtract(amountUtilized);
                        if(toBalance.equals(new BigDecimal(0))){
                            break;
                        }
                    }

                    return transactionRepository.saveAll(participatingTransactions)
                            .then(Mono.just(transactionDTO.withBalance(toBalance)));
                });
    }

    /**
     * Get the amount that can be utilized from given transaction.
     * @param existingBalance is negative
     * @param toBalance is positive
     * @return Positive amount that was utilized
     */
    private BigDecimal getUtilizableBalance(BigDecimal existingBalance, BigDecimal toBalance) {
        existingBalance = existingBalance.abs();
        return existingBalance.min(toBalance);
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
