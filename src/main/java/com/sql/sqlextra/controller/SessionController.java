package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.SessionDTO;
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
    public List<SessionDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{gaSessionId}")
    public ResponseEntity<SessionDTO> findById(@PathVariable String gaSessionId) {
        return service.findById(gaSessionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SessionDTO create(@RequestBody SessionDTO sessionDTO) {
        return service.save(sessionDTO);
    }

    @PutMapping("/{gaSessionId}")
    public ResponseEntity<SessionDTO> update(@PathVariable String gaSessionId, @RequestBody SessionDTO sessionDTO) {
        if (!service.existsById(gaSessionId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(sessionDTO));
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
