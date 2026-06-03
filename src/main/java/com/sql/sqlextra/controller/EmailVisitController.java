package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EmailVisitDTO;
import com.sql.sqlextra.service.EmailVisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-visit")
@RequiredArgsConstructor
public class EmailVisitController {

    private final EmailVisitService service;

    @GetMapping
    public List<EmailVisitDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailVisitDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailVisitDTO create(@RequestBody EmailVisitDTO emailVisitDTO) {
        return service.save(emailVisitDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailVisitDTO> update(@PathVariable Long id, @RequestBody EmailVisitDTO emailVisitDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(emailVisitDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account/{idAccount}")
    public List<EmailVisitDTO> findByIdAccount(@PathVariable Long idAccount) {
        return service.findByIdAccount(idAccount);
    }
}
