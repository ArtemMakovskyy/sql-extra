package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.EmailOpen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailOpenRepository extends JpaRepository<EmailOpen, Long> {
}