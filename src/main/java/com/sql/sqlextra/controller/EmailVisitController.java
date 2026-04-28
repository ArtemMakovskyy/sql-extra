package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.EmailVisit;
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
    public List<EmailVisit> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailVisit> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailVisit create(@RequestBody EmailVisit emailVisit) {
        return service.save(emailVisit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailVisit> update(@PathVariable Long id, @RequestBody EmailVisit emailVisit) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(emailVisit));
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
    public List<EmailVisit> findByIdAccount(@PathVariable Long idAccount) {
        return service.findByIdAccount(idAccount);
    }
}