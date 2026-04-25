package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.AbTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbTestRepository extends JpaRepository<AbTest, Long> {
}