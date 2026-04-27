-- Get all sessions
SELECT * FROM sessions;

-- Get session by ID
SELECT * FROM sessions WHERE ga_session_id = 'ga_abc123';

-- Sessions by date range
SELECT * FROM sessions WHERE date BETWEEN '2026-01-01' AND '2026-01-31';

-- Session with params (join)
SELECT s.*, sp.*
FROM sessions s
LEFT JOIN session_params sp ON s.ga_session_id = sp.ga_session_id
WHERE s.ga_session_id = 'ga_abc123';

-- Sessions by Browsers
SELECT
    browser,
    COUNT(ga_session_id) as ssession_cnt
FROM session_params
GROUP BY browser
order by ssession_cnt DESC;