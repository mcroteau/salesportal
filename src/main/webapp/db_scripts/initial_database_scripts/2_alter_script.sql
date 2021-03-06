ALTER TABLE COMPANY 
	ADD CONSTRAINT COMPANY_02 
	UNIQUE( COMPANY ) ; 

ALTER TABLE SYSTEM_TYPE 
	ADD CONSTRAINT SYSTEM_TYPE_02 
	UNIQUE( SYSTEM_TYPE ) ; 
      
ALTER TABLE TERRITORY 
	ADD CONSTRAINT TERRITORY_02 
	UNIQUE( TERRITORY ) ; 

ALTER TABLE LOB 
	ADD CONSTRAINT LOB_02 
	UNIQUE( LOB ) ; 

ALTER TABLE SOFTWARE_TYPE 
	ADD CONSTRAINT SOFTWARE_TYPE_02 
	UNIQUE( SOFTWARE_TYPE ) ; 

ALTER TABLE VERIFIED 
	ADD CONSTRAINT VERIFIED_02 
	UNIQUE( VERIFIED ) ; 

ALTER TABLE COUNTRY 
	ADD CONSTRAINT COUNTRY_02 
	UNIQUE( COUNTRY ) ; 

ALTER TABLE STATE 
	ADD CONSTRAINT STATE_02 
	UNIQUE( COUNTRY_ID, STATE ) ; 
	
