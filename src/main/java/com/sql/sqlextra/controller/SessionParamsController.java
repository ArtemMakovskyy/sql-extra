package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.SessionParams;
import com.sql.sqlextra.service.SessionParamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session-params")
@RequiredArgsConstructor
public class SessionParamsController {

    private final SessionParamsService service;

    @GetMapping
    public List<SessionParams> findAll() {
        return service.findAll();
    }

    @GetMapping("/{gaSessionId}")
    public ResponseEntity<SessionParams> findById(@PathVariable String gaSessionId) {
        return service.findById(gaSessionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SessionParams create(@RequestBody SessionParams sessionParams) {
        return service.save(sessionParams);
    }

    @PutMapping("/{gaSessionId}")
    public ResponseEntity<SessionParams> update(@PathVariable String gaSessionId, @RequestBody SessionParams sessionParams) {
        if (!service.existsById(gaSessionId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(sessionParams));
    }

    @DeleteMapping("/{gaSessionId}")
    public ResponseEntity<Void> delete(@PathVariable String gaSessionId) {
        if (!service.existsById(gaSessionId)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(gaSessionId);
        return ResponseEntity.noContent().build();
    }
}