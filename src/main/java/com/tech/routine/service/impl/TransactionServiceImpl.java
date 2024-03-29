package com.tech.routine.service.impl;

import com.tech.routine.dto.TransactionDTO;
import com.tech.routine.mapper.TransactionMapper;
import com.tech.routine.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;

    @Override
    public Mono<TransactionDTO> create(TransactionDTO transactionDTO) {
        return null;
    }
}
