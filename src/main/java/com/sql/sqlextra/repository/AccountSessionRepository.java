package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.AccountSession;
import com.sql.sqlextra.entity.AccountSessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSessionRepository extends JpaRepository<AccountSession, AccountSessionId> {
}