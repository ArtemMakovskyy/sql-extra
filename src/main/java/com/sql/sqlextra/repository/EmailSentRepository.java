package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.EmailSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSentRepository extends JpaRepository<EmailSent, Long> {
}