package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.RevenuePredictDTO;
import com.sql.sqlextra.entity.RevenuePredict;
import com.sql.sqlextra.mapper.RevenuePredictMapper;
import com.sql.sqlextra.repository.RevenuePredictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RevenuePredictService {

    private final RevenuePredictRepository repository;
    private final RevenuePredictMapper mapper;

    public List<RevenuePredictDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<RevenuePredictDTO> findById(LocalDate date) {
        return repository.findById(date).map(mapper::toDTO);
    }

    public RevenuePredictDTO save(RevenuePredictDTO dto) {
        RevenuePredict entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(LocalDate date) {
        repository.deleteById(date);
    }

    public boolean existsById(LocalDate date) {
        return repository.existsById(date);
    }

    public List<RevenuePredictDTO> saveAll(List<RevenuePredictDTO> dtos) {
        List<RevenuePredict> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }
}
