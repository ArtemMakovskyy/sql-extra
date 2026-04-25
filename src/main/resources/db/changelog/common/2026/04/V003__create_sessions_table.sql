-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V003__create_sessions_table.sql

-- changeset author:1
CREATE TABLE sessions (
    ga_session_id VARCHAR(255) PRIMARY KEY,
    date DATE NOT NULL
);

-- changeset author:2
CREATE INDEX idx_sessions_date ON sessions(date);