-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V006__create_ab_test_table.sql

-- changeset author:1
CREATE TABLE ab_test (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    test INTEGER,
    test_group INTEGER
);

-- changeset author:2
ALTER TABLE ab_test ADD CONSTRAINT fk_ab_test_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

-- changeset author:3
CREATE INDEX idx_ab_test_ga_session_id ON ab_test(ga_session_id);