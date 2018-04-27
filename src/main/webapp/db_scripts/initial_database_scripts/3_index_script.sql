CREATE INDEX COMPANY_IDX1
	ON COMPANY ( COMPANY ) ; 
	
CREATE INDEX SYS_TYPE_IDX1
	ON SYSTEM_TYPE ( SYSTEM_TYPE ) ; 	

CREATE INDEX TERRITORY_IDX1
	ON TERRITORY ( TERRITORY ) ; 	

CREATE INDEX LOB_IDX1
	ON LOB ( LOB ) ; 	

CREATE INDEX SOFT_TYPE_IDX1
	ON SOFTWARE_TYPE ( SOFTWARE_TYPE ) ; 	

CREATE INDEX VERIF_IDX1
	ON VERIFIED ( VERIFIED ) ; 	
	
CREATE INDEX COUNTRY_IDX1
	ON COUNTRY ( COUNTRY ) ; 	

CREATE INDEX COUNTRY_IDX2
	ON COUNTRY ( COUNTRY_CODE ) ; 	
CREATE INDEX SIZE_IDX1
	ON SIZE ( SIZE ) ; 	

CREATE INDEX STATE_IDX1
	ON STATE ( STATE ) ; 	

CREATE INDEX STATE_IDX2
	ON STATE ( STATE_CODE ) ; 	
	
CREATE INDEX STATE_IDX3
	ON STATE ( COUNTRY_ID ) ; 	

CREATE INDEX STATUS_IDX1
	ON STATUS ( STATUS ) ; 	

CREATE INDEX USER_IDX1
	ON WEBUSER ( USER_NAME ) ; 
  
CREATE INDEX USER_IDX3
	ON WEBUSER ( COMPANY_ID ) ; 
	
CREATE INDEX USER_IDX4
	ON WEBUSER ( USER_TYPE ) ; 
	
CREATE INDEX USER_IDX5
	ON WEBUSER ( ACTIVE ) ; 
	
CREATE INDEX USER_IDX6
	ON WEBUSER ( SYSTEM_TYPE_ID ) ; 
	
CREATE INDEX USER_IDX7
	ON WEBUSER ( LOB_ID ) ; 
	
CREATE INDEX USER_IDX8
	ON WEBUSER ( TERRITORY_ID ) ; 
	
CREATE INDEX USER_IDX9
	ON WEBUSER ( SOFTWARE_TYPE_ID ) ; 
	
CREATE INDEX USER_IDX10
	ON WEBUSER ( VERIFIED_ID ) ; 

CREATE INDEX PROSPECT_IDX01
	ON PROSPECT ( COMPANY_ID ) ; 

CREATE INDEX PROSPECT_IDX02
	ON PROSPECT ( SYSTEM_TYPE_ID ) ; 

CREATE INDEX PROSPECT_IDX03
	ON PROSPECT ( TERRITORY_ID ) ; 

CREATE INDEX PROSPECT_IDX04
	ON PROSPECT ( LOB_ID ) ; 
	
CREATE INDEX PROSPECT_IDX05
	ON PROSPECT ( SOFTWARE_TYPE_ID ) ; 
	
CREATE INDEX PROSPECT_IDX06
	ON PROSPECT ( VERIFIED_ID ) ; 
	
CREATE INDEX PROSPECT_IDX07
	ON PROSPECT ( STATUS_ID ) ; 
	
CREATE INDEX PROSPECT_IDX08
	ON PROSPECT ( CUSTOMER_COMPANY ) ; 
	
CREATE INDEX PROSPECT_IDX09
	ON PROSPECT ( CITY ) ; 
	
CREATE INDEX PROSPECT_IDX10
	ON PROSPECT ( ZIP ) ; 

CREATE INDEX PROSPECT_IDX11
	ON PROSPECT ( STATE_ID ) ; 
	
CREATE INDEX PROSPECT_IDX12
	ON PROSPECT ( COUNTRY_ID ) ; 
	
CREATE INDEX PROSPECT_IDX13
	ON PROSPECT ( CONTACT_NAME ) ; 
	
CREATE INDEX PROSPECT_IDX14
	ON PROSPECT ( COMPANY_ID ) ; 
	
CREATE INDEX NOTE_IDX01
	ON NOTE ( PROSPECT_ID ) ; 

CREATE INDEX NOTE_IDX02
	ON NOTE ( USER_NAME ) ; 

CREATE INDEX COMMISION_IDX01
	ON COMMISION ( USER_ID ) ; 
	
CREATE INDEX ACTION_IDX1
	ON ACTION ( ACTION ) ; 	

CREATE INDEX WEBSITE_IDX1
	ON WEBSITE ( WEBSITE_ID ) ; 	

CREATE INDEX WEBSITE_IDX2
	ON WEBSITE ( PROSPECT_ID ) ; 

CREATE INDEX DOCUMENT_IDX1
	ON DOCUMENT ( DOCUMENT_ID ) ;

CREATE INDEX DOCUMENT_IDX2
	ON DOCUMENT ( DOCUMENT_TYPE ) ;

CREATE INDEX DOCUMENT_IDX3
	ON DOCUMENT ( FILE_TYPE ) ;

CREATE INDEX PROSPECT_DOCUMENT_IDX1
	ON PROSPECT_DOCUMENT ( PROSPECT_DOCUMENT_ID ) ;

CREATE INDEX PROSPECT_DOCUMENT_IDX2
	ON PROSPECT_DOCUMENT ( DOCUMENT_ID ) ;

CREATE INDEX PROSPECT_DOCUMENT_IDX3
	ON PROSPECT_DOCUMENT ( PROSPECT_ID ) ;


CREATE UNIQUE INDEX USR_PK_DM 
	ON WEBUSER_DM ( USER_NO ) ; 
	
CREATE INDEX USR_USRN_IDX_DM 
	ON WEBUSER_DM ( USER_NAME ) ; 	
	
CREATE UNIQUE INDEX EDI_GRP_PK 
	ON EDI_GROUP ( EDI_GROUP_ID ) ; 	
	
CREATE INDEX EDI_GRP_UPLDT_IDX 
	ON EDI_GROUP ( UPLOAD_DATE ) ; 		
	
CREATE INDEX EDI_GRP_DWN_IDX 
	ON EDI_GROUP ( DOWNLOADED ) ; 			
	
CREATE UNIQUE INDEX EDI_HEAD_PK 
	ON EDI_HEADER ( EDI_HEADER_ID ) ; 		
	
CREATE INDEX EDI_HEAD_GRP_IDX 
	ON EDI_HEADER ( EDI_GROUP_ID ) ; 		
	
CREATE UNIQUE INDEX EDI_DET_PK 
	ON EDI_DETAIL ( EDI_DETAIL_ID ) ; 				
	
CREATE INDEX EDI_DET_HEAD_IDX 
	ON EDI_DETAIL ( EDI_HEADER_ID ) ; 					
	
	

