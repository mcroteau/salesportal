 
-- 10/29/06 
-- Change 1 - A new table is created -Label
-- We have generisized 4 user fields and 2 user drop downs.
-- Use this table to set up the default label that will display on 
-- the search, create, update and display screens.

-- Change 2 - Webuser table user Admin is changed to type 2.  
-- A type 2 user can access admin functions from the menu.


CREATE TABLE LABEL ( 
	LABEL_ID NUMERIC(10,0) NOT NULL , 
	LABEL_DEFAULT VARCHAR(40) NOT NULL , 
	LABEL_ACTUAL VARCHAR(40) NOT NULL ,
	CONSTRAINT LABEL_01 PRIMARY KEY( LABEL_ID ) ) ; 
	
	-- values for label table --

INSERT INTO LABEL (LABEL_ID, LABEL_DEFAULT, LABEL_ACTUAL)
		VALUES (1,'USER_1'   , 'User 1'  );
INSERT INTO LABEL (LABEL_ID, LABEL_DEFAULT, LABEL_ACTUAL)
		VALUES (2,'USER_2'  , 'User 2'  );
INSERT INTO LABEL (LABEL_ID, LABEL_DEFAULT, LABEL_ACTUAL)
		VALUES (3,'USER_3'   , 'User 3' );
INSERT INTO LABEL (LABEL_ID, LABEL_DEFAULT, LABEL_ACTUAL)
		VALUES (4,'USER_4'  , 'User 4' );
INSERT INTO LABEL (LABEL_ID, LABEL_DEFAULT, LABEL_ACTUAL)
		VALUES (5,'USER_DROPDOWN_1'   , 'User Dropdown 1'  );
INSERT INTO LABEL (LABEL_ID, LABEL_DEFAULT, LABEL_ACTUAL)
		VALUES (6,'USER_DROPDOWN_2'  , 'User Dropdown 2'  );
		
-- change Admin user to type 2 --

update webuser set user_type = 2 where user_id = 100;

-- add administration documentation to document folder  --
	-- Note, change the document_id if you have added documents to the table--

INSERT INTO DOCUMENT (DOCUMENT_ID, DOCUMENT_TYPE, FILE_NAME, FILE_TYPE, DESCRIPTION)
		VALUES (3, 1, 'SalesPortal_Admin.doc', 'doc', 'Sales Portal Administration Manual') ; 