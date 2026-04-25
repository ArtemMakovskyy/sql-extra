package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.RevenuePredict;
import com.sql.sqlextra.service.RevenuePredictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/revenue-predict")
@RequiredArgsConstructor
public class RevenuePredictController {

    private final RevenuePredictService service;

    @GetMapping
    public List<RevenuePredict> findAll() {
        return service.findAll();
    }

    @GetMapping("/{date}")
    public ResponseEntity<RevenuePredict> findById(@PathVariable LocalDate date) {
        return service.findById(date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RevenuePredict create(@RequestBody RevenuePredict revenuePredict) {
        return service.save(revenuePredict);
    }

    @PutMapping("/{date}")
    public ResponseEntity<RevenuePredict> update(@PathVariable LocalDate date, @RequestBody RevenuePredict revenuePredict) {
        if (!service.existsById(date)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(revenuePredict));
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<Void> delete(@PathVariable LocalDate date) {
        if (!service.existsById(date)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(date);
        return ResponseEntity.noContent().build();
    }
}