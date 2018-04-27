-- new label field  April/2011--
ALTER TABLE label ADD COLUMN label_short character varying(15);


-- add fields to prospect table updated to inital scripts----
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (42, 'Historical Reports - Revenue/Commission', 3, 0, '', 1);		
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (43, 'Historical Reports - Performance Data', 3, 0, '', 1);			

alter table webuser add column limit_company numeric(1) default 0;

alter table prospect add column open_orders_number numeric(7);
alter table prospect add column open_orders_value numeric(11,2);
alter table prospect add column open_quotes_number numeric(7);
alter table prospect add column open_quotes_value numeric(11,2);
alter table prospect add column open_accounts_receivable numeric(11,2);

alter table prospect add column orders_invoices_number_ltd numeric(7);
alter table prospect add column orders_invoices_value_ltd numeric(11,2);
alter table prospect add column orders_invoices_number_ytd numeric(7);
alter table prospect add column orders_invoices_value_ytd numeric(11,2);
alter table prospect add column forecast numeric(11,2);

alter table prospect add column orderportal_user_id numeric(11);
alter table prospect add column ebs_user_id numeric(11);


-- add fields to commision table 8/12/10 updated to initial scripts---

alter table commision add column expected_close_date timestamp;
alter table commision add column cancel_date timestamp;
alter table commision add column original_expected_close_date timestamp;
alter table commision add column revenue numeric(11,4);
alter table commision add column commission_type_id numeric(9);

-- set a default currency code updated to initial scripts--
insert into currency (currency_id, currency_code, currency_description) values(1,'$', 'US Dollar');

-- 9/1/10  dmp change updated to initial scriptions--
alter table edi_group add column UPLOAD_TYPE varchar(50);

