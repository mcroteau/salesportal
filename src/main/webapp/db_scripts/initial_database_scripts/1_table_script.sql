CREATE TABLE COMPANY ( 
	COMPANY_ID NUMERIC(5, 0) NOT NULL , 
	COMPANY VARCHAR(80) NOT NULL ,  
	time_offset_id NUMERIC(11,0),
	date_format_id NUMERIC(11,0),
	CONSTRAINT COMPANY_01 PRIMARY KEY( COMPANY_ID )) ; 

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
  action_note varchar(200),
  prospect_campaign_id numeric(11,0),
  prospect_campaign_sequence numeric(5,0),
  action_status numeric(1,0) default 0 not null,
  action_notification_flag numeric(1) NOT NULL DEFAULT 0,
  CHANGE_DATE TIMESTAMP,
  CREATION_DATE TIMESTAMP,
  CHANGED_BY_USER_ID NUMERIC(9,0),
  email_draft_to_use numeric (9,0),
  CONSTRAINT prospect_sales_action_01 PRIMARY KEY (prospect_sales_action_id)
);

CREATE TABLE PROSPECT_CAMPAIGN  (
	PROSPECT_CAMPAIGN_ID NUMERIC(11,0) NOT NULL, 
	CAMPAIGN_ID NUMERIC(9,0) NOT NULL, 
	PROSPECT_ID NUMERIC(11,0), 
	CREATION_DATE TIMESTAMP DEFAULT NULL, 
	DESCRIPTION CHARACTER VARYING(200),
	CONSTRAINT PROSPECT_CAMPAIGN_01 PRIMARY KEY(PROSPECT_CAMPAIGN_ID)
); 

CREATE TABLE CURRENCY  (
	CURRENCY_ID NUMERIC(9,0) NOT NULL, 
	CURRENCY_CODE VARCHAR(6) NOT NULL, 
	CURRENCY_DESCRIPTION VARCHAR(50) NOT NULL, 
	CREATION_DATE TIMESTAMP DEFAULT NULL, 
	CREATED_BY_USER VARCHAR(30) DEFAULT NULL, 
	UPDATE_DATE TIMESTAMP DEFAULT NULL,
	UPDATED_BY_USER VARCHAR(30) DEFAULT NULL,
	CONSTRAINT CURRY_01 PRIMARY KEY(CURRENCY_ID)
); 	
	
CREATE TABLE SYSTEM_TYPE ( 
	SYSTEM_TYPE_ID NUMERIC(5,0) NOT NULL , 
	SYSTEM_TYPE VARCHAR(80) NOT NULL , 
	CONSTRAINT SYS_TYPE_01 PRIMARY KEY( SYSTEM_TYPE_ID ) ) ; 

CREATE TABLE TERRITORY ( 
	TERRITORY_ID NUMERIC(5,0) NOT NULL , 
	TERRITORY VARCHAR(80) NOT NULL , 
	
	OWNER_USER_ID NUMERIC(10, 0) DEFAULT NULL,
	SALES_MANAGER_USER_ID NUMERIC(10, 0) DEFAULT NULL,
	SERVICE_MANAGER_USER_ID NUMERIC(10, 0) DEFAULT NULL,
	RETURN_TERRITORY_ID NUMERIC(9,0) DEFAULT NULL,
	RETURN_TIME NUMERIC(9,0) DEFAULT NULL,
	RANDOM_SEQUENCE NUMERIC(9,0) DEFAULT NULL,
	MAX_PROSPECT_DISPLAY NUMERIC(9,0) DEFAULT NULL,
	INCLUDE_IN_CONVERSION_REPORT NUMERIC(9,0) DEFAULT 1,
	include_in_round_robin numeric(9,0) DEFAULT 1,
	round_robin_selected numeric(9,0) DEFAULT 0,
	CONSTRAINT TERRITORY_01 PRIMARY KEY( TERRITORY_ID ) ) ; 

CREATE TABLE LOB ( 
	LOB_ID NUMERIC(5,0) NOT NULL , 
	LOB VARCHAR(80) NOT NULL , 
	CONSTRAINT LOB_01 PRIMARY KEY( LOB_ID ) ) ; 

CREATE TABLE SOFTWARE_TYPE ( 
	SOFTWARE_TYPE_ID NUMERIC(5,0) NOT NULL , 
	SOFTWARE_TYPE VARCHAR(80) NOT NULL , 
	CONSTRAINT SOFTWARE_TYPE_01 PRIMARY KEY( SOFTWARE_TYPE_ID ) ) ; 

