package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventParamsDTO {
    private Long id;
    private String gaSessionId;
    private LocalDate eventDate;
    private LocalDateTime eventTimestamp;
    private String eventName;
    private String eventParams;
}
