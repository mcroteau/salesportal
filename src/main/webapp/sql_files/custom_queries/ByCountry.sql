select count (*) as count, upper(c.country) as country from prospect p inner join country c on p.country_id=c.country_id group by upper(c.country)
union all
select count (*) as count, 'z Total' as country from prospect p inner join country c on p.country_id=c.country_id order by 2;
