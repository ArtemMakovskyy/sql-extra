-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V005__create_account_session_table.sql

-- changeset author:1
CREATE TABLE account_session (
    account_id BIGINT NOT NULL,
    ga_session_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id, ga_session_id)
);

-- changeset author:2
ALTER TABLE account_session ADD CONSTRAINT fk_account_session_account
    FOREIGN KEY (account_id) REFERENCES account(id);

-- changeset author:3
ALTER TABLE account_session ADD CONSTRAINT fk_account_session_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

-- changeset author:4
CREATE INDEX idx_account_session_account_id ON account_session(account_id);

-- changeset author:5
CREATE INDEX idx_account_session_ga_session_id ON account_session(ga_session_id);