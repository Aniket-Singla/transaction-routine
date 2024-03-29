package com.tech.routine.mapper;

import com.tech.routine.domain.Transaction;
import com.tech.routine.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    @Mapping(target = "whetherNew", source = "isNew")
    Transaction toDomain(TransactionDTO dto, boolean isNew);

    TransactionDTO toDTO(Transaction domain);
}
