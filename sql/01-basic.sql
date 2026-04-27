-- 1. Scroll Events (UK)
SELECT COUNT(DISTINCT sp.ga_session_id) AS uk_scroll_sessions
FROM session_params sp
JOIN account_session acs ON sp.ga_session_id = acs.ga_session_id
JOIN event_params ep ON sp.ga_session_id = ep.ga_session_id
WHERE sp.country = 'United Kingdom'
  AND ep.event_name = 'scroll';

-- 2. Sessions by Browsers
SELECT browser, COUNT(ga_session_id) AS session_cnt
FROM session_params
GROUP BY browser
ORDER BY session_cnt DESC;

-- 3. Emails Sent to Unsubscribers by Country
SELECT sp.country,
    COUNT(DISTINCT es.id_message) AS sent_cnt,
    ROUND(
        COUNT(DISTINCT CASE WHEN a.is_unsubscribed = 1 THEN es.id_message END) * 100.0 /
        NULLIF(COUNT(DISTINCT es.id_message), 0),
    2) AS sent_cnt_unsub_percent
FROM email_sent es
JOIN account a ON es.id_account = a.id
JOIN account_session acs ON a.id = acs.account_id
JOIN session_params sp ON acs.ga_session_id = sp.ga_session_id
GROUP BY sp.country
ORDER BY sent_cnt DESC;

-- 4. Category Revenue by Country (Beds, Europe)
SELECT sp.country,
    SUM(p.price) AS revenue,
    COUNT(o.item_id) AS count_of_orders
FROM session_params sp
JOIN orders o ON sp.ga_session_id = o.ga_session_id
JOIN products p ON o.item_id = p.item_id
WHERE p.category = 'Beds' AND sp.continent = 'Europe'
GROUP BY sp.country
ORDER BY count_of_orders DESC;

-- 5. Email Funnel by Country
SELECT sp.country,
    COUNT(DISTINCT es.id_message) AS email_sent_cnt,
    COUNT(DISTINCT eo.id_message) AS email_open_cnt,
    COUNT(DISTINCT ev.id_message) AS email_click_cnt,
    ROUND(COUNT(DISTINCT eo.id_message)::numeric / NULLIF(COUNT(DISTINCT es.id_message), 0), 4) AS open_rate,
    ROUND(COUNT(DISTINCT ev.id_message)::numeric / NULLIF(COUNT(DISTINCT es.id_message), 0), 4) AS click_rate,
    ROUND(COUNT(DISTINCT ev.id_message)::numeric / NULLIF(COUNT(DISTINCT eo.id_message), 0), 4) AS ctor
FROM email_sent es
LEFT JOIN email_open eo ON es.id_message = eo.id_message
LEFT JOIN email_visit ev ON es.id_message = ev.id_message
LEFT JOIN account_session acs ON es.id_account = acs.account_id
LEFT JOIN session_params sp ON acs.ga_session_id = sp.ga_session_id
GROUP BY sp.country
ORDER BY email_sent_cnt DESC
LIMIT 4;

-- 6. Letter Type with Best Open Rate (US)
SELECT es.letter_type,
    COUNT(DISTINCT es.id_message) AS email_sent_cnt,
    COUNT(DISTINCT eo.id_message) AS email_open_cnt,
    ROUND(COUNT(DISTINCT eo.id_message)::numeric / NULLIF(COUNT(DISTINCT es.id_message), 0), 4) AS open_rate
FROM email_sent es
LEFT JOIN email_open eo ON es.id_message = eo.id_message
JOIN account_session acs ON es.id_account = acs.account_id
JOIN session_params sp ON acs.ga_session_id = sp.ga_session_id
WHERE sp.country = 'United States'
GROUP BY es.letter_type
ORDER BY open_rate DESC
LIMIT 1;

-- 7. Shopping Sessions Percentage by Country
SELECT sp.country,
    COUNT(DISTINCT o.ga_session_id) * 100.0 / NULLIF(COUNT(DISTINCT s.ga_session_id), 0) AS session_with_orders_percent,
    COUNT(DISTINCT s.ga_session_id) AS session_cnt
FROM sessions s
JOIN session_params sp ON s.ga_session_id = sp.ga_session_id
LEFT JOIN orders o ON s.ga_session_id = o.ga_session_id
GROUP BY sp.country
ORDER BY session_cnt DESC;