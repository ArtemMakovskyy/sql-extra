package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.Product;
import com.sql.sqlextra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(Integer itemId) {
        return repository.findById(itemId);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void deleteById(Integer itemId) {
        repository.deleteById(itemId);
    }

    public boolean existsById(Integer itemId) {
        return repository.existsById(itemId);
    }
}