package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.SessionParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionParamsRepository extends JpaRepository<SessionParams, String> {
}