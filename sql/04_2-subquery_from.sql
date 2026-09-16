SELECT category, AVG(price)
FROM (SELECT * FROM products WHERE price > 50) AS expensive
GROUP BY category;

-- User Engagement Events (sessions with >2 events)
SELECT COUNT(*) AS user_engagement_cnt
FROM event_params ep
JOIN (
    SELECT ga_session_id
    FROM event_params
    GROUP BY ga_session_id
    HAVING COUNT(*) > 2
) AS active_sessions ON ep.ga_session_id = active_sessions.ga_session_id
WHERE ep.event_name = 'user_engagement';

-- Product Price vs Category Average (JOIN + subquery)
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
