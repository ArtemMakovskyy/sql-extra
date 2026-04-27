-- 1. Product Quantity by Category Group
SELECT
  CASE
    WHEN position('furniture' in category) > 0 THEN 'furniture'
    WHEN position('units' in category) > 0 THEN 'units'
    ELSE 'other'
  END AS category_group,
  COUNT(*) AS quantity
FROM products
GROUP BY category_group;

-- 2. Accounts with More Than 2 Email Visits (non-unsubscribed)
SELECT *
FROM account
WHERE is_unsubscribed = 0
  AND id IN (
    SELECT id_account
    FROM email_visit
    GROUP BY id_account
    HAVING COUNT(DISTINCT id_message) > 2
  );

-- 3. User Engagement Events (sessions with >2 events)
SELECT COUNT(*) AS user_engagement_cnt
FROM event_params ep
JOIN (
    SELECT ga_session_id
    FROM event_params
    GROUP BY ga_session_id
    HAVING COUNT(*) > 2
) AS active_sessions ON ep.ga_session_id = active_sessions.ga_session_id
WHERE ep.event_name = 'user_engagement';

-- 4. Extract Size from Product Description
SELECT
  short_description,
  CASE
    WHEN position('x' in short_description) > 0 AND position('cm' in short_description) > 0
    THEN SUBSTRING(short_description FROM position('x' in short_description) + 1 FOR length(short_description))
    ELSE NULL
  END AS size
FROM products;

-- 5. Sessions by Language Type (en-)
SELECT
  SUBSTRING(language FROM length(language) - 1 FOR 2) AS en_type,
  COUNT(ga_session_id) AS session_cnt
FROM session_params
WHERE language IS NOT NULL
  AND language LIKE 'en-%'
GROUP BY en_type
ORDER BY session_cnt DESC;