package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.OrderDTO;
import com.sql.sqlextra.entity.OrderEntity;
import com.sql.sqlextra.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDTO> findAll() {
        return orderMapper.toDTOList(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(order -> ResponseEntity.ok(orderMapper.toDTO(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderDTO create(@RequestBody OrderDTO orderDTO) {
        OrderEntity order = orderMapper.toEntity(orderDTO);
        return orderMapper.toDTO(service.save(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        OrderEntity order = orderMapper.toEntity(orderDTO);
        return ResponseEntity.ok(orderMapper.toDTO(service.save(order)));
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
        return orderMapper.toDTOList(service.findByGaSessionId(gaSessionId));
    }

    @GetMapping("/product/{itemId}")
    public List<OrderDTO> findByItemId(@PathVariable Long itemId) {
        return orderMapper.toDTOList(service.findByItemId(itemId));
    }
}