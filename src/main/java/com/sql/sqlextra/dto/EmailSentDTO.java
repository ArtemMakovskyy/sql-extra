package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSentDTO {
    private Long id;
    private Long idAccount;
    private Integer sentDate;
    private Integer letterType;
    private String idMessage;
}