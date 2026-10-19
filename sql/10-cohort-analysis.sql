-- Cohort analysis: email-visit retention by account signup week
-- email_visit.visit_date = days after account creation, so visit_date / 7 = weeks since signup
WITH acc AS (
    SELECT
        a.id AS account_id,
        MIN(s.date) AS account_created_date
    FROM account a
    JOIN account_session acs ON a.id = acs.account_id
    JOIN sessions s ON acs.ga_session_id = s.ga_session_id
    GROUP BY a.id
),
cohort_size AS (
    SELECT
        DATE_TRUNC('week', account_created_date)::date AS account_created_week,
        COUNT(*) AS total_accounts
    FROM acc
    GROUP BY 1
),
visits AS (
    SELECT DISTINCT
        acc.account_id,
        DATE_TRUNC('week', acc.account_created_date)::date AS account_created_week,
        ev.visit_date / 7 AS weeks_since_signup
    FROM acc
    JOIN email_visit ev ON acc.account_id = ev.id_account
)
SELECT
    v.account_created_week,
    v.weeks_since_signup,
    cs.total_accounts,
    COUNT(DISTINCT v.account_id) AS active_accounts,
    COUNT(DISTINCT v.account_id)::numeric / NULLIF(cs.total_accounts, 0) AS open_rate
FROM visits v
JOIN cohort_size cs ON v.account_created_week = cs.account_created_week
GROUP BY 1, 2, 3
ORDER BY 1, 2;

-- Email visit dates per account: real visit_date reconstructed from days-after-signup offset
WITH acc AS (
    SELECT
        a.id AS account_id,
        MIN(s.date) AS account_created_date
    FROM account a
    JOIN account_session acs ON a.id = acs.account_id
    JOIN sessions s ON acs.ga_session_id = s.ga_session_id
    GROUP BY a.id
)
SELECT
    acc.account_id,
    acc.account_created_date,
    (acc.account_created_date + ev.visit_date * INTERVAL '1 day')::date AS visit_date
FROM acc
LEFT JOIN email_visit ev ON acc.account_id = ev.id_account
ORDER BY acc.account_id, visit_date;