CREATE TABLE VERIFIED ( 
	VERIFIED_ID NUMERIC(5,0) NOT NULL , 
	VERIFIED VARCHAR(30) NOT NULL , 
	CONSTRAINT VERIFIED_01 PRIMARY KEY( VERIFIED_ID ) ) ; 

CREATE TABLE COUNTRY ( 
	COUNTRY_ID NUMERIC(6,0) NOT NULL , 
	COUNTRY VARCHAR(30) NOT NULL , 
	COUNTRY_CODE VARCHAR(3) NOT NULL , 
	CONSTRAINT COUNTRY_01 PRIMARY KEY( COUNTRY_ID ) ) ; 

CREATE TABLE SIZE ( 
	SIZE_ID NUMERIC(5,0) NOT NULL , 
	SIZE VARCHAR(50) NOT NULL , 
	CONSTRAINT SIZE_01 PRIMARY KEY( SIZE_ID ) ) ; 

CREATE TABLE STATE ( 
	STATE_ID NUMERIC(6,0) NOT NULL , 	
	STATE VARCHAR(30) NOT NULL , 
	STATE_CODE VARCHAR(2) NOT NULL , 
	COUNTRY_ID NUMERIC(6,0) NOT NULL , 
	CONSTRAINT STATE_01 PRIMARY KEY( STATE_ID ) ) ; 

CREATE TABLE STATUS ( 
	STATUS_ID NUMERIC(5,0) NOT NULL , 
	STATUS VARCHAR(50) NOT NULL , 
	CONSTRAINT STATUS_01 PRIMARY KEY( STATUS_ID ) ) ; 
	
	
