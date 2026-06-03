package com.sql.sqlextra.controller;

import com.sql.sqlextra.dto.AccountSessionDTO;
import com.sql.sqlextra.entity.AccountSessionId;
import com.sql.sqlextra.service.AccountSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-sessions")
@RequiredArgsConstructor
public class AccountSessionController {

    private final AccountSessionService service;

    @GetMapping
    public List<AccountSessionDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{accountId}/{gaSessionId}")
    public ResponseEntity<AccountSessionDTO> findById(
            @PathVariable Long accountId,
            @PathVariable String gaSessionId) {
        AccountSessionId id = new AccountSessionId(accountId, gaSessionId);
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccountSessionDTO create(@RequestBody AccountSessionDTO accountSessionDTO) {
        return service.save(accountSessionDTO);
    }

    @DeleteMapping("/{accountId}/{gaSessionId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long accountId,
            @PathVariable String gaSessionId) {
        AccountSessionId id = new AccountSessionId(accountId, gaSessionId);
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account/{accountId}")
    public List<AccountSessionDTO> findByAccountId(@PathVariable Long accountId) {
        return service.findByAccountId(accountId);
    }

    @GetMapping("/session/{gaSessionId}")
    public List<AccountSessionDTO> findByGaSessionId(@PathVariable String gaSessionId) {
        return service.findByGaSessionId(gaSessionId);
    }
}
