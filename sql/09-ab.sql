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
-- # AB Testing - SQL for analysis PostgreSQL

-- # AB Testing - SQL for analysis PostgreSQL

WITH session_info AS (SELECT s.date,
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
                                    ON sp.ga_session_id = ab.ga_session_id),

     session_with_orders AS (SELECT session_info.date,
                                    session_info.country,
                                    session_info.device,
                                    session_info.continent,
                                    session_info.channel,
                                    session_info.test,
                                    session_info.test_group,
                                    COUNT(DISTINCT o.ga_session_id) AS session_with_orders
                             FROM "orders" o
                                      JOIN session_info
                                           ON o.ga_session_id = session_info.ga_session_id
                             GROUP BY session_info.date,
                                      session_info.country,
                                      session_info.device,
                                      session_info.continent,
                                      session_info.channel,
                                      session_info.test,
                                      session_info.test_group),

     events AS (SELECT session_info.date,
                       session_info.country,
                       session_info.device,
                       session_info.continent,
                       session_info.channel,
                       session_info.test,
                       session_info.test_group,
                       sp.event_name,
                       COUNT(sp.ga_session_id) AS event_cnt
                FROM event_params sp
                         JOIN session_info
                              ON sp.ga_session_id = session_info.ga_session_id
                GROUP BY session_info.date,
                         session_info.country,
                         session_info.device,
                         session_info.continent,
                         session_info.channel,
                         session_info.test,
                         session_info.test_group,
                         sp.event_name),

     session_cnts AS (SELECT session_info.date,
                             session_info.country,
                             session_info.device,
                             session_info.continent,
                             session_info.channel,
                             session_info.test,
                             session_info.test_group,
                             COUNT(DISTINCT session_info.ga_session_id) AS session_cnt
                      FROM session_info
                      GROUP BY session_info.date,
                               session_info.country,
                               session_info.device,
                               session_info.continent,
                               session_info.channel,
                               session_info.test,
                               session_info.test_group),

     account AS (SELECT session_info.date,
                        session_info.country,
                        session_info.device,
                        session_info.continent,
                        session_info.channel,
                        session_info.test,
                        session_info.test_group,
                        COUNT(DISTINCT acs.ga_session_id) AS new_account_cnt
                 FROM account_session acs
                          JOIN session_info
                               ON acs.ga_session_id = session_info.ga_session_id
                 GROUP BY session_info.date,
                          session_info.country,
                          session_info.device,
                          session_info.continent,
                          session_info.channel,
                          session_info.test,
                          session_info.test_group)

SELECT date,
       country,
       device,
       continent,
       channel,
       test,
       test_group,
       'session with orders' AS event_name,
       session_with_orders   AS value
FROM session_with_orders

UNION ALL

SELECT date,
       country,
       device,
       continent,
       channel,
       test,
       test_group,
       event_name,
       event_cnt AS value
FROM events

UNION ALL

SELECT date,
       country,
       device,
       continent,
       channel,
       test,
       test_group,
       'session'   AS event_name,
       session_cnt AS value
FROM session_cnts

UNION ALL

SELECT date,
       country,
       device,
       continent,
       channel,
       test,
       test_group,
       'new account'   AS event_name,
       new_account_cnt AS value
FROM account;