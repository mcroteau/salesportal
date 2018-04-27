select count (*) as count, upper(t.territory) as territory from prospect p inner join territory t on p.territory_id=t.territory_id 
where verified_id=1  
group by upper(t.territory);