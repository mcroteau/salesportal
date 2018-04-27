-- all updated to install scripts april 1, 2010---
---5/20/09---
ALTER TABLE document ADD CREATION_DATE TIMESTAMP DEFAULT NULL;
ALTER TABLE document ADD creation_user_id NUMERIC(9,0) DEFAULT NULL;
update document set creation_date = '2001-01-01 00:00:00' where creation_date is null;
update document set creation_user_id = 100 where creation_user_id is null;




--- 06/30/09 additional info fields fro OEP for commision table --------
alter table commision add column date1 timestamp;
alter table commision add column date2  timestamp;
alter table commision add column date3 timestamp;
alter table commision add column date4 timestamp;
alter table commision add column date5 timestamp;
alter table commision add column yes_no1 varchar(25);
alter table commision add column drop_down1_name varchar(100);
alter table commision add column numeric1 numeric(11,2);
alter table commision add column oep_transactionid numeric(12);
alter table commision add column oep_line numeric(12);
alter table commision add column oep_item_no character varying(150);
alter table commision add column total_units numeric(10);
alter table commision add column total_units_closed numeric(10);
alter table commision add column total_units_in_process numeric(10);
----07/01/09 sp migratino log --
CREATE TABLE migration_log_sp(
  migration_log_id serial,
  migration_id varchar(255),
  application varchar(255),
  migration_type varchar(255),
  migration_sub_type varchar(255),
  migration_result varchar(255),
  migration_tag varchar(255),
  migration_timestamp timestamp,
  CONSTRAINT migration_log_sp_01 PRIMARY KEY (migration_log_id)
);
--- remove migration_log and replace with this ------
CREATE TABLE migration_log_dm(
  migration_log_id numeric(12) NOT NULL,
  migration_id varchar(255),
  application varchar(255),
  migration_type varchar(255),
  migration_sub_type varchar(255),
  migration_result varchar(255),
  migration_tag varchar(255),
  migration_timestamp timestamp,
  CONSTRAINT migration_log_dm_01 PRIMARY KEY (migration_log_id)
);

CREATE TABLE prospect_import_log(
  prospect_import_log_id serial,
  action_taken varchar(255),
  external_id varchar(255),
  email varchar(255),
  phone varchar(255),
  prospect_import_timestamp timestamp,
  CONSTRAINT prospect_import_log_01 PRIMARY KEY (prospect_import_log_id)
);
---06/30/09 Migration Log query to add to custom queries--- 
select * from migration_log_sp order by migration_timestamp desc;
