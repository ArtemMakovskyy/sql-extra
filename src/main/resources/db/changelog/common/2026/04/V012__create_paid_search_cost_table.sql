-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V012__create_paid_search_cost_table.sql

-- changeset author:1
CREATE TABLE paid_search_cost (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    cost DECIMAL(12,2) NOT NULL
);

-- changeset author:2
CREATE UNIQUE INDEX idx_paid_search_cost_date ON paid_search_cost(date);