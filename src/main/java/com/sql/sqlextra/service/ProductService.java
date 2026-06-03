package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.ProductDTO;
import com.sql.sqlextra.entity.Product;
import com.sql.sqlextra.mapper.ProductMapper;
import com.sql.sqlextra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<ProductDTO> findById(Long itemId) {
        return repository.findById(itemId).map(mapper::toDTO);
    }

    public ProductDTO save(ProductDTO dto) {
        Product entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long itemId) {
        repository.deleteById(itemId);
    }

    public boolean existsById(Long itemId) {
        return repository.existsById(itemId);
    }

    public List<ProductDTO> saveAll(List<ProductDTO> dtos) {
        List<Product> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public long count() {
        return repository.count();
    }
}
