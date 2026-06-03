package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.EmailVisit;
import com.sql.sqlextra.repository.EmailVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVisitService {

    private final EmailVisitRepository repository;

    public List<EmailVisit> findAll() {
        return repository.findAll();
    }

    public Optional<EmailVisit> findById(Long id) {
        return repository.findById(id);
    }

    public EmailVisit save(EmailVisit emailVisit) {
        return repository.save(emailVisit);
    }

    public List<EmailVisit> saveAll(List<EmailVisit> emailVisits) {
        return repository.saveAll(emailVisits);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EmailVisit> findByIdAccount(Long idAccount) {
        return repository.findAll().stream()
                .filter(e -> e.getIdAccount().equals(idAccount))
                .toList();
    }
}