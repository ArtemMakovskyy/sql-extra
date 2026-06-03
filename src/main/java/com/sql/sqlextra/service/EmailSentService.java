package com.sql.sqlextra.service;

import com.sql.sqlextra.entity.EmailSent;
import com.sql.sqlextra.repository.EmailSentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailSentService {

    private final EmailSentRepository repository;

    public List<EmailSent> findAll() {
        return repository.findAll();
    }

    public Optional<EmailSent> findById(Long id) {
        return repository.findById(id);
    }

    public EmailSent save(EmailSent emailSent) {
        return repository.save(emailSent);
    }

    public List<EmailSent> saveAll(List<EmailSent> emailSents) {
        return repository.saveAll(emailSents);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EmailSent> findByIdAccount(Long idAccount) {
        return repository.findAll().stream()
                .filter(e -> e.getIdAccount().equals(idAccount))
                .toList();
    }
}