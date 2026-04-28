package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.ProductDTO;
import com.sql.sqlextra.entity.Product;
import com.sql.sqlextra.mapper.ProductMapper;
import com.sql.sqlextra.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> findAll() {
        return productMapper.toDTOList(service.findAll());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long itemId) {
        return service.findById(itemId)
                .map(product -> ResponseEntity.ok(productMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDTO(service.save(product));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long itemId, @RequestBody ProductDTO productDTO) {
        if (!service.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        Product product = productMapper.toEntity(productDTO);
        return ResponseEntity.ok(productMapper.toDTO(service.save(product)));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Long itemId) {
        if (!service.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }
}