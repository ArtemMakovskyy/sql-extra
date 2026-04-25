package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.PaidSearchCost;
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

    public List<PaidSearchCost> findAll() {
        return repository.findAll();
    }

    public Optional<PaidSearchCost> findById(LocalDate date) {
        return repository.findById(date);
    }

    public PaidSearchCost save(PaidSearchCost paidSearchCost) {
        return repository.save(paidSearchCost);
    }

    public void deleteById(LocalDate date) {
        repository.deleteById(date);
    }

    public boolean existsById(LocalDate date) {
        return repository.existsById(date);
    }
}