package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.EmailOpen;
import com.sql.sqlextra.repository.EmailOpenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailOpenService {

    private final EmailOpenRepository repository;

    public List<EmailOpen> findAll() {
        return repository.findAll();
    }

    public Optional<EmailOpen> findById(Long id) {
        return repository.findById(id);
    }

    public EmailOpen save(EmailOpen emailOpen) {
        return repository.save(emailOpen);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EmailOpen> findByIdAccount(Integer idAccount) {
        return repository.findAll().stream()
                .filter(e -> e.getIdAccount().equals(idAccount))
                .toList();
    }
}