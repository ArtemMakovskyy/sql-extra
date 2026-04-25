package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_visit")
@Getter
@Setter
@NoArgsConstructor
public class EmailVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_account", nullable = false)
    private Long idAccount;

    @Column(name = "visit_date")
    private Integer visitDate;

    @Column(name = "letter_type")
    private Integer letterType;

    @Column(name = "id_message")
    private String idMessage;
}