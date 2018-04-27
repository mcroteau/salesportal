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
