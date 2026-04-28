package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailOpenDTO {
    private Long id;
    private Long idAccount;
    private Integer openDate;
    private Integer letterType;
    private String idMessage;
}