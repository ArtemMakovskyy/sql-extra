package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ab_test")
@Getter
@Setter
@NoArgsConstructor
public class AbTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ga_session_id", nullable = false)
    private String gaSessionId;

    private Integer test;

    @Column(name = "test_group")
    private Integer testGroup;
}