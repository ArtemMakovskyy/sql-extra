package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.PaidSearchCost;
import com.sql.sqlextra.service.PaidSearchCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/paid-search-cost")
@RequiredArgsConstructor
public class PaidSearchCostController {

    private final PaidSearchCostService service;

    @GetMapping
    public List<PaidSearchCost> findAll() {
        return service.findAll();
    }

    @GetMapping("/{date}")
    public ResponseEntity<PaidSearchCost> findById(@PathVariable LocalDate date) {
        return service.findById(date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PaidSearchCost create(@RequestBody PaidSearchCost paidSearchCost) {
        return service.save(paidSearchCost);
    }

    @PutMapping("/{date}")
    public ResponseEntity<PaidSearchCost> update(@PathVariable LocalDate date, @RequestBody PaidSearchCost paidSearchCost) {
        if (!service.existsById(date)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(paidSearchCost));
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