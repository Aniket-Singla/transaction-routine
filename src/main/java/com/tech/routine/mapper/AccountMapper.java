package com.tech.routine.mapper;

import com.tech.routine.domain.Account;
import com.tech.routine.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    Account toDomain(AccountDTO dto);

    AccountDTO toDTO(Account domain);
}
