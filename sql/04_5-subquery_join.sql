SELECT p.name, so.item_id
FROM products p
         JOIN (SELECT item_id
               FROM orders
               WHERE id < 100) AS so
              ON p.item_id = so.item_id;


-- Join accounts with list of sent emails for a specific date
SELECT a.id, e.sent_date
FROM account a
         JOIN (SELECT id_account, sent_date
               FROM email_sent
               WHERE sent_date > 20) e ON a.id = e.id_account;