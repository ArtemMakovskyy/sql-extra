package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.Product;
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
    public List<Product> findAll() {
        return service.findAll();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Product> findById(@PathVariable Integer itemId) {
        return service.findById(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Product> update(@PathVariable Integer itemId, @RequestBody Product product) {
        if (!service.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(product));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Integer itemId) {
        if (!service.existsById(itemId)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }
}