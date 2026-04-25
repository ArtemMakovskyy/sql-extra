package com.sql.sqlextra.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_session")
@Getter
@Setter
@NoArgsConstructor
public class AccountSession {

    @EmbeddedId
    private AccountSessionId id;
}