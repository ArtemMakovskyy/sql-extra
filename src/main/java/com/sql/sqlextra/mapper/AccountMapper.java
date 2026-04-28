package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.AccountDTO;
import com.sql.sqlextra.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDTO(Account account);

    Account toEntity(AccountDTO dto);

    List<AccountDTO> toDTOList(List<Account> accounts);
}