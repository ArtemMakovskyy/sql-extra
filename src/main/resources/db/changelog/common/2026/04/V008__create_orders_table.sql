-- liquibase formatted sql
-- logicalFilePath: db/changelog/common/2026/04/V008__create_orders_table.sql

-- changeset author:1
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    ga_session_id VARCHAR(255) NOT NULL,
    item_id INTEGER NOT NULL
);

-- changeset author:2
ALTER TABLE orders ADD CONSTRAINT fk_orders_session
    FOREIGN KEY (ga_session_id) REFERENCES sessions(ga_session_id);

-- changeset author:3
ALTER TABLE orders ADD CONSTRAINT fk_orders_product
    FOREIGN KEY (item_id) REFERENCES products(item_id);

-- changeset author:4
CREATE INDEX idx_orders_ga_session_id ON orders(ga_session_id);

-- changeset author:5
CREATE INDEX idx_orders_item_id ON orders(item_id);