ALTER TABLE STATE
	ADD CONSTRAINT STATE_03
	FOREIGN KEY( COUNTRY_ID ) 
	REFERENCES COUNTRY ( COUNTRY_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE NOTE
	ADD CONSTRAINT PROSPECT_03
	FOREIGN KEY( PROSPECT_ID ) 
	REFERENCES PROSPECT ( PROSPECT_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE WEBUSER 
	ADD CONSTRAINT USER_02 
	UNIQUE( USER_NAME ) ; 

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_04
	FOREIGN KEY( SYSTEM_TYPE_ID ) 
	REFERENCES SYSTEM_TYPE ( SYSTEM_TYPE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_05
	FOREIGN KEY( TERRITORY_ID ) 
	REFERENCES TERRITORY ( TERRITORY_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_06
	FOREIGN KEY( LOB_ID ) 
	REFERENCES LOB ( LOB_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_07
	FOREIGN KEY( SOFTWARE_TYPE_ID ) 
	REFERENCES SOFTWARE_TYPE ( SOFTWARE_TYPE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_08
	FOREIGN KEY( VERIFIED_ID ) 
	REFERENCES VERIFIED ( VERIFIED_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 
	

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_09
	FOREIGN KEY( STATUS_ID ) 
	REFERENCES STATUS ( STATUS_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_10
	FOREIGN KEY( STATE_ID ) 
	REFERENCES STATE ( STATE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 
	
ALTER TABLE WEBUSER
	ADD CONSTRAINT USER_11
	FOREIGN KEY( COUNTRY_ID ) 
	REFERENCES COUNTRY ( COUNTRY_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 



ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_04
	FOREIGN KEY( SYSTEM_TYPE_ID ) 
	REFERENCES SYSTEM_TYPE ( SYSTEM_TYPE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_05
	FOREIGN KEY( TERRITORY_ID ) 
	REFERENCES TERRITORY ( TERRITORY_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_06
	FOREIGN KEY( LOB_ID ) 
	REFERENCES LOB ( LOB_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_07
	FOREIGN KEY( SOFTWARE_TYPE_ID ) 
	REFERENCES SOFTWARE_TYPE ( SOFTWARE_TYPE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_08
	FOREIGN KEY( VERIFIED_ID ) 
	REFERENCES VERIFIED ( VERIFIED_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 
	
ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_09
	FOREIGN KEY( STATUS_ID ) 
	REFERENCES STATUS ( STATUS_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_10
	FOREIGN KEY( STATE_ID ) 
	REFERENCES STATE ( STATE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 
	
ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_11
	FOREIGN KEY( COUNTRY_ID ) 
	REFERENCES COUNTRY ( COUNTRY_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_12
	FOREIGN KEY( SIZE_ID ) 
	REFERENCES SIZE ( SIZE_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 


ALTER TABLE COMMISION
	ADD CONSTRAINT COMMISION_02
	FOREIGN KEY( USER_ID ) 
	REFERENCES WEBUSER ( USER_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 

ALTER TABLE CUSTOM_QUERY_USERS 
	ADD CONSTRAINT CSTQRYUSR_01
	FOREIGN KEY( CUSTOM_QUERY_NO ) 
	REFERENCES CUSTOM_QUERY ( CUSTOM_QUERY_NO ) 
	ON DELETE CASCADE
	ON UPDATE RESTRICT ;
	
ALTER TABLE CUSTOM_QUERY_USERS 
	ADD CONSTRAINT CSTQRYUSR_02
	FOREIGN KEY( USER_ID ) 
	REFERENCES WEBUSER ( USER_ID ) 
	ON DELETE CASCADE
	ON UPDATE RESTRICT;
	
ALTER TABLE ACTION 
	ADD CONSTRAINT ACTION_02 
	UNIQUE( ACTION ) ; 
	
ALTER TABLE PROSPECT
	ADD CONSTRAINT PROSPECT_25
	FOREIGN KEY( NEXT_ACTION_ID ) 
	REFERENCES ACTION ( ACTION_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 
	
ALTER TABLE TERRITORY
	ADD CONSTRAINT TERRITORY_04
	FOREIGN KEY( OWNER_USER_ID ) 
	REFERENCES WEBUSER ( USER_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 	
	
ALTER TABLE TERRITORY
	ADD CONSTRAINT TERRITORY_05
	FOREIGN KEY( SALES_MANAGER_USER_ID ) 
	REFERENCES WEBUSER ( USER_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 	
	
ALTER TABLE TERRITORY
	ADD CONSTRAINT TERRITORY_06
	FOREIGN KEY( SERVICE_MANAGER_USER_ID ) 
	REFERENCES WEBUSER ( USER_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 			
	
ALTER TABLE WEBSITE
	ADD CONSTRAINT WEBSITE_02
	FOREIGN KEY( PROSPECT_ID ) 
	REFERENCES PROSPECT ( PROSPECT_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;

ALTER TABLE PROSPECT_DOCUMENT
	ADD CONSTRAINT PROSP_DOC_02
	FOREIGN KEY( PROSPECT_ID )
	REFERENCES PROSPECT ( PROSPECT_ID )
	ON DELETE RESTRICT
	ON UPDATE RESTRICT ;

ALTER TABLE PROSPECT_DOCUMENT
	ADD CONSTRAINT PROSP_DOC_03
	FOREIGN KEY( DOCUMENT_ID )
	REFERENCES DOCUMENT ( DOCUMENT_ID ) 
	ON DELETE RESTRICT
	ON UPDATE RESTRICT ;
	
ALTER TABLE CONTACT
	ADD CONSTRAINT CONTACT_02
	FOREIGN KEY( PROSPECT_ID ) 
	REFERENCES PROSPECT ( PROSPECT_ID ) 
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ; 
	
ALTER TABLE EDI_HEADER  
	ADD CONSTRAINT EDI_HEAD_02 
	FOREIGN KEY( EDI_GROUP_ID ) 
	REFERENCES EDI_GROUP ( EDI_GROUP_ID ) 
	ON DELETE CASCADE 
	ON UPDATE RESTRICT ;
	
ALTER TABLE EDI_DETAIL  
	ADD CONSTRAINT EDI_DET_02 
	FOREIGN KEY( EDI_DETAIL_ID ) 
	REFERENCES EDI_DETAIL ( EDI_DETAIL_ID ) 
	ON DELETE CASCADE 
	ON UPDATE RESTRICT ; 

	
	