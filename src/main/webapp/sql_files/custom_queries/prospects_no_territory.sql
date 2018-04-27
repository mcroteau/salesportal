Select 
Count (*) as count, 'No Territory' as territory,
Sum (case when p.verified_id = 1 then 1 else 0 end) as Verified
From prospect p  where p.territory_id is null
