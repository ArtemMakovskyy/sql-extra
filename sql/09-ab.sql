-- # AB Testing - SQL for analysis

/* date,
country,
device,
continent,
channel,
test,
test_group,
event_name, - session with orders - event name - session - new accounts
value
*/

WITH session_info AS (
    SELECT
        s.date,
        s.ga_session_id,
        sp.country,
        sp.device,
        sp.continent,
        sp.channel,
        ab.test,
        ab.test_group
    FROM ab_test ab
             JOIN sessions s
                  ON ab.ga_session_id = s.ga_session_id
             JOIN session_params sp
                  ON ab.ga_session_id = sp.ga_session_id
),

     session_with_orders AS (
         SELECT
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group,
             COUNT(DISTINCT o.ga_session_id) AS session_with_orders
         FROM "orders" o
                  JOIN session_info si
                       ON o.ga_session_id = si.ga_session_id
         GROUP BY
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group
     ),

     events AS (
         SELECT
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group,
             ep.event_name,
             COUNT(DISTINCT ep.ga_session_id) AS events_cnt
         FROM event_params ep
                  JOIN session_info si
                       ON ep.ga_session_id = si.ga_session_id
         GROUP BY
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group,
             ep.event_name
     ),

     session_cnt AS (
         SELECT
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group,
             COUNT(DISTINCT si.ga_session_id) AS session_cnt
         FROM session_info si
         GROUP BY
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group
     ),

     account AS (
         SELECT
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group,
             COUNT(DISTINCT acs.ga_session_id) AS new_account_cnt
         FROM account_session acs
                  JOIN session_info si
                       ON acs.ga_session_id = si.ga_session_id
         GROUP BY
             si.date,
             si.ga_session_id,
             si.country,
             si.device,
             si.continent,
             si.channel,
             si.test,
             si.test_group
     )

SELECT
    swo.date,
    swo.ga_session_id,
    swo.country,
    swo.device,
    swo.continent,
    swo.channel,
    swo.test,
    swo.test_group,
    'session with orders' AS event_name,
    swo.session_with_orders AS value
FROM session_with_orders swo

UNION ALL

SELECT
    e.date,
    e.ga_session_id,
    e.country,
    e.device,
    e.continent,
    e.channel,
    e.test,
    e.test_group,
    e.event_name,
    e.events_cnt AS value
FROM events e

UNION ALL

SELECT
    s.date,
    s.ga_session_id,
    s.country,
    s.device,
    s.continent,
    s.channel,
    s.test,
    s.test_group,
    'session' AS event_name,
    s.session_cnt AS value
FROM session_cnt s

UNION ALL

SELECT
    a.date,
    a.ga_session_id,
    a.country,
    a.device,
    a.continent,
    a.channel,
    a.test,
    a.test_group,
    'new account' AS event_name,
    a.new_account_cnt AS value
FROM account a;