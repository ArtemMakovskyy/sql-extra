package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.AbTestDTO;
import com.sql.sqlextra.entity.AbTest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AbTestMapper {
    AbTestMapper INSTANCE = Mappers.getMapper(AbTestMapper.class);

    AbTestDTO toDTO(AbTest abTest);

    AbTest toEntity(AbTestDTO dto);

    List<AbTestDTO> toDTOList(List<AbTest> abTests);
}
