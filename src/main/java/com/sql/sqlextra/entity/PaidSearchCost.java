package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paid_search_cost")
@Getter
@Setter
@NoArgsConstructor
public class PaidSearchCost {

    @Id
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal cost;
}