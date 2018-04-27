package com.randr.webdw.emailSalesAction.emailCampaign;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.randr.webdw.AppSettings;
import com.randr.webdw.campaign.CampaignDetails;
import com.randr.webdw.campaign.CampaignView;
import com.randr.webdw.emailSalesAction.EmailSalesActionDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsView;
import com.randr.webdw.emailSalesAction.emailSalesActionSentProspects.EmailSalesActionSentProspectsDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionSentProspects.EmailSalesActionSentProspectsView;
import com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesView;
import com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesDetails;
import com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesView;
import com.randr.webdw.lob.LobDetails;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.status.StatusDetails;
import com.randr.webdw.status.StatusView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.util.DateUtilities;


/**
 */
public class EmailCampaignController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
    	System.out.println("formAction= " + formAction);
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayEmailCampaigns();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertEmailCampaign();
        } else if (formAction.equals("INSERT")) {
            insertEmailCampaign();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateEmailCampaign();
        } else if (formAction.equals("UPDATE")) {
            updateEmailCampaign();
        } else if (formAction.equals("DELETE")) {
            deleteEmailCampaign();
        }else if (formAction.equals("ESTIMATE_PROSPECTS")) {
            estimateNumberOfProspects();
        }else if (formAction.equals("DISPLAY_PROSPECTS")) {
        	displaySentProspects();
        }
    }
    
 
	/**
     * Method displayCampaigns.
     */
    private void displayEmailCampaigns() {
        try {

            openConnection();

            EmailCampaignView emailCampaignView = new EmailCampaignView();
            emailCampaignView.fillWithElements(connection,
                                             EmailCampaignDetails.FILL_TYPE_ALL,
                                             new EmailCampaignDetails());
            try {
                if (emailCampaignView.getElements().size() > 0) {
                    request.setAttribute("emailCampaignView", emailCampaignView);
                    
                } else {
                    throw new ModelException("No email sales actions found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailCampaign/displayEmailCampaigns.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertCampaign.
     */
    private void insertEmailCampaign() {
        try {
            openConnection();

            // check if the campaign already exist- all values except the selection boxes.
            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
            getEmailCampaignInformation(emailCampaignDetails);
            emailCampaignDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
            
            EmailCampaignView emailCampaignView = new EmailCampaignView();
            emailCampaignView.fillWithElements(connection, EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);

            try {
                if (emailCampaignView.getElements().size() > 0) {
                    throw new ModelException("An email Campaign with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert campaign
                emailCampaignView.doAction(connection, "INSERT", EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);
                setLobTerritoryStatus(emailCampaignDetails.getEmailCampaignId());
                
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailCampaign/emailCampaignActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateCampaign.
     */
    private void updateEmailCampaign() {
        try {
            openConnection();

            // check if the campaign already exist
           
            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
            emailCampaignDetails.setEmailCampaignId(new BigDecimal(request.getParameter("dfEmailCampaignId")));        
            EmailCampaignView emailCampaignView = new EmailCampaignView();
            emailCampaignView.fillWithElements(connection, EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);
            try {
	            if(emailCampaignView.getElements().size() ==1){
	            	emailCampaignDetails = (EmailCampaignDetails)emailCampaignView.getElement(0);
	            	getEmailCampaignInformation(emailCampaignDetails);
            		emailCampaignView.doAction(connection, "UPDATE", EmailSalesActionDetails.FILL_TYPE_ALL, emailCampaignDetails);
            		setLobTerritoryStatus(emailCampaignDetails.getEmailCampaignId());
	            	
	            }else{
	            	throw new ModelException("Could not find email sales action.", ModelException.RECORD_NOT_FOUND);
	            }
                
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailCampaign/emailCampaignActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void setLobTerritoryStatus(BigDecimal emailCampaignId) throws Exception{
    	//first delete everything that is there
    	deleteEmailLobs(emailCampaignId);
    	deleteEmailTerritories(emailCampaignId);
    	deleteEmailStatuses(emailCampaignId);
    	//then add back based on what was selected
		setEmailLobs(emailCampaignId);
		setEmailTerritories(emailCampaignId);
		setEmailStatuses(emailCampaignId);
		
	}

	private void deleteEmailLobs(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionLobsDetails emailLobsDetails = new EmailSalesActionLobsDetails();
		EmailSalesActionLobsView emailLobsView = new EmailSalesActionLobsView();
		emailLobsDetails.setEmailCampaignId(emailCampaignId);
		emailLobsView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailLobsDetails);
		if (emailLobsView.getElements().size()>0){
			for(int i =0;i<emailLobsView.getElements().size();i++){
				emailLobsDetails = (EmailSalesActionLobsDetails)emailLobsView.getElement(i);
				emailLobsView.doAction(connection, "DELETE", EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
			}
		}
	}

	private void deleteEmailTerritories(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
		EmailSalesActionTerritoriesView emailTerritoriesView = new EmailSalesActionTerritoriesView();
		emailTerritoriesDetails.setEmailCampaignId(emailCampaignId);
		System.out.println("===about to delete territories,for campaigid= " + emailTerritoriesDetails.getEmailCampaignId());
		emailTerritoriesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
		if (emailTerritoriesView.getElements().size()>0){
			for(int i =0;i<emailTerritoriesView.getElements().size();i++){
				emailTerritoriesDetails = (EmailSalesActionTerritoriesDetails)emailTerritoriesView.getElement(i);
				emailTerritoriesView.doAction(connection, "DELETE", EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
			}
		}
	}
	
	private void deleteEmailStatuses(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails = new EmailSalesActionStatusesDetails();
		EmailSalesActionStatusesView emailStatusesView = new EmailSalesActionStatusesView();
		emailStatusesDetails.setEmailCampaignId(emailCampaignId);
		emailStatusesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailStatusesDetails);
		if (emailStatusesView.getElements().size()>0){
			for(int i =0;i<emailStatusesView.getElements().size();i++){
				emailStatusesDetails = (EmailSalesActionStatusesDetails)emailStatusesView.getElement(i);
				emailStatusesView.doAction(connection, "DELETE", EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
			}
		}
	}
	private void setEmailLobs(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionLobsDetails emailLobsDetails;
		EmailSalesActionLobsView emailLobsView ;
        String[] lobsSelected = request.getParameterValues("dfLobId");
        if(lobsSelected!= null){
	        for(int i = 0; i < lobsSelected.length; i++){
	        	emailLobsDetails = new EmailSalesActionLobsDetails();
	        	emailLobsView = new EmailSalesActionLobsView();
	        	emailLobsDetails.setLobId(new BigDecimal(lobsSelected[i]));
	        	emailLobsDetails.setEmailActionId(new BigDecimal(0));
	        	emailLobsDetails.setEmailCampaignId(emailCampaignId);
	        	emailLobsView.doAction(connection, "INSERT", EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
	        }
        }
	}

	private void setEmailTerritories(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails;
		EmailSalesActionTerritoriesView emailTerritoriesView ;
        String[] territoriesSelected = request.getParameterValues("dfTerritoryId");
        if(territoriesSelected!= null){
	        for(int i = 0; i < territoriesSelected.length; i++){
	        	emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
	        	emailTerritoriesView = new EmailSalesActionTerritoriesView();
	        	emailTerritoriesDetails.setTerritoryId(new BigDecimal(territoriesSelected[i]));
	        	emailTerritoriesDetails.setEmailActionId(new BigDecimal(0));
	        	emailTerritoriesDetails.setEmailCampaignId(emailCampaignId);
	        	emailTerritoriesView.doAction(connection, "INSERT", EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
	        }
        }
	}
	
	private void setEmailStatuses(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails;
		EmailSalesActionStatusesView emailStatusesView ;
        String[] statusesSelected = request.getParameterValues("dfStatusId");
        if(statusesSelected!= null){
	        for(int i = 0; i < statusesSelected.length; i++){
	        	emailStatusesDetails = new EmailSalesActionStatusesDetails();
	        	emailStatusesView = new EmailSalesActionStatusesView();
	        	emailStatusesDetails.setStatusId(new BigDecimal(statusesSelected[i]));
	        	emailStatusesDetails.setEmailActionId(new BigDecimal(0));
	        	emailStatusesDetails.setEmailCampaignId(emailCampaignId);
	        	emailStatusesView.doAction(connection, "INSERT", EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
	        }
        }
	}
	/**
     * @param emailCampaignDetails
     */
    private void getEmailCampaignInformation(EmailCampaignDetails emailCampaignDetails) {
    	System.out.println("cmbCampaign = " + request.getParameter("cmbCampaign"));
    	emailCampaignDetails.setCampaignId(new BigDecimal(request.getParameter("cmbCampaign")));
    	emailCampaignDetails.setEmailCampaignDescription(request.getParameter("dfEmailCampaignDescription"));
        emailCampaignDetails.setVerifiedYesNo(new BigDecimal(request.getParameter("dfVerifiedYesNo")));
        emailCampaignDetails.setUseSalesmanFromEmail(new BigDecimal(request.getParameter("dfUseSalesmanFromEmail")));
        emailCampaignDetails.setUseCampaignStartDate(new BigDecimal(request.getParameter("dfUseCampaignStartDate")));
        emailCampaignDetails.setStartEmailDate(DateUtilities.getSqlTimestamp(request.getParameter("dfStartEmailDate"), "00", "00",""));
        emailCampaignDetails.setEndEmailDate(DateUtilities.getSqlTimestamp(request.getParameter("dfEndEmailDate"), "00", "00",""));
		
	}

	/**
     * Method displayUpdateCampaign.
     */
    private void displayUpdateEmailCampaign() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the salesAction already exist
            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
            emailCampaignDetails.setEmailCampaignId(new BigDecimal(request.getParameter("dfEmailCampaignId")));

            EmailCampaignView emailCampaignView = new EmailCampaignView();
            emailCampaignView.fillWithElements(connection, EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);

            try {
                if (emailCampaignView.getElements().size() ==1) {
                    emailCampaignDetails = (EmailCampaignDetails) emailCampaignView.getElements().elementAt(0);
                    request.setAttribute("emailCampaignDetails", emailCampaignDetails);
                    EmailSalesActionTerritoriesView emailTerritoriesView = getEmailTerritoriesView(emailCampaignDetails.getEmailCampaignId());
                    EmailSalesActionStatusesView emailStatusesView = getEmailStatusesView(emailCampaignDetails.getEmailCampaignId());
                    EmailSalesActionLobsView emailLobsView = getEmailLobsView(emailCampaignDetails.getEmailCampaignId());
                    request.setAttribute("emailTerritoriesView", emailTerritoriesView);
                    request.setAttribute("emailStatusesView", emailStatusesView);
                    request.setAttribute("emailLobsView", emailLobsView);
                    getLobTerritoryStatus();
                    CampaignView campaignView = new CampaignView();
                    campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, new CampaignDetails());
                    request.setAttribute("campaignView", campaignView);
                } else {
                    throw new ModelException("Email Campaign can not be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailCampaign/displayInsertUpdateEmailCampaign.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void getLobTerritoryStatus() throws Exception{
    	 TerritoryView territoryView = new TerritoryView();
         TerritoryDetails territoryDetails = new TerritoryDetails();
         territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
         request.setAttribute("territoryView", territoryView);

         // line of business
         LobView lobView = new LobView();
         LobDetails lobDetails = new LobDetails();
         lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);
         request.setAttribute("lobView", lobView);
         
         // status
         StatusView statusView = new StatusView();
         StatusDetails statusDetails = new StatusDetails();
         statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);
         request.setAttribute("statusView", statusView);
	}

	/**
     * Method displayInsertCampaign.
     */
    private void displayInsertEmailCampaign() {
        try {
        	openConnection();
        	
            request.setAttribute("displayInsert", new Boolean(true));
            EmailSalesActionTerritoriesView emailTerritoriesView = getEmailTerritoriesView(new BigDecimal(0));
            EmailSalesActionStatusesView emailStatusesView = getEmailStatusesView(new BigDecimal(0));
            EmailSalesActionLobsView emailLobsView = getEmailLobsView(new BigDecimal(0));
            
            request.setAttribute("emailTerritoriesView", emailTerritoriesView);
            request.setAttribute("emailStatusesView", emailStatusesView);
            request.setAttribute("emailLobsView", emailLobsView);
            getLobTerritoryStatus();
            CampaignView campaignView = new CampaignView();
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, new CampaignDetails());
            request.setAttribute("campaignView", campaignView);
            
            commit();
            // forward request
           forward("/jsp/admin/emailCampaign/displayInsertUpdateEmailCampaign.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private EmailSalesActionLobsView getEmailLobsView(BigDecimal emailCampaignId) throws Exception{
    	EmailSalesActionLobsDetails emailLobsDetails = new EmailSalesActionLobsDetails();
		EmailSalesActionLobsView emailLobsView = new EmailSalesActionLobsView();
		if(emailCampaignId.compareTo(new BigDecimal(0))!=0){
			emailLobsDetails.setEmailCampaignId(emailCampaignId);
		}
		emailLobsView.fillWithElements(connection, EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
		return emailLobsView;
	}

	private EmailSalesActionStatusesView getEmailStatusesView(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails = new EmailSalesActionStatusesDetails();
		EmailSalesActionStatusesView emailStatusesView = new EmailSalesActionStatusesView();
		if(emailCampaignId.compareTo(new BigDecimal(0))!=0){
			emailStatusesDetails.setEmailCampaignId(emailCampaignId);
		}
		emailStatusesView.fillWithElements(connection, EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
		return emailStatusesView;
	}

	private EmailSalesActionTerritoriesView getEmailTerritoriesView(BigDecimal emailCampaignId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
		EmailSalesActionTerritoriesView emailTerritoriesView = new EmailSalesActionTerritoriesView();
		if(emailCampaignId.compareTo(new BigDecimal(0))!=0){
			emailTerritoriesDetails.setEmailCampaignId(emailCampaignId);
		}
		emailTerritoriesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
		return emailTerritoriesView;
	}

	   private void estimateNumberOfProspects() {
		   try {
	            openConnection();
	            System.out.println("estimateNumberOfProspects, start");
	            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
	            emailCampaignDetails.setEmailCampaignId(new BigDecimal(request.getParameter("dfEmailCampaignId")));

	            EmailCampaignView emailCampaignView = new EmailCampaignView();
	            emailCampaignView.fillWithElements(connection, EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);
	            System.out.println("estimateNumberOfProspects, id= " + emailCampaignDetails.getEmailCampaignId());
	            if (emailCampaignView.getElements().size() ==1) {
	            	 System.out.println("estimateNumberOfProspects, size of emailCampaignView= " + emailCampaignView.getElements().size());
                    emailCampaignDetails = (EmailCampaignDetails) emailCampaignView.getElements().elementAt(0);
                    request.setAttribute("emailCampaignDetails", emailCampaignDetails);
	            }
	            
	            ProspectDetails prospectDetails = new ProspectDetails();
    			ProspectView prospectView = new ProspectView();
    			
    			if(emailCampaignDetails.getVerifiedYesNo().compareTo(EmailCampaignDetails.USE_VERIFED_YES_ONLY)==0){
    				prospectDetails.setVerifiedId(new BigDecimal(AppSettings.getParm("VERIFIED_VALUE_FOR_EMAIL_SALES_ACTION")));
    			}
    			prospectDetails.setLobIdList(getLobIdList(emailCampaignDetails.getEmailCampaignId()));
    			prospectDetails.setTerritoryIdList(getTerritoryIdList(emailCampaignDetails.getEmailCampaignId()));
    			prospectDetails.setStatusIdList(getStatusIdList(emailCampaignDetails.getEmailCampaignId()));
    			if(emailCampaignDetails.getUseCampaignStartDate().compareTo(EmailCampaignDetails.YES_USE_CAMPAIGN_START_DATE)==0){
    				prospectDetails.setCreationDate(emailCampaignDetails.getStartEmailDate());//limit by prospects by creation date
		   		}	
    			prospectDetails.setExcludeNIAndDNC(true);
//    			System.out.println("lobIdList= " + prospectDetails.getLobIdList() + " terIdList= " + prospectDetails.getTerritoryIdList() + 
//    					" Statusidlist= " + prospectDetails.getStatusIdList());
    			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
    			request.setAttribute("prospectView", prospectView);
    			 System.out.println("estimateNumberOfProspects, size of prospectView= " + prospectView.getElements().size());
      
	            commit();
	            // forward request
	            forward("/jsp/admin/emailCampaign/emailCampaignEstimate.jsp");
	        } catch (Exception e) {
	            doCatch(e);
	        } finally {
	            finallyMethod();
	        }
			
		}
	   
	   private void displaySentProspects() {
		   try {
	            openConnection();
	            System.out.println("displaySentProspects, start");
	            //do we add display here based on campaign exists in prospect campaign table?
	            
//	            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
//	            emailCampaignDetails.setEmailCampaignId(new BigDecimal(request.getParameter("dfEmailCampaignId")));
//
//	            EmailCampaignView emailCampaignView = new EmailCampaignView();
//	            emailCampaignView.fillWithElements(connection, EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);
//	            System.out.println("displaySentProspects, id= " + emailCampaignDetails.getEmailCampaignId());
//	            if (emailCampaignView.getElements().size() ==1) {
//	            	 System.out.println("displaySentProspects, size of emailCampaignView= " + emailCampaignView.getElements().size());
//                    emailCampaignDetails = (EmailCampaignDetails) emailCampaignView.getElements().elementAt(0);
//                    request.setAttribute("emailCampaignDetails", emailCampaignDetails);
//	            }
//
//	            EmailCampaignSentProspectsDetails emailSentProspectsDetails = new EmailCampaignSentProspectsDetails();
//	            EmailCampaignSentProspectsView emailSentProspectsView = new EmailCampaignSentProspectsView();
//	            emailSentProspectsDetails.setEmailCampaignId(emailCampaignDetails.getEmailCampaignId());
//	            emailSentProspectsView.fillWithElements(connection, EmailCampaignSentProspectsView.FILL_TYPE_ALL, emailSentProspectsDetails);
//	            
//	            ProspectView displayProspectView = new ProspectView();
//	            
//	            System.out.println("displaySentProspects, size of emailsentProspectView= " + emailSentProspectsView.getElements().size());
//	            if(emailSentProspectsView.getElements().size()>0){
//	            	//for each prospect in the emailSentProspectsView, get the prospect detail info for display purposes
//	            	for(int i=0; i<emailSentProspectsView.getElements().size();i++){
//	            		emailSentProspectsDetails = (EmailCampaignSentProspectsDetails)emailSentProspectsView.getElement(i);
//	            		ProspectDetails prospectDetails = new ProspectDetails();
//	            		ProspectView prospectView = new ProspectView();
//	            		prospectDetails.setProspectId(emailSentProspectsDetails.getProspectId());
//	            		prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
//	            		if(prospectView.getElements().size()>0){
//	            			 System.out.println("displaySentProspects, do we get here?");
//	            			prospectDetails = (ProspectDetails)prospectView.getElement(0);
//	            			displayProspectView.addElement(prospectDetails);
//	            		}
//	            	}
//	            }
//
//    			request.setAttribute("prospectView", displayProspectView);
//    			 System.out.println("estimateNumberOfProspects, size of prospectView= " + displayProspectView.getElements().size());
//      
	            commit();
	            // forward request
	            forward("/jsp/admin/emailCampaign/emailCampaignSentProspects.jsp");
	        } catch (Exception e) {
	            doCatch(e);
	        } finally {
	            finallyMethod();
	        }
			
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


	/**
     * Method deleteCampaign.
     */
    private void deleteEmailCampaign() {
        try {
            openConnection();

            // check if Campaign exist
            EmailCampaignDetails emailCampaignDetails = new EmailCampaignDetails();
            emailCampaignDetails.setEmailCampaignId(new BigDecimal(request.getParameter("dfEmailCampaignId")));

            EmailCampaignView emailCampaignView = new EmailCampaignView();
            emailCampaignView.fillWithElements(connection, EmailCampaignDetails.FILL_TYPE_ALL, emailCampaignDetails);

            try {
                if (emailCampaignView.getElements().size() == 0) {
                    throw new ModelException("The email sales action you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete campaign

               
                
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailCampaign/emailCampaignActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
