package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.EmailVisitDTO;
import com.sql.sqlextra.entity.EmailVisit;
import com.sql.sqlextra.mapper.EmailVisitMapper;
import com.sql.sqlextra.repository.EmailVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVisitService {

    private final EmailVisitRepository repository;
    private final EmailVisitMapper mapper;

    public List<EmailVisitDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<EmailVisitDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public EmailVisitDTO save(EmailVisitDTO dto) {
        EmailVisit entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EmailVisitDTO> saveAll(List<EmailVisitDTO> dtos) {
        List<EmailVisit> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<EmailVisitDTO> findByIdAccount(Long idAccount) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(e -> e.getIdAccount().equals(idAccount))
                .toList());
    }
}
