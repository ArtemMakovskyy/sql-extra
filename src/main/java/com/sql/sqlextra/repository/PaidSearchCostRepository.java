package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.PaidSearchCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaidSearchCostRepository extends JpaRepository<PaidSearchCost, Long> {
}