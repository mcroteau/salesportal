-- updated to install scripts for release --	
	-- 2/10/09  added for final email automation ----
alter table action add column email_draft_to_use numeric (9,0);
alter table prospect_sales_action add column email_draft_to_use numeric (9,0);
alter table campaign_sales_action add column send_email_days numeric (9,0);

--- 2/17/09 automated email campaigns---


CREATE TABLE email_campaign ( 
	email_campaign_id NUMERIC(9,0) NOT NULL , 
	campaign_id NUMERIC(9,0) NOT NULL , 
	email_campaign_description VARCHAR(100) NOT NULL , 
	verified_yes_no NUMERIC(1,0), 
	use_salesman_from_email NUMERIC(1,0) , 
	creation_date TIMESTAMP,
	start_email_date TIMESTAMP,
	end_email_date TIMESTAMP,
	CONSTRAINT email_campaign_01 PRIMARY KEY( email_campaign_id ) ) ; 
	
	
alter table email_sales_action_statuses add column email_campaign_id numeric (9,0);
alter table email_sales_action_lobs add column email_campaign_id numeric (9,0);
alter table email_sales_action_territories add column email_campaign_id numeric (9,0);
alter table campaign add column display_for_salesmen numeric (1,0);
update campaign set display_for_salesmen = 1;
alter table action add column display_for_salesmen numeric (1,0);
update action set display_for_salesmen = 1;
update campaign_sales_action set send_email_days = 0;


-- 2/26/09---
alter table prospect add column optout_date timestamp;

--- 3/23/09---
alter table email_campaign add column use_campaign_start_date numeric(1,0);
update email_campaign set use_campaign_start_date= 1;

CREATE TABLE audit_round_robin ( 
	audit_rr_id NUMERIC(9,0) NOT NULL , 
	prosect_id NUMERIC(10,0) , 
	territory_id NUMERIC(5,0) , 
	creation_date TIMESTAMP,
	user_id_making_change numeric (10,0),
	external_id varchar(25),
	CONSTRAINT audit_round_robin_01 PRIMARY KEY( audit_rr_id ) ) ; 

--- may be missing ----

update webuser set specific_lobs = 0 where specific_lobs is null and user_name <>'admin';
update webuser set specific_territories = 0 where specific_territories is null and user_name <>'admin';
update webuser set specific_statuses = 0 where specific_statuses is null and user_name <>'admin';


--5/9/09 email templates ---

CREATE TABLE EMAIL_TEMPLATE ( 
	EMAIL_TEMPLATE_ID NUMERIC(10,0) NOT NULL , 
	USER_NAME VARCHAR(100) NOT NULL , 
	EMAIL_TEMPLATE_DATE TIMESTAMP NOT NULL , 
	DESCRIPTION VARCHAR(200) NOT NULL,			
	TEXT VARCHAR(2000) NOT NULL,
	CONSTRAINT EMAIL_TEMPLATE_01 PRIMARY KEY( EMAIL_TEMPLATE_ID ) ) ; 

---5/18/09 DMP changes --- 

alter table edi_group add column ORDER_LIST VARCHAR(255);

alter table edi_detail add column temp varchar(2000);
update edi_detail set temp = edi_detail_content;
alter table edi_detail drop column edi_detail_content;
alter table edi_detail add column edi_detail_content varchar(2000);
update edi_detail set edi_detail_content = temp;
alter table edi_detail drop column temp;

alter table edi_header add column temp varchar(2000);
update edi_header set temp = edi_header_content;
alter table edi_header drop column edi_header_content;
alter table edi_header add column edi_header_content varchar(2000);
update edi_header set edi_header_content = temp;
alter table edi_header drop column temp;


alter table territory add column include_in_round_robin numeric(9,0);
alter table territory add column round_robin_selected numeric(9,0);








								