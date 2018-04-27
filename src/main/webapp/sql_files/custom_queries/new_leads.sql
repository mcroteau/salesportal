select upper(t.territory) as territory, p.customer_company, date(p.creation_date) as creation_date,
p.creation_date::date - current_date::date as days_since_add from prospect p inner join territory t on p.territory_id=t.territory_id where p.creation_date is not null and current_date::date-p.creation_date::date<= 7
order by t.territory, p.customer_company