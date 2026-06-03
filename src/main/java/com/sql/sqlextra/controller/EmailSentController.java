package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EmailSentDTO;
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
    public List<EmailSentDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailSentDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailSentDTO create(@RequestBody EmailSentDTO emailSentDTO) {
        return service.save(emailSentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailSentDTO> update(@PathVariable Long id, @RequestBody EmailSentDTO emailSentDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(emailSentDTO));
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
    public List<EmailSentDTO> findByIdAccount(@PathVariable Long idAccount) {
        return service.findByIdAccount(idAccount);
    }
}
