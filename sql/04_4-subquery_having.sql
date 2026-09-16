-- Browsers where session count exceeds average session count by countries
SELECT browser, COUNT(id) AS session_cnt
FROM session_params
GROUP BY browser
HAVING COUNT(id) > (SELECT AVG(cnt)
                    FROM (SELECT COUNT(id) AS cnt FROM session_params GROUP BY country) AS sub);

-- Filter categories where total price is greater than average
SELECT category, SUM(price) AS total
FROM products
GROUP BY category
HAVING SUM(price) > (SELECT AVG(price) FROM products);
