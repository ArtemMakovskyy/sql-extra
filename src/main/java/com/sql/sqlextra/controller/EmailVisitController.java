package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.EmailVisitDTO;
import com.sql.sqlextra.entity.EmailVisit;
import com.sql.sqlextra.mapper.EmailVisitMapper;
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
    private final EmailVisitMapper emailVisitMapper;

    @GetMapping
    public List<EmailVisitDTO> findAll() {
        return emailVisitMapper.toDTOList(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailVisitDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(emailVisit -> ResponseEntity.ok(emailVisitMapper.toDTO(emailVisit)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmailVisitDTO create(@RequestBody EmailVisitDTO emailVisitDTO) {
        EmailVisit emailVisit = emailVisitMapper.toEntity(emailVisitDTO);
        return emailVisitMapper.toDTO(service.save(emailVisit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailVisitDTO> update(@PathVariable Long id, @RequestBody EmailVisitDTO emailVisitDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        EmailVisit emailVisit = emailVisitMapper.toEntity(emailVisitDTO);
        return ResponseEntity.ok(emailVisitMapper.toDTO(service.save(emailVisit)));
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
        return emailVisitMapper.toDTOList(service.findByIdAccount(idAccount));
    }
}