CREATE TABLE WEBUSER( 
	USER_ID NUMERIC(10, 0) NOT NULL , 	
	USER_TYPE NUMERIC(1,0) NOT NULL , 

	ACTIVE NUMERIC(1,0) DEFAULT NULL , 
	USER_NAME VARCHAR(30) DEFAULT NULL , 
	PASSWORD VARCHAR(20) DEFAULT NULL , 

	COMPANY_ID NUMERIC(5, 0) NOT NULL , 
	SYSTEM_TYPE_ID NUMERIC(5,0) DEFAULT NULL , 	
	TERRITORY_ID NUMERIC(5,0) DEFAULT NULL , 	
	LOB_ID NUMERIC(5,0) DEFAULT NULL , 
	SOFTWARE_TYPE_ID NUMERIC(5,0) DEFAULT NULL , 
	VERIFIED_ID NUMERIC(5,0) DEFAULT NULL , 
	STATUS_ID NUMERIC(5,0) DEFAULT NULL , 
	SIZE_ID NUMERIC(5,0) DEFAULT NULL , 	
	
	STATE_ID NUMERIC(6,0) DEFAULT NULL,
	COUNTRY_ID NUMERIC(6, 0) DEFAULT NULL,
			
	FIRST_NAME VARCHAR(30) NOT NULL , 
	LAST_NAME VARCHAR(30) NOT NULL , 
	EMAIL VARCHAR(100) DEFAULT NULL , 	
	
	CREATION_DATE TIMESTAMP NOT NULL , 
	CHANGE_DATE TIMESTAMP DEFAULT NULL , 
	
	ALLOW_CAL_OF_ALL_TERRITORIES numeric(1),
	
	time_offset_id NUMERIC(11,0),
	date_format_id NUMERIC(11,0),
	
	SPECIFIC_TERRITORIES NUMERIC(9,0) default 0,
	SPECIFIC_STATUSES NUMERIC(9,0)  default 0,
	SPECIFIC_LOBS NUMERIC(9,0)  default 0,
		
	limit_view numeric(9,0),
	limit_company numeric(1) default 0,
	
	CONSTRAINT USER_01 PRIMARY KEY( USER_ID ) ) ; 
	
 CREATE TABLE USER_LOBS  (
	USER_LOB_ID NUMERIC(9,0) NOT NULL,
	LOB_ID NUMERIC(9,0) NOT NULL, 
	USER_ID NUMERIC(9,0) NOT NULL,  
	CONSTRAINT USER_LOB_01 PRIMARY KEY(USER_LOB_ID)); 	
  
 CREATE TABLE PROSPECT ( 
	PROSPECT_ID NUMERIC(10, 0) NOT NULL , 	
	COMPANY_ID NUMERIC(5, 0) NOT NULL , 
	SYSTEM_TYPE_ID NUMERIC(5,0) DEFAULT NULL , 	
	TERRITORY_ID NUMERIC(5,0) DEFAULT NULL , 	
	LOB_ID NUMERIC(5,0) DEFAULT NULL , 
	SOFTWARE_TYPE_ID NUMERIC(5,0) DEFAULT NULL , 
	VERIFIED_ID NUMERIC(5,0) DEFAULT NULL , 
	SIZE_ID NUMERIC(5,0) DEFAULT NULL , 			
	STATUS_ID NUMERIC(5,0) DEFAULT NULL , 			
	CUSTOMER_COMPANY VARCHAR(100) DEFAULT NULL , 		
	ADDRESS VARCHAR(50) DEFAULT NULL , 
	ADDRESS2 VARCHAR(50) DEFAULT NULL , 
	CITY VARCHAR(30) DEFAULT NULL , 
	COUNTY VARCHAR(30) DEFAULT NULL , 	
	ZIP VARCHAR(15) DEFAULT NULL , 
	STATE_ID NUMERIC(6,0) DEFAULT NULL,
	COUNTRY_ID NUMERIC(6, 0) DEFAULT NULL,
	CONTACT_NAME VARCHAR(30) DEFAULT NULL , 
	CONTACT_TITLE VARCHAR(100) DEFAULT NULL , 			
	PHONE VARCHAR(30) DEFAULT NULL , 	
	PHONE_EXT VARCHAR(30) DEFAULT NULL , 
	CELL_PHONE VARCHAR(30) DEFAULT NULL , 
	EMAIL VARCHAR(100) DEFAULT NULL , 	
	WEBSITE VARCHAR(100) DEFAULT NULL , 	
	FAX VARCHAR(30) DEFAULT NULL , 			
	
	SIZE VARCHAR(100) DEFAULT NULL , 			
	SYSTEM_NO VARCHAR(100) DEFAULT NULL , 			
	SSA VARCHAR(100) DEFAULT NULL , 			
	SIC VARCHAR(100) DEFAULT NULL , 			
	HARDMNT VARCHAR(100) DEFAULT NULL , 			
	
	CREATION_DATE TIMESTAMP NOT NULL , 
	CHANGE_DATE TIMESTAMP DEFAULT NULL , 
	CREATION_USER_ID NUMERIC(10, 0) DEFAULT NULL , 	
	CHANGE_USER_ID NUMERIC(10, 0) DEFAULT NULL , 			
	
	NEXT_ACTION_ID NUMERIC(5, 0) DEFAULT NULL,
	NEXT_ACTION_DATE TIMESTAMP DEFAULT NULL,
	NEXT_SALES_ACTION_FLAG NUMERIC(1, 0) NOT NULL DEFAULT 0,
	
	NEXT_ACTION_HOUR NUMERIC(2,0),
	NEXT_ACTION_MINUTE NUMERIC(2,0),
	NEXT_ACTION_AM_PM NUMERIC(2,0),
	
	EXTERNAL_ID VARCHAR(25),
	TERRITORY_CHANGED_DATE TIMESTAMP DEFAULT NULL,
	
	TERRITORY_ASSIGNED_ON_CREATION NUMERIC(9,0) DEFAULT NULL,
	
	ACTIVITY_DATE TIMESTAMP DEFAULT NULL,
	optout_date timestamp DEFAULT NULL,
	
	open_orders_number numeric(7),
	open_orders_value numeric(11,2),
	open_quotes_number numeric(7),
	open_quotes_value numeric(11,2),
	open_accounts_receivable numeric(11,2),

	orders_invoices_number_ltd numeric(7),
	orders_invoices_value_ltd numeric(11,2),
	orders_invoices_number_ytd numeric(7),
	orders_invoices_value_ytd numeric(11,2),
	forecast numeric(11,2),

	orderportal_user_id numeric(11),
	ebs_user_id numeric(11),
	
	CONSTRAINT PROSP_01 PRIMARY KEY( PROSPECT_ID ) ) ; 
	
	
CREATE TABLE NOTE ( 
	NOTE_ID NUMERIC(10,0) NOT NULL , 
	PROSPECT_ID NUMERIC(10,0) NOT NULL , 
	USER_NAME VARCHAR(100) NOT NULL , 
	NOTE_DATE TIMESTAMP NOT NULL , 			
	NOTE VARCHAR(2000) NOT NULL,
	CONSTRAINT NOTE_01 PRIMARY KEY( NOTE_ID ) ) ; 

