# SQL Queries — 22 files

A collection of SQL queries demonstrating various constructs and approaches.

## Learning queries (01–08)

From basic constructs to more complex — progression of difficulty:

| File | Topic | Constructs |
|---|---|---|
| [`01-basic.sql`](01-basic.sql) | Basic SELECT, JOIN, aggregation | `JOIN`, `GROUP BY`, `HAVING`, `COUNT`, `LIMIT` |
| [`02-case-when.sql`](02-case-when.sql) | CASE WHEN conditions | `CASE`, `WHEN`, `THEN`, `ELSE`, `END`, conditional aggregation |
| [`03-union.sql`](03-union.sql) | UNION / UNION ALL | `UNION ALL`, combining results from different queries |
| [`04_1-subquery_select.sql`](04_1-subquery_select.sql) | Subquery in SELECT | Scalar subqueries in SELECT |
| [`04_2-subquery_from.sql`](04_2-subquery_from.sql) | Subquery in FROM | Derived tables, subqueries as source |
| [`04_3-subquery_where.sql`](04_3-subquery_where.sql) | Subquery in WHERE | `IN`, `EXISTS`, comparison with subquery |
| [`04_4-subquery_having.sql`](04_4-subquery_having.sql) | Subquery in HAVING | Filtering groups by subquery result |
| [`04_5-subquery_join.sql`](04_5-subquery_join.sql) | Subquery in JOIN | Joining with subquery result |
| [`05-data.sql`](05-data.sql) | Working with dates | `EXTRACT`, `+ interval`, `BETWEEN`, `DATE_TRUNC` |
| [`06-window-functions.sql`](06-window-functions.sql) | Window functions | `ROW_NUMBER()`, `RANK()`, `SUM() OVER`, `PARTITION BY` |
| [`06_0-window-functions-overview.sql`](06_0-window-functions-overview.sql) | Window functions overview | `ROW_NUMBER`, `RANK`, `DENSE_RANK`, `LAG`, `LEAD` |
| [`07-cte.sql`](07-cte.sql) | Common Table Expressions | `WITH`, recursive and non-recursive CTE |
| [`08_1-view.sql`](08_1-view.sql) | Views (VIEW) | `CREATE OR REPLACE VIEW`, `WITH` in VIEW |
| [`08_2-temp.sql`](08_2-temp.sql) | Temporary tables | `CREATE TEMP TABLE`, `DROP TABLE` |

## Analytical queries

Real-world web analytics scenarios:

| File | Description |
|---|---|
| [`analytics.sql`](analytics.sql) | Sessions by device type, country, browser, channel |
| [`email-funnel.sql`](email-funnel.sql) | Email funnel per account: sent → opened → clicked |
| [`orders.sql`](orders.sql) | Orders count by product, revenue by product, orders by session |
| [`products.sql`](products.sql) | All products, filter by category, price range, top expensive |
| [`sessions.sql`](sessions.sql) | All sessions, filter by date, join with session_params |

## Other

| File | Description |
|---|---|
| [`09-ab.sql`](09-ab.sql) | A/B testing analysis: sessions, events, orders, new accounts by test groups |
