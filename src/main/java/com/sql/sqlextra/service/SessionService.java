package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.Session;
import com.sql.sqlextra.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;

    public List<Session> findAll() {
        return repository.findAll();
    }

    public Optional<Session> findById(String gaSessionId) {
        return repository.findById(gaSessionId);
    }

    public Session save(Session session) {
        return repository.save(session);
    }

    public void deleteById(String gaSessionId) {
        repository.deleteById(gaSessionId);
    }

    public boolean existsById(String gaSessionId) {
        return repository.existsById(gaSessionId);
    }
}