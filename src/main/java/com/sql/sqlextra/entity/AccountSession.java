package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountSessionId implements Serializable {

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "ga_session_id")
    private String gaSessionId;
}

@Entity
@Table(name = "account_session")
@Getter
@Setter
@NoArgsConstructor
public class AccountSession {

    @EmbeddedId
    private AccountSessionId id;
}