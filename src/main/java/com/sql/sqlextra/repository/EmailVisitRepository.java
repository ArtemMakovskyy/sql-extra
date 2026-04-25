package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.EmailVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVisitRepository extends JpaRepository<EmailVisit, Long> {
}