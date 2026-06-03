package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.RevenuePredictDTO;
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
    public List<RevenuePredictDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{date}")
    public ResponseEntity<RevenuePredictDTO> findById(@PathVariable LocalDate date) {
        return service.findById(date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RevenuePredictDTO create(@RequestBody RevenuePredictDTO revenuePredictDTO) {
        return service.save(revenuePredictDTO);
    }

    @PutMapping("/{date}")
    public ResponseEntity<RevenuePredictDTO> update(@PathVariable LocalDate date, @RequestBody RevenuePredictDTO revenuePredictDTO) {
        if (!service.existsById(date)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(revenuePredictDTO));
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
