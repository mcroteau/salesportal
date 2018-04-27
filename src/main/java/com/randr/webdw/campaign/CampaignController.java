package com.randr.webdw.campaign;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;

import com.randr.webdw.AppSettings;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectCampaign.ProspectCampaignDetails;
import com.randr.webdw.prospectCampaign.ProspectCampaignView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.util.CollectionUtilities;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;

/**
 */
public class CampaignController extends AbstractController {
	
	private static int FIRST_FROM_DB = 0;
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayCampaigns();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertCampaign();
        } else if (formAction.equals("INSERT")) {
            insertCampaign();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateCampaign();
        } else if (formAction.equals("UPDATE")) {
            updateCampaign();
        } else if (formAction.equals("DELETE")) {
            deleteCampaign();
        } else if (formAction.equals("MOVE_UP")) {
            moveUpList();
        } else if (formAction.equals("MOVE_DOWN")) {
            moveDownList();
        } else if (formAction.equals("REMOVE")) {
            removeFromList();
        } else if (formAction.equals("ADD")) {
            addToList();
        }
    }

    private void addToList() {
    	 try {
    		 
    		 openConnection();
             
             CampaignData campaignData = (CampaignData)request.getSession().getAttribute("campaignData");
             SalesActionDetails salesActionDetails = new SalesActionDetails();
             salesActionDetails.setActionId(new BigDecimal(request.getParameter("cmbSalesAction")));
             SalesActionView salesActionView = new SalesActionView();
             salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
             
             if (salesActionView.getElements().size()>0){
            	 salesActionDetails = (SalesActionDetails)salesActionView.getElement(0);
            	 //add days to schedule from prior sales action
            	 if(!Utilities.nullToBlank(request.getParameter("dfDays")).equals("")) {
                  	salesActionDetails.setSendEmailDays(new BigDecimal(request.getParameter("dfDays")));
                  }else if(!Utilities.nullToBlank(AppSettings.getParm("AUTOMATED_CAMPAIGN_DAYS")).equals("")) {//use the default from appConfig
                	  salesActionDetails.setSendEmailDays(new BigDecimal(AppSettings.getParm("AUTOMATED_CAMPAIGN_DAYS")));
                  }else{
                 	 salesActionDetails.setSendEmailDays(new BigDecimal(0));
                  }
            	 campaignData.addToList(salesActionDetails);			
             }    
             request.getSession().setAttribute("campaignData", campaignData); 
             connection.close();
			 displayUpdateCampaign();
           
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
	}

	private void removeFromList() {
		CampaignData campaignData = (CampaignData)request.getSession().getAttribute("campaignData");
		campaignData.removeFromList(Integer.parseInt(request.getParameter("row")));		
		request.getSession().setAttribute("campaignData", campaignData);
		displayUpdateCampaign();	
	}

	private void moveDownList() {
		CampaignData campaignData = (CampaignData)request.getSession().getAttribute("campaignData");
		campaignData.moveDownList(Integer.parseInt(request.getParameter("row")));
		request.getSession().setAttribute("campaignData", campaignData);
		displayUpdateCampaign();			
	}

	private void moveUpList() {
		CampaignData campaignData = (CampaignData)request.getSession().getAttribute("campaignData");
		campaignData.moveUpList(Integer.parseInt(request.getParameter("row")));
		request.getSession().setAttribute("campaignData", campaignData);
		displayUpdateCampaign();
	}

	/**
     * Method displayCampaigns.
     */
    private void displayCampaigns() {
        try {

            openConnection();

            CampaignView campaignView = new CampaignView();
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, new CampaignDetails());
            try {
                if (campaignView.getElements().size() > 0) {
                    request.setAttribute("campaignView", campaignView);
                } else {
                    throw new ModelException("No campaigns found in the database", ModelException.RECORD_NOT_FOUND);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/campaign/displayCampaigns.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method insertCampaign.
     */
    private void insertCampaign() {
        try {
            openConnection();

            // check if the campaign already exist
            CampaignDetails campaignDetails = new CampaignDetails();
            campaignDetails.setCampaign(request.getParameter("dfCampaign"));
            campaignDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
            campaignDetails.setDescription(request.getParameter("dfDescription"));
            campaignDetails.setDisplayForSalesmen(new BigDecimal(request.getParameter("dfDisplayForSalesmen")));

            CampaignView campaignView = new CampaignView();
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignDetails);

            try {
                if (campaignView.getElements().size() > 0) {
                    throw new ModelException("A campaign with this name has already been created.", ModelException.DUPLICATE_RECORD);
                }

                // insert campaign
                campaignView.doAction(connection, "INSERT", CampaignDetails.FILL_TYPE_ALL, campaignDetails);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/campaign/campaignActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateCampaign.
     */
    private void updateCampaign() {
        try {
            openConnection();
            CampaignData campaignData = (CampaignData)request.getSession().getAttribute("campaignData");
            
            // check if the campaign already exist
            CampaignDetails searchCampaignDetails = new CampaignDetails();
            searchCampaignDetails.setCampaignId(new BigDecimal(request.getParameter("dfCampaignId")));
            CampaignView campaignView = new CampaignView();
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, searchCampaignDetails);
            
            CampaignDetails campaignDetails = (CampaignDetails)campaignView.getElement(FIRST_FROM_DB);
            
            System.out.println("campaignDetails " + campaignDetails.getCampaignId());
           // checkIfCampaignBelongsToProspects(campaignDetails); - 2/18/09 I don't think we care if it is being used
            
            
            
            // update campaign
            campaignDetails.setCampaign(request.getParameter("dfCampaign"));
            campaignDetails.setUpdateDate(DateUtilities.getCurrentSQLTimestamp());
            campaignDetails.setDescription(request.getParameter("dfDescription"));
            campaignDetails.setDisplayForSalesmen(new BigDecimal(request.getParameter("dfDisplayForSalesmen")));
            
            campaignView.doAction(connection, "UPDATE", CampaignDetails.FILL_TYPE_ALL, campaignDetails);
            
            // delete campaign sales actions
            CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
            CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
            campaignSalesActionDetails.setCampaignId(campaignDetails.getCampaignId());
            campaignSalesActionView.doAction(connection, "DELETE", 
            		CampaignSalesActionDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
            
            
            
            // recreate new campaign sales actions
            for(int i = 0; i < campaignData.getCampaignSalesActionList().size(); i++) {
            	campaignSalesActionDetails 
            		= (CampaignSalesActionDetails)campaignData.getCampaignSalesActionList().get(i);
            	campaignSalesActionDetails.setDisplaySequence(new BigDecimal(i));
            	campaignSalesActionDetails.setCampaignId(campaignDetails.getCampaignId());
            	campaignSalesActionView.doAction(connection, "INSERT", 
                		CampaignSalesActionDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
            }
            
            
            //NEXT METHOD CALL IS FOR NEXT RELEASE
//            changeAllProspectCampaigns(campaignData, campaignSalesActionDetails, CampaignDetails);

            
            request.getSession().removeAttribute("campaignData");

            commit();
            // forward request
            forward("/jsp/admin/campaign/campaignActionResult.jsp");
            
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void changeAllProspectCampaigns(CampaignData campaignData, 
    						CampaignSalesActionDetails campaignSalesActionDetails,
    						CampaignDetails campaignDetails) throws Exception {
      
    	//update prospect sales actions belong to campaign
      ProspectCampaignDetails searchProspectCampaignDetails = new ProspectCampaignDetails();
      ProspectCampaignView searchProspectCampaignView = new ProspectCampaignView();
      
      searchProspectCampaignDetails.setCampaignId(campaignDetails.getCampaignId());
      searchProspectCampaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, searchProspectCampaignDetails);
      
      
      for(int j = 0; j < searchProspectCampaignView.getElements().size(); j++ ){
    	  ProspectCampaignDetails prospectCampaignDetails = (ProspectCampaignDetails)searchProspectCampaignView.getElement(j);
          
    	  ProspectSalesActionDetails searchProspectSalesActionDetails = new ProspectSalesActionDetails();
          ProspectSalesActionView searchProspectSalesActionView = new ProspectSalesActionView();
          searchProspectSalesActionDetails.setProspectCampaignId(prospectCampaignDetails.getProspectCampaignId());
          searchProspectSalesActionView.fillWithElements(connection, ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED, searchProspectSalesActionDetails);
          
          for(int k = 0; k < searchProspectSalesActionView.getElements().size(); k++) {
        	  ProspectSalesActionDetails prospectSalesActionDetails = (ProspectSalesActionDetails)searchProspectSalesActionView.getElement(k);    
        	  ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();                
        	  prospectSalesActionView.doAction(connection, "DELETE", new BigDecimal(1), prospectSalesActionDetails);
          }


          for(int a = 0; a < campaignData.getCampaignSalesActionList().size(); a++) {
        	  campaignSalesActionDetails = (CampaignSalesActionDetails)campaignData.getCampaignSalesActionList().get(a);
	          	
        	  ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
        	  ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
			
        	  prospectSalesActionDetails.setSalesActionId(campaignSalesActionDetails.getSalesActionId());
        	  prospectSalesActionDetails.setProspectCampaignId(searchProspectSalesActionDetails.getProspectCampaignId());
        	  prospectSalesActionDetails.setProspectId(prospectCampaignDetails.getProspectId());
        	  prospectSalesActionDetails.setActionDate(DateUtilities.getCurrentSQLTimestamp());
        	  prospectSalesActionDetails.setActionNote("This campaign has been updated, please address these changes.");
        	  prospectSalesActionDetails.setActionStatus(ProspectSalesActionDetails.STATUS_ACTIVE);
        	  prospectSalesActionDetails.setActionPriority(new BigDecimal(1));
        	  prospectSalesActionDetails.setActionNotificationflag(SalesActionDetails.HASNT_BEEN_NOTIFIED);
        	  prospectSalesActionDetails.setProspectCampaignSequence(new BigDecimal(a));
			
        	  prospectSalesActionView.doAction(connection, "INSERT", null, prospectSalesActionDetails);

          	}
      	}
		
	}

	private void checkIfCampaignBelongsToProspects(CampaignDetails campaignDetails) {
        try {
        	
	        ProspectCampaignDetails searchProspectCampaignDetails = new ProspectCampaignDetails();
	        searchProspectCampaignDetails.setCampaignId(campaignDetails.getCampaignId());
	        ProspectCampaignView prospectCampaignView = new ProspectCampaignView();             

			prospectCampaignView.fillWithElements(connection, null, searchProspectCampaignDetails);
			
			try {
		        if(prospectCampaignView.getElements().size() > 0){
		        	Set uniqueProspectIdSet = createUniqueSearchSetProspectId(prospectCampaignView);
		            ProspectDetails prospectDetails = new ProspectDetails();
		            ProspectView prospectView = new ProspectView();
		            prospectDetails.setIdsVector(CollectionUtilities.setToVector(uniqueProspectIdSet));
		
		            prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, prospectDetails);
		
		        	
		        	request.setAttribute("prospectView", prospectView);
		        	throw new ModelException("The campaign cannot be updated at this time. " +
		        	 		"<br>The most probable reason for this is that there are users or prospects attached to it." +
		        	 		"<br/>We suggest you either create a new Campaign to work with or remove the campaign from these customers below: ", ModelException.UNKNOWN_ERROR);
		        }
		        
		    } catch (ModelException modelException) {
		        request.setAttribute("modelException", modelException);
		    }
	    
    	} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	//mikes new
    public Set createUniqueSearchSetProspectId(ProspectCampaignView prospectCampaignView)throws Exception{
		Set set = new HashSet();
		ProspectCampaignDetails details;
		for(int i = 0; i < prospectCampaignView.getElements().size();i++) {
			details = (ProspectCampaignDetails)prospectCampaignView.getElement(i);
			set.add(details.getProspectId());
		}
		return set;
	}
    /**
     * Method displayUpdateCampaign.
     */
    private void displayUpdateCampaign() {
        try {
            request.setAttribute("displayInsert", new Boolean(false));
            if(connection != null) {
            	openConnection();
            }
            
            CampaignData campaignData = (CampaignData)request.getSession().getAttribute("campaignData");
            BigDecimal campaignId = new BigDecimal(request.getParameter("dfCampaignId"));
            
            if(campaignData == null || campaignData.getCampaignDetails().getCampaignId() == null ||
            		campaignData.getCampaignDetails().getCampaignId().compareTo(campaignId) != 0) {
            	
            	campaignData = new CampaignData();
                CampaignDetails campaignDetails = new CampaignDetails();
                campaignDetails.setCampaignId(campaignId);

                CampaignView campaignView = new CampaignView();
                campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignDetails);
                campaignData.setCampaignDetails((CampaignDetails)campaignView.getElement(0));

                CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
                campaignSalesActionDetails.setCampaignId(new BigDecimal(request.getParameter("dfCampaignId")));
                
                CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
                campaignSalesActionView.fillWithElements(connection, CampaignSalesActionDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
                campaignData.setCampaignSalesActionList(campaignSalesActionView.getElements()) ;
                
                SalesActionView salesActionView = new SalesActionView();
                salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, new SalesActionDetails());
                campaignData.setSalesActionView(salesActionView);
            } 

            request.getSession().setAttribute("campaignData", campaignData);

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/campaign/displayInsertUpdateCampaign.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

  
	/**
     * Method displayInsertCampaign.
     */
    private void displayInsertCampaign() {
        try {
        	request.getSession().removeAttribute("campaignData");
            request.setAttribute("displayInsert", new Boolean(true));
            // forward request
            request.getRequestDispatcher("/jsp/admin/campaign/displayInsertUpdateCampaign.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteCampaign.
     */
    private void deleteCampaign() {
        try {
            openConnection();
            // check if campaign exist
            CampaignDetails campaignDetails = new CampaignDetails();
            campaignDetails.setCampaignId(new BigDecimal(request.getParameter("dfCampaignId")));

            CampaignView campaignView = new CampaignView();
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignDetails);

            try {
                if (campaignView.getElements().size() == 0) {
                    throw new ModelException("The campaign you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete campaign

                try {
                	//2/18/09 - we don't care , however may want to save the description in the prospectCampaign 
//                    ProspectCampaignDetails searchProspectCampaignDetails = new ProspectCampaignDetails();
//                    searchProspectCampaignDetails.setCampaignId(campaignDetails.getCampaignId());
//                    ProspectCampaignView prospectCampaignView = new ProspectCampaignView();             
//                    prospectCampaignView.fillWithElements(connection, null, searchProspectCampaignDetails);
//                    
//                    if(prospectCampaignView.getElements().size() > 0){
//                    	 throw new ModelException("The campaign cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
//                    }else{
                        campaignView.doAction(connection, "DELETE", null, campaignDetails);
	                    // delete all sales action lines associated with campaign
	                    CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
	                    campaignSalesActionDetails.setCampaignId(campaignDetails.getCampaignId());
	                    CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
	                    campaignSalesActionView.doAction(connection, "DELETE", null, campaignSalesActionDetails);
                    //}
                    
                } catch (SQLException sqlE) {
                    throw new ModelException("The campaign cannot be deleted. <br>The most probable reason for this is that there are users or prospects attached to it.", ModelException.UNKNOWN_ERROR);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            request.getSession().removeAttribute("campaignData");
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/campaign/campaignActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
