package com.tech.routine.service;

import com.tech.routine.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface TransactionService {

    /**
     * Create Transaction For an Account
     *
     * @param transactionDTO Transaction
     * @return TranssactionDTO created transaction
     */
    Mono<TransactionDTO> create(TransactionDTO transactionDTO);

}
