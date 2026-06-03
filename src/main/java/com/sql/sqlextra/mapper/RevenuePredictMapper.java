package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.RevenuePredictDTO;
import com.sql.sqlextra.entity.RevenuePredict;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RevenuePredictMapper {
    RevenuePredictMapper INSTANCE = Mappers.getMapper(RevenuePredictMapper.class);

    RevenuePredictDTO toDTO(RevenuePredict revenuePredict);

    RevenuePredict toEntity(RevenuePredictDTO dto);

    List<RevenuePredictDTO> toDTOList(List<RevenuePredict> predicts);
}
