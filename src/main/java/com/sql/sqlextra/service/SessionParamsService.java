package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.SessionParamsDTO;
import com.sql.sqlextra.entity.SessionParams;
import com.sql.sqlextra.mapper.SessionParamsMapper;
import com.sql.sqlextra.repository.SessionParamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionParamsService {

    private final SessionParamsRepository repository;
    private final SessionParamsMapper mapper;

    public List<SessionParamsDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<SessionParamsDTO> findById(String gaSessionId) {
        return repository.findById(gaSessionId).map(mapper::toDTO);
    }

    public SessionParamsDTO save(SessionParamsDTO dto) {
        SessionParams entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(String gaSessionId) {
        repository.deleteById(gaSessionId);
    }

    public boolean existsById(String gaSessionId) {
        return repository.existsById(gaSessionId);
    }

    public List<SessionParamsDTO> saveAll(List<SessionParamsDTO> dtos) {
        List<SessionParams> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }
}
