package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.PaidSearchCostDTO;
import com.sql.sqlextra.entity.PaidSearchCost;
import com.sql.sqlextra.mapper.PaidSearchCostMapper;
import com.sql.sqlextra.repository.PaidSearchCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaidSearchCostService {

    private final PaidSearchCostRepository repository;
    private final PaidSearchCostMapper mapper;

    public List<PaidSearchCostDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<PaidSearchCostDTO> findById(LocalDate date) {
        return repository.findById(date).map(mapper::toDTO);
    }

    public PaidSearchCostDTO save(PaidSearchCostDTO dto) {
        PaidSearchCost entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(LocalDate date) {
        repository.deleteById(date);
    }

    public boolean existsById(LocalDate date) {
        return repository.existsById(date);
    }

    public List<PaidSearchCostDTO> saveAll(List<PaidSearchCostDTO> dtos) {
        List<PaidSearchCost> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }
}
