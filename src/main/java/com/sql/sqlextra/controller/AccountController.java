package com.sql.sqlextra.controller;

import com.sql.sqlextra.entity.Account;
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
    public List<Account> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return service.save(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable Integer id, @RequestBody Account account) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}