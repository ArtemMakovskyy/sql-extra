package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.EventParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParamsRepository extends JpaRepository<EventParams, Long> {
}