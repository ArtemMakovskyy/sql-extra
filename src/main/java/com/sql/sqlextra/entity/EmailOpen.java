package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_open")
@Getter
@Setter
@NoArgsConstructor
public class EmailOpen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_account", nullable = false)
    private Integer idAccount;

    @Column(name = "open_date")
    private Integer openDate;

    @Column(name = "letter_type")
    private Integer letterType;

    @Column(name = "id_message")
    private String idMessage;
}