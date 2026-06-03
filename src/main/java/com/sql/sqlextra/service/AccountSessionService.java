package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.AccountSessionDTO;
import com.sql.sqlextra.entity.AccountSession;
import com.sql.sqlextra.entity.AccountSessionId;
import com.sql.sqlextra.mapper.AccountSessionMapper;
import com.sql.sqlextra.repository.AccountSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountSessionService {

    private final AccountSessionRepository repository;
    private final AccountSessionMapper mapper;

    public List<AccountSessionDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<AccountSessionDTO> findById(AccountSessionId id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public AccountSessionDTO save(AccountSessionDTO dto) {
        AccountSession entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(AccountSessionId id) {
        repository.deleteById(id);
    }

    public boolean existsById(AccountSessionId id) {
        return repository.existsById(id);
    }

    public List<AccountSessionDTO> saveAll(List<AccountSessionDTO> dtos) {
        List<AccountSession> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }

    public List<AccountSessionDTO> findByAccountId(Long accountId) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(as -> as.getId().getAccountId().equals(accountId))
                .toList());
    }

    public List<AccountSessionDTO> findByGaSessionId(String gaSessionId) {
        return mapper.toDTOList(repository.findAll().stream()
                .filter(as -> as.getId().getGaSessionId().equals(gaSessionId))
                .toList());
    }
}
