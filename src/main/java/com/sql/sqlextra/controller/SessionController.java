package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.Session;
import com.sql.sqlextra.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;

    @GetMapping
    public List<Session> findAll() {
        return service.findAll();
    }

    @GetMapping("/{gaSessionId}")
    public ResponseEntity<Session> findById(@PathVariable String gaSessionId) {
        return service.findById(gaSessionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Session create(@RequestBody Session session) {
        return service.save(session);
    }

    @PutMapping("/{gaSessionId}")
    public ResponseEntity<Session> update(@PathVariable String gaSessionId, @RequestBody Session session) {
        if (!service.existsById(gaSessionId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(session));
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