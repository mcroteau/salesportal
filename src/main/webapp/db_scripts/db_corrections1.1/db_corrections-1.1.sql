-- If you are upgrading from Sales Portal 1.0 to Sales Portal 1.1,
-- and have a database already set up with data, run these scripts below
-- to update your database to work with Sales Portal 1.1

<-- 

Change - Creation of the Territory Zips table for auto assign change

-->

CREATE TABLE TERRITORY_ZIPS ( 
	TERRITORY_ZIP_ID NUMERIC(10,0) NOT NULL , 
	TERRITORY_ID NUMERIC(10,0) NOT NULL , 
	ZIP VARCHAR(20) NOT NULL ,
	CONSTRAINT TERRITORY_ZIPS_01 PRIMARY KEY( TERRITORY_ZIP_ID ) ) ; 
	
 