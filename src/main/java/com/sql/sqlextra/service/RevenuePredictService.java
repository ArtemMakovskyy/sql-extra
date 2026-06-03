package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.RevenuePredict;
import com.sql.sqlextra.repository.RevenuePredictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RevenuePredictService {

    private final RevenuePredictRepository repository;

    public List<RevenuePredict> findAll() {
        return repository.findAll();
    }

    public Optional<RevenuePredict> findById(LocalDate date) {
        return repository.findById(date);
    }

    public RevenuePredict save(RevenuePredict revenuePredict) {
        return repository.save(revenuePredict);
    }

    public List<RevenuePredict> saveAll(List<RevenuePredict> predicts) {
        return repository.saveAll(predicts);
    }

    public void deleteById(LocalDate date) {
        repository.deleteById(date);
    }

    public boolean existsById(LocalDate date) {
        return repository.existsById(date);
    }
}