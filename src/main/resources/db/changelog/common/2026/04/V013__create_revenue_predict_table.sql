-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V013__create_revenue_predict_table.sql

-- changeset author:1
CREATE TABLE revenue_predict (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    predict DECIMAL(12,2) NOT NULL
);

-- changeset author:2
CREATE UNIQUE INDEX idx_revenue_predict_date ON revenue_predict(date);