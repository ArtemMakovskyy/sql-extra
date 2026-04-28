package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.AccountDTO;
import com.sql.sqlextra.entity.Account;
import com.sql.sqlextra.mapper.AccountMapper;
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
    private final AccountMapper accountMapper;

    @GetMapping
    public List<AccountDTO> findAll() {
        return accountMapper.toDTOList(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(account -> ResponseEntity.ok(accountMapper.toDTO(account)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        return accountMapper.toDTO(service.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody AccountDTO accountDTO) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Account account = accountMapper.toEntity(accountDTO);
        return ResponseEntity.ok(accountMapper.toDTO(service.save(account)));
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