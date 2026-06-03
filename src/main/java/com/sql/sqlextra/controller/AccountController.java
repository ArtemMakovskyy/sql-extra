package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.AccountDTO;
import com.sql.sqlextra.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @GetMapping
    public List<AccountDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        return service.save(accountDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(accountDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
