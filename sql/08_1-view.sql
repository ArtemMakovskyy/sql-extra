CREATE OR REPLACE VIEW vw_revenue_by_country AS
SELECT
    sp.country,
    COUNT(*) AS orders_count,
    SUM(p.price) AS total_revenue
FROM orders o
         JOIN products p ON p.item_id = o.item_id
         JOIN session_params sp ON sp.ga_session_id = o.ga_session_id
GROUP BY sp.country;

SELECT *
FROM vw_revenue_by_country
ORDER BY total_revenue DESC;


CREATE OR REPLACE VIEW v_accounts_by_month AS
WITH aggregated AS (
    SELECT
        DATE_TRUNC('month', s.date + (es.sent_date || ' days')::INTERVAL) AS sent_month,
        es.id_account,
        COUNT(*) AS msg_count,
        MIN(s.date + (es.sent_date || ' days')::INTERVAL) AS first_sent_date,
        MAX(s.date + (es.sent_date || ' days')::INTERVAL) AS last_sent_date
    FROM email_sent es
             JOIN account_session acs ON acs.account_id = es.id_account
             JOIN sessions s ON s.ga_session_id = acs.ga_session_id
    GROUP BY sent_month, id_account
)
SELECT
    sent_month,
    id_account,
    msg_count * 100.0 / SUM(msg_count) OVER (PARTITION BY sent_month) AS sent_msg_percent_from_this_month,
    first_sent_date,
    last_sent_date
FROM aggregated
ORDER BY sent_month, id_account;