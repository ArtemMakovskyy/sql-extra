package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.AbTestDTO;
import com.sql.sqlextra.entity.AbTest;
import com.sql.sqlextra.entity.AbTestId;
import com.sql.sqlextra.mapper.AbTestMapper;
import com.sql.sqlextra.repository.AbTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbTestService {

    private final AbTestRepository repository;
    private final AbTestMapper mapper;

    public List<AbTestDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<AbTestDTO> findById(AbTestId id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public AbTestDTO save(AbTestDTO dto) {
        AbTest entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(AbTestId id) {
        repository.deleteById(id);
    }

    public boolean existsById(AbTestId id) {
        return repository.existsById(id);
    }

    public List<AbTestDTO> saveAll(List<AbTestDTO> dtos) {
        List<AbTest> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<AbTestDTO> findByGaSessionId(String gaSessionId) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(ab -> ab.getGaSessionId().equals(gaSessionId))
                .toList());
    }
}
