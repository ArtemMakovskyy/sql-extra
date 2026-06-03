package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.AbTest;
import com.sql.sqlextra.entity.AbTestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbTestRepository extends JpaRepository<AbTest, AbTestId> {
}