package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVisitDTO {
    private Long id;
    private Long idAccount;
    private Integer visitDate;
    private Integer letterType;
    private String idMessage;
}