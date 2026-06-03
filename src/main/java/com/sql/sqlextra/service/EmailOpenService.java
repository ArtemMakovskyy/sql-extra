package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.EmailOpenDTO;
import com.sql.sqlextra.entity.EmailOpen;
import com.sql.sqlextra.mapper.EmailOpenMapper;
import com.sql.sqlextra.repository.EmailOpenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailOpenService {

    private final EmailOpenRepository repository;
    private final EmailOpenMapper mapper;

    public List<EmailOpenDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<EmailOpenDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public EmailOpenDTO save(EmailOpenDTO dto) {
        EmailOpen entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<EmailOpenDTO> saveAll(List<EmailOpenDTO> dtos) {
        List<EmailOpen> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<EmailOpenDTO> findByIdAccount(Long idAccount) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(e -> e.getIdAccount().equals(idAccount))
                .toList());
    }
}
