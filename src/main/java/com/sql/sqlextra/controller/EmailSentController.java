package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EmailSentDTO;
import com.sql.sqlextra.entity.EmailSent;
import com.sql.sqlextra.mapper.EmailSentMapper;
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
    private final EmailSentMapper emailSentMapper;

    @GetMapping
    public List<EmailSentDTO> findAll() {
        return emailSentMapper.toDTOList(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailSentDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(emailSent -> ResponseEntity.ok(emailSentMapper.toDTO(emailSent)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailSentDTO create(@RequestBody EmailSentDTO emailSentDTO) {
        EmailSent emailSent = emailSentMapper.toEntity(emailSentDTO);
        return emailSentMapper.toDTO(service.save(emailSent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailSentDTO> update(@PathVariable Long id, @RequestBody EmailSentDTO emailSentDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        EmailSent emailSent = emailSentMapper.toEntity(emailSentDTO);
        return ResponseEntity.ok(emailSentMapper.toDTO(service.save(emailSent)));
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
        return emailSentMapper.toDTOList(service.findByIdAccount(idAccount));
    }
}