CREATE TABLE COMMISION ( 
	COMMISION_ID NUMERIC(5, 0) NOT NULL , 
	USER_ID NUMERIC(5, 0) NOT NULL , 
	DESCRIPTION VARCHAR(250) DEFAULT NULL , 
	AMOUNT NUMERIC(11,4) NOT NULL,
	DATE_SOLD DATE DEFAULT NULL,
	DATE_BILLED DATE DEFAULT NULL,
	DATE_PAID DATE DEFAULT NULL,
	CHECK_NUMBER VARCHAR(50) DEFAULT NULL,
	CHECK_AMOUNT NUMERIC(11,4) DEFAULT NULL,
	CREATION_DATE TIMESTAMP NOT NULL,
	COMMISSION_PROSPECT_ID NUMERIC (10,0),
	COMMISSION_CURRENCY_ID NUMERIC (9,0),
	PO_NUMBER VARCHAR(50),
	INTERNAL_DESCRIPTION VARCHAR(255) DEFAULT NULL,
	date1 timestamp,
	date2  timestamp,
	date3 timestamp,
	date4 timestamp,
	date5 timestamp,
	yes_no1 varchar(25),
	drop_down1_name varchar(100),
	numeric1 numeric(11,2),
	oep_transactionid numeric(12),
	total_units numeric(10),
	total_units_closed numeric(10),
	expected_close_date timestamp,
	cancel_date timestamp,
	original_expected_close_date timestamp,
	revenue numeric(11,4),
	commission_type_id numeric(9),
	CONSTRAINT COMM_01 PRIMARY KEY( COMMISION_ID ) 
);

CREATE TABLE CAMPAIGN  (
	CAMPAIGN_ID NUMERIC(9,0) NOT NULL, 
	CAMPAIGN VARCHAR(80) NOT NULL, 
	CREATION_DATE TIMESTAMP DEFAULT NULL, 
	CREATED_BY_USER VARCHAR(30) DEFAULT NULL, 
	UPDATE_DATE TIMESTAMP DEFAULT NULL,
	UPDATED_BY_USER VARCHAR(30) DEFAULT NULL,
	DESCRIPTION CHARACTER VARYING(200) DEFAULT NULL,
	display_for_salesmen numeric (1,0) default 1,
	
	CONSTRAINT CAMPAIGN_01 PRIMARY KEY(CAMPAIGN_ID)
); 	
	
CREATE TABLE CAMPAIGN_SALES_ACTION  (
	CAMPAIGN_SALES_ACTION_ID NUMERIC (9,0) NOT NULL, 
	CAMPAIGN_ID NUMERIC(9,0) NOT NULL, 
	SALES_ACTION_ID NUMERIC (5,0) NOT NULL, 
	DISPLAY_SEQUENCE NUMERIC (5,0) NOT NULL, 
	CREATION_DATE TIMESTAMP DEFAULT NULL, 
	CREATED_BY_USER VARCHAR(30) DEFAULT NULL, 
	UPDATE_DATE TIMESTAMP DEFAULT NULL,
	UPDATED_BY_USER VARCHAR(30) DEFAULT NULL,
	send_email_days numeric (9,0),
	CONSTRAINT CAMPAIGN_SALES_ACTION_01 PRIMARY KEY(CAMPAIGN_SALES_ACTION_ID)
); 	
	
CREATE TABLE CUSTOM_QUERY(
	CUSTOM_QUERY_NO NUMERIC(9, 0)  NOT NULL ,
	CUSTOM_QUERY_NAME VARCHAR(30)  NOT NULL ,
	CUSTOM_QUERY_FILE_NAME VARCHAR(255)  NOT NULL ,
	CUSTOM_QUERY_DESCRIPTION VARCHAR(255)  NOT NULL,
	CONSTRAINT CSTQRY_01 PRIMARY KEY (CUSTOM_QUERY_NO) );

CREATE TABLE CUSTOM_QUERY_USERS(
	CUSTOM_QUERY_NO NUMERIC(9, 0)  NOT NULL ,
	USER_ID NUMERIC(10, 0)  NOT NULL );

CREATE TABLE ACTION ( 
	ACTION_ID NUMERIC(5,0) NOT NULL , 
	ACTION VARCHAR(50) NOT NULL , 
	MANDATORY_DATE NUMERIC(1,0) NOT NULL , 	
	COLOR VARCHAR(50) NOT NULL,
	email_draft_to_use numeric (9,0),
	display_for_salesmen numeric (1,0) default 1,
	CONSTRAINT ACTION_01 PRIMARY KEY( ACTION_ID ) ) ; 

 CREATE TABLE WEBSITE ( 
 	WEBSITE_ID NUMERIC(10, 0) NOT NULL , 	
	PROSPECT_ID NUMERIC(10, 0) NOT NULL , 	
	URL VARCHAR(250) NOT NULL,
	DESCRIPTION VARCHAR(500) DEFAULT NULL,
	CONSTRAINT WEBSITE_01 PRIMARY KEY( WEBSITE_ID ) ) ;

