package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.OrderEntity;
import com.sql.sqlextra.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping
    public List<OrderEntity> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderEntity create(@RequestBody OrderEntity order) {
        return service.save(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderEntity> update(@PathVariable Long id, @RequestBody OrderEntity order) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/session/{gaSessionId}")
    public List<OrderEntity> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }

    @GetMapping("/product/{itemId}")
    public List<OrderEntity> findByItemId(@PathVariable Integer itemId) {
        return service.findByItemId(itemId);
    }
}