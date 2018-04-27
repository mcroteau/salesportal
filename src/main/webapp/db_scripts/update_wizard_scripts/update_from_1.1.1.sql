-- script 1.1.2

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
	
 

-- script 1.1.3

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


-- script 1.1.4

--now time offset can handle up to 12 hours --
insert into time_offset(time_offset_id, time_offset) values(14,7);
insert into time_offset(time_offset_id, time_offset) values(15,-7);
insert into time_offset(time_offset_id, time_offset) values(16,8);
insert into time_offset(time_offset_id, time_offset) values(17,-8);
insert into time_offset(time_offset_id, time_offset) values(18,9);
insert into time_offset(time_offset_id, time_offset) values(19,-9);
insert into time_offset(time_offset_id, time_offset) values(20,10);
insert into time_offset(time_offset_id, time_offset) values(21,-10);
insert into time_offset(time_offset_id, time_offset) values(22,11);
insert into time_offset(time_offset_id, time_offset) values(23,-11);
insert into time_offset(time_offset_id, time_offset) values(24,12);
insert into time_offset(time_offset_id, time_offset) values(25,-13);





-- script 1.2.0

-- change note size to 500 from 255--

alter table note add column note_temp varchar(500);
update note set note_temp = note;
alter table note	drop column note;
alter table note add column note varchar(500);
update note set note = note_temp;
alter table note drop column note_temp;


-- 3 new custom queries --

INSERT INTO CUSTOM_QUERY (CUSTOM_QUERY_NO, CUSTOM_QUERY_NAME, CUSTOM_QUERY_FILE_NAME,CUSTOM_QUERY_DESCRIPTION)
		VALUES(9, 'New Prospects in Last 7 Days', 'new_leads.sql', 'New Prospects within 7 Days');

INSERT INTO CUSTOM_QUERY (CUSTOM_QUERY_NO, CUSTOM_QUERY_NAME, CUSTOM_QUERY_FILE_NAME, CUSTOM_QUERY_DESCRIPTION)
		VALUES (10, 'New Prospects in Last 30 Days', 'new_leads_1.sql', 'New Prospects within 30 Days');

INSERT INTO CUSTOM_QUERY (CUSTOM_QUERY_NO, CUSTOM_QUERY_NAME, CUSTOM_QUERY_FILE_NAME,
		CUSTOM_QUERY_DESCRIPTION)
		VALUES (11, 'Prospects by Country', 'ByCountry.sql', 'Count of prospects by Country');
		
INSERT INTO CUSTOM_QUERY_USERS (CUSTOM_QUERY_NO, USER_ID)
		VALUES (9, 100);

INSERT INTO CUSTOM_QUERY_USERS (CUSTOM_QUERY_NO, USER_ID)
		VALUES (10, 100);

INSERT INTO CUSTOM_QUERY_USERS (CUSTOM_QUERY_NO, USER_ID)
		VALUES (11, 100);

--- add prospectSalesAction notes

alter table prospect_sales_action add column action_note varchar(200);
alter table prospect_sales_action add column action_status numeric(1,0) default 0 not null;

-- create currency table

CREATE TABLE CURRENCY  (CURRENCY_ID NUMERIC(9,0) NOT NULL, CURRENCY_CODE VARCHAR(6) NOT NULL, CURRENCY_DESCRIPTION VARCHAR(50) NOT NULL, CREATION_DATE TIMESTAMP DEFAULT NULL, CREATED_BY_USER VARCHAR(30) DEFAULT NULL, UPDATE_DATE TIMESTAMP DEFAULT NULL ,UPDATED_BY_USER VARCHAR(30) DEFAULT NULL,CONSTRAINT CURRY_01 PRIMARY KEY(CURRENCY_ID)); 	

-- add fields to commission table---
alter table commision add column commission_prospect_id numeric (10,0);
alter table commision add column commission_currency_id numeric (9,0);
alter table commision add column po_number varchar(50);

-- campaign table

CREATE TABLE CAMPAIGN  (CAMPAIGN_ID NUMERIC(9,0) NOT NULL, CAMPAIGN VARCHAR(80) NOT NULL, CREATION_DATE TIMESTAMP DEFAULT NULL, CREATED_BY_USER VARCHAR(30) DEFAULT NULL, UPDATE_DATE TIMESTAMP DEFAULT NULL ,UPDATED_BY_USER VARCHAR(30) DEFAULT NULL,CONSTRAINT CAMPAIGN_01 PRIMARY KEY(CAMPAIGN_ID)); 	

-- campaign sales action table

CREATE TABLE CAMPAIGN_SALES_ACTION  (CAMPAIGN_SALES_ACTION_ID NUMERIC (9,0) NOT NULL, CAMPAIGN_ID NUMERIC(9,0) NOT NULL, SALES_ACTION_ID NUMERIC (5,0) NOT NULL, DISPLAY_SEQUENCE NUMERIC (5,0) NOT NULL, CREATION_DATE TIMESTAMP DEFAULT NULL, CREATED_BY_USER VARCHAR(30) DEFAULT NULL, UPDATE_DATE TIMESTAMP DEFAULT NULL ,UPDATED_BY_USER VARCHAR(30) DEFAULT NULL,CONSTRAINT CAMPAIGN_SALES_ACTION_01 PRIMARY KEY(CAMPAIGN_SALES_ACTION_ID)); 	

-- add necessary fields to prospect sales actions involved in campaigns

alter table prospect_sales_action add column prospect_campaign_id numeric(11,0);
alter table prospect_sales_action add column prospect_campaign_sequence numeric(5,0);

-- create prospect_campaign table

CREATE TABLE PROSPECT_CAMPAIGN  (PROSPECT_CAMPAIGN_ID NUMERIC(11,0) NOT NULL, CAMPAIGN_ID NUMERIC(9,0) NOT NULL, PROSPECT_ID NUMERIC(11,0), CREATION_DATE TIMESTAMP DEFAULT NULL, CONSTRAINT PROSPECT_CAMPAIGN_01 PRIMARY KEY(PROSPECT_CAMPAIGN_ID)); 



-- script 1.2.3

ALTER TABLE ACTION
	ADD COLOR VARCHAR(50);



--script 1.2.5

-- USED FOR PROSPECT ID FROM EXTERNAL SYSTEM --
ALTER TABLE PROSPECT
	ADD EXTERNAL_ID VARCHAR(25);
	
ALTER TABLE DOCUMENT
	ADD EXTERNAL_ID VARCHAR(25);	

alter table prospect add temp_phone varchar(30);
update  prospect set temp_phone = phone_ext;
alter table prospect drop column phone_ext;
alter table prospect add phone_ext varchar(30);
update  prospect set phone_ext = temp_phone;
alter table prospect drop column temp_phone;


--mikes changes
CREATE TABLE USER_TERRITORIES  (
	USER_TERRITORY_ID NUMERIC(9,0) NOT NULL,
	TERRITORY_ID NUMERIC(9,0) NOT NULL, 
	USER_ID NUMERIC(9,0) NOT NULL,  
	CONSTRAINT PROS_TERR_01 PRIMARY KEY(USER_TERRITORY_ID)); 	


CREATE TABLE USER_STATUSES  (
	USER_STATUS_ID NUMERIC(9,0) NOT NULL,
	STATUS_ID NUMERIC(9,0) NOT NULL, 
	USER_ID NUMERIC(9,0) NOT NULL,  
	CONSTRAINT PROS_STATUS_01 PRIMARY KEY(USER_STATUS_ID)); 	


ALTER TABLE TERRITORY ADD COLUMN RETURN_TERRITORY_ID NUMERIC(9,0);
ALTER TABLE TERRITORY ADD COLUMN RETURN_TIME NUMERIC(9,0);

ALTER TABLE PROSPECT ADD COLUMN TERRITORY_CHANGED_DATE TIMESTAMP DEFAULT NULL;

CREATE TABLE TERRITORY_CHANGE (
	TERRITORY_CHANGE_ID NUMERIC(9,0) NOT NULL,
	ORIGINAL_TERRITORY_ID NUMERIC(9,0),
	NEW_TERRITORY_ID NUMERIC(9,0) NOT NULL,
	USER_ID NUMERIC(9,0) NOT NULL,
	CHANGE_DATE TIMESTAMP NOT NULL,
	CONSTRAINT TERR_CHAN_01 PRIMARY KEY(TERRITORY_CHANGE_ID)); 


ALTER TABLE TERRITORY_CHANGE ADD COLUMN AUTO_TERRITORY_CHANGE NUMERIC(9,0);

ALTER TABLE TERRITORY ADD COLUMN RANDOM_SEQUENCE NUMERIC(9,0);
ALTER TABLE TERRITORY ADD COLUMN MAX_PROSPECT_DISPLAY NUMERIC(9,0);

ALTER TABLE WEBUSER ADD COLUMN SPECIFIC_TERRITORIES NUMERIC(9,0);
ALTER TABLE WEBUSER ADD COLUMN SPECIFIC_STATUSES NUMERIC(9,0);

ALTER TABLE TERRITORY ADD COLUMN INCLUDE_IN_CONVERSION_REPORT NUMERIC(9,0);
ALTER TABLE PROSPECT ADD COLUMN ACTIVITY_DATE TIMESTAMP;

ALTER TABLE WEBUSER ADD COLUMN LIMIT_VIEW NUMERIC(9,0);


-- script 1.2.6

ALTER TABLE PROSPECT_SALES_ACTION ADD CHANGE_DATE TIMESTAMP DEFAULT NULL;

ALTER TABLE PROSPECT_SALES_ACTION ADD CREATION_DATE TIMESTAMP DEFAULT NULL;

ALTER TABLE PROSPECT_SALES_ACTION ADD CHANGED_BY_USER_ID NUMERIC(9,0) DEFAULT NULL;