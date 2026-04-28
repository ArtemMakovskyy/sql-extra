package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.AccountSession;
import com.sql.sqlextra.entity.AccountSessionId;
import com.sql.sqlextra.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountSessionService {

    private final AccountSessionRepository repository;

    public List<AccountSession> findAll() {
        return repository.findAll();
    }

    public Optional<AccountSession> findById(AccountSessionId id) {
        return repository.findById(id);
    }

    public AccountSession save(AccountSession accountSession) {
        return repository.save(accountSession);
    }

    public void deleteById(AccountSessionId id) {
        repository.deleteById(id);
    }

    public boolean existsById(AccountSessionId id) {
        return repository.existsById(id);
    }

    public List<AccountSession> findByAccountId(Long accountId) {
        return repository.findAll().stream()
                .filter(as -> as.getId().getAccountId().equals(accountId))
                .toList();
    }

    public List<AccountSession> findByGaSessionId(String gaSessionId) {
        return repository.findAll().stream()
                .filter(as -> as.getId().getGaSessionId().equals(gaSessionId))
                .toList();
    }
}