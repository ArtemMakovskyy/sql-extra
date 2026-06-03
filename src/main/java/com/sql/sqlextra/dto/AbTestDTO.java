package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbTestDTO {
    private String gaSessionId;
    private Integer test;
    private Integer testGroup;
}
