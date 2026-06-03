package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.AccountSessionDTO;
import com.sql.sqlextra.entity.AccountSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountSessionMapper {
    AccountSessionMapper INSTANCE = Mappers.getMapper(AccountSessionMapper.class);

    @Mapping(target = "accountId", source = "id.accountId")
    @Mapping(target = "gaSessionId", source = "id.gaSessionId")
    AccountSessionDTO toDTO(AccountSession session);

    @Mapping(target = "id.accountId", source = "accountId")
    @Mapping(target = "id.gaSessionId", source = "gaSessionId")
    AccountSession toEntity(AccountSessionDTO dto);

    List<AccountSessionDTO> toDTOList(List<AccountSession> sessions);
}