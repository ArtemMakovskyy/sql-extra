package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.EmailOpen;
import com.sql.sqlextra.service.EmailOpenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-open")
@RequiredArgsConstructor
public class EmailOpenController {

    private final EmailOpenService service;

    @GetMapping
    public List<EmailOpen> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailOpen> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailOpen create(@RequestBody EmailOpen emailOpen) {
        return service.save(emailOpen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailOpen> update(@PathVariable Long id, @RequestBody EmailOpen emailOpen) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(emailOpen));
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
    public List<EmailOpen> findByIdAccount(@PathVariable Integer idAccount) {
        return service.findByIdAccount(idAccount);
    }
}