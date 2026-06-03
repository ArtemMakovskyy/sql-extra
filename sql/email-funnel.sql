-- Email funnel per account
SELECT
    a.id as account_id,
    COUNT(DISTINCT es.id) as emails_sent,
    COUNT(DISTINCT eo.id) as emails_opened,
    COUNT(DISTINCT ev.id) as emails_visited,
    CASE WHEN COUNT(DISTINCT es.id) > 0
         THEN ROUND(COUNT(DISTINCT eo.id)::numeric / COUNT(DISTINCT es.id)::numeric * 100, 2)
         ELSE 0 END as open_rate,
    CASE WHEN COUNT(DISTINCT es.id) > 0
         THEN ROUND(COUNT(DISTINCT ev.id)::numeric / COUNT(DISTINCT es.id)::numeric * 100, 2)
         ELSE 0 END as click_rate
FROM account a
LEFT JOIN email_sent es ON a.id = es.id_account
LEFT JOIN email_open eo ON a.id = eo.id_account
LEFT JOIN email_visit ev ON a.id = ev.id_account
GROUP BY a.id
ORDER BY a.id;