package com.tech.routine.mapper;

import com.tech.routine.domain.Transaction;
import com.tech.routine.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    Transaction toDomain(TransactionDTO dto);

    TransactionDTO toDTO(Transaction domain);
}
