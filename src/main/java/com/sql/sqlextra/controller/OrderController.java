package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.OrderDTO;
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
    public List<OrderDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        return service.save(orderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(orderDTO));
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
    public List<OrderDTO> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }

    @GetMapping("/product/{itemId}")
    public List<OrderDTO> findByItemId(@PathVariable Long itemId) {
        return service.findByItemId(itemId);
    }
}
