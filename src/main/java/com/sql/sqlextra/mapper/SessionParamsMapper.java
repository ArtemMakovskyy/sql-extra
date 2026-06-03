package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.SessionParamsDTO;
import com.sql.sqlextra.entity.SessionParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionParamsMapper {
    SessionParamsMapper INSTANCE = Mappers.getMapper(SessionParamsMapper.class);

    SessionParamsDTO toDTO(SessionParams sessionParams);

    SessionParams toEntity(SessionParamsDTO dto);

    List<SessionParamsDTO> toDTOList(List<SessionParams> sessionParams);
}
