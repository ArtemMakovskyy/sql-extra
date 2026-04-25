-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V001__create_products_table.sql

-- changeset author:1
CREATE TABLE product (
    item_id INTEGER PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100),
    price DECIMAL(10, 2),
    short_description TEXT
);

-- changeset author:2
CREATE INDEX idx_products_category ON product(category);