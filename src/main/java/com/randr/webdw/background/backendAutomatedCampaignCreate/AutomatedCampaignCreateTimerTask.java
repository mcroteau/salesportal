package com.randr.webdw.background.backendAutomatedCampaignCreate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.randr.webdw.campaign.CampaignDetails;
import com.randr.webdw.campaign.CampaignView;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionView;
import com.randr.webdw.emailSalesAction.emailCampaign.EmailCampaignDetails;
import com.randr.webdw.emailSalesAction.emailCampaign.EmailCampaignView;
import com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsView;
import com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesView;
import com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesView;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectCampaign.ProspectCampaignDetails;
import com.randr.webdw.prospectCampaign.ProspectCampaignView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.util.ConnectionPool;
import com.randr.webdw.util.DateUtilities;


/**
 */
public class AutomatedCampaignCreateTimerTask extends TimerTask {
    protected Connection connection = null;

    /**
     * Method run.
     * 1. goes thru the emailCampaigns and adds a campaign to prospects that match
     * the selection criteria and do not already have this campaign.
     * 
     */
    public void run() {
        createCampaignsAction();//this goes thru the emailCampaigns and creates campaigns for each prospect
       
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
     * Method createCampaignsAction.
     * this goes thru the emailCampaigns and add a campaign to each
     * prospect if they didn't already get it.
     * step1 - for each emailCampaign get the prospectView with all prospects
     * step 2 - check if the prospect already has this campaign
     * step 3 if no, then add the campaign and calculate the dates
     * 
     */
    protected void createCampaignsAction() {
        try {
            openConnection();
            System.out.println("createCampaignsAction1 - getting started");
            EmailCampaignView emailCampaignView = new EmailCampaignView();
            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
            emailCampaignDetails.setOpenEmailCampaign(true);
            emailCampaignDetails.setCheckStartEmailDate(DateUtilities.getCurrentSQLDate());
            emailCampaignDetails.setCheckEndEmailDate(DateUtilities.getCurrentSQLDate());   
            emailCampaignView.fillWithElements(connection, EmailCampaignView.FILL_TYPE_ALL, emailCampaignDetails);
            System.out.println("createCampaignsAction1.2 size of emailCampaignView= " + emailCampaignView.getElements().size());
            for (int i = 0; i < emailCampaignView.getElements().size(); i++) {
            	emailCampaignDetails = (EmailCampaignDetails) emailCampaignView.getElements().elementAt(i);
			
    			//step1 - for each emailCampaign get the prospectView with all prospects
    			ProspectDetails prospectDetails = new ProspectDetails();
    			ProspectView prospectView = new ProspectView();
    			prospectDetails.setLobIdList(getLobIdList(emailCampaignDetails.getEmailCampaignId()));
    			prospectDetails.setTerritoryIdList(getTerritoryIdList(emailCampaignDetails.getEmailCampaignId()));
    			prospectDetails.setStatusIdList(getStatusIdList(emailCampaignDetails.getEmailCampaignId()));
    			if(emailCampaignDetails.getUseCampaignStartDate().compareTo(EmailCampaignDetails.YES_USE_CAMPAIGN_START_DATE)==0){
    				prospectDetails.setCreationDate(emailCampaignDetails.getStartEmailDate());//limit by prospects by creation date
		   		}	
    			prospectDetails.setExcludeNIAndDNC(true);
    			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);	        		
    			System.out.println("createCampaignsAction3 - size of prospectView= " + prospectView.getElements().size());
    			if(prospectView.getElements().size()>0){			
    				//for each propsect in the view do the following:
    				for(int j=0;j<prospectView.getElements().size();j++){
//    					step 2 - check if the prospect already has this campaign
        				prospectDetails = (ProspectDetails)prospectView.getElement(j);
        				ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
        				ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
        				prospectCampaignDetails.setProspectId(prospectDetails.getProspectId()); 
        				prospectCampaignDetails.setCampaignId(emailCampaignDetails.getCampaignId());
        				prospectCampaignView.fillWithElements(connection, ProspectCampaignView.FILL_TYPE_ALL, prospectCampaignDetails);
        				//System.out.println("createCampaignsAction4 - size of prospectCampaignView= " + prospectCampaignView.getElements().size() +
        				//		" name= " + prospectDetails.getContactName());
        				if(prospectCampaignView.getElements().size()==0){
        				//step 3 if no, then add the campaign and calculate the dates
        					createCampaign(prospectDetails, emailCampaignDetails);
        					 //also, change the prospect Update Date and User
        		            ProspectDetails prospectUpdateDetails = new ProspectDetails();
        		            ProspectView prospectUpdateView = new ProspectView();
        		            prospectUpdateDetails.setProspectId(prospectDetails.getProspectId());
        		            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
        		            if(prospectUpdateView.getElements().size()>0){
        		            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
        		            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
        		            	prospectUpdateDetails.setChangeUserId(new BigDecimal(0));
        		            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
        		            }
        				}
    				}//end for loop of prospectView
                 }// end if prospectView>0
        	}// end for loop of emailCampaignView
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
    

	/**
	 * @param prospectDetails
	 * @param campaignId
	 * Create the campaign for the prospect
	 * iterate throught the campaign sales actions to set each one up
	 */
	private void createCampaign(ProspectDetails prospectDetails, EmailCampaignDetails emailCampaignDetails) throws Exception{

    	CampaignDetails campaignDetails = getCampaignDetails(emailCampaignDetails.getCampaignId());
    	CampaignSalesActionView campaignSalesActionView = getCampaignSalesActionView(emailCampaignDetails.getCampaignId());
    	
    	// insert ProspectCampaign
    	ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
    	prospectCampaignDetails.setCampaignId(campaignDetails.getCampaignId());
    	prospectCampaignDetails.setProspectId(prospectDetails.getProspectId());
    	prospectCampaignDetails.setCreationDate(new Timestamp(System.currentTimeMillis()));
    	ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
    	prospectCampaignView.doAction(connection, "INSERT", new BigDecimal(1), prospectCampaignDetails);
    	prospectCampaignDetails = (ProspectCampaignDetails)prospectCampaignView.getCurrent();
    	
    	// insert ProspectSalesActions
    	ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
    	Timestamp priorActionDate = DateUtilities.getCurrentSQLTimestamp() ;
    	for(int i = 0; i < campaignSalesActionView.getElements().size(); i++) {
    		CampaignSalesActionDetails campaignSalesActionDetails 
    			= (CampaignSalesActionDetails)campaignSalesActionView.getElement(i);
    		ProspectSalesActionDetails prospectSalesActionDetails
    			= getProspectSalesActionDetails(prospectCampaignDetails, campaignSalesActionDetails, 
    					prospectCampaignDetails.getProspectId(), priorActionDate, emailCampaignDetails.getEmailCampaignDescription());
    		prospectSalesActionView.doAction(connection, "INSERT", new BigDecimal(1), prospectSalesActionDetails);
    		priorActionDate= prospectSalesActionDetails.getActionDate();
    	}
       
	}

	/**
	 * @param campaignId
	 * @return
	 * @throws Exception
	 * get the campaign details
	 */
	private CampaignDetails getCampaignDetails(BigDecimal campaignId) throws Exception{
	    	CampaignView campaignView = new CampaignView();
	    	CampaignDetails campaignDetails = new CampaignDetails();
	    	campaignDetails.setCampaignId(campaignId);
	    	campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignDetails);
	    	if(campaignView.getElements().size() > 0) {
	    		return (CampaignDetails)campaignView.getElement(0);
	    	} else {
	    		return new CampaignDetails();
	    	}
	    }
	   
	   /**
	 * @param campaignId
	 * @return
	 * @throws Exception
	 * get the campaign view
	 */
	private CampaignSalesActionView getCampaignSalesActionView(BigDecimal campaignId) throws Exception{
	    	CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
	    	CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
	    	campaignSalesActionDetails.setCampaignId(campaignId);
	    	campaignSalesActionView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
	    	return campaignSalesActionView;
	    }
	   
	   /**
	 * @param campaignDetails
	 * @param campaignSalesActionDetails
	 * @param prospectId
	 * @param priorActionDate  // date of the prior sales action, today if this is the first sales action
	 * @return
	 * @throws Exception
	 * create the prospectSalesAction for each step in the campaign
	 */
	private ProspectSalesActionDetails getProspectSalesActionDetails(ProspectCampaignDetails prospectCampaignDetails, 
	    		CampaignSalesActionDetails campaignSalesActionDetails, BigDecimal prospectId, Timestamp priorActionDate,
	    		String emailCampaignDescription) throws Exception {
	    	//System.out.println("setting up the prospect Sales Action, priorActionDate= "+ priorActionDate + " days to add= " +
	    	//		campaignSalesActionDetails.getSendEmailDays() + " sales action id= " + campaignSalesActionDetails.getSalesActionId());
	    	ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
	    	prospectSalesActionDetails.setProspectId(prospectId);
	    	prospectSalesActionDetails.setSalesActionId(campaignSalesActionDetails.getSalesActionId());
	    	prospectSalesActionDetails.setProspectCampaignSequence(campaignSalesActionDetails.getDisplaySequence());
	    	prospectSalesActionDetails.setProspectCampaignId(prospectCampaignDetails.getProspectCampaignId());
	    	prospectSalesActionDetails.setActionPriority(new BigDecimal(1));
	        prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
	    	prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));
	    	prospectSalesActionDetails.setActionNote("Automated Campaign " + emailCampaignDescription);
	    	prospectSalesActionDetails.setEmailDraftToUse(getDraftEmailToUse(campaignSalesActionDetails.getSalesActionId()));
	    	prospectSalesActionDetails.setMandatoryDate(campaignSalesActionDetails.getSalesActionMandatoryDate());
	    	Timestamp timestamp=null;
	    	if(prospectSalesActionDetails.getMandatoryDate().compareTo(ProspectSalesActionDetails.REQUIRES_MANDATORY_DATE)==0){ 
	    		timestamp = DateUtilities.addDaysToSQLTimestamp(priorActionDate,campaignSalesActionDetails.getSendEmailDays());//set the date for this sales action
	    	}
	    	//System.out.println("actiondate = " + timestamp);
	        prospectSalesActionDetails.setActionDate(timestamp);
	    	
