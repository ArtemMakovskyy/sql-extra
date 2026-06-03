package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.SessionParamsDTO;
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
    public List<SessionParamsDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{gaSessionId}")
    public ResponseEntity<SessionParamsDTO> findById(@PathVariable String gaSessionId) {
        return service.findById(gaSessionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SessionParamsDTO create(@RequestBody SessionParamsDTO sessionParamsDTO) {
        return service.save(sessionParamsDTO);
    }

    @PutMapping("/{gaSessionId}")
    public ResponseEntity<SessionParamsDTO> update(@PathVariable String gaSessionId, @RequestBody SessionParamsDTO sessionParamsDTO) {
        if (!service.existsById(gaSessionId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(sessionParamsDTO));
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
