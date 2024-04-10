package com.tech.routine.repository;

import com.tech.routine.domain.Transaction;
import com.tech.routine.enums.TransactionType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {

    @Query("select * from routine.transactions where account_id = :accountId and balance < 0.0")
    Flux<Transaction> getTransactionWithBalanceForGivenAccount(UUID accountId);
}
