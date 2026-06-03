package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EmailOpenDTO;
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
    public List<EmailOpenDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailOpenDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailOpenDTO create(@RequestBody EmailOpenDTO emailOpenDTO) {
        return service.save(emailOpenDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailOpenDTO> update(@PathVariable Long id, @RequestBody EmailOpenDTO emailOpenDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(emailOpenDTO));
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
    public List<EmailOpenDTO> findByIdAccount(@PathVariable Long idAccount) {
        return service.findByIdAccount(idAccount);
    }
}
