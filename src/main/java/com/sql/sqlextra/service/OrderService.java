package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.OrderEntity;
import com.sql.sqlextra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public List<OrderEntity> findAll() {
        return repository.findAll();
    }

    public Optional<OrderEntity> findById(Long id) {
        return repository.findById(id);
    }

    public OrderEntity save(OrderEntity order) {
        return repository.save(order);
    }

    public List<OrderEntity> saveAll(List<OrderEntity> orders) {
        return repository.saveAll(orders);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<OrderEntity> findByGaSessionId(String gaSessionId) {
        return repository.findAll().stream()
                .filter(o -> o.getGaSessionId().equals(gaSessionId))
                .toList();
    }

    public List<OrderEntity> findByItemId(Long itemId) {
        return repository.findAll().stream()
                .filter(o -> o.getItemId().equals(itemId))
                .toList();
    }
}