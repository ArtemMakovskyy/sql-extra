-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V002__create_account_table.sql

-- changeset author:1
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    send_interval INTEGER,
    is_verified INTEGER NOT NULL CHECK (is_verified IN (0, 1)),
    is_unsubscribed INTEGER NOT NULL CHECK (is_unsubscribed IN (0, 1))
);

-- changeset author:2
CREATE INDEX idx_account_is_verified ON account(is_verified);

-- changeset author:3
CREATE INDEX idx_account_is_unsubscribed ON account(is_unsubscribed);