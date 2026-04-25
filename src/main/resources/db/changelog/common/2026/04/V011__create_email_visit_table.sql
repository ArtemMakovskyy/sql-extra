-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V011__create_email_visit_table.sql

-- changeset author:1
CREATE TABLE email_visit (
    id BIGSERIAL PRIMARY KEY,
    id_account BIGINT NOT NULL,
    visit_date INTEGER,
    letter_type INTEGER,
    id_message VARCHAR(100)
);

-- changeset author:2
ALTER TABLE email_visit ADD CONSTRAINT fk_email_visit_account
    FOREIGN KEY (id_account) REFERENCES account(id);

-- changeset author:3
CREATE INDEX idx_email_visit_id_account ON email_visit(id_account);