-- 1. Revenue by Continent with Device Breakdown
WITH continent_revenue AS (
    SELECT
        sp.continent,
        SUM(p.price) AS revenue,
        SUM(CASE WHEN sp.device = 'mobile' THEN p.price ELSE 0 END) AS revenue_from_mobile,
        SUM(CASE WHEN sp.device = 'desktop' THEN p.price ELSE 0 END) AS revenue_from_desktop
    FROM session_params sp
    JOIN orders o ON o.ga_session_id = sp.ga_session_id
    JOIN products p ON p.item_id = o.item_id
    GROUP BY sp.continent
),
revenue_with_percent AS (
    SELECT
        continent,
        revenue,
        revenue_from_mobile,
        revenue_from_desktop,
        ROUND(revenue / NULLIF(SUM(revenue) OVER (), 0) * 100, 2) AS percent_revenue_from_total
    FROM continent_revenue
),
accounts AS (
    SELECT
        sp.continent,
        COUNT(DISTINCT acs.account_id) AS account_count,
        COUNT(CASE WHEN a.is_verified = 1 THEN acs.account_id END) AS verified_account,
        COUNT(DISTINCT acs.ga_session_id) AS session_count
    FROM account_session acs
    JOIN session_params sp ON sp.ga_session_id = acs.ga_session_id
    JOIN account a ON a.id = acs.account_id
    GROUP BY sp.continent
)
SELECT
    r.continent,
    r.revenue,
    r.revenue_from_mobile,
    r.revenue_from_desktop,
    r.percent_revenue_from_total,
    a.account_count,
    a.verified_account,
    a.session_count
FROM revenue_with_percent r
LEFT JOIN accounts a ON r.continent = a.continent
ORDER BY r.revenue DESC;

-- 2. Revenue by Country with Registration and Email Metrics
WITH revenue_usd AS (
    SELECT
        sp.country,
        SUM(p.price) AS revenue_usd,
        SUM(CASE WHEN device = 'mobile' THEN p.price END) AS revenue_from_mobile,
        SUM(CASE WHEN operating_system = 'iOS' THEN p.price END) AS revenue_from_ios_mobile,
        SUM(CASE WHEN operating_system = 'Android' THEN p.price END) AS revenue_from_android_mobile
    FROM orders o
    JOIN products p ON o.item_id = p.item_id
    JOIN session_params sp ON sp.ga_session_id = o.ga_session_id
    GROUP BY sp.country
),
registration AS (
    SELECT
        sp.country,
        COUNT(sp.ga_session_id) AS session_cnt,
        COUNT(ac.account_id) AS account_cnt
    FROM session_params sp
    LEFT JOIN account_session ac ON sp.ga_session_id = ac.ga_session_id
    GROUP BY sp.country
),
email_metrics AS (
    SELECT
        sp.country,
        COUNT(DISTINCT ems.id_message) AS sent_msg
    FROM email_sent ems
    JOIN account_session ac ON ems.id_account = ac.account_id
    JOIN session_params sp ON ac.ga_session_id = sp.ga_session_id
    GROUP BY sp.country
)
SELECT
    r.country,
    rev.revenue_usd,
    rev.revenue_from_mobile,
    rev.revenue_from_ios_mobile,
    rev.revenue_from_android_mobile,
    r.session_cnt,
    ROUND(r.account_cnt::numeric / NULLIF(r.session_cnt, 0) * 100, 2) AS registration_percent,
    em.sent_msg
FROM registration r
LEFT JOIN revenue_usd rev ON r.country = rev.country
LEFT JOIN email_metrics em ON r.country = em.country;

-- 3. Monthly Metrics Summary (Revenue, Cost, Email, Registrations)
WITH revenue_usd AS (
    SELECT
        s.date,
        SUM(p.price) AS revenue
    FROM orders o
    JOIN products p ON o.item_id = p.item_id
    JOIN sessions s ON o.ga_session_id = s.ga_session_id
    GROUP BY s.date
),
email_metrics AS (
    SELECT
        (s.date + (ems.sent_date || ' days')::interval) AS sent_date,
        COUNT(DISTINCT ems.id_message) AS sent_msg,
        COUNT(DISTINCT eo.id_message) AS open_msg,
        COUNT(DISTINCT ev.id_message) AS click_msg
    FROM email_sent ems
    JOIN account_session acs ON ems.id_account = acs.account_id
    JOIN sessions s ON acs.ga_session_id = s.ga_session_id
    LEFT JOIN email_open eo ON ems.id_message = eo.id_message
    LEFT JOIN email_visit ev ON ems.id_message = ev.id_message
    GROUP BY (s.date + (ems.sent_date || ' days')::interval)
),
registrations AS (
    SELECT
        s.date,
        COUNT(ac.account_id) AS account_cnt
    FROM account_session ac
    JOIN sessions s ON ac.ga_session_id = s.ga_session_id
    GROUP BY s.date
),
final AS (
    SELECT date, revenue, 0 AS cost, 0 AS sent_msg, 0 AS open_msg, 0 AS click_msg, 0 AS account_cnt
    FROM revenue_usd
    UNION ALL
    SELECT date, 0 AS revenue, cost, 0 AS sent_msg, 0 AS open_msg, 0 AS click_msg, 0 AS account_cnt
    FROM paid_search_cost
    UNION ALL
    SELECT sent_date, 0 AS revenue, 0 AS cost, sent_msg, open_msg, click_msg, 0 AS account_cnt
    FROM email_metrics
    UNION ALL
    SELECT date, 0 AS revenue, 0 AS cost, 0 AS sent_msg, 0 AS open_msg, 0 AS click_msg, account_cnt
    FROM registrations
)
SELECT
    EXTRACT(YEAR FROM date) AS year,
    EXTRACT(MONTH FROM date) AS month,
    SUM(revenue) AS revenue,
    SUM(cost) AS cost,
    SUM(sent_msg) AS sent_msg,
    ROUND(SUM(open_msg)::numeric / NULLIF(SUM(sent_msg), 0), 4) AS open_rate,
    ROUND(SUM(click_msg)::numeric / NULLIF(SUM(sent_msg), 0), 4) AS click_rate,
    SUM(account_cnt) AS registrations
FROM final
GROUP BY
    EXTRACT(YEAR FROM date),
    EXTRACT(MONTH FROM date)
ORDER BY year, month;