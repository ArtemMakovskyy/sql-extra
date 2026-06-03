package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.AbTestDTO;
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
    public List<AbTestDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbTestDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AbTestDTO create(@RequestBody AbTestDTO abTestDTO) {
        return service.save(abTestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AbTestDTO> update(@PathVariable Long id, @RequestBody AbTestDTO abTestDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(abTestDTO));
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
    public List<AbTestDTO> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}
