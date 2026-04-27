-- 1. Email Details (timestamps relative to account creation)
SELECT
    (base.account_created + (es.sent_date || ' days')::interval) AS sent_date,
    (base.account_created + (eo.open_date || ' days')::interval) AS open_date,
    (base.account_created + (ev.visit_date || ' days')::interval) AS visit_date,
    acc.id AS id_account,
    es.id_message
FROM account acc
JOIN (
    SELECT
        acs.account_id,
        MIN(s.date) AS account_created
    FROM account_session acs
    JOIN sessions s ON acs.ga_session_id = s.ga_session_id
    GROUP BY acs.account_id
) base ON acc.id = base.account_id
JOIN email_sent es ON acc.id = es.id_account
LEFT JOIN email_open eo ON es.id_account = eo.id_account AND es.id_message = eo.id_message
LEFT JOIN email_visit ev ON es.id_account = ev.id_account AND es.id_message = ev.id_message;

-- 2. Event Params - Extract Date Parts from Timestamp
SELECT
    event_timestamp,
    EXTRACT(YEAR FROM event_timestamp) AS year,
    EXTRACT(MONTH FROM event_timestamp) AS month,
    EXTRACT(DAY FROM event_timestamp) AS day,
    EXTRACT(HOUR FROM event_timestamp) AS hour,
    EXTRACT(MINUTE FROM event_timestamp) AS minute,
    EXTRACT(SECOND FROM event_timestamp) AS second
FROM event_params;