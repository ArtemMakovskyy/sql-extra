# Структура проєкту

## Папки проєкту

| Папка | Вміст |
|---|---|
| [`http/`](../http/) | HTTP запити для тестування REST API (JetBrains HTTP Client) |
| [`sql/`](../sql/) | SQL запити для виконання — 15 файлів |
| `src/main/java/.../controller/` | REST контролери (13 файлів) |
| `src/main/java/.../entity/` | JPA сутності (13 таблиць) |
| `src/main/java/.../repository/` | Spring Data JPA репозиторії |
| `src/main/java/.../service/` | Бізнес-логіка |
| `src/main/java/.../mapper/` | MapStruct мапери Entity ↔ DTO |
| `src/main/java/.../config/DataSeeder.java` | Генератор тестових даних |
| [`src/main/resources/db/changelog/`](../src/main/resources/db/changelog/) | Liquibase міграції — DDL створення таблиць |
| [`src/main/resources/static/db_schema_full.html`](../src/main/resources/static/db_schema_full.html) | Інтерактивна ERD схема |

## REST API Endpoints

У проєкті **13 контролерів** з повною CRUD логікою.

| Метод | Ендпоїнт | Контролер |
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

## DDL створення таблиць (Liquibase міграції)

Повний опис міграцій із таблицею всіх 13 файлів — у розділі [Схема бази даних → DDL створення таблиць](database-schema.md#ddl-створення-таблиць-liquibase-міграції).

## Технічний план проєкту

Повний план проєкту з описом сутностей, таблиць, DDL, Java Entity коду та черги виконання — у документі [plan.md](plan.md).
