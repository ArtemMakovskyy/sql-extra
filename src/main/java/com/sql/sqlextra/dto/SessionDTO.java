package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    private String gaSessionId;
    private LocalDate date;
}
