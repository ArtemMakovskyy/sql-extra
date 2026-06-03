package com.sql.sqlextra.service;

import com.sql.sqlextra.dto.AccountDTO;
import com.sql.sqlextra.entity.Account;
import com.sql.sqlextra.mapper.AccountMapper;
import com.sql.sqlextra.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    public List<AccountDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public Optional<AccountDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public AccountDTO save(AccountDTO dto) {
        Account entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<AccountDTO> saveAll(List<AccountDTO> dtos) {
        List<Account> entities = dtos.stream().map(mapper::toEntity).toList();
        return mapper.toDTOList(repository.saveAll(entities));
    }
}
