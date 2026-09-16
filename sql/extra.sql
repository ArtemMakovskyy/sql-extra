

-- =====================================================================
-- Combined query that joins data from all 13 tables
-- =====================================================================
-- Idea: account is the central entity (level 0). From it:
--   - email activity (email_sent / email_open / email_visit)
--   - sessions (via account_session -> sessions), and from session -
--     session_params, ab_test, event_params, orders -> products
-- paid_search_cost and revenue_predict have no FK to anything - they can
-- be joined only by date (sessions.date) as a separate "macro" context.
--
-- NOTE: ab_test, event_params, orders, email_sent/open/visit are
-- 1:many relationships. If joined directly, rows will be duplicated
-- (cartesian product). Therefore each block is first
-- aggregated in CTE to "one row per key" level, and only then
-- combined into one final SELECT.
-- =====================================================================

WITH

-- 1) Email activity per account -> one row per account_id
email_agg AS (
    SELECT
        a.id AS account_id,
        COUNT(DISTINCT es.id)              AS emails_sent_cnt,
        COUNT(DISTINCT eo.id)              AS emails_open_cnt,
        COUNT(DISTINCT ev.id)              AS emails_visit_cnt,
        MAX(es.sent_date)                  AS last_email_sent_date
    FROM account a
             LEFT JOIN email_sent  es ON es.id_account = a.id
             LEFT JOIN email_open  eo ON eo.id_account = a.id
             LEFT JOIN email_visit ev ON ev.id_account = a.id
    GROUP BY a.id
),

-- 2) A/B test sessions -> one row per ga_session_id
ab_test_agg AS (
    SELECT
        ga_session_id,
        STRING_AGG(DISTINCT test || ':' || test_group, ', ') AS ab_tests
    FROM ab_test
    GROUP BY ga_session_id
),

-- 3) Session events -> one row per ga_session_id
event_params_agg AS (
    SELECT
        ga_session_id,
        COUNT(*)                        AS events_cnt,
        COUNT(DISTINCT event_name)      AS distinct_event_types,
        MIN(event_timestamp)            AS first_event_ts,
        MAX(event_timestamp)            AS last_event_ts
    FROM event_params
    GROUP BY ga_session_id
),

-- 4) Session orders + products -> one row per ga_session_id
orders_agg AS (
    SELECT
        o.ga_session_id,
        COUNT(*)                                   AS orders_cnt,
        SUM(p.price)                                AS orders_revenue,
        STRING_AGG(DISTINCT p.category, ', ')       AS ordered_categories
    FROM orders o
             JOIN products p ON p.item_id = o.item_id
    GROUP BY o.ga_session_id
),

-- 5) Marketing/finance by dates (no FK, only date)
marketing_by_date AS (
    SELECT
        COALESCE(psc.date, rp.date) AS date,
        psc.cost      AS paid_search_cost,
        rp.predict    AS revenue_predict
    FROM paid_search_cost psc
             FULL JOIN revenue_predict rp ON rp.date = psc.date
)

-- =====================================================================
-- Final combined SELECT
-- =====================================================================
SELECT
    -- level 0: customer
    a.id                        AS account_id,
    a.send_interval,
    a.is_verified,
    a.is_unsubscribed,

    -- level 1: email activity (aggregated)
    ea.emails_sent_cnt,
    ea.emails_open_cnt,
    ea.emails_visit_cnt,
    ea.last_email_sent_date,

    -- level 1: session (via account_session)
    s.ga_session_id,
    s.date                      AS session_date,

    -- level 2: session params (1:1)
    sp.device,
    sp.mobile_model_name,
    sp.operating_system,
    sp.language,
    sp.browser,
    sp.continent,
    sp.country,
    sp.medium,
    sp.name                     AS traffic_source_name,
    sp.channel,

    -- level 2: A/B tests (aggregated)
    at.ab_tests,

    -- level 2: events (aggregated)
    ep.events_cnt,
    ep.distinct_event_types,
    ep.first_event_ts,
    ep.last_event_ts,

    -- level 2: orders + products (aggregated)
    ord.orders_cnt,
    ord.orders_revenue,
    ord.ordered_categories,

    -- independent topic: marketing/finance by session date
    mkt.paid_search_cost,
    mkt.revenue_predict

FROM account a
         LEFT JOIN email_agg          ea  ON ea.account_id = a.id
         LEFT JOIN account_session    acs ON acs.account_id = a.id
         LEFT JOIN sessions           s   ON s.ga_session_id = acs.ga_session_id
         LEFT JOIN session_params     sp  ON sp.ga_session_id = s.ga_session_id
         LEFT JOIN ab_test_agg        at  ON at.ga_session_id = s.ga_session_id
         LEFT JOIN event_params_agg   ep  ON ep.ga_session_id = s.ga_session_id
         LEFT JOIN orders_agg         ord ON ord.ga_session_id = s.ga_session_id
         LEFT JOIN marketing_by_date  mkt ON mkt.date = s.date

ORDER BY a.id, s.date;