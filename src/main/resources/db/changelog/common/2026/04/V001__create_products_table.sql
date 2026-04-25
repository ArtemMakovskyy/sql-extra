-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V001__create_products_table.sql

-- changeset author:1
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    item_id INTEGER UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price DECIMAL(10, 2) NOT NULL,
    short_description TEXT
);

-- changeset author:2
CREATE INDEX idx_products_category ON products(category);

-- changeset author:3
CREATE INDEX idx_products_item_id ON products(item_id);