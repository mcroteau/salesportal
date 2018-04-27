package com.randr.webdw.background.backendEmailSalesActionSend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.randr.webdw.AppSettings;
import com.randr.webdw.emailSalesAction.EmailSalesActionDetails;
import com.randr.webdw.emailSalesAction.EmailSalesActionView;
import com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsView;
import com.randr.webdw.emailSalesAction.emailSalesActionSentProspects.EmailSalesActionSentProspectsDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionSentProspects.EmailSalesActionSentProspectsView;
import com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesView;
import com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesView;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.URLHandle;
import com.randr.webdw.util.Utilities;


/**
 */
public class EmailSalesActionSendTimerTask extends TimerTask {
    protected Connection connection = null;

    /**
     * Method run.
     * 2 steps
     * 1. goes thru the emailSalesActions and sends out emails
     * 2. goes thru the prospectSalesActions and selects any emails that need to be sent
     */
    public void run() {
        sendEmailSalesAction();//this goes thru the emailSalesActions and sends out emails
        sendEmailsForProspectSalesActions();//this goes thru the prospectSalesActions and selects any emails that need to be sent
    }

	/**
     * Method openConnection.
     * @throws SQLException
     */
    protected void openConnection() throws SQLException {
        this.connection = ConnectionPool.getNewConnection();
    }

    /**
     * Method commit.
     * @throws SQLException
     */
    protected void commit() throws SQLException {
        this.connection.commit();
    }

    /**
     * Method finallyMethod.
     */
    protected void finallyMethod() {
        if (this.connection != null) {
            try {
                this.connection.rollback();
            } catch (Exception sql) {
            }

            try {
                this.connection.close();
            } catch (Exception sql) {
            }
        }
    }

    /**
     * Method doCatch.
     * @param e Exception
     */
    protected void doCatch(Exception e) {
        e.printStackTrace();
    }
    
