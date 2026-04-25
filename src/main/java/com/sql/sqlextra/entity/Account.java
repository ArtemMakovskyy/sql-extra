package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "send_interval")
    private Integer sendInterval;

    @Column(name = "is_verified", nullable = false)
    private Integer isVerified;

    @Column(name = "is_unsubscribed", nullable = false)
    private Integer isUnsubscribed;
}