CREATE TABLE DOCUMENT (
 	DOCUMENT_ID NUMERIC(10, 0) NOT NULL ,
 	DOCUMENT_TYPE NUMERIC(1, 0) NOT NULL ,
	FILE_NAME VARCHAR(150) NOT NULL,
	FILE_TYPE VARCHAR(4) DEFAULT NULL,
	DESCRIPTION VARCHAR(500) DEFAULT NULL,
	EXTERNAL_ID VARCHAR(25) DEFAULT NULL,
	CREATION_DATE TIMESTAMP DEFAULT NULL,
	creation_user_id NUMERIC(9,0) DEFAULT NULL,
	CONSTRAINT DOCUMENT_01 PRIMARY KEY( DOCUMENT_ID ) ) ;

CREATE TABLE PROSPECT_DOCUMENT (
    PROSPECT_DOCUMENT_ID NUMERIC(10, 0) NOT NULL ,
 	DOCUMENT_ID NUMERIC(10, 0) NOT NULL ,
 	PROSPECT_ID NUMERIC(10, 0) NOT NULL ,
	CONSTRAINT PROSP_DOC_01 PRIMARY KEY( PROSPECT_DOCUMENT_ID ) ) ;	
	
CREATE TABLE CONTACT ( 
	CONTACT_ID NUMERIC(10, 0) NOT NULL , 
	PROSPECT_ID NUMERIC(10, 0) NOT NULL , 
	CONTACT_NAME VARCHAR(50) DEFAULT NULL , 
	CONTACT_TITLE VARCHAR(50) DEFAULT NULL , 
	PHONE VARCHAR(30) DEFAULT NULL , 
	PHONE_EXT VARCHAR(5) DEFAULT NULL , 
	CELL_PHONE VARCHAR(30) DEFAULT NULL , 
	FAX VARCHAR(30) DEFAULT NULL , 
	EMAIL VARCHAR(100) DEFAULT NULL,
	CONSTRAINT CONTACT_01 PRIMARY KEY( CONTACT_ID )  ) ; 	
	
CREATE TABLE LABEL ( 
	LABEL_ID NUMERIC(10,0) NOT NULL , 
	LABEL_DEFAULT VARCHAR(40) NOT NULL , 
	LABEL_ACTUAL VARCHAR(40) NOT NULL ,
	LABEL_SHORT character varying(15),
	CONSTRAINT LABEL_01 PRIMARY KEY( LABEL_ID ) ) ;
	
CREATE TABLE TERRITORY_ZIPS ( 
	TERRITORY_ZIP_ID NUMERIC(10,0) NOT NULL , 
	TERRITORY_ID NUMERIC(10,0) NOT NULL , 
	ZIP VARCHAR(20) NOT NULL ,
	CONSTRAINT TERRITORY_ZIPS_01 PRIMARY KEY( TERRITORY_ZIP_ID ) ) ;  
	
CREATE TABLE TERRITORY_CHANGE (
	TERRITORY_CHANGE_ID NUMERIC(9,0) NOT NULL,
	ORIGINAL_TERRITORY_ID NUMERIC(9,0),
	NEW_TERRITORY_ID NUMERIC(9,0) NOT NULL,
	USER_ID NUMERIC(9,0) NOT NULL,
	CHANGE_DATE TIMESTAMP NOT NULL,
	AUTO_TERRITORY_CHANGE NUMERIC(9,0),
	CONSTRAINT TERR_CHAN_01 PRIMARY KEY(TERRITORY_CHANGE_ID)); 
	
CREATE TABLE USER_STATUSES  (
	USER_STATUS_ID NUMERIC(9,0) NOT NULL,
	STATUS_ID NUMERIC(9,0) NOT NULL, 
	USER_ID NUMERIC(9,0) NOT NULL,  
	CONSTRAINT PROS_STATUS_01 PRIMARY KEY(USER_STATUS_ID)); 
	
CREATE TABLE USER_TERRITORIES  (
	USER_TERRITORY_ID NUMERIC(9,0) NOT NULL,
	TERRITORY_ID NUMERIC(9,0) NOT NULL, 
	USER_ID NUMERIC(9,0) NOT NULL,  
	CONSTRAINT PROS_TERR_01 PRIMARY KEY(USER_TERRITORY_ID)); 
	
	