    /**
     * Method sendNextSalesActionEmailNotification.
     * this goes thru the emailSalesActions and sends out emails, there are 5 steps
     * this could send mulitple CSV files, one for each different Email Sales Action
     * 1. get all the prospects, and add the email specific fields
     * 2. do the logic in the EmailQueTransferController
     * 3. write each prospect into the email Sent Action Prospect table.
     * 4. write a completed sales action
     * 5. update the emailSalesAction with sent date
     */
    protected void sendEmailSalesAction() {
        try {
            openConnection();
            //System.out.println("EmailSendTimer1 - getting started");
            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
            emailSalesActionDetails.setOpenEmailSalesAction(true);
            emailSalesActionDetails.setCheckSendEmailDate(DateUtilities.getCurrentSQLDate());
            //System.out.println("EmailSendTimer1.1 size of emailSalesActionView, date= " + emailSalesActionDetails.getCheckSendEmailDate());
   
            emailSalesActionView.fillWithElements(connection, EmailSalesActionView.FILL_TYPE_ALL, emailSalesActionDetails);
            //System.out.println("EmailSendTimer1.2 size of emailSalesActionView= " + emailSalesActionView.getElements().size());
            for (int i = 0; i < emailSalesActionView.getElements().size(); i++) {
            	emailSalesActionDetails = (EmailSalesActionDetails) emailSalesActionView.getElements().elementAt(i);
			
    			//step1 - get the prospectView with all prospects
    			ProspectDetails prospectDetails = new ProspectDetails();
    			ProspectView prospectView = new ProspectView();
    			
    			if(emailSalesActionDetails.getVerifiedYesNo().compareTo(EmailSalesActionDetails.USE_VERIFED_YES_ONLY)==0){
    				prospectDetails.setVerifiedId(new BigDecimal(AppSettings.getParm("VERIFIED_VALUE_FOR_EMAIL_SALES_ACTION")));
    			}
    			prospectDetails.setLobIdList(getLobIdList(emailSalesActionDetails.getEmailActionId()));
    			prospectDetails.setTerritoryIdList(getTerritoryIdList(emailSalesActionDetails.getEmailActionId()));
    			prospectDetails.setStatusIdList(getStatusIdList(emailSalesActionDetails.getEmailActionId()));
    			prospectDetails.setNotOptedOut(true);
    			prospectDetails.setExcludeNIAndDNC(true);
    			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);	        		
    			System.out.println("EmailSendTimer2 - size of prospectView= " + prospectView.getElements().size());
    			UserDetails salesmanDetails;
    			if(prospectView.getElements().size()>0){
    				//step 2 - do the logic from EmailQueTransferController
	       			 for (int j = 0; j < prospectView.getElements().size(); j++) {
	       				prospectDetails = (ProspectDetails)prospectView.getElement(j);
	       				prospectDetails.setEmailDraftToUse(emailSalesActionDetails.getEmailDraftToUse());
	    	        	prospectDetails.setEmailDescription(emailSalesActionDetails.getEmailActionDescription());
	    	        	salesmanDetails = getSalesmanFromEmail(prospectDetails);
	        			prospectDetails.setFromEmailToUse(salesmanDetails.getEmail());
	        			prospectDetails.setSalesmanName(salesmanDetails.getFirstName().trim() + " " + salesmanDetails.getLastName().trim());
	        			System.out.println("sendEmail, salesmanName= " + prospectDetails.getSalesmanName());
	       			 }// end for loop over prospectView to populate email fields
    				 //System.out.println("EmailSendTimer3 - about to start email transfer");
    				 startEmailQueTransfer(prospectView);
    				//this will save the csv file and notify DMP to pick it up.
    				//DMP will send the request to the EmailQueTransferController which will retrieve and hand off the csv file.
    				
    				//for each propsect in the view do the following:
    				//System.out.println("EmailSendTimer3.1 size of ProspectView now=" + prospectView.getElements().size());
    				for(int k=0;k<prospectView.getElements().size();k++){
        				//step 3 - write the prospect to the Email sent action prospect table
        				//System.out.println("EmailSendTimer4 - about to write to sent prospect table");
        				prospectDetails = (ProspectDetails)prospectView.getElement(k);
        				writeEmailAudit(prospectDetails);//separatte method
  
        				//step 4 - write a completed sales action
        				//System.out.println("EmailSendTimer5 - about to create completed sales action for each prospect");
        				ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
        				ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
        				prospectSalesActionDetails.setProspectId(prospectDetails.getProspectId());
        				prospectSalesActionDetails.setSalesActionId(new BigDecimal(AppSettings.getParm("SALES_ACTION_ID_FOR_COMPLETED_EMAIL_SALES_ACTION")));
        				prospectSalesActionDetails.setActionPriority(ProspectSalesActionDetails.FILL_TYPE_ALL);
        				prospectSalesActionDetails.setActionDate(DateUtilities.getCurrentSQLTimestamp());
        				prospectSalesActionDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
        				prospectSalesActionDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
        				prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(1));
        				prospectSalesActionDetails.setActionNote("Automated Email: " + emailSalesActionDetails.getEmailActionDescription());
        				prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_COMPLETE);
        				prospectSalesActionView.doAction(connection, "INSERT", ProspectSalesActionView.FILL_TYPE_ALL, prospectSalesActionDetails);
    				}//end for loop of prospectView
                 }// end if prospectView>0
    			//step 5 update the sent date for the email sales action
    			//System.out.println("EmailSendTimer6 - about to flag emailSales Action as sent with sent date");
    			emailSalesActionDetails.setActualEmailDate(DateUtilities.getCurrentSQLTimestamp());
    			emailSalesActionView.doAction(connection, "UPDATE", EmailSalesActionView.FILL_TYPE_ALL, emailSalesActionDetails);
        	}// end for loop of emailSalesActionView
 
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
    
    /**
     * @throws Exception
     * Write to the emailSalesActionSentProspect Table
     */
    private void writeEmailAudit(ProspectDetails prospectDetails)throws Exception{
    	EmailSalesActionSentProspectsDetails emailSentProspectDetails = new EmailSalesActionSentProspectsDetails();
		EmailSalesActionSentProspectsView emailSentProspectView = new EmailSalesActionSentProspectsView();
		//emailSentProspectDetails.setEmailActionId(emailSalesActionDetails.getEmailActionId());
		emailSentProspectDetails.setEmailActionId(prospectDetails.getEmailDraftToUse());//this is the email draft#, not the action id
		emailSentProspectDetails.setProspectId(prospectDetails.getProspectId());
		emailSentProspectDetails.setSentDate(DateUtilities.getCurrentSQLTimestamp());
		emailSentProspectView.doAction(connection, "INSERT", EmailSalesActionSentProspectsView.FILL_TYPE_ALL, emailSentProspectDetails);
	}

    /**
     * this goes thru the prospectSalesActions and selects any emails that need to be sent, there are 5 steps
     * There will be 1 csv file for all the prospects, OP has been changed to handle different email draft# in the csv file
     * 1. selec the prospectSalesActions, and then get a list of all prospects,
     * 2. then create a prospect view with all the prospects, populating all the data we need for the email
     * 3. do the logic in the EmailQueTransferController, note we changed getEmailQueCSVContent to get all the info from the prospect details
     * 4. update the prospectSalesAction as completed with complete date
     * 5. write each prospect into the email Sent Action Prospect table. 
     */
    private void sendEmailsForProspectSalesActions() {
    	try {
    		System.out.println("SP in sendEmailsForProspectSalesActions");
            openConnection();
	    	//Step 1 - get the prospect sales actions to 
	    	ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
	    	ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
	    	prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);//not completed
	    	prospectSalesActionDetails.setCheckActionDate(DateUtilities.getCurrentSQLDate());//date less than or = today
	    	prospectSalesActionDetails.setEmailProspectSalesAction(true);//has email draft number
	    	prospectSalesActionView.fillWithElements(connection, EmailSalesActionView.FILL_TYPE_ALL, prospectSalesActionDetails);
	    	System.out.println("EmailSendTimer, sales actions 1 - getting prospectSalesActionView, size= " + prospectSalesActionView.getElements().size());
	    	//only do the following if prospectSalesActionView >0
	    	if(prospectSalesActionView.getElements().size()>0){
		    	ProspectDetails prospectDetails;
		    	ProspectView prospectView;
		    	ProspectView collectionProspectView = new ProspectView();
		    	for (int i = 0; i < prospectSalesActionView.getElements().size(); i++) {
		        	prospectSalesActionDetails = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
		        	//step 2 - create a prospect view that has every prospect and all the fields we need
		        	prospectDetails = new ProspectDetails();
		        	prospectDetails.setProspectId(prospectSalesActionDetails.getProspectId());
		        	prospectView = new ProspectView();
		        	prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
		        	UserDetails salesmanDetails;
		        	if (prospectView.getElements().size()>0){
		        		prospectDetails = (ProspectDetails)prospectView.getElement(0);
		        		if(prospectDetails.getOptOutDate()==null){// add to collection if not opted out
		        			prospectDetails.setEmailDraftToUse(prospectSalesActionDetails.getEmailDraftToUse());
		        			prospectDetails.setEmailDescription(prospectSalesActionDetails.getActionNote());
		        			salesmanDetails = getSalesmanFromEmail(prospectDetails);
		        			prospectDetails.setFromEmailToUse(salesmanDetails.getEmail());
		        			prospectDetails.setSalesmanName(salesmanDetails.getFirstName().trim()+ " " + salesmanDetails.getLastName().trim());
		        			System.out.println("sendEmail, salesmanName= " + prospectDetails.getSalesmanName());
		        			collectionProspectView.addElement(prospectDetails);
		        		}else{//updateProspectSalesActionDetails with NotSentFlag, use this belos
		        			prospectSalesActionDetails.setEmailNotSent(true);
		        		}
		        	}//end prospectView >0	  
		        	System.out.println("EmailSendTimer, sales actions 2 - size of collectionProspectView= " + collectionProspectView.getElements().size());
		        }//end for loop prospectSalesActionView - get prospect 
				 if (collectionProspectView.getElements().size()>0){
//					step 3 - do the logic from EmailQueTransferController
					 System.out.println("EmailSendTimer, sales actions 3 - about to start email transfer");
					 startEmailQueTransfer(collectionProspectView);
				 }//end of if collectionProspectView >0
				//this will save the csv file and notify DMP to pick it up.
				//DMP will send the request to the EmailQueTransferController which will retrieve and hand off the csv file.
	//			 Step 4 update the prospectSalesAction as completed with complete date
				 System.out.println("EmailSendTimer, sales actions 4 - about to update sales actions a completed, size of prospectSalesActionView= " + prospectSalesActionView.getElements().size());
				  for (int i = 0; i < prospectSalesActionView.getElements().size(); i++) {//update everyone
			        	prospectSalesActionDetails = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
			        	prospectSalesActionDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
			        	prospectSalesActionDetails.setChangedByUserId(new BigDecimal(9999999));//filler
			        	if(prospectSalesActionDetails.isEmailNotSent()){
			        		//email did not out
			        		prospectSalesActionDetails.setActionNote("Not Sent, Prospect has Opted Out");
			        	}else{//email went out
			        		prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_COMPLETE);
			        	}
			        	prospectSalesActionView.doAction(connection, "UPDATE", ProspectSalesActionView.FILL_TYPE_ALL, prospectSalesActionDetails);
				  }//end for loop with prospectSalesActionView - update as completed
				  //step 5 write each prospect into the email Sent Action Prospect table. 
				  //System.out.println("EmailSendTimer, sales actions 5 - about to write to email audit table, size of collectionProspectView= " + collectionProspectView.getElements().size());
				  for (int i = 0; i < collectionProspectView.getElements().size(); i++) {
					  prospectDetails = (ProspectDetails)collectionProspectView.getElement(i);
					  writeEmailAudit(prospectDetails);
				  }//end for loop over collectionProspectView, write to email audit table
				 }//end of if prospectSalesActionView >0
    			
    	} catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
	}

    private void startEmailQueTransfer(ProspectView prospectView) {
        try {
        	String datePrefix = DateUtilities.formatDate((new java.util.Date()), "yyyyMMdd");
        	String fileName = datePrefix + "_emailque_" + System.currentTimeMillis()+ ".csv";
        	//System.out.println("About to write CSV file = " + fileName);
        	writeCsvFile(prospectView, fileName);   
			notifyEmailQueCsvReady(fileName);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
  
    /**
     * Method writeCsvFile.
     * @param prospectView ProspectView
     */
    private void writeCsvFile(ProspectView sessionProspectView, String fileName) {
        try {
        	//System.out.println("do we get here 2, createEmailQueCsvFile");
            String csvContent = getEmailQueCSVContent(sessionProspectView);
            String dirName = getUnprocessedCsvEmailQueDirectoryName();
            //System.out.println("do we get here 2.1, createEmailQueCsvFile");
            createRequiredDirectory(dirName);
            //System.out.println("do we get here 2.2, createEmailQueCsvFile");
            String prospectFileName = dirName + fileName;
            //System.out.println("value of csvContent= " + csvContent);
            //System.out.println("do we get here 2.3, createEmailQueCsvFile");
    		if(!csvContent.equals("")){
    			//System.out.println("do we get here 2.4, createEmailQueCsvFile, writing csv content");
                BufferedWriter out = new BufferedWriter(new FileWriter(prospectFileName));
                //System.out.println("do we get here 2.5, createEmailQueCsvFile");
                out.write(csvContent);
                out.close();
            }
    		
            //System.out.println("SAVED");
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println("DID NOT SAVE");
            doCatch(e);
        }
    }
    
    private String getEmailQueCSVContent(ProspectView prospectView) throws Exception {
    	StringBuffer csvContent = new StringBuffer();
        ProspectDetails prospectDetails = null;
        //System.out.println(" in getEmailQueCSVContent, size of sessionProspectView= " + prospectView.getElements().size());
    	for (int i = 0; i < prospectView.getElements().size(); i++) {
    		prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i);
    		//System.out.println(" in getEmailQueCSVContent,prospectDetails.email= " + prospectDetails.getEmail()  + " number= " + i +
    		//		" name= " + prospectDetails.getContactName() + " id= " + prospectDetails.getProspectId());
            if(prospectDetails.getEmail() != null && !prospectDetails.getEmail().equals("")){// any prospects with email
            	//System.out.println(" in getEmailQueCSVContent,email is not null");
            	csvContent.append(prospectDetails.getEmail() + ",");//A = email
            	csvContent.append(prospectDetails.getEmailDescription().trim() + ",");//B = Category
            	csvContent.append(prospectDetails.getCustomerCompany() + ",");//C= company
            	csvContent.append(prospectDetails.getContactName() + ",");//D = Name
            	csvContent.append(prospectDetails.getContactName() + ",");//E = Title
            	//System.out.println("csvContent, value of prospectDetails.getFromEmailToUse()=" + prospectDetails.getFromEmailToUse());
            	if(Utilities.nullToBlank(prospectDetails.getFromEmailToUse()).compareTo("")!=0){
            		csvContent.append(prospectDetails.getFromEmailToUse()+ ",");//F = from email
            	}else{
            		csvContent.append(""+ ",");//F = from email
            	}
            	csvContent.append(prospectDetails.getEmailDraftToUse() + ",");//G = from email
            	csvContent.append(prospectDetails.getSalesmanName());//H = salesman Name
            	csvContent.append("\n");
            	//System.out.println("csv content= " + csvContent.toString());
            }
        }
		return csvContent.toString();
	}

    /**
     * @param prospectDetails
     * return userDetails
     * get the salesman email to use as from email
     * get salesman first anem + last name as salesman name
     * @return
     */
    private UserDetails getSalesmanFromEmail(ProspectDetails prospectDetails) throws Exception{
    	TerritoryDetails territoryDetails = new TerritoryDetails();
    	TerritoryView territoryView = new TerritoryView();
    	territoryDetails.setTerritoryId(prospectDetails.getTerritoryId());
    	territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, territoryDetails);
    	if (territoryView.getElements().size()>0){
    		territoryDetails = (TerritoryDetails)territoryView.getElement(0);
    		
    		UserDetails userDetails = new UserDetails();
    		UserView userView = new UserView();
    		userDetails.setUserId(territoryDetails.getOwnerUserId());
    		userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, userDetails);
    		if(userView.getElements().size()>0){
    			userDetails = (UserDetails)userView.getElement(0);
    			return userDetails;
    		}
    	}
		return null;
	}

	private String getUnprocessedCsvEmailQueDirectoryName() {
		return AppSettings.getAppRealPath()+ "csv/emailQue/";
	}

	protected void createRequiredDirectory(String directory) {
		File dir = new File(directory);
		//System.out.println("dirname = " + directory);
		//System.out.println("dir = " + dir.isDirectory());
		if(!dir.isDirectory()) {
			//System.out.println("making dirs");
			dir.mkdirs();
		}
		//System.out.println("made dir");
	}
	
	private void notifyEmailQueCsvReady(String fileName) {
		String id = "";
		String content = "";
		//System.out.println("do we get here 3, createEmailQueCsvFile");
		try {
			content = URLHandle.getURLContent(AppSettings.getParm("SALESPORTAL_AUTOMATED_EMAIL_LINK"),
						URLHandle.HTTP, URLHandle.GET, "formAction=NOTIFY_EMAIL_QUE_CSV_READY&id=" + id +"&fileName=" + fileName);
			//System.out.println("content=" + content);
			logResult(content, id, "SUCCESS");
		} catch(Exception e) {
			logResult(content, id, "FAILURE");
			e.printStackTrace();
		} 
	}
	 
	private void logResult(String content, String id, String tag) {
	    	try {
	    		//System.out.println("LOG RESULTS   ID: " + id + "  CONTENT: "+ content + "  TAG: " + tag);
			} catch(Exception e) {
				e.getStackTrace();
	        }
		}
	 

	private List getLobIdList(BigDecimal emailActionId) throws Exception{
		EmailSalesActionLobsDetails emailLobsDetails = new EmailSalesActionLobsDetails();
		EmailSalesActionLobsView emailLobsView = new EmailSalesActionLobsView();
		emailLobsDetails.setEmailActionId(emailActionId);
		emailLobsView.fillWithElements(connection, EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
		List lobList = new ArrayList();
		if (emailLobsView.getElements().size()>0){
			for(int i = 0; i < emailLobsView.getElements().size(); i++){
				emailLobsDetails = (EmailSalesActionLobsDetails)emailLobsView.getElement(i);
				lobList.add(emailLobsDetails.getLobId());
			}
			return lobList;
		}
		return null;
	}
	
	private List getTerritoryIdList(BigDecimal emailActionId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
		EmailSalesActionTerritoriesView emailTerritoriesView = new EmailSalesActionTerritoriesView();
		emailTerritoriesDetails.setEmailActionId(emailActionId);
		emailTerritoriesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
		List territoryList = new ArrayList();
		if (emailTerritoriesView.getElements().size()>0){
			for(int i = 0; i < emailTerritoriesView.getElements().size(); i++){
				emailTerritoriesDetails = (EmailSalesActionTerritoriesDetails)emailTerritoriesView.getElement(i);
				territoryList.add(emailTerritoriesDetails.getTerritoryId());
			}
			return territoryList;
		}
		return null;
	}

	private List getStatusIdList(BigDecimal emailActionId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails = new EmailSalesActionStatusesDetails();
		EmailSalesActionStatusesView emailStatusesView = new EmailSalesActionStatusesView();
		emailStatusesDetails.setEmailActionId(emailActionId);
		emailStatusesView.fillWithElements(connection, EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
		List statusList = new ArrayList();
		if (emailStatusesView.getElements().size()>0){
			for(int i = 0; i < emailStatusesView.getElements().size(); i++){
				emailStatusesDetails = (EmailSalesActionStatusesDetails)emailStatusesView.getElement(i);
				statusList.add(emailStatusesDetails.getStatusId());
			}
			return statusList;
		}
		return null;
	}

  
}
