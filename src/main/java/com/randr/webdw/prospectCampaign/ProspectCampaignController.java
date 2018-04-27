package com.randr.webdw.prospectCampaign;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.servlet.ServletException;

import com.randr.webdw.campaign.CampaignDetails;
import com.randr.webdw.campaign.CampaignView;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;

/**
 * @author randr
 * @version $Revision: 1.6 $
 */
public class ProspectCampaignController extends AbstractController {
    /**
     * Constructor for ContactController.
     */
    public ProspectCampaignController() {
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("INSERT"))) {
            insertProspectCampaign();
        } else if (formAction.equals("INSERT_COLLECTION")) {
            insertProspectCampaignCollection();    
        } else if (formAction.equals("UPDATE")) {
            updateProspectCampaign();
        } else if (formAction.equals("DELETE")) {
            deleteProspectCampaign();
        } 
    }

    /**
     * Method insertProspectCampaign.
     */
    private void insertProspectCampaign() {
        try {
        	openConnection();
        	CampaignDetails campaignDetails 
        		= getCampaignDetails(new BigDecimal(request.getParameter("campaignId")));
        	CampaignSalesActionView campaignSalesActionView 
        		= getCampaignSalesActionView(new BigDecimal(request.getParameter("campaignId")));
        	
        	// insert ProspectCampaign
        	ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
        	prospectCampaignDetails.setCampaignId(campaignDetails.getCampaignId());
        	prospectCampaignDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
        	prospectCampaignDetails.setCreationDate(new Timestamp(System.currentTimeMillis()));
        	ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
        	prospectCampaignView.doAction(connection, "INSERT", new BigDecimal(1), prospectCampaignDetails);
        	prospectCampaignDetails = (ProspectCampaignDetails)prospectCampaignView.getCurrent();
        	
        	// insert ProspectSalesActions
        	ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
        	for(int i = 0; i < campaignSalesActionView.getElements().size(); i++) {
        		CampaignSalesActionDetails campaignSalesActionDetails 
        			= (CampaignSalesActionDetails)campaignSalesActionView.getElement(i);
        		ProspectSalesActionDetails prospectSalesActionDetails
        			= getProspectSalesActionDetails(prospectCampaignDetails, campaignSalesActionDetails, prospectCampaignDetails.getProspectId());
        		prospectSalesActionView.doAction(connection, "INSERT", new BigDecimal(1), prospectSalesActionDetails);
        	}
        	
            //also, change the prospect Update Date and User
            ProspectDetails prospectUpdateDetails = new ProspectDetails();
            ProspectView prospectUpdateView = new ProspectView();
            prospectUpdateDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            if(prospectUpdateView.getElements().size()>0){
            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            }
        	
            commit();

            request.getRequestDispatcher("/jsp/public/prospectCampaign/insertUpdateProspectCampaign.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertProspectCampaignCollection.
     * for every prospect in the collection, insert a campaign
     */
    private void insertProspectCampaignCollection() {
        try {
        	ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
        	openConnection();
        	CampaignDetails campaignDetails 
        		= getCampaignDetails(new BigDecimal(request.getParameter("campaignId")));
        	CampaignSalesActionView campaignSalesActionView 
        		= getCampaignSalesActionView(new BigDecimal(request.getParameter("campaignId")));
        	//for every prospect in the collection, insert a campaign
        	ProspectDetails prospectDetails;
        	for(int i = 0; i < sessionProspectView.getElements().size(); i++) {
        		prospectDetails = (ProspectDetails)sessionProspectView.getElement(i);
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
	        	for(int j = 0; j < campaignSalesActionView.getElements().size(); j++) {
	        		CampaignSalesActionDetails campaignSalesActionDetails 
	        			= (CampaignSalesActionDetails)campaignSalesActionView.getElement(j);
	        		ProspectSalesActionDetails prospectSalesActionDetails
	        			= getProspectSalesActionDetails(prospectCampaignDetails, campaignSalesActionDetails, prospectDetails.getProspectId());
	        		prospectSalesActionView.doAction(connection, "INSERT", new BigDecimal(1), prospectSalesActionDetails);
	        	}
	        	
	            //also, change the prospect Update Date and User
	        	ProspectDetails prospectUpdateDetails = new ProspectDetails();
	            ProspectView prospectUpdateView = new ProspectView();
	            prospectUpdateDetails.setProspectId(prospectDetails.getProspectId());
	            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
	            if(prospectUpdateView.getElements().size()>0){
	            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
	            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
	            	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
	            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
	            }
        	}
            commit();
            request.getSession().setAttribute("prospectView", sessionProspectView);
            forward("/jsp/public/prospectCampaign/prospectCampaignCollectionActionResult.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
    /**
     * Method updateProspectCampaign.
     */
    private void updateProspectCampaign() {
        try {
            openConnection();

            ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
            ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
            prospectCampaignDetails.setProspectCampaignId(new BigDecimal(request.getParameter("dfProspectCampaignId")));
            prospectCampaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, prospectCampaignDetails);
            prospectCampaignDetails = (ProspectCampaignDetails)prospectCampaignView.getElement(0);
            
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();        
            prospectSalesActionDetails.setProspectCampaignId(new BigDecimal(request.getParameter("dfProspectCampaignId")));
            prospectSalesActionView.fillWithElements(connection, 
            		ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED, prospectSalesActionDetails);
            
            ProspectSalesActionView updateProspectSalesActionView = new ProspectSalesActionView();
            
        	for(int i = 0; i < prospectSalesActionView.getElements().size(); i++) {
        		prospectSalesActionDetails 
        			= (ProspectSalesActionDetails)prospectSalesActionView.getElement(i);
        		
        		int position = prospectSalesActionDetails.getProspectCampaignSequence().intValue();
        		
        		
                if (Utilities.nullToBlank(request.getParameter("cmbActionStatus_" + position)).equals("Completed")) {
                    prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_COMPLETE);
                    // when action is set to completed, set notification flag to 1 so no email goes out.
                    prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(1));
                } else {
                	prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
                }

                if (!Utilities.nullToBlank(request.getParameter("dfActionNote_" + position)).equals("")) {
                    prospectSalesActionDetails.setActionNote((request.getParameter("dfActionNote_" + position)));
                } else {
                    prospectSalesActionDetails.setActionNote(null);
                }
                
                if (!Utilities.nullToBlank(request.getParameter("dfHour_" + position)).equals("") &&
                	!Utilities.nullToBlank(request.getParameter("time_" + position)).equals("") &&
                	!Utilities.nullToBlank(request.getParameter("dfSalesActionDate_" + position)).equals("")) {
                	Timestamp timestamp=null;
                	if(!Utilities.nullToBlank(request.getParameter("dfMinute_"  + position)).equals("")){
                		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate_"  + position),
                			request.getParameter("dfHour_" + position), request.getParameter("dfMinute_"  + position),
                			request.getParameter("time_" + position));
                	}else{
                		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate_" + position),
                			request.getParameter("dfHour_" + position), "00",
                			request.getParameter("time_" + position));
                	}
                    prospectSalesActionDetails.setActionDate(timestamp);
                }
                updateProspectSalesActionView.doAction(connection, "UPDATE", new BigDecimal(1), prospectSalesActionDetails);
        	}
        	
            //also, change the prospect Update Date and User
            ProspectDetails prospectUpdateDetails = new ProspectDetails();
            ProspectView prospectUpdateView = new ProspectView();
            prospectUpdateDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            if(prospectUpdateView.getElements().size()>0){
            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            }

            commit();
            request.getRequestDispatcher("/jsp/public/prospectCampaign/insertUpdateProspectCampaign.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteProspectCampaign.
     */
    private void deleteProspectCampaign() {
        try {
            openConnection();
            ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
            ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
            prospectCampaignDetails.setProspectCampaignId(new BigDecimal(request.getParameter("dfProspectCampaignId")));
            prospectCampaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, prospectCampaignDetails);
            prospectCampaignDetails = (ProspectCampaignDetails)prospectCampaignView.getElement(0);
            
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();        
            prospectSalesActionDetails.setProspectCampaignId(new BigDecimal(request.getParameter("dfProspectCampaignId")));
            prospectSalesActionView.fillWithElements(connection, 
            		ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED, prospectSalesActionDetails);
            
        	for(int i = 0; i < prospectSalesActionView.getElements().size(); i++) {
        		prospectSalesActionDetails 
        			= (ProspectSalesActionDetails)prospectSalesActionView.getElement(i);
        		
        		prospectSalesActionView.doAction(connection, "DELETE", new BigDecimal(1), prospectSalesActionDetails);
        	}
        	
        	prospectCampaignView.doAction(connection, "DELETE", new BigDecimal(1), prospectCampaignDetails);
        	
            commit();
            request.getRequestDispatcher("/jsp/public/prospectCampaign/insertUpdateProspectCampaign.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method getProspectCampaignInformation.
     * @param prospectSalesActionDetails ProspectCampaignDetails
     */
    private void getProspectCampaignInformation(ProspectCampaignDetails prospectSalesActionDetails) {


    }
    
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
    
    private CampaignSalesActionView getCampaignSalesActionView(BigDecimal campaignId) throws Exception{
    	CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
    	CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
    	campaignSalesActionDetails.setCampaignId(campaignId);
    	campaignSalesActionView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
    	return campaignSalesActionView;
    }
    
    private ProspectSalesActionDetails getProspectSalesActionDetails(ProspectCampaignDetails campaignDetails, 
    		CampaignSalesActionDetails campaignSalesActionDetails, BigDecimal prospectId) throws Exception {
    	
    	ProspectSalesActionDetails prospectSalesActionDetails 
    		= new ProspectSalesActionDetails();
    	
    	prospectSalesActionDetails.setProspectId(prospectId);
    	prospectSalesActionDetails.setSalesActionId(campaignSalesActionDetails.getSalesActionId());
    	prospectSalesActionDetails.setProspectCampaignSequence(campaignSalesActionDetails.getDisplaySequence());
    	prospectSalesActionDetails.setProspectCampaignId(campaignDetails.getProspectCampaignId());
    	prospectSalesActionDetails.setActionPriority(new BigDecimal(1));
        
        if (Utilities.nullToBlank(request.getParameter("cmbActionStatus")).equals("Completed")) {
            prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_COMPLETE);
            // when action is set to completed, set notification flag to 1 so no email goes out.
            prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(1));
        } else {
        	prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
        }
        
    	prospectSalesActionDetails.setActionNotificationflag(new BigDecimal(0));
    	
    	int position = campaignSalesActionDetails.getDisplaySequence().intValue();
    	
        if (!Utilities.nullToBlank(request.getParameter("dfActionNote_" + position)).equals("")) {
            prospectSalesActionDetails.setActionNote((request.getParameter("dfActionNote_" + position)));
        } else {
            prospectSalesActionDetails.setActionNote(null);
        }
        
        if (Utilities.nullToBlank(request.getParameter("cmbActionStatus_" + position)).equals("Completed")) {
            prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_COMPLETE);
        } else {
        	prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
        }
        
        if (!Utilities.nullToBlank(request.getParameter("dfHour_" + position)).equals("") &&
        	!Utilities.nullToBlank(request.getParameter("time_" + position)).equals("") &&
        	!Utilities.nullToBlank(request.getParameter("dfSalesActionDate_" + position)).equals("")) {
        	Timestamp timestamp=null;
        	if(!Utilities.nullToBlank(request.getParameter("dfMinute_"  + position)).equals("")){
        		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate_"  + position),
        			request.getParameter("dfHour_" + position), request.getParameter("dfMinute_"  + position),
        			request.getParameter("time_" + position));
        	}else{
        		timestamp = DateUtilities.getSqlTimestamp(request.getParameter("dfSalesActionDate_" + position),
        			request.getParameter("dfHour_" + position), "00",
        			request.getParameter("time_" + position));
        	}
            prospectSalesActionDetails.setActionDate(timestamp);
        }
        
        prospectSalesActionDetails.setMandatoryDate(campaignSalesActionDetails.getSalesActionMandatoryDate());
    	
    	return prospectSalesActionDetails;  	
    }
    
   
}
