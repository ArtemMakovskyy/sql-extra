package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.AbTest;
import com.sql.sqlextra.service.AbTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ab-tests")
@RequiredArgsConstructor
public class AbTestController {

    private final AbTestService service;

    @GetMapping
    public List<AbTest> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbTest> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AbTest create(@RequestBody AbTest abTest) {
        return service.save(abTest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbTest> update(@PathVariable Long id, @RequestBody AbTest abTest) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(abTest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/session/{gaSessionId}")
    public List<AbTest> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}