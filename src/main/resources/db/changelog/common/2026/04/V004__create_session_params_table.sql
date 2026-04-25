-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V004__create_session_params_table.sql

-- changeset author:1
CREATE TABLE session_params (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL UNIQUE,
    device VARCHAR(50),
    mobile_model_name VARCHAR(100),
    operating_system VARCHAR(50),
    language VARCHAR(10),
    browser VARCHAR(50),
    continent VARCHAR(20),
    country VARCHAR(50),
    medium VARCHAR(50),
    name VARCHAR(100),
    channel VARCHAR(50)
);

-- changeset author:2
ALTER TABLE session_params ADD CONSTRAINT fk_session_params_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

-- changeset author:3
CREATE INDEX idx_session_params_ga_session_id ON session_params(ga_session_id);

-- changeset author:4
CREATE INDEX idx_session_params_country ON session_params(country);

-- changeset author:5
CREATE INDEX idx_session_params_device ON session_params(device);