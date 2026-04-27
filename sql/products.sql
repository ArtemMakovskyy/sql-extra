-- Get all products
SELECT * FROM products;

-- Get product by ID
SELECT * FROM products WHERE item_id = 1;

-- Products by category
SELECT * FROM products WHERE category = 'wine';

-- Products with price range
SELECT * FROM products WHERE price BETWEEN 10 AND 50;

-- Top 10 expensive products
SELECT * FROM products ORDER BY price DESC LIMIT 10;