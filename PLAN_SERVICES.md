# Services Plan — Data Analytics Mate

## Goal

Create Service layer with CRUD operations for all 13 entities AND analytical repositories with **PostgreSQL** native queries matching BigQuery CTEs.

---

## Part 1: CRUD Services + Controllers

Standard CRUD operations for each entity (see previous section).

---

## Part 2: Analytical Repositories (PostgreSQL Native Queries)

### 1. Revenue By Country (with device breakdown)

```sql
SELECT
    sp.country,
    SUM(p.price) AS revenue_usd,
    SUM(CASE WHEN sp.device = 'mobile' THEN p.price ELSE 0 END) AS revenue_from_mobile,
    SUM(CASE WHEN sp.operating_system = 'iOS' THEN p.price ELSE 0 END) AS revenue_from_ios_mobile,
    SUM(CASE WHEN sp.operating_system = 'Android' THEN p.price ELSE 0 END) AS revenue_from_android_mobile
FROM orders o
JOIN product p ON o.item_id = p.item_id
JOIN session_params sp ON sp.ga_session_id = o.ga_session_id
GROUP BY sp.country
```

### 2. Registration By Country

```sql
SELECT
    sp.country,
    COUNT(sp.ga_session_id) AS session_cnt,
    COUNT(ac.account_id) AS account_cnt
FROM session_params sp
LEFT JOIN account_session ac ON sp.ga_session_id = ac.ga_session_id
GROUP BY sp.country
```

### 3. Email Metrics By Country

```sql
SELECT
    sp.country,
    COUNT(DISTINCT ems.id_message) AS sent_msg
FROM email_sent ems
JOIN account_session ac ON ems.id_account = ac.account_id
JOIN session_params sp ON ac.ga_session_id = sp.ga_session_id
GROUP BY sp.country
```

### 4. Monthly Revenue and Cost

```sql
SELECT
    EXTRACT(YEAR FROM db.date) AS year,
    EXTRACT(MONTH FROM db.date) AS month,
    SUM(db.revenue) AS revenue,
    SUM(db.cost) AS cost
FROM (
    SELECT date, 0 AS revenue, cost FROM paid_search_cost
    UNION ALL
    SELECT s.date, p.price AS revenue, 0 AS cost
    FROM orders o
    JOIN session s ON o.ga_session_id = s.ga_session_id
    JOIN product p ON o.item_id = p.item_id
) AS db
GROUP BY EXTRACT(YEAR FROM db.date), EXTRACT(MONTH FROM db.date)
ORDER BY year, month
```

### 5. Cumulative Revenue vs Predict

```sql
WITH daily_data AS (
    SELECT s.date, SUM(p.price) AS revenue, 0::numeric AS predict
    FROM session s
    JOIN orders o ON s.ga_session_id = o.ga_session_id
    JOIN product p ON o.item_id = p.item_id
    GROUP BY s.date
    UNION ALL
    SELECT date, 0::numeric AS revenue, predict FROM revenue_predict
)
SELECT
    date,
    SUM(revenue) OVER (ORDER BY date) AS cumulative_revenue,
    SUM(predict) OVER (ORDER BY date) AS cumulative_predict,
    SUM(revenue) OVER (ORDER BY date) / NULLIF(SUM(predict) OVER (ORDER BY date), 0) * 100 AS percent_of_goal
FROM daily_data
GROUP BY date
ORDER BY date
```

### 6. Account Monthly Email Stats

```sql
WITH account_registration AS (
    SELECT acs.account_id, MIN(s.date) AS reg_date
    FROM account_session acs
    JOIN session s ON acs.ga_session_id = s.ga_session_id
    GROUP BY acs.account_id
),
account_monthly_stat AS (
    SELECT
        DATE_TRUNC('MONTH', s.date + (ems.sent_date || ' days')::interval) AS sent_month,
        ems.id_account,
        COUNT(*) AS emails_per_account,
        MIN(s.date + (ems.sent_date || ' days')::interval) AS first_sent_date,
        MAX(s.date + (ems.sent_date || ' days')::interval) AS last_sent_date
    FROM email_sent ems
    JOIN account_registration reg ON ems.id_account = reg.account_id
    JOIN session s ON s.ga_session_id = (
        SELECT ga_session_id FROM account_session WHERE account_id = ems.id_account LIMIT 1
    )
    GROUP BY DATE_TRUNC('MONTH', s.date + (ems.sent_date || ' days')::interval), ems.id_account
)
SELECT * FROM account_monthly_stat ORDER BY sent_month, id_account
```

### 7. Browser Statistics

```sql
SELECT
    browser,
    COUNT(ga_session_id) AS session_cnt,
    COUNT(CASE WHEN language IS NULL THEN 1 END) AS session_cnt_with_empty_language,
    COUNT(CASE WHEN language IS NULL THEN 1 END) * 100.0 / COUNT(ga_session_id) AS session_cnt_with_empty_language_percent
FROM session_params
GROUP BY browser
ORDER BY session_cnt DESC
```

### 8. Revenue By Continent & Device

```sql
SELECT
    continent,
    SUM(p.price) AS revenue,
    SUM(CASE WHEN sp.device = 'mobile' THEN p.price ELSE 0 END) * 100.0 / NULLIF(SUM(p.price), 0) AS revenue_from_mobile_percent
FROM session_params sp
JOIN orders o ON sp.ga_session_id = o.ga_session_id
JOIN product p ON p.item_id = o.item_id
GROUP BY continent
ORDER BY revenue DESC
```

