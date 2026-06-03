package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.EmailSentDTO;
import com.sql.sqlextra.entity.EmailSent;
import com.sql.sqlextra.mapper.EmailSentMapper;
import com.sql.sqlextra.repository.EmailSentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailSentService {

    private final EmailSentRepository repository;
    private final EmailSentMapper mapper;

    public List<EmailSentDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<EmailSentDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public EmailSentDTO save(EmailSentDTO dto) {
        EmailSent entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EmailSentDTO> saveAll(List<EmailSentDTO> dtos) {
        List<EmailSent> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<EmailSentDTO> findByIdAccount(Long idAccount) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(e -> e.getIdAccount().equals(idAccount))
                .toList());
    }
}
