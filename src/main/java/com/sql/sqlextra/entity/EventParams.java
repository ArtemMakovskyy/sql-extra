package com.sql.sqlextra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_params")
@Getter
@Setter
@NoArgsConstructor
@IdClass(EventParamsId.class)
public class EventParams {

    @Id
    @Column(name = "ga_session_id", nullable = false)
    private String gaSessionId;

    @Id
    @Column(name = "event_timestamp")
    private LocalDateTime eventTimestamp;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_name")
    private String eventName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "event_params", columnDefinition = "jsonb")
    private String eventParams;
}