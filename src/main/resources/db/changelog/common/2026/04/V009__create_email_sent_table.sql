-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V009__create_email_sent_table.sql

-- changeset author:1
CREATE TABLE email_sent (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    sent_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

-- changeset author:2
ALTER TABLE email_sent ADD CONSTRAINT fk_email_sent_account
    FOREIGN KEY (id_account) REFERENCES account(id);

-- changeset author:3
CREATE INDEX idx_email_sent_id_account ON email_sent(id_account);