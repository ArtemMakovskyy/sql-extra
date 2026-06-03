package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.ProductDTO;
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

    @GetMapping
    public List<ProductDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long itemId) {
        return service.findById(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        return service.save(productDTO);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long itemId, @RequestBody ProductDTO productDTO) {
        if (!service.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(productDTO));
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
