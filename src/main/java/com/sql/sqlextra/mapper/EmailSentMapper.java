package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.EmailSentDTO;
import com.sql.sqlextra.entity.EmailSent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailSentMapper {
    EmailSentMapper INSTANCE = Mappers.getMapper(EmailSentMapper.class);

    EmailSentDTO toDTO(EmailSent emailSent);

    EmailSent toEntity(EmailSentDTO dto);

    List<EmailSentDTO> toDTOList(List<EmailSent> emailSents);
}