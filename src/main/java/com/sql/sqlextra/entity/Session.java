package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
public class Session {

    @Id
    @Column(name = "ga_session_id")
    private String gaSessionId;

    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return gaSessionId != null && gaSessionId.equals(session.gaSessionId);
    }

    @Override
    public int hashCode() {
        return gaSessionId != null ? gaSessionId.hashCode() : 0;
    }
}