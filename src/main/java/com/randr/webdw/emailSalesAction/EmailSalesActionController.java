package com.randr.webdw.emailSalesAction;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.randr.webdw.AppSettings;
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
public class EmailSalesActionController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
    	//System.out.println("formAction= " + formAction);
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayEmailSalesActions();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertEmailSalesAction();
        } else if (formAction.equals("INSERT")) {
            insertEmailSalesAction();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateEmailSalesAction();
        } else if (formAction.equals("UPDATE")) {
            updateEmailSalesAction();
        } else if (formAction.equals("DELETE")) {
            deleteEmailSalesAction();
        }else if (formAction.equals("ESTIMATE_PROSPECTS")) {
            estimateNumberOfProspects();
        }else if (formAction.equals("DISPLAY_PROSPECTS")) {
        	displaySentProspects();
        }
    }
    
 
	/**
     * Method displaySalesActions.
     */
    private void displayEmailSalesActions() {
        try {

            openConnection();

            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
            emailSalesActionView.fillWithElements(connection,
                                             EmailSalesActionDetails.FILL_TYPE_ALL,
                                             new EmailSalesActionDetails());
            try {
                if (emailSalesActionView.getElements().size() > 0) {
                    request.setAttribute("emailSalesActionView", emailSalesActionView);
                    
                } else {
                    throw new ModelException("No email sales actions found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailSalesAction/displayEmailSalesActions.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertSalesAction.
     */
    private void insertEmailSalesAction() {
        try {
            openConnection();

            // check if the salesAction already exist- all values except the selection boxes.
            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
            getEmailSalesActionInformation(emailSalesActionDetails);
            emailSalesActionDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
            
            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
            emailSalesActionView.fillWithElements(connection, EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);

            try {
                if (emailSalesActionView.getElements().size() > 0) {
                    throw new ModelException("An email sales action with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert salesAction
                emailSalesActionView.doAction(connection, "INSERT", EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);
                setLobTerritoryStatus(emailSalesActionDetails.getEmailActionId());
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailSalesAction/emailSalesActionActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateSalesAction.
     */
    private void updateEmailSalesAction() {
        try {
            openConnection();

            // check if the salesAction already exist
           
            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
            emailSalesActionDetails.setEmailActionId(new BigDecimal(request.getParameter("dfEmailSalesActionId")));        
            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
            emailSalesActionView.fillWithElements(connection, EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);
            try {
	            if(emailSalesActionView.getElements().size() ==1){
	            	emailSalesActionDetails = (EmailSalesActionDetails)emailSalesActionView.getElement(0);
	            	if(emailSalesActionDetails.getActualEmailDate()==null){//dont change if email is already sent
	            		getEmailSalesActionInformation(emailSalesActionDetails);
	            		emailSalesActionView.doAction(connection, "UPDATE", EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);
	            		setLobTerritoryStatus(emailSalesActionDetails.getEmailActionId());
	            	}else{
	            		throw new ModelException("Can not update, email already sent.", ModelException.EMAIL_ALREADY_SENT);
	            	}
	            }else{
	            	throw new ModelException("Could not find email sales action.", ModelException.RECORD_NOT_FOUND);
	            }
                
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailSalesAction/emailSalesActionActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void setLobTerritoryStatus(BigDecimal emailActionId) throws Exception{
    	//first delete everything that is there
    	deleteEmailLobs(emailActionId);
    	deleteEmailTerritories(emailActionId);
    	deleteEmailStatuses(emailActionId);
    	//then add back based on what was selected
		setEmailLobs(emailActionId);
		setEmailTerritories(emailActionId);
		setEmailStatuses(emailActionId);
		
	}

	private void deleteEmailLobs(BigDecimal emailActionId) throws Exception{
		EmailSalesActionLobsDetails emailLobsDetails = new EmailSalesActionLobsDetails();
		EmailSalesActionLobsView emailLobsView = new EmailSalesActionLobsView();
		emailLobsDetails.setEmailCampaignId(emailActionId);
		emailLobsView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailLobsDetails);
		if (emailLobsView.getElements().size()>0){
			for(int i =0;i<emailLobsView.getElements().size();i++){
				emailLobsDetails = (EmailSalesActionLobsDetails)emailLobsView.getElement(i);
				emailLobsView.doAction(connection, "DELETE", EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
			}
		}
	}

	private void deleteEmailTerritories(BigDecimal emailActionId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
		EmailSalesActionTerritoriesView emailTerritoriesView = new EmailSalesActionTerritoriesView();
		emailTerritoriesDetails.setEmailCampaignId(emailActionId);
		System.out.println("===about to delete territories,for campaigid= " + emailTerritoriesDetails.getEmailCampaignId());
		emailTerritoriesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
		if (emailTerritoriesView.getElements().size()>0){
			for(int i =0;i<emailTerritoriesView.getElements().size();i++){
				emailTerritoriesDetails = (EmailSalesActionTerritoriesDetails)emailTerritoriesView.getElement(i);
				emailTerritoriesView.doAction(connection, "DELETE", EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
			}
		}
	}
	
	private void deleteEmailStatuses(BigDecimal emailActionId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails = new EmailSalesActionStatusesDetails();
		EmailSalesActionStatusesView emailStatusesView = new EmailSalesActionStatusesView();
		emailStatusesDetails.setEmailCampaignId(emailActionId);
		emailStatusesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailStatusesDetails);
		if (emailStatusesView.getElements().size()>0){
			for(int i =0;i<emailStatusesView.getElements().size();i++){
				emailStatusesDetails = (EmailSalesActionStatusesDetails)emailStatusesView.getElement(i);
				emailStatusesView.doAction(connection, "DELETE", EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
			}
		}
	}
	private void setEmailLobs(BigDecimal emailActionId) throws Exception{
		EmailSalesActionLobsDetails emailLobsDetails;
		EmailSalesActionLobsView emailLobsView ;
        String[] lobsSelected = request.getParameterValues("dfLobId");
        if(lobsSelected!= null){
	        for(int i = 0; i < lobsSelected.length; i++){
	        	emailLobsDetails = new EmailSalesActionLobsDetails();
	        	emailLobsView = new EmailSalesActionLobsView();
	        	emailLobsDetails.setLobId(new BigDecimal(lobsSelected[i]));
	        	emailLobsDetails.setEmailActionId(emailActionId);
	        	emailLobsView.doAction(connection, "INSERT", EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
	        }
        }
	}

	private void setEmailTerritories(BigDecimal emailActionId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails;
		EmailSalesActionTerritoriesView emailTerritoriesView ;
        String[] TerritoriesSelected = request.getParameterValues("dfTerritoryId");
        if(TerritoriesSelected!= null){
	        for(int i = 0; i < TerritoriesSelected.length; i++){
	        	emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
	        	emailTerritoriesView = new EmailSalesActionTerritoriesView();
	        	emailTerritoriesDetails.setTerritoryId(new BigDecimal(TerritoriesSelected[i]));
	        	emailTerritoriesDetails.setEmailActionId(emailActionId);
	        	emailTerritoriesView.doAction(connection, "INSERT", EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
	        }
        }
	}
	
	private void setEmailStatuses(BigDecimal emailActionId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails;
		EmailSalesActionStatusesView emailStatusesView ;
        String[] StatusesSelected = request.getParameterValues("dfStatusId");
        if(StatusesSelected!= null){
	        for(int i = 0; i < StatusesSelected.length; i++){
	        	emailStatusesDetails = new EmailSalesActionStatusesDetails();
	        	emailStatusesView = new EmailSalesActionStatusesView();
	        	emailStatusesDetails.setStatusId(new BigDecimal(StatusesSelected[i]));
	        	emailStatusesDetails.setEmailActionId(emailActionId);
	        	emailStatusesView.doAction(connection, "INSERT", EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
	        }
        }
	}
	/**
     * @param emailSalesActionDetails
     */
    private void getEmailSalesActionInformation(EmailSalesActionDetails emailSalesActionDetails) {
    	emailSalesActionDetails.setEmailActionDescription(request.getParameter("dfEmailSalesActionDescription"));
        emailSalesActionDetails.setEmailDraftToUse(new BigDecimal(request.getParameter("dfEmailDraftToUse")));
        emailSalesActionDetails.setVerifiedYesNo(new BigDecimal(request.getParameter("dfVerifiedYesNo")));
        emailSalesActionDetails.setUseSalesmanFromEmail(new BigDecimal(request.getParameter("dfUseSalesmanFromEmail")));
        emailSalesActionDetails.setSendEmailDate(DateUtilities.getSqlTimestamp(request.getParameter("dfSendEmailDate"), "00", "00",""));
		
	}

	/**
     * Method displayUpdateSalesAction.
     */
    private void displayUpdateEmailSalesAction() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            openConnection();

            // check if the salesAction already exist
            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
            emailSalesActionDetails.setEmailActionId(new BigDecimal(request.getParameter("dfEmailSalesActionId")));

            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
            emailSalesActionView.fillWithElements(connection, EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);

            try {
                if (emailSalesActionView.getElements().size() ==1) {
                    emailSalesActionDetails = (EmailSalesActionDetails) emailSalesActionView.getElements().elementAt(0);
                    request.setAttribute("emailSalesActionDetails", emailSalesActionDetails);
                    EmailSalesActionTerritoriesView emailTerritoriesView = getEmailTerritoriesView(emailSalesActionDetails.getEmailActionId());
                    EmailSalesActionStatusesView emailStatusesView = getEmailStatusesView(emailSalesActionDetails.getEmailActionId());
                    EmailSalesActionLobsView emailLobsView = getEmailLobsView(emailSalesActionDetails.getEmailActionId());
                    request.setAttribute("emailTerritoriesView", emailTerritoriesView);
                    request.setAttribute("emailStatusesView", emailStatusesView);
                    request.setAttribute("emailLobsView", emailLobsView);
                    getLobTerritoryStatus();
                } else {
                    throw new ModelException("Email Sales Action can not be found", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailSalesAction/displayInsertUpdateEmailSalesAction.jsp");
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
     * Method displayInsertSalesAction.
     */
    private void displayInsertEmailSalesAction() {
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
            
            commit();
            // forward request
           forward("/jsp/admin/emailSalesAction/displayInsertUpdateEmailSalesAction.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private EmailSalesActionLobsView getEmailLobsView(BigDecimal emailActionId) throws Exception{
    	EmailSalesActionLobsDetails emailLobsDetails = new EmailSalesActionLobsDetails();
		EmailSalesActionLobsView emailLobsView = new EmailSalesActionLobsView();
		if(emailActionId.compareTo(new BigDecimal(0))!=0){
			emailLobsDetails.setEmailActionId(emailActionId);
		}
		emailLobsView.fillWithElements(connection, EmailSalesActionLobsView.FILL_TYPE_ALL, emailLobsDetails);
		return emailLobsView;
	}

	private EmailSalesActionStatusesView getEmailStatusesView(BigDecimal emailActionId) throws Exception{
		EmailSalesActionStatusesDetails emailStatusesDetails = new EmailSalesActionStatusesDetails();
		EmailSalesActionStatusesView emailStatusesView = new EmailSalesActionStatusesView();
		if(emailActionId.compareTo(new BigDecimal(0))!=0){
			emailStatusesDetails.setEmailActionId(emailActionId);
		}
		emailStatusesView.fillWithElements(connection, EmailSalesActionStatusesView.FILL_TYPE_ALL, emailStatusesDetails);
		return emailStatusesView;
	}

	private EmailSalesActionTerritoriesView getEmailTerritoriesView(BigDecimal emailActionId) throws Exception{
		EmailSalesActionTerritoriesDetails emailTerritoriesDetails = new EmailSalesActionTerritoriesDetails();
		EmailSalesActionTerritoriesView emailTerritoriesView = new EmailSalesActionTerritoriesView();
		if(emailActionId.compareTo(new BigDecimal(0))!=0){
			emailTerritoriesDetails.setEmailActionId(emailActionId);
		}
		emailTerritoriesView.fillWithElements(connection, EmailSalesActionTerritoriesView.FILL_TYPE_ALL, emailTerritoriesDetails);
		return emailTerritoriesView;
	}

	   private void estimateNumberOfProspects() {
		   try {
	            openConnection();
	            //System.out.println("estimateNumberOfProspects, start");
	            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
	            emailSalesActionDetails.setEmailActionId(new BigDecimal(request.getParameter("dfEmailSalesActionId")));

	            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
	            emailSalesActionView.fillWithElements(connection, EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);
	            //System.out.println("estimateNumberOfProspects, id= " + emailSalesActionDetails.getEmailActionId());
	            if (emailSalesActionView.getElements().size() ==1) {
	            	 //System.out.println("estimateNumberOfProspects, size of emailActionView= " + emailSalesActionView.getElements().size());
                    emailSalesActionDetails = (EmailSalesActionDetails) emailSalesActionView.getElements().elementAt(0);
                    request.setAttribute("emailSalesActionDetails", emailSalesActionDetails);
	            }
	            
	            ProspectDetails prospectDetails = new ProspectDetails();
    			ProspectView prospectView = new ProspectView();
    			
    			if(emailSalesActionDetails.getVerifiedYesNo().compareTo(EmailSalesActionDetails.USE_VERIFED_YES_ONLY)==0){
    				prospectDetails.setVerifiedId(new BigDecimal(AppSettings.getParm("VERIFIED_VALUE_FOR_EMAIL_SALES_ACTION")));
    			}
    			prospectDetails.setLobIdList(getLobIdList(emailSalesActionDetails.getEmailActionId()));
    			prospectDetails.setTerritoryIdList(getTerritoryIdList(emailSalesActionDetails.getEmailActionId()));
    			prospectDetails.setStatusIdList(getStatusIdList(emailSalesActionDetails.getEmailActionId()));
    			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
    			request.setAttribute("prospectView", prospectView);
    			 //System.out.println("estimateNumberOfProspects, size of prospectView= " + prospectView.getElements().size());
      
	            commit();
	            // forward request
	            forward("/jsp/admin/emailSalesAction/emailSalesActionEstimate.jsp");
	        } catch (Exception e) {
	            doCatch(e);
	        } finally {
	            finallyMethod();
	        }
			
		}
	   
	   private void displaySentProspects() {
		   try {
	            openConnection();
	            //System.out.println("displaySentProspects, start");
	            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
	            emailSalesActionDetails.setEmailActionId(new BigDecimal(request.getParameter("dfEmailSalesActionId")));

	            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
	            emailSalesActionView.fillWithElements(connection, EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);
	            //System.out.println("displaySentProspects, id= " + emailSalesActionDetails.getEmailActionId());
	            if (emailSalesActionView.getElements().size() ==1) {
	            	 //System.out.println("displaySentProspects, size of emailActionView= " + emailSalesActionView.getElements().size());
                    emailSalesActionDetails = (EmailSalesActionDetails) emailSalesActionView.getElements().elementAt(0);
                    request.setAttribute("emailSalesActionDetails", emailSalesActionDetails);
	            }

	            EmailSalesActionSentProspectsDetails emailSentProspectsDetails = new EmailSalesActionSentProspectsDetails();
	            EmailSalesActionSentProspectsView emailSentProspectsView = new EmailSalesActionSentProspectsView();
	            emailSentProspectsDetails.setEmailActionId(emailSalesActionDetails.getEmailActionId());
	            emailSentProspectsView.fillWithElements(connection, EmailSalesActionSentProspectsView.FILL_TYPE_ALL, emailSentProspectsDetails);
	            
	            ProspectView displayProspectView = new ProspectView();
	            
	            //System.out.println("displaySentProspects, size of emailsentProspectView= " + emailSentProspectsView.getElements().size());
	            if(emailSentProspectsView.getElements().size()>0){
	            	//for each prospect in the emailSentProspectsView, get the prospect detail info for display purposes
	            	for(int i=0; i<emailSentProspectsView.getElements().size();i++){
	            		emailSentProspectsDetails = (EmailSalesActionSentProspectsDetails)emailSentProspectsView.getElement(i);
	            		ProspectDetails prospectDetails = new ProspectDetails();
	            		ProspectView prospectView = new ProspectView();
	            		prospectDetails.setProspectId(emailSentProspectsDetails.getProspectId());
	            		prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
	            		if(prospectView.getElements().size()>0){
	            			 //System.out.println("displaySentProspects, do we get here?");
	            			prospectDetails = (ProspectDetails)prospectView.getElement(0);
	            			displayProspectView.addElement(prospectDetails);
	            		}
	            	}
	            }

    			request.setAttribute("prospectView", displayProspectView);
    			 //System.out.println("estimateNumberOfProspects, size of prospectView= " + displayProspectView.getElements().size());
      
	            commit();
	            // forward request
	            forward("/jsp/admin/emailSalesAction/emailSalesActionSentProspects.jsp");
	        } catch (Exception e) {
	            doCatch(e);
	        } finally {
	            finallyMethod();
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


	/**
     * Method deleteSalesAction.
     */
    private void deleteEmailSalesAction() {
        try {
            openConnection();

            // check if salesAction exist
            EmailSalesActionDetails emailSalesActionDetails = new EmailSalesActionDetails();
            emailSalesActionDetails.setEmailActionId(new BigDecimal(request.getParameter("dfEmailSalesActionId")));

            EmailSalesActionView emailSalesActionView = new EmailSalesActionView();
            emailSalesActionView.fillWithElements(connection, EmailSalesActionDetails.FILL_TYPE_ALL, emailSalesActionDetails);

            try {
                if (emailSalesActionView.getElements().size() == 0) {
                    throw new ModelException("The email sales action you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete salesAction

                try {
                	if(emailSalesActionDetails.getActualEmailDate()==null){//dont change if email is already sent
                		emailSalesActionView.doAction(connection, "DELETE", null, emailSalesActionDetails);
                	}else{
                		throw new ModelException("Can not delete email sales action, email is already sent.", ModelException.EMAIL_ALREADY_SENT);
                	}
                } catch (SQLException sqlE) {
                    throw new ModelException("The Email Sales Action cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            forward("/jsp/admin/emailSalesAction/emailSalesActionActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