	    	return prospectSalesActionDetails;  	
	    }
	    
	private BigDecimal getDraftEmailToUse(BigDecimal salesActionId) throws Exception{
		SalesActionDetails salesActionDetails = new SalesActionDetails();
		SalesActionView salesActionView = new SalesActionView();
		salesActionDetails.setActionId(salesActionId);
		salesActionView.fillWithElements(connection, new BigDecimal(1), salesActionDetails);
		if (salesActionView.getElements().size()>0){
			salesActionDetails = (SalesActionDetails)salesActionView.getElement(0);
			System.out.println("AutoCampaign, EmailDraft= " + salesActionDetails.getEmailDraftToUse());
			return salesActionDetails.getEmailDraftToUse();
			
		}
		return null;
	}

	private List getLobIdList(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionLobsDetails emailLobsDetails = new EmailSalesActionLobsDetails();
		EmailSalesActionLobsView emailLobsView = new EmailSalesActionLobsView();
		emailLobsDetails.setEmailCampaignId(emailCampaignId);
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
	
	private List getTerritoryIdList(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
		EmailSalesActionTerritoriesView emailTerritoriesView = new EmailSalesActionTerritoriesView();
		emailTerritoriesDetails.setEmailCampaignId(emailCampaignId);
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

	private List getStatusIdList(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails = new EmailSalesActionStatusesDetails();
		EmailSalesActionStatusesView emailStatusesView = new EmailSalesActionStatusesView();
		emailStatusesDetails.setEmailCampaignId(emailCampaignId);
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
