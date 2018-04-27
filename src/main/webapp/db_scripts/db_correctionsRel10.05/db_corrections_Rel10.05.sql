---- fixing IP security --

delete from security_actions where sec_act_pk <100;

INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (0, 'CustomerSelection', 0, 1, 'Entry point for All reports', 1);
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (1, 'ActivitySummary', 1, 1, 'Activity Summary report', 1);
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (2, 'Misc. Reports Menu', 2, 1, '', 1);	
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (3, 'Historical Reports Menu', 3, 1, '', 1);		

	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (4, 'Date Reports Menu', 4, 1, '', 1);	
		
			

	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (20, 'Propsect Summary Queries(by Status Id)', 1, 0, '', 1);	
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (21, 'Propsect Summary By State Queries', 1, 0, '', 1);	
	
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (22, 'Propsect Summary By Country Queries', 1, 0, '', 1);								

	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (23, 'Propsect Summary by Territory Queries', 1, 0, '', 1);	
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (24, 'Sales Actions Queries', 1, 0, '', 1);	
		
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (30, 'Misc. Reports - New Leads', 2, 0, '', 1);	
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (31, 'Misc. Reports - Prospects Not Linked to Ter', 2, 0, '', 1);	
		

		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (40, 'Historical Reports - Sales Actions', 3, 0, '', 1);		
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (41, 'Historical Reports - Campaigns', 3, 0, '', 1);		
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (50, 'Date Summary - Lead Source Report', 4, 0, '', 1);	
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (51, 'Date Summary - Rep Sales Report', 4, 0, '', 1);
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (52, 'Date Summary - Sales Action Report', 4, 0, '', 1);
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (53, 'Date Summary - Updates Report', 4, 0, '', 1);
		
	INSERT INTO  SECURITY_ACTIONS (SEC_ACT_PK, ACTION_DESC, ACTION_CATEGORY, ENTRY_POINT, COMMENTS, APP_GROUP_FK) 
		VALUES (54, 'Date Summary - New Lead Report', 4, 0, '', 1);		


