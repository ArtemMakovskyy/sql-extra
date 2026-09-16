-- Average product price for each account
SELECT ga_session_id,
       (SELECT AVG(price) FROM products) AS avg_product_price
FROM account acc
         LEFT JOIN account_session accs on acc.id = accs.account_id;

-- Returns each product and the average price of all products
SELECT name,
       (SELECT AVG(price) FROM products) AS avg_price
FROM products;

