-- If you are upgrading from Sales Portal 1.1 to Sales Portal 1.1.1,
-- and have a database already set up with data, run these scripts below
-- to update your database to work with Sales Portal 1.1.1
-- Add time to prospect table --

ALTER TABLE PROSPECT
	ADD NEXT_ACTION_HOUR NUMERIC(2,0);

ALTER TABLE PROSPECT
	ADD NEXT_ACTION_MINUTE NUMERIC(2,0);
	
ALTER TABLE PROSPECT
	ADD NEXT_ACTION_AM_PM NUMERIC(2,0);