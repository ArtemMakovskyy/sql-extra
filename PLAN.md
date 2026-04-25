# План: PostgreSQL схема, сумісна з BigQuery `data-analytics-mate.DA`

## Порядок роботи

Кожна сутність: **Entity → Migration → Repository → COMMIT** → наступна

---

## Черга 1 (без залежностей)

### 1) product ✅
- Entity: product → Migration: product → Repository: ProductRepository → COMMIT ✅

### 2) account ✅
- Entity: account → Migration: account → Repository: AccountRepository → COMMIT ✅

### 3) "session" ❌
- Entity: "session" → Migration: "session" → Repository: SessionRepository → COMMIT

---

## Черга 2 (залежить від черги 1)

### 4) session_params ❌
- Entity: session_params → Migration: session_params → Repository: SessionParamsRepository → COMMIT

### 5) account_session ❌
- Entity: account_session → Migration: account_session → Repository: AccountSessionRepository → COMMIT

### 6) ab_test ❌
- Entity: ab_test → Migration: ab_test → Repository: AbTestRepository → COMMIT

### 7) event_params ❌
- Entity: event_params → Migration: event_params → Repository: EventParamsRepository → COMMIT

### 8) "order" ❌
- Entity: "order" → Migration: "order" → Repository: OrderRepository → COMMIT

### 9) email_sent ❌
- Entity: email_sent → Migration: email_sent → Repository: EmailSentRepository → COMMIT

### 10) email_open ❌
- Entity: email_open → Migration: email_open → Repository: EmailOpenRepository → COMMIT

### 11) email_visit ❌
- Entity: email_visit → Migration: email_visit → Repository: EmailVisitRepository → COMMIT

### 12) paid_search_cost ❌
- Entity: paid_search_cost → Migration: paid_search_cost → Repository: PaidSearchCostRepository → COMMIT

### 13) revenue_predict ❌
- Entity: revenue_predict → Migration: revenue_predict → Repository: RevenuePredictRepository → COMMIT

---

## Важливо

- Таблиці `"session"` і `"order"` — в лапках (ключові слова SQL)
- Колонки НЕ перейменовувати: `id_account`, `ga_session_id`, `item_id`
- `ga_session_id` — TEXT (відповідає BigQuery STRING)
- Для таблиць без PK в BigQuery: використовувати @EmbeddedId або JdbcTemplate