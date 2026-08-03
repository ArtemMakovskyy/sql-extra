-- 1. First Sent Date by Account
SELECT
    acc.id AS account_id,
    MIN(s.date + (ems.sent_date || ' days')::interval) AS first_sent_date
FROM account acc
JOIN account_session accs ON accs.account_id = acc.id
JOIN sessions s ON accs.ga_session_id = s.ga_session_id
JOIN email_sent ems ON ems.id_account = acc.id
GROUP BY acc.id
ORDER BY first_sent_date;

-- 2. Emails Sent by Month with Metrics
SELECT DISTINCT
    sent_month,
    id_account,
    COUNT(*) OVER (PARTITION BY sent_month, id_account)
        / NULLIF(COUNT(*) OVER (PARTITION BY sent_month), 0) * 100 AS sent_msg_percent_from_this_month,
    MIN(sent_date) OVER (PARTITION BY sent_month, id_account) AS first_sent_date,
    MAX(sent_date) OVER (PARTITION BY sent_month, id_account) AS last_sent_date
FROM (
    SELECT
        es.id_account,
        (acc_start.min_date + (es.sent_date || ' days')::interval) AS sent_date,
        DATE_TRUNC('month', acc_start.min_date + (es.sent_date || ' days')::interval) AS sent_month
    FROM email_sent es
    JOIN (
        SELECT
            acs.account_id,
            MIN(s.date) AS min_date
        FROM account_session acs
        JOIN sessions s ON s.ga_session_id = acs.ga_session_id
        GROUP BY acs.account_id
    ) acc_start ON acc_start.account_id = es.id_account
) db
ORDER BY sent_month, id_account;

-- 3. Top 3 Categories by Revenue per Country
SELECT country, category, revenue, total_country_revenue, row_num,
    ROUND(revenue / NULLIF(total_country_revenue, 0) * 100, 2) AS c_r_p
FROM (
    SELECT
        sp.country,
        p.category,
        SUM(p.price) AS revenue,
        SUM(SUM(p.price)) OVER (PARTITION BY sp.country) AS total_country_revenue,
        ROW_NUMBER() OVER (PARTITION BY sp.country ORDER BY SUM(p.price) DESC) AS row_num
    FROM session_params sp
    JOIN orders o ON sp.ga_session_id = o.ga_session_id
    JOIN products p ON o.item_id = p.item_id
    GROUP BY sp.country, p.category
) row_numbers
WHERE row_num <= 3;

-- 4. Revenue and Cost with Running Totals by Month
SELECT
    month_date,
    date,
    revenue,
    cost,
    SUM(revenue) OVER (PARTITION BY month_date ORDER BY date) AS acc_revenue,
    SUM(revenue) OVER (PARTITION BY month_date) AS total_month_revenue,
    SUM(cost) OVER (PARTITION BY month_date ORDER BY date) AS acc_cost
FROM (
    SELECT
        DATE_TRUNC('month', s.date) AS month_date,
        s.date,
        SUM(p.price) AS revenue,
        0 AS cost
    FROM orders o
    JOIN products p ON o.item_id = p.item_id
    JOIN sessions s ON o.ga_session_id = s.ga_session_id
    GROUP BY s.date
    UNION ALL
    SELECT
        DATE_TRUNC('month', date) AS month_date,
        date,
        0 AS revenue,
        cost
    FROM paid_search_cost
) combined_data
ORDER BY date;

-- 5. First Event by Session (row_number)
SELECT ga_session_id, event_timestamp, event_name
FROM (
    SELECT
        ga_session_id,
        event_timestamp,
        event_name,
        ROW_NUMBER() OVER (PARTITION BY ga_session_id ORDER BY event_timestamp) AS row_num
    FROM event_params
) rows_events
WHERE row_num = 1;

-- 6. Revenue and Cost Combined
SELECT
    s.date,
    SUM(p.price) AS revenue,
    0 AS cost
FROM orders o
JOIN products p ON o.item_id = p.item_id
JOIN sessions s ON o.ga_session_id = s.ga_session_id
GROUP BY s.date
UNION ALL
SELECT
    date,
    0 AS revenue,
    cost
FROM paid_search_cost;

-- 7. Category Revenue Percentage
SELECT
    p.category,
    SUM(p.price) AS revenue,
    SUM(p.price) / NULLIF(SUM(SUM(p.price)) OVER (), 0) * 100 AS category_revenue
FROM products p
JOIN orders o ON p.item_id = o.item_id
GROUP BY p.category
ORDER BY revenue DESC;

-- 8. Product Price vs Category Average (JOIN + window)
SELECT
    p.name,
    p.category,
    p.price,
    cat_avg.avg_price_in_category
FROM products p
JOIN (
    SELECT category, AVG(price) AS avg_price_in_category
    FROM products
    GROUP BY category
) cat_avg ON p.category = cat_avg.category;

-- 9. Product Price vs Category Average (Window function)
SELECT
    name,
    category,
    price,
    AVG(price) OVER (PARTITION BY category) AS avg_price_in_category
FROM products;

-- 10. Product Ranking by Price in Category
SELECT
    name,
    category,
    price,
    ROW_NUMBER() OVER (PARTITION BY category ORDER BY price DESC) AS price_rank
FROM products;

-- 11. Account Rank by Email Count
SELECT
    id_account AS account_id,
    COUNT(id_message) AS total_emails,
    RANK() OVER (ORDER BY COUNT(id_message) DESC) AS account_rank
FROM email_sent
GROUP BY id_account
ORDER BY account_rank;

-- 12. Top 10 Sent Days per Account
SELECT account_id, sent_date, sent_day_rank
FROM (
    SELECT
        id_account AS account_id,
        sent_date,
        DENSE_RANK() OVER (PARTITION BY id_account ORDER BY sent_date DESC) AS sent_day_rank
    FROM (
        SELECT DISTINCT
            es.id_account,
            (s.date + (es.sent_date || ' days')::interval) AS sent_date
        FROM email_sent es
        JOIN account_session acs ON acs.account_id = es.id_account
        JOIN sessions s ON s.ga_session_id = acs.ga_session_id
    ) AS unique_days
) ranked
WHERE sent_day_rank <= 10
ORDER BY account_id, sent_day_rank;

-- 13. Continents and countries
SELECT
    continent,
    country,
    COUNT(*) AS session_count,
    ROW_NUMBER() OVER (
        PARTITION BY continent
        ORDER BY country
        ) AS country_number
FROM session_params
GROUP BY continent, country
ORDER BY continent, country;