# Project Structure

## Project Folders

| Folder | Contents |
|---|---|
| [`http/`](../http/) | HTTP requests for testing REST API (JetBrains HTTP Client) |
| [`sql/`](../sql/) | SQL queries to run — 15 files |
| `src/main/java/.../controller/` | REST controllers (13 files) |
| `src/main/java/.../entity/` | JPA entities (13 tables) |
| `src/main/java/.../repository/` | Spring Data JPA repositories |
| `src/main/java/.../service/` | Business logic |
| `src/main/java/.../mapper/` | MapStruct Entity ↔ DTO mappers |
| `src/main/java/.../config/DataSeeder.java` | Test data generator |
| [`src/main/resources/db/changelog/`](../src/main/resources/db/changelog/) | Liquibase migrations — DDL table creation |
| [`src/main/resources/static/db_schema_full.html`](../src/main/resources/static/db_schema_full.html) | Interactive ERD schema |

## REST API Endpoints

The project has **13 controllers** with full CRUD logic.

| Method | Endpoint | Controller |
|---|---|---|
| `GET` | `/api/accounts` | AccountController |
| `GET` | `/api/accounts/{id}` | AccountController |
| `POST` | `/api/accounts` | AccountController |
| `PUT` | `/api/accounts/{id}` | AccountController |
| `DELETE` | `/api/accounts/{id}` | AccountController |
| `GET` | `/api/account-sessions` | AccountSessionController |
| `GET` | `/api/account-sessions/{accountId}/{gaSessionId}` | AccountSessionController |
| `POST` | `/api/account-sessions` | AccountSessionController |
| `DELETE` | `/api/account-sessions/{accountId}/{gaSessionId}` | AccountSessionController |
| `GET` | `/api/account-sessions/account/{accountId}` | AccountSessionController |
| `GET` | `/api/account-sessions/session/{gaSessionId}` | AccountSessionController |
| `GET` | `/api/email-open` | EmailOpenController |
| `GET` | `/api/email-open/{id}` | EmailOpenController |
| `POST` | `/api/email-open` | EmailOpenController |
| `PUT` | `/api/email-open/{id}` | EmailOpenController |
| `DELETE` | `/api/email-open/{id}` | EmailOpenController |
| `GET` | `/api/email-open/account/{idAccount}` | EmailOpenController |
| `GET` | `/api/email-sent` | EmailSentController |
| `GET` | `/api/email-sent/{id}` | EmailSentController |
| `POST` | `/api/email-sent` | EmailSentController |
| `PUT` | `/api/email-sent/{id}` | EmailSentController |
| `DELETE` | `/api/email-sent/{id}` | EmailSentController |
| `GET` | `/api/email-sent/account/{idAccount}` | EmailSentController |
| `GET` | `/api/email-visit` | EmailVisitController |
| `GET` | `/api/email-visit/{id}` | EmailVisitController |
| `POST` | `/api/email-visit` | EmailVisitController |
| `PUT` | `/api/email-visit/{id}` | EmailVisitController |
| `DELETE` | `/api/email-visit/{id}` | EmailVisitController |
| `GET` | `/api/email-visit/account/{idAccount}` | EmailVisitController |
| `GET` | `/api/orders` | OrderController |
| `GET` | `/api/orders/{id}` | OrderController |
| `POST` | `/api/orders` | OrderController |
| `PUT` | `/api/orders/{id}` | OrderController |
| `DELETE` | `/api/orders/{id}` | OrderController |
| `GET` | `/api/orders/session/{gaSessionId}` | OrderController |
| `GET` | `/api/orders/product/{itemId}` | OrderController |
| `GET` | `/api/paid-search-cost` | PaidSearchCostController |
| `GET` | `/api/paid-search-cost/{date}` | PaidSearchCostController |
| `POST` | `/api/paid-search-cost` | PaidSearchCostController |
| `PUT` | `/api/paid-search-cost/{date}` | PaidSearchCostController |
| `DELETE` | `/api/paid-search-cost/{date}` | PaidSearchCostController |
| `GET` | `/api/products` | ProductController |
| `GET` | `/api/products/{itemId}` | ProductController |
| `POST` | `/api/products` | ProductController |
| `PUT` | `/api/products/{itemId}` | ProductController |
| `DELETE` | `/api/products/{itemId}` | ProductController |
| `GET` | `/api/revenue-predict` | RevenuePredictController |
| `GET` | `/api/revenue-predict/{date}` | RevenuePredictController |
| `POST` | `/api/revenue-predict` | RevenuePredictController |
| `PUT` | `/api/revenue-predict/{date}` | RevenuePredictController |
| `DELETE` | `/api/revenue-predict/{date}` | RevenuePredictController |
| `GET` | `/api/sessions` | SessionController |
| `GET` | `/api/sessions/{gaSessionId}` | SessionController |
| `POST` | `/api/sessions` | SessionController |
| `PUT` | `/api/sessions/{gaSessionId}` | SessionController |
| `DELETE` | `/api/sessions/{gaSessionId}` | SessionController |
| `GET` | `/api/session-params` | SessionParamsController |
| `GET` | `/api/session-params/{gaSessionId}` | SessionParamsController |
| `POST` | `/api/session-params` | SessionParamsController |
| `PUT` | `/api/session-params/{gaSessionId}` | SessionParamsController |
| `DELETE` | `/api/session-params/{gaSessionId}` | SessionParamsController |
| `GET` | `/api/ab-tests` | AbTestController |
| `POST` | `/api/ab-tests` | AbTestController |
| `GET` | `/api/ab-tests/session/{gaSessionId}` | AbTestController |
| `GET` | `/api/event-params` | EventParamsController |
| `POST` | `/api/event-params` | EventParamsController |
| `GET` | `/api/event-params/session/{gaSessionId}` | EventParamsController |

## DDL Table Creation (Liquibase Migrations)

Full migration descriptions with a table of all 13 files — in the [Database Schema → DDL Table Creation](database-schema.md#ddl-table-creation-liquibase-migrations) section.

## Technical Project Plan

The full project plan with entity descriptions, tables, DDL, Java Entity code, and execution order — in the [plan.md](plan.md) document.
