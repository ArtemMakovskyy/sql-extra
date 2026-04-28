package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.EmailOpenDTO;
import com.sql.sqlextra.entity.EmailOpen;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailOpenMapper {
    EmailOpenMapper INSTANCE = Mappers.getMapper(EmailOpenMapper.class);

    EmailOpenDTO toDTO(EmailOpen emailOpen);

    EmailOpen toEntity(EmailOpenDTO dto);

    List<EmailOpenDTO> toDTOList(List<EmailOpen> emailOpens);
}