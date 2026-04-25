-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V007__create_event_params_table.sql

-- changeset author:1
CREATE TABLE event_params (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    event_date DATE,
    event_timestamp TIMESTAMP,
    event_name VARCHAR(100),
    event_params JSONB
);

-- changeset author:2
ALTER TABLE event_params ADD CONSTRAINT fk_event_params_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

-- changeset author:3
CREATE INDEX idx_event_params_ga_session_id ON event_params(ga_session_id);

-- changeset author:4
CREATE INDEX idx_event_params_event_date ON event_params(event_date);