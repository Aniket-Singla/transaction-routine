package com.tech.routine.mapper;

import com.tech.routine.domain.Account;
import com.tech.routine.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    @Mapping(target = "whetherNew", source = "isNew")
    Account toDomain(AccountDTO dto, boolean isNew);

    AccountDTO toDTO(Account domain);
}
