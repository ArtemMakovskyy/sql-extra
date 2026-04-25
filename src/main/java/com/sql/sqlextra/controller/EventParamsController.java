package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.EventParams;
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
    public List<EventParams> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventParams> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventParams create(@RequestBody EventParams eventParams) {
        return service.save(eventParams);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventParams> update(@PathVariable Long id, @RequestBody EventParams eventParams) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(eventParams));
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
    public List<EventParams> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}