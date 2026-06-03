# SQL Queries — 15 файлів

Набір SQL запитів, що демонструють різні конструкції та підходи.

## Навчальні запити (01–08)

Від базових конструкцій до складніших — прогресія складності:

| Файл | Тема | Конструкції |
|---|---|---|
| [`01-basic.sql`](01-basic.sql) | Базові SELECT, JOIN, агрегація | `JOIN`, `GROUP BY`, `HAVING`, `COUNT`, `LIMIT` |
| [`02-case-when.sql`](02-case-when.sql) | CASE WHEN умови | `CASE`, `WHEN`, `THEN`, `ELSE`, `END`, агрегація з умовами |
| [`03-union.sql`](03-union.sql) | UNION / UNION ALL | `UNION ALL`, комбінування результатів різних запитів |
| [`04-subquery.sql`](04-subquery.sql) | Підзапити | Підзапити в `FROM`, `WHERE`, скалярні підзапити |
| [`05-data.sql`](05-data.sql) | Робота з датами | `EXTRACT`, `+ interval`, `BETWEEN`, `DATE_TRUNC`, `age()` |
| [`06-window-functions.sql`](06-window-functions.sql) | Віконні функції | `ROW_NUMBER()`, `RANK()`, `SUM() OVER`, `PARTITION BY` |
| [`07-cte.sql`](07-cte.sql) | Common Table Expressions | `WITH`, рекурсивні та нерекурсивні CTE |
| [`08_1-view.sql`](08_1-view.sql) | Представлення (VIEW) | `CREATE OR REPLACE VIEW`, `WITH` у VIEW |
| [`08_2-temp.sql`](08_2-temp.sql) | Тимчасові таблиці | `CREATE TEMP TABLE`, `DROP TABLE` |

## Аналітичні запити

Реалістичні сценарії веб-аналітики:

| Файл | Опис |
|---|---|
| [`analytics.sql`](analytics.sql) | Аналітика: сесії за типами пристроїв, канали трафіку, A/B тести |
| [`email-funnel.sql`](email-funnel.sql) | Email-воронка: надіслано → відкрито → клікнуто |
| [`orders.sql`](orders.sql) | Замовлення: кількість товарів, суми за сесіями |
| [`products.sql`](products.sql) | Товари: всі товари, фільтрація за категорією |
| [`sessions.sql`](sessions.sql) | Сесії: всі сесії, фільтрація за датою, кількість подій |
