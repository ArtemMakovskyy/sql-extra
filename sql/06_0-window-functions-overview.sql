SELECT
    item_id,
    name,
    category,
    price,
    ROW_NUMBER()   OVER (PARTITION BY category ORDER BY price DESC) AS row_num,-- unique sequential number
    RANK()         OVER (PARTITION BY category ORDER BY price DESC) AS rank_val, -- same rank on ties, skips next numbers
    DENSE_RANK()   OVER (PARTITION BY category ORDER BY price DESC) AS dense_rank_val, -- same rank on ties, no gaps
    PERCENT_RANK() OVER (PARTITION BY category ORDER BY price DESC) AS percent_rank_val -- relative rank from 0 to 1 within category
FROM products;

SELECT
--     item_id,
    name,
    category,
    price,
    SUM(price)   OVER (PARTITION BY category) AS total_p,
    round(AVG(price)   OVER (PARTITION BY category),2)      AS avg_cat_p,
    MIN(price)   OVER (PARTITION BY category)               AS min_cat_p,
    MAX(price)   OVER (PARTITION BY category)               AS max_cat_p,
    COUNT(*)     OVER (PARTITION BY category)               AS items_in_c
FROM products;

SELECT
    name,
    category,
    price,
    FIRST_VALUE(name) OVER (PARTITION BY category ORDER BY price ASC) AS cheapest_item_name,
    LAST_VALUE(price)  OVER (PARTITION BY category ORDER BY price ASC RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) AS highest_p_in_c,
    LAG(price, 1)      OVER (PARTITION BY category ORDER BY price ASC) AS prev_item_pp, -- value from previous row
    LEAD(price, 1)     OVER (PARTITION BY category ORDER BY price ASC) AS next_item_pp -- value from next row in window
FROM products
;
