-- file: examples/temp_tables_examples.sql
-- Temporary Tables examples for dataset data-analytics-mate.DA
-- DB: PostgreSQL

-- ============================================================
-- EXAMPLE 1: TEMP TABLE for "Top products by revenue"
-- ============================================================

DROP TABLE IF EXISTS tmp_product_revenue;
CREATE TEMP TABLE tmp_product_revenue AS
SELECT
    p.item_id,
    p.name,
    p.category,
    SUM(p.price) AS revenue,
    COUNT(*) AS orders_count
FROM orders o
         JOIN products p ON p.item_id = o.item_id
GROUP BY p.item_id, p.name, p.category;

-- Use temp table
SELECT *
FROM tmp_product_revenue
ORDER BY revenue DESC
LIMIT 10;


-- ============================================================
-- EXAMPLE 4: TEMP TABLE with ON COMMIT DROP
-- Table будет удалена после COMMIT
-- ============================================================

DROP TABLE IF EXISTS tmp_daily_revenue;
CREATE TEMP TABLE tmp_daily_revenue (
                                        date DATE,
                                        revenue NUMERIC(10,2)
) ON COMMIT DROP;

INSERT INTO tmp_daily_revenue (date, revenue)
SELECT
    s.date,
    SUM(p.price) AS revenue
FROM orders o
         JOIN products p ON p.item_id = o.item_id
         JOIN sessions s ON s.ga_session_id = o.ga_session_id
GROUP BY s.date;

-- Use temp table
SELECT *
FROM tmp_daily_revenue
ORDER BY date;

COMMIT;

-- После COMMIT таблицы tmp_daily_revenue уже не существует.
