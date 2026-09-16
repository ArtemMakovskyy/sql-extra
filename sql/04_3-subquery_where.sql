-- Orders where product price exceeds 50
SELECT *
FROM orders
WHERE item_id IN (SELECT item_id
                  FROM products
                  WHERE price > 50);

-- Filter categories where total price is greater than average
SELECT name, price
FROM products
WHERE price > (SELECT AVG(price) FROM products);

-- Accounts with More Than 2 Email Visits (non-unsubscribed)
SELECT *
FROM account
WHERE is_unsubscribed = 0
  AND id IN (
    SELECT id_account
    FROM email_visit
    GROUP BY id_account
    HAVING COUNT(DISTINCT id_message) > 2
  );

-- Top 10 Sent Days per Account
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

