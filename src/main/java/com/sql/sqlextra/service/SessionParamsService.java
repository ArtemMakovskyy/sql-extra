package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.SessionParams;
import com.sql.sqlextra.repository.SessionParamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionParamsService {

    private final SessionParamsRepository repository;

    public List<SessionParams> findAll() {
        return repository.findAll();
    }

    public Optional<SessionParams> findById(String gaSessionId) {
        return repository.findById(gaSessionId);
    }

    public SessionParams save(SessionParams sessionParams) {
        return repository.save(sessionParams);
    }

    public void deleteById(String gaSessionId) {
        repository.deleteById(gaSessionId);
    }

    public boolean existsById(String gaSessionId) {
        return repository.existsById(gaSessionId);
    }
}