package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.EventParams;
import com.sql.sqlextra.repository.EventParamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventParamsService {

    private final EventParamsRepository repository;

    public List<EventParams> findAll() {
        return repository.findAll();
    }

    public Optional<EventParams> findById(Long id) {
        return repository.findById(id);
    }

    public EventParams save(EventParams eventParams) {
        return repository.save(eventParams);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EventParams> findByGaSessionId(String gaSessionId) {
        return repository.findAll().stream()
                .filter(ep -> ep.getGaSessionId().equals(gaSessionId))
                .toList();
    }
}