CREATE TABLE WEBUSER_DM ( 
	USER_NO NUMERIC(10, 0) NOT NULL , 
	ACTIVE NUMERIC(1, 0) DEFAULT NULL , 
	USER_TYPE NUMERIC(1, 0) DEFAULT NULL , 
	USER_NAME VARCHAR(30) DEFAULT NULL , 
	PASSWORD VARCHAR(20) DEFAULT NULL , 
	DEFAULT_CONVERSION_CLASS VARCHAR(50) DEFAULT NULL ,
	CONSTRAINT USER_01_DM PRIMARY KEY( USER_NO ) ) ; 
	
CREATE TABLE EDI_GROUP ( 
	EDI_GROUP_ID NUMERIC(11, 0) NOT NULL , 
	UPLOADED_BY VARCHAR(50) DEFAULT NULL , 
	UPLOAD_DATE TIMESTAMP DEFAULT NULL , 
	FILENAME VARCHAR(255) DEFAULT NULL , 
	DOWNLOADED NUMERIC(1,0) DEFAULT NULL ,
	DOWNLOADED_BY VARCHAR(50) DEFAULT NULL , 
	DOWNLOAD_DATE TIMESTAMP DEFAULT NULL ,
	ORDER_LIST VARCHAR(255) DEFAULT NULL ,
	UPLOAD_TYPE varchar(50),
	CONSTRAINT EDI_GRP_01 PRIMARY KEY( EDI_GROUP_ID ) ) ; 	
	
CREATE TABLE EDI_HEADER ( 
	EDI_HEADER_ID NUMERIC(11, 0) NOT NULL , 
	EDI_GROUP_ID NUMERIC(11, 0) NOT NULL , 
	EDI_HEADER_CONTENT VARCHAR(2000) DEFAULT NULL , 
	CONSTRAINT EDI_HEAD_01 PRIMARY KEY( EDI_HEADER_ID ) ) ; 		
	
CREATE TABLE EDI_DETAIL ( 
	EDI_DETAIL_ID NUMERIC(11, 0) NOT NULL , 
	EDI_HEADER_ID NUMERIC(11, 0) NOT NULL , 
	EDI_DETAIL_CONTENT VARCHAR(2000) DEFAULT NULL , 
	CONSTRAINT EDI_DET_01 PRIMARY KEY( EDI_DETAIL_ID ) ) ; 	

CREATE TABLE EDI_INTERCHANGE_CONTROL ( 
	EDI_INTERCHANGE_CONTROL_ID NUMERIC(5, 0) NOT NULL , 
	EDI_INTERCHANGE_CONTROL_NUMBER NUMERIC (9,0) ,
	CONSTRAINT EDI_INTERCHANGE_CONTROL_01 PRIMARY KEY( EDI_INTERCHANGE_CONTROL_ID ) ) ; 
	
CREATE TABLE EDI_GROUP_CONTROL ( 
	EDI_GROUP_CONTROL_ID NUMERIC(5, 0) NOT NULL , 
	EDI_GROUP_CONTROL_NUMBER NUMERIC (9,0) ,
	CONSTRAINT EDI_GROUP_CONTROL_01 PRIMARY KEY( EDI_GROUP_CONTROL_ID ) ) ; 	
			

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
	
CREATE TABLE email_sales_action ( 
	email_action_id NUMERIC(9,0) NOT NULL , 
	email_action_description VARCHAR(100) NOT NULL , 
	email_draft_to_use NUMERIC(9,0) NOT NULL , 
	verified_yes_no NUMERIC(1,0), 
	use_salesman_from_email NUMERIC(1,0) , 
	creation_date TIMESTAMP,
	send_email_date TIMESTAMP,
	actual_email_date TIMESTAMP,
	CONSTRAINT email_sales_action_01 PRIMARY KEY( email_action_id ) ) ; 

CREATE TABLE email_sales_action_sent_prospects ( 
	sent_email_action_id NUMERIC(9,0), 
	email_action_id NUMERIC(9,0), 
	prospect_id numeric(10),
	sent_date TIMESTAMP,
	CONSTRAINT sent_email_action_01 PRIMARY KEY( sent_email_action_id ) ) ; 

CREATE TABLE email_sales_action_statuses  (
	email_status_id NUMERIC(9,0) NOT NULL,
	email_action_id NUMERIC(9,0) NOT NULL , 
	status_id NUMERIC(9,0) NOT NULL, 
	email_campaign_id numeric (9,0),
	CONSTRAINT email_status_01 PRIMARY KEY(email_status_id)); 

CREATE TABLE email_sales_action_lobs  (
	email_lob_id NUMERIC(9,0) NOT NULL,
	email_action_id NUMERIC(9,0) NOT NULL , 
	lob_id NUMERIC(9,0) NOT NULL, 
	email_campaign_id numeric (9,0),
	CONSTRAINT email_lob_01 PRIMARY KEY(email_lob_id)); 
	
CREATE TABLE email_sales_action_territories  (
	email_territory_id NUMERIC(9,0) NOT NULL,
	email_action_id NUMERIC(9,0) NOT NULL , 
	territory_id NUMERIC(9,0) NOT NULL, 
	email_campaign_id numeric (9,0),
	CONSTRAINT email_territory_01 PRIMARY KEY(email_territory_id)); 


CREATE TABLE EMAIL_QUEUE_AUDIT  (

	EMAIL_QUEUE_AUDIT_ID NUMERIC(9,0) NOT NULL,
	PROSPECT_ID NUMERIC(9,0) NOT NULL, 
	WEBPROFILE_ID NUMERIC(9,0) NOT NULL,  
	EMAIL_DRAFT_ID NUMERIC(9,0) NOT NULL,
	EXPORT_DATE TIMESTAMP,
	RETURN_DATE TIMESTAMP, 
	USER_ID NUMERIC(9,0),
	STATUS NUMERIC(9,0),
	FILE_NAME VARCHAR(50),

	CONSTRAINT EMAIL_QUEUE_AUDIT_01 PRIMARY KEY(EMAIL_QUEUE_AUDIT_ID)); 	

CREATE TABLE email_campaign ( 
	email_campaign_id NUMERIC(9,0) NOT NULL , 
	campaign_id NUMERIC(9,0) NOT NULL , 
	email_campaign_description VARCHAR(100) NOT NULL , 
	verified_yes_no NUMERIC(1,0), 
	use_salesman_from_email NUMERIC(1,0) , 
	creation_date TIMESTAMP,
	start_email_date TIMESTAMP,
	end_email_date TIMESTAMP,
	use_campaign_start_date numeric(1,0) default 1,
	CONSTRAINT email_campaign_01 PRIMARY KEY( email_campaign_id ) ) ; 


CREATE TABLE audit_round_robin ( 
	audit_rr_id NUMERIC(9,0) NOT NULL , 
	prosect_id NUMERIC(10,0) , 
	territory_id NUMERIC(5,0) , 
	creation_date TIMESTAMP,
	user_id_making_change numeric (10,0),
	external_id varchar(25),
	CONSTRAINT audit_round_robin_01 PRIMARY KEY( audit_rr_id ) ) ; 

CREATE TABLE EMAIL_TEMPLATE ( 
	EMAIL_TEMPLATE_ID NUMERIC(10,0) NOT NULL , 
	USER_NAME VARCHAR(100) NOT NULL , 
	EMAIL_TEMPLATE_DATE TIMESTAMP NOT NULL , 
	DESCRIPTION VARCHAR(200) NOT NULL,			
	TEXT VARCHAR(2000) NOT NULL,
	CONSTRAINT EMAIL_TEMPLATE_01 PRIMARY KEY( EMAIL_TEMPLATE_ID ) ) ; 

CREATE TABLE prospect_import_log(
  prospect_import_log_id serial,
  action_taken varchar(255),
  external_id varchar(255),
  email varchar(255),
  phone varchar(255),
  prospect_import_timestamp timestamp,
  CONSTRAINT prospect_import_log_01 PRIMARY KEY (prospect_import_log_id)
);
	
		
		
-- TABLE CREATION
-- these scripts should be run against your salesportal database.
-- the Infoportal tables will be added into the same database

--
-- Name: user_company_relation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_company_relation (
    company_rel_pk numeric(10,0) NOT NULL,
    customer_no numeric(10,0),
    company_fk numeric(10,0),
    relationship character(1)
);


ALTER TABLE public.user_company_relation OWNER TO postgres;

--
-- Name: user_customer_relation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_customer_relation (
    customer_rel_pk numeric(10,0) NOT NULL,
    customer_no numeric(10,0),
    customer_fk character varying(10),
    company_fk numeric(10,0),
    relationship character(1)
);


ALTER TABLE public.user_customer_relation OWNER TO postgres;

--
-- Name: user_installer_relation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_installer_relation (
    installer_rel_pk numeric(10,0) NOT NULL,
    customer_no numeric(10,0),
    installer_fk numeric(10,0),
    relationship character(1)
);


ALTER TABLE public.user_installer_relation OWNER TO postgres;

--
-- Name: user_territory_relation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_territory_relation (
    territory_rel_pk numeric(10,0) NOT NULL,
    customer_no numeric(10,0),
    territory_fk numeric(10,0),
    relationship character(1)
);


ALTER TABLE public.user_territory_relation OWNER TO postgres;

--
-- Name: user_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_type (
    user_type_pk numeric(10,0) NOT NULL,
    description character varying(500),
    short_desc character varying(30)
);


ALTER TABLE public.user_type OWNER TO postgres;

SET default_with_oids = false;




--
-- Name: webuser_ip; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE webuser_ip (
    customer_no numeric(10,0) NOT NULL,
    user_type numeric(3,0),
    user_name character varying(30),
    "password" character varying(20),
    active numeric(1,0),
    first_name character varying(30),
    last_name character varying(30),
    address character varying(50),
    city character varying(30),
    zip character varying(15),
    state_id numeric(6,0),
    country_id numeric(6,0),
    phone character varying(15),
    company character varying(30),
    email character varying(80),
    exported character(1),
    no_of_stores numeric(6,0),
    resale_no character varying(30),
    ref_cust_no numeric(10,0),
    fax character varying(15),
    system_type_id numeric(5,0),
    territory_id numeric(5,0),
    lob_id numeric(5,0),
    software_type_id numeric(5,0),
    verified_id numeric(5,0),
    status_id numeric(5,0),
    creation_date timestamp without time zone,
    last_logged timestamp without time zone,
    logon_counter numeric(9,0),
    sec_profile_fk numeric(10,0)
);


ALTER TABLE public.webuser_ip OWNER TO postgres;

--
-- Name: COLUMN webuser_ip.sec_profile_fk; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN webuser_ip.sec_profile_fk IS 'Key to SECURITY_PROFILE Table';




--
-- Name: security_actions; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE security_actions (
    sec_act_pk numeric(10,0) NOT NULL,
    action_desc character varying(50),
    action_category numeric(5,0),
    entry_point numeric(1,0),
    comments character varying(500),
    app_group_fk numeric(10,0)
);


ALTER TABLE public.security_actions OWNER TO postgres;

--
-- Name: TABLE security_actions; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE security_actions IS 'Contains which actions are allowed by the different user types.';


--
-- Name: security_auth; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE security_auth (
    auth_pk numeric(10,0) NOT NULL,
    sec_profile_fk numeric(10,0),
    sec_act_fk numeric(10,0),
    sec_code character varying(1)
);


ALTER TABLE public.security_auth OWNER TO postgres;

--
-- Name: COLUMN security_auth.sec_code; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN security_auth.sec_code IS 'I - include, E - exclude, A - all (using I and A in current plan)';


--
-- Name: security_profile; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE security_profile (
    sec_profile_pk numeric(10,0) NOT NULL,
    sec_desc character varying(500),
    sec_short_desc character varying(50),
    app_group_fk numeric(10,0)
);


ALTER TABLE public.security_profile OWNER TO postgres;

--
-- Name: COLUMN security_profile.sec_profile_pk; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN security_profile.sec_profile_pk IS 'Primary Key';


--
-- Name: COLUMN security_profile.sec_desc; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN security_profile.sec_desc IS 'Long Description of Security Profile';


--
-- Name: COLUMN security_profile.sec_short_desc; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN security_profile.sec_short_desc IS 'Short Description of Security Profile';



--
-- Name: application_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE application_group (
    app_group_pk numeric(10,0) NOT NULL,
    app_desc character varying(50)
);


ALTER TABLE public.application_group OWNER TO postgres;



--
-- Name: c4122906; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_company_relation
    ADD CONSTRAINT c4122906 PRIMARY KEY (company_rel_pk);


--
-- Name: c4122969; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_customer_relation
    ADD CONSTRAINT c4122969 PRIMARY KEY (customer_rel_pk);


--
-- Name: c4123062; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_territory_relation
    ADD CONSTRAINT c4123062 PRIMARY KEY (territory_rel_pk);

ALTER TABLE ONLY user_installer_relation
    ADD CONSTRAINT c41230621 PRIMARY KEY (installer_rel_pk);
--
-- Name: c4123172; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_type
    ADD CONSTRAINT c4123172 PRIMARY KEY (user_type_pk);


--
-- Name: c4123469; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY webuser_ip
    ADD CONSTRAINT c4123469 PRIMARY KEY (customer_no);


--
-- Name: c4134562; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY security_profile
    ADD CONSTRAINT c4134562 PRIMARY KEY (sec_profile_pk);


--
-- Name: c4858047; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY application_group
    ADD CONSTRAINT c4858047 PRIMARY KEY (app_group_pk);


--
-- Name: c5321109; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY security_actions
    ADD CONSTRAINT c5321109 PRIMARY KEY (sec_act_pk);


--
-- Name: c5933189; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY security_auth
    ADD CONSTRAINT c5933189 PRIMARY KEY (auth_pk);


	
						