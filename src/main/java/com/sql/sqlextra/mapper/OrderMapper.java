package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.OrderDTO;
import com.sql.sqlextra.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(OrderEntity order);

    OrderEntity toEntity(OrderDTO dto);

    List<OrderDTO> toDTOList(List<OrderEntity> orders);
}