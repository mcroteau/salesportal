-- If you are upgrading from Sales Portal 1.1.2 to Sales Portal 1.1.3,
-- and have a database already set up with data, run these scripts below
-- to update your database to work with Sales Portal 1.1.3

-- 
-- Add time offset and date format to company
--

	
alter table company add time_offset_id NUMERIC(11,0);
alter table company add date_format_id NUMERIC(11,0);

create table date_format (
	date_format_id NUMERIC(11,0) not null ,
	date_format varchar(15) not null, 
	constraint date_format_01 primary key(date_format_id)
);

create table time_offset (
	time_offset_id NUMERIC(11,0) not null ,
	time_offset NUMERIC(5,0) not null, 
	constraint time_offset_01 primary key(time_offset_id)
);

CREATE TABLE prospect_sales_action (
  prospect_sales_action_id numeric(11,0) NOT NULL,
  prospect_id numeric(10),
  sales_action_id numeric(5),
  action_priority numeric(2),
  action_date timestamp,
  action_notification_flag numeric(1) NOT NULL DEFAULT 0,
  CONSTRAINT prospect_sales_action_01 PRIMARY KEY (prospect_sales_action_id)
);
-- 
-- Add time offset and date format to user
--

	
alter table webuser add time_offset_id NUMERIC(11,0);
alter table webuser add date_format_id NUMERIC(11,0);

--Initial date format / time offset data

insert into date_format(date_format_id, date_format)values(1,'MM/dd/yyyy');
insert into date_format(date_format_id, date_format)values(2,'dd/MM/yyyy');
insert into time_offset(time_offset_id, time_offset)values(1,0);
insert into time_offset(time_offset_id, time_offset)values(2,1);
insert into time_offset(time_offset_id, time_offset)values(3,-1);
insert into time_offset(time_offset_id, time_offset)values(4,2);
insert into time_offset(time_offset_id, time_offset)values(5,-2);
insert into time_offset(time_offset_id, time_offset)values(6,3);
insert into time_offset(time_offset_id, time_offset)values(7,-3);
insert into time_offset(time_offset_id, time_offset)values(8,4);
insert into time_offset(time_offset_id, time_offset)values(9,-4);
insert into time_offset(time_offset_id, time_offset)values(10,5);
insert into time_offset(time_offset_id, time_offset)values(11,-5);
insert into time_offset(time_offset_id, time_offset) values(12,6);
insert into time_offset(time_offset_id, time_offset) values(13,-6);