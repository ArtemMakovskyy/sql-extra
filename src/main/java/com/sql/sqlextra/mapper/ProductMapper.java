package com.sql.sqlextra.mapper;

import com.sql.sqlextra.dto.ProductDTO;
import com.sql.sqlextra.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO dto);

    List<ProductDTO> toDTOList(List<Product> products);
}