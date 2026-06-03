package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.SessionDTO;
import com.sql.sqlextra.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionDTO toDTO(Session session);

    Session toEntity(SessionDTO dto);

    List<SessionDTO> toDTOList(List<Session> sessions);
}
