package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.EventParamsDTO;
import com.sql.sqlextra.entity.EventParams;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventParamsMapper {
    EventParamsMapper INSTANCE = Mappers.getMapper(EventParamsMapper.class);

    EventParamsDTO toDTO(EventParams eventParams);

    EventParams toEntity(EventParamsDTO dto);

    List<EventParamsDTO> toDTOList(List<EventParams> events);
}
