Select 
Count (*) as count, upper(t.territory) as territory,
Sum (case when p.verified_id = 1 then 1 else 0 end) as Verified
From prospect p inner join territory t on p.territory_id=t.territory_id
Group by upper(t.territory)
Union all
Select count(*) as count, 'z Total' as territory,
Sum (case when p.verified_id = 1 then 1 else 0 end) as Verified
From prospect p inner join territory t on p.territory_id=t.territory_id
order by 2;
