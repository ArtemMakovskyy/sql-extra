package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaidSearchCostDTO {
    private LocalDate date;
    private BigDecimal cost;
}
