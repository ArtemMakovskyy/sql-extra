-- 1. Revenue and Cost by Date
SELECT date, 'cost' AS type, SUM(cost) AS value
FROM paid_search_cost
GROUP BY date
UNION ALL
SELECT s.date, 'revenue' AS type, SUM(p.price) AS value
FROM sessions s
JOIN orders o ON s.ga_session_id = o.ga_session_id
JOIN products p ON o.item_id = p.item_id
GROUP BY s.date
ORDER BY date, type;

-- 2. Sessions with Account and Orders
SELECT DISTINCT ga_session_id, 'account' AS type_of_action
FROM account_session acs
UNION ALL
SELECT DISTINCT ga_session_id, 'order' AS type_of_action
FROM orders o;