select count (*) as count, upper(s.state) as state from prospect p inner join state s on p.state_id=s.state_id group by upper(s.state)
union all
select count (*) as count, 'z Total' as state from prospect p inner join state s on p.state_id=s.state_id order by 2;
