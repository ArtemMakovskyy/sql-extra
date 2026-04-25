package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session_params")
@Getter
@Setter
@NoArgsConstructor
public class SessionParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ga_session_id", nullable = false, unique = true)
    private String gaSessionId;

    private String device;

    @Column(name = "mobile_model_name")
    private String mobileModelName;

    @Column(name = "operating_system")
    private String operatingSystem;

    private String language;

    private String browser;

    private String continent;

    private String country;

    private String medium;

    private String name;

    private String channel;
}