package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EventParamsDTO;
import com.sql.sqlextra.service.EventParamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-params")
@RequiredArgsConstructor
public class EventParamsController {

    private final EventParamsService service;

    @GetMapping
    public List<EventParamsDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventParamsDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventParamsDTO create(@RequestBody EventParamsDTO eventParamsDTO) {
        return service.save(eventParamsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventParamsDTO> update(@PathVariable Long id, @RequestBody EventParamsDTO eventParamsDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(eventParamsDTO));
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
    public List<EventParamsDTO> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}
