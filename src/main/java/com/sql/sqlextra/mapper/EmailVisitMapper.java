package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.EmailVisitDTO;
import com.sql.sqlextra.entity.EmailVisit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailVisitMapper {
    EmailVisitMapper INSTANCE = Mappers.getMapper(EmailVisitMapper.class);

    EmailVisitDTO toDTO(EmailVisit emailVisit);

    EmailVisit toEntity(EmailVisitDTO dto);

    List<EmailVisitDTO> toDTOList(List<EmailVisit> emailVisits);
}