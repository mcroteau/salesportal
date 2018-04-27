select p.customer_company, a.action, date(sa.action_date) as action_date,
sa.action_date::date - current_date::date as days_left,sa.action_note
from prospect p 
left join prospect_sales_action sa on p.prospect_id = sa.prospect_id
inner join action a on sa.sales_action_id=a.action_id
where sa.sales_action_id is not null and sa.action_date is not null and sa.action_date::date - current_date::date>= 0 and sa.action_status = 0
order by action_date