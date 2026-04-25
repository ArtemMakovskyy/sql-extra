package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.EmailSent;
import com.sql.sqlextra.service.EmailSentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-sent")
@RequiredArgsConstructor
public class EmailSentController {

    private final EmailSentService service;

    @GetMapping
    public List<EmailSent> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailSent> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailSent create(@RequestBody EmailSent emailSent) {
        return service.save(emailSent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailSent> update(@PathVariable Long id, @RequestBody EmailSent emailSent) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(emailSent));
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
    public List<EmailSent> findByIdAccount(@PathVariable Integer idAccount) {
        return service.findByIdAccount(idAccount);
    }
}