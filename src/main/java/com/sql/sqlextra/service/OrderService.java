package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.OrderDTO;
import com.sql.sqlextra.entity.OrderEntity;
import com.sql.sqlextra.mapper.OrderMapper;
import com.sql.sqlextra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public List<OrderDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<OrderDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public OrderDTO save(OrderDTO dto) {
        OrderEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<OrderDTO> saveAll(List<OrderDTO> dtos) {
        List<OrderEntity> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<OrderDTO> findByGaSessionId(String gaSessionId) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(o -> o.getGaSessionId().equals(gaSessionId))
                .toList());
    }

    public List<OrderDTO> findByItemId(Long itemId) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(o -> o.getItemId().equals(itemId))
                .toList());
    }
}
