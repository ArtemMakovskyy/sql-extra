package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.AbTest;
import com.sql.sqlextra.repository.AbTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbTestService {

    private final AbTestRepository repository;

    public List<AbTest> findAll() {
        return repository.findAll();
    }

    public Optional<AbTest> findById(Long id) {
        return repository.findById(id);
    }

    public AbTest save(AbTest abTest) {
        return repository.save(abTest);
    }

    public List<AbTest> saveAll(List<AbTest> abTests) {
        return repository.saveAll(abTests);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<AbTest> findByGaSessionId(String gaSessionId) {
        return repository.findAll().stream()
                .filter(ab -> ab.getGaSessionId().equals(gaSessionId))
                .toList();
    }
}