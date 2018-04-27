-- change note size to 500 from 255--

alter table note add column note_temp varchar(500);
update note set note_temp = note;
alter table note	drop column note;
alter table note add column note varchar(2000);
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



