package com.sql.sqlextra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionParamsDTO {
    private String gaSessionId;
    private String device;
    private String mobileModelName;
    private String operatingSystem;
    private String language;
    private String browser;
    private String continent;
    private String country;
    private String medium;
    private String name;
    private String channel;
}
