package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.SessionDTO;
import com.sql.sqlextra.entity.Session;
import com.sql.sqlextra.mapper.SessionMapper;
import com.sql.sqlextra.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;
    private final SessionMapper mapper;

    public List<SessionDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<SessionDTO> findById(String gaSessionId) {
        return repository.findById(gaSessionId).map(mapper::toDTO);
    }

    public SessionDTO save(SessionDTO dto) {
        Session entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(String gaSessionId) {
        repository.deleteById(gaSessionId);
    }

    public boolean existsById(String gaSessionId) {
        return repository.existsById(gaSessionId);
    }

    public List<SessionDTO> saveAll(List<SessionDTO> dtos) {
        List<Session> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }
}
