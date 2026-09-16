with email as (
    select
        accs.ga_session_id,
        es.id,
        es.letter_type,
        ev.id_message,
        es.sent_date,
        eo.open_date,
        ev.visit_date
    from email_sent es
             join email_open eo on es.id_message = eo.id_message
             join email_visit ev on es.id_message = ev.id_message
             join account_session accs on accs.account_id = es.id_account
),
     data as (
         select s.ga_session_id, s.date
         from account_session accs
                  join sessions s on accs.ga_session_id = s.ga_session_id
     )
select em.id,
       em.ga_session_id,
       d.date,
       em.letter_type,
       em.sent_date + d.date  as sent_date,
       em.open_date + d.date as open_date,
       em.visit_date + d.date as visit_date,
       em.id_message

from email em
         join data d on em.ga_session_id = d.ga_session_id;


