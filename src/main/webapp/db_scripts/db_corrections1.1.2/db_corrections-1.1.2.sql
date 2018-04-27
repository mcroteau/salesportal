-- If you are upgrading from Sales Portal 1.1.1 to Sales Portal 1.1.2,
-- and have a database already set up with data, run these scripts below
-- to update your database to work with Sales Portal 1.1.2

-- 
-- Add flag to webuser for calendar viewing
--

alter table webuser add column allow_cal_of_all_territories numeric (1);

-- set all users to not view all territories except admin

update webuser set allow_cal_of_all_territories=0;

update webuser set allow_cal_of_all_territories=1 where user_id=100; 
	
 