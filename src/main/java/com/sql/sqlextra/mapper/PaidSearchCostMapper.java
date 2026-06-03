package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.PaidSearchCostDTO;
import com.sql.sqlextra.entity.PaidSearchCost;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaidSearchCostMapper {
    PaidSearchCostMapper INSTANCE = Mappers.getMapper(PaidSearchCostMapper.class);

    PaidSearchCostDTO toDTO(PaidSearchCost paidSearchCost);

    PaidSearchCost toEntity(PaidSearchCostDTO dto);

    List<PaidSearchCostDTO> toDTOList(List<PaidSearchCost> costs);
}