### 9. Email To Unsubscribed Users

```sql
SELECT
    sp.country,
    COUNT(DISTINCT es.id_message) AS sent_cnt
FROM email_sent es
JOIN account a ON es.id_account = a.id
JOIN account_session acs ON a.id = acs.account_id
JOIN session_params sp ON acs.ga_session_id = sp.ga_session_id
WHERE a.is_unsubscribed = 1
GROUP BY sp.country
ORDER BY sent_cnt DESC
```

### 10. Revenue By Category & Continent

```sql
SELECT
    sp.continent,
    SUM(p.price) AS revenue,
    SUM(CASE WHEN p.category = 'Bookcases & shelving units' THEN p.price ELSE 0 END) AS revenue_from_bookcases,
    SUM(CASE WHEN p.category = 'Bookcases & shelving units' THEN p.price ELSE 0 END) * 100.0 / NULLIF(SUM(p.price), 0) AS revenue_from_bookcases_percent
FROM orders o
JOIN product p ON o.item_id = p.item_id
JOIN session_params sp ON o.ga_session_id = sp.ga_session_id
GROUP BY sp.continent
ORDER BY revenue DESC
```

### 11. Cost vs Revenue By Date

```sql
SELECT date, 'cost' AS type, SUM(cost) AS value
FROM paid_search_cost
GROUP BY date
UNION ALL
SELECT s.date, 'revenue' AS type, SUM(p.price) AS value
FROM session s
JOIN orders o ON s.ga_session_id = o.ga_session_id
JOIN product p ON o.item_id = p.item_id
GROUP BY s.date
ORDER BY date, type
```

### 12. Email Journey (Sent, Open, Visit)

```sql
WITH account_registration AS (
    SELECT acs.account_id, MIN(s.date) AS account_created
    FROM account_session acs
    JOIN session s ON acs.ga_session_id = s.ga_session_id
    GROUP BY acs.account_id
)
SELECT
    base.account_created + (es.sent_date || ' days')::interval AS sent_date,
    base.account_created + (eo.open_date || ' days')::interval AS open_date,
    base.account_created + (ev.visit_date || ' days')::interval AS visit_date,
    acc.id AS id_account,
    es.id_message
FROM account acc
JOIN account_registration base ON acc.id = base.account_id
JOIN email_sent es ON acc.id = es.id_account
LEFT JOIN email_open eo ON es.id_account = eo.id_account AND es.id_message = eo.id_message
LEFT JOIN email_visit ev ON es.id_account = ev.id_account AND es.id_message = ev.id_message
```

### 13. Email Metrics by Date (Sent, Opens, Clicks)

```sql
WITH email_metrics AS (
    SELECT
        s.date + (ems.sent_date || ' days')::interval AS sent_date,
        COUNT(DISTINCT ems.id_message) AS sent_msg,
        COUNT(DISTINCT eo.id_message) AS open_msg,
        COUNT(DISTINCT ev.id_message) AS click_msg
    FROM email_sent ems
    JOIN account_session acs ON ems.id_account = acs.account_id
    JOIN session s ON acs.ga_session_id = s.ga_session_id
    LEFT JOIN email_open eo ON ems.id_message = eo.id_message
    LEFT JOIN email_visit ev ON ems.id_message = ev.id_message
    GROUP BY s.date + (ems.sent_date || ' days')::interval
)
SELECT * FROM email_metrics ORDER BY sent_date
```

### 14. Monthly Email Open Rate and Click Rate

```sql
WITH email_metrics AS (
    SELECT
        DATE_TRUNC('MONTH', s.date + (ems.sent_date || ' days')::interval) AS month,
        COUNT(DISTINCT ems.id_message) AS sent_msg,
        COUNT(DISTINCT eo.id_message) AS open_msg,
        COUNT(DISTINCT ev.id_message) AS click_msg
    FROM email_sent ems
    JOIN account_session acs ON ems.id_account = acs.account_id
    JOIN session s ON acs.ga_session_id = s.ga_session_id
    LEFT JOIN email_open eo ON ems.id_message = eo.id_message
    LEFT JOIN email_visit ev ON ems.id_message = ev.id_message
    GROUP BY DATE_TRUNC('MONTH', s.date + (ems.sent_date || ' days')::interval)
)
SELECT
    EXTRACT(YEAR FROM month) AS year,
    EXTRACT(MONTH FROM month) AS month,
    sent_msg,
    open_msg,
    click_msg,
    open_msg * 100.0 / NULLIF(sent_msg, 0) AS open_rate,
    click_msg * 100.0 / NULLIF(sent_msg, 0) AS click_rate
FROM email_metrics
ORDER BY year, month
```

---

## PostgreSQL Syntax Notes

| BigQuery | PostgreSQL |
|----------|------------|
| `EXTRACT(YEAR FROM TIMESTAMP_MICROS(ts))` | `EXTRACT(YEAR FROM TO_TIMESTAMP(ts/1000000))` |
| `DATE_ADD(date, INTERVAL N DAY)` | `date + (N \|\| ' days')::interval` |
| `DATE_TRUNC('MONTH', date)` | `DATE_TRUNC('month', date)` |
| `NULLIF(x, 0)` | `NULLIF(x, 0)` (same) |
| `COUNT(*) OVER (PARTITION BY ...)` | Same in PostgreSQL |

## Implementation Order

1. CRUD Services + Controllers
2. Analytical Repositories (native queries)
3. Analytical Services
4. Analytical Controllers