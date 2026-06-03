package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.EventParamsDTO;
import com.sql.sqlextra.entity.EventParams;
import com.sql.sqlextra.mapper.EventParamsMapper;
import com.sql.sqlextra.repository.EventParamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventParamsService {

    private final EventParamsRepository repository;
    private final EventParamsMapper mapper;

    public List<EventParamsDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<EventParamsDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public EventParamsDTO save(EventParamsDTO dto) {
        EventParams entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EventParamsDTO> saveAll(List<EventParamsDTO> dtos) {
        List<EventParams> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<EventParamsDTO> findByGaSessionId(String gaSessionId) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(ep -> ep.getGaSessionId().equals(gaSessionId))
                .toList());
    }
}
