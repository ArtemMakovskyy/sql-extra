package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "email_sent")
@Getter
@Setter
@NoArgsConstructor
public class EmailSent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_account", nullable = false)
    private Long idAccount;

    @Column(name = "sent_date")
    private Integer sentDate;

    @Column(name = "letter_type")
    private Integer letterType;

    @Column(name = "id_message")
    private String idMessage;
}