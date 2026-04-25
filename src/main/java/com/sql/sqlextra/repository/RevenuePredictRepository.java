package com.sql.sqlextra.repository;

import com.sql.sqlextra.entity.RevenuePredict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenuePredictRepository extends JpaRepository<RevenuePredict, Long> {
}