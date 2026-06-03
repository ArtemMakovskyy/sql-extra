# Data Configuration

On first startup, the application automatically populates the database. The number of records can be adjusted via the [`.env`](../.env) file in the project root.

## `.env` Variables

| Variable | Default | Description |
|---|---|---|
| `SEEDER_PRODUCTS` | 500 | Number of products |
| `SEEDER_SESSIONS` | 2000 | Number of sessions |
| `SEEDER_ACCOUNTS` | 1000 | Number of accounts |

## What Is Created on Startup

| Table | Quantity | Details |
|---|---|---|
| **products** | `SEEDER_PRODUCTS` | Random names, 10 categories, prices, descriptions with sizes |
| **sessions** | `SEEDER_SESSIONS` | Unique `ga_session_id`, random date within the last 90 days |
| **session_params** | 1 per session | Device, browser, OS, country, traffic channel |
| **accounts** | `SEEDER_ACCOUNTS` | Send interval, ~70% verified, ~30% unsubscribed |
| **account_session** | 1–3 per account | Link: which account was created in which session |
| **orders** | 0–3 per session | Link: which product was purchased in which session |
| **ab_test** | ~30% of sessions | A/B test (1–5), group (A/B) |
| **event_params** | 1–10 per session | Events: page_view, scroll, click, purchase, etc. |
| **email_sent** | 1–5 per account | Sent emails with `sent_date` (days after registration) |
| **email_open** | ~50% of sent | Email opens |
| **email_visit** | ~25% of sent | Email clicks |
| **paid_search_cost** | 90 days | Daily ad spend ($50–$550) |
| **revenue_predict** | 90 days | Daily revenue forecast ($200–$1200) |
