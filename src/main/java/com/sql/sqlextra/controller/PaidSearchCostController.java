package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.PaidSearchCostDTO;
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
    public List<PaidSearchCostDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{date}")
    public ResponseEntity<PaidSearchCostDTO> findById(@PathVariable LocalDate date) {
        return service.findById(date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PaidSearchCostDTO create(@RequestBody PaidSearchCostDTO paidSearchCostDTO) {
        return service.save(paidSearchCostDTO);
    }

    @PutMapping("/{date}")
    public ResponseEntity<PaidSearchCostDTO> update(@PathVariable LocalDate date, @RequestBody PaidSearchCostDTO paidSearchCostDTO) {
        if (!service.existsById(date)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(paidSearchCostDTO));
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
