-- Sessions by device type
SELECT device, COUNT(*) as count
FROM session_params
GROUP BY device;

-- Sessions by country
SELECT country, COUNT(*) as sessions_count
FROM session_params
GROUP BY country
ORDER BY sessions_count DESC;

-- Sessions by browser
SELECT browser, COUNT(*) as count
FROM session_params
GROUP BY browser;

-- Sessions by channel
SELECT channel, COUNT(*) as count
FROM session_params
GROUP BY channel;