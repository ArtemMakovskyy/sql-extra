package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.AccountSessionDTO;
import com.sql.sqlextra.entity.AccountSession;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountSessionMapper {
    AccountSessionMapper INSTANCE = Mappers.getMapper(AccountSessionMapper.class);

    AccountSessionDTO toDTO(AccountSession session);

    AccountSession toEntity(AccountSessionDTO dto);

    List<AccountSessionDTO> toDTOList(List<AccountSession> sessions);
}