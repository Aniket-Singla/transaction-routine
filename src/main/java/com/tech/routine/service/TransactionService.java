package com.tech.routine.service;

import com.tech.routine.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<TransactionDTO> create(TransactionDTO transactionDTO);

}
