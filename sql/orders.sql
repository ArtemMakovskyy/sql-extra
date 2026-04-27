-- Orders count by product
SELECT p.name, COUNT(o.id) as order_count
FROM products p
LEFT JOIN orders o ON p.item_id = o.item_id
GROUP BY p.item_id, p.name
ORDER BY order_count DESC;

-- Revenue by product
SELECT p.name, SUM(p.price) as total_revenue
FROM products p
JOIN orders o ON p.item_id = o.item_id
GROUP BY p.item_id, p.name
ORDER BY total_revenue DESC;

-- Orders by session
SELECT * FROM orders WHERE ga_session_id = 'ga_abc123';

-- All orders with product details
SELECT o.id, o.ga_session_id, p.name as product_name, p.price
FROM orders o
JOIN products p ON o.item_id = p.item_id;