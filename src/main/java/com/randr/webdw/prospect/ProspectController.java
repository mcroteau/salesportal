package com.randr.webdw.prospect;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Vector;

import com.randr.webdw.audit.auditRoundRobin.AuditRoundRobinDetails;
import com.randr.webdw.audit.auditRoundRobin.AuditRoundRobinView;
import com.randr.webdw.campaign.CampaignDetails;
import com.randr.webdw.campaign.CampaignView;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails;
import com.randr.webdw.campaignSalesAction.CampaignSalesActionView;
import com.randr.webdw.commision.CommisionDetails;
import com.randr.webdw.commision.CommisionView;
import com.randr.webdw.contact.ContactDetails;
import com.randr.webdw.contact.ContactView;
import com.randr.webdw.document.DocumentDetails;
import com.randr.webdw.document.DocumentView;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.prospectCampaign.ProspectCampaignDetails;
import com.randr.webdw.prospectCampaign.ProspectCampaignView;
import com.randr.webdw.prospectDocument.ProspectDocumentDetails;
import com.randr.webdw.prospectDocument.ProspectDocumentView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.territoryChange.TerritoryChangeDetails;
import com.randr.webdw.territoryChange.TerritoryChangeView;
import com.randr.webdw.territoryZip.TerritoryZipDetails;
import com.randr.webdw.territoryZip.TerritoryZipView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.website.WebsiteDetails;
import com.randr.webdw.website.WebsiteView;


/**
 */
public class ProspectController extends ProspectBaseController {

	String isFromDuplicateSearch = "";
	/**
     * Constructor for ProspectController.
     */
    public ProspectController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {
    	if (formAction == null || formAction.equals("DISPLAY_INSERT")) {
            displayInsert();
        } else if (formAction.equals("DISPLAY_ONE_PROSPECT")) {
            displayUpdateOneProspect();
        } else if (formAction.equals("DISPLAY_UPDATE") || formAction.equals("DISPLAY_UPDATE_CURRENT_ACTIONS")) {
            displayUpdateCollectionElement();
        } else if (formAction.equals("INSERT")) {
            insert();
        } else if (formAction.equals("UPDATE")) {
            update();
            displayUpdateCollectionElement();
        } else if (formAction.equals("DELETE")) {
            delete();
            routeToRightSearchResults();
        } else if (formAction.equals("DELETE_CASCADE")) {
            deleteCascade();
            routeToRightSearchResults();
        } else if (formAction.equals("DISPLAY_UPDATE_COLLECTION")) {
            displayUpdateAllCollectionElements();
        } else if (formAction.equals("UPDATE_COLLECTION")) {
            updateCollection();            
            routeToRightSearchResults();
        } else if (formAction.equals("DISPLAY_UPDATE_CHECKED_COLLECTION")) {
            displayUpdateCheckedCollectionElements();
        } else if (formAction.equals("DISPLAY_INSERT_NOTE")) {
            displayInsertNote();
        } else if (formAction.equals("DISPLAY_UPDATE_NOTE")) {
            displayUpdateNote();
        } else if (formAction.equals("DISPLAY_DELETE_NOTE")) {
            displayDeleteNote();
        } else if (formAction.equals("DISPLAY_INSERT_WEBSITE")) {
            displayInsertWebsite();
        } else if (formAction.equals("DISPLAY_UPDATE_WEBSITE")) {
            displayUpdateWebsite();
        } else if (formAction.equals("DISPLAY_DELETE_WEBSITE")) {
            displayDeleteWebsite();
        } else if (formAction.equals("DISPLAY_INSERT_PROSPECT_DOCUMENT")) {
            displayInsertDocument();
        } else if (formAction.equals("DISPLAY_UPDATE_PROSPECT_DOCUMENT")) {
            displayUpdateDocument();
        } else if (formAction.equals("DISPLAY_DELETE_PROSPECT_DOCUMENT")) {
            displayDeleteDocument();
        } else if (formAction.equals("DISPLAY_INSERT_CONTACT")) {
            displayInsertContact();
        } else if (formAction.equals("DISPLAY_UPDATE_CONTACT")) {
            displayUpdateContact();
        } else if (formAction.equals("DISPLAY_DELETE_CONTACT")) {
            displayDeleteContact();
        } else if (formAction.equals("DISPLAY_INSERT_PROSPECT_SALES_ACTION")) {
            displayInsertSalesAction();
        } else if (formAction.equals("DISPLAY_UPDATE_PROSPECT_SALES_ACTION")) {
            displayUpdateSalesAction();
        } else if (formAction.equals("DISPLAY_DELETE_PROSPECT_SALES_ACTION")) {
            displayDeleteSalesAction();
        } else if (formAction.equals("DISPLAY_SELECT_CAMPAIGN")) {
            displaySelectProspectCampaign();
        } else if (formAction.equals("DISPLAY_SELECT_CAMPAIGN_COLLECTION")) {
            displaySelectProspectCampaignCollection();    
        } else if (formAction.equals("DISPLAY_INSERT_PROSPECT_CAMPAIGN")) {
            displayInsertProspectCampaign();
        } else if (formAction.equals("DISPLAY_INSERT_PROSPECT_CAMPAIGN_COLLECTION")) {
            displayInsertProspectCampaignCollection();    
        }else if (formAction.equals("DISPLAY_UPDATE_PROSPECT_CAMPAIGN")) {
            displayUpdateProspectCampaign();
        } else if (formAction.equals("DISPLAY_DELETE_PROSPECT_CAMPAIGN")) {
            displayDeleteProspectCampaign();
        }else if (formAction.equals("INSERT_ROUND_ROBIN_COLLECTION")) {
            insertProspectIntoRoundRobinCollection(); 
            routeToRightSearchResults();
        }
    }  
	
	
    
    private void insertProspectIntoRoundRobinCollection() {
		//System.out.println(" insertProspectIntoRoundRobinCollection ");
		try {
            openConnection();
           
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            if (sessionProspectView != null) {

                Vector idsVector = new Vector();

                for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
                    ProspectDetails prospectDetails = (ProspectDetails) sessionProspectView.getElements().elementAt(i);
                    idsVector.add(prospectDetails.getProspectId());
                    
                    prospectDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                    prospectDetails.setChangeUserId(userProfile.getUserId());
                    prospectDetails.setTerritoryId(assignToRoundRobin(prospectDetails));//get the new territory Id

                    sessionProspectView.doAction(connection,
                            "UPDATE",
                            ProspectView.FILL_TYPE_UPDATE_COLLECTION,
                            prospectDetails);
                    //insert into auditRoundRobin table
                    AuditRoundRobinDetails auditRRDetails = new AuditRoundRobinDetails();
                    AuditRoundRobinView auditRRView = new AuditRoundRobinView();
                    auditRRDetails.setProspectId(prospectDetails.getProspectId());
                    auditRRDetails.setTerritoryId(prospectDetails.getTerritoryId());
                    auditRRDetails.setUserIdMakingChange(prospectDetails.getChangeUserId());
                    auditRRDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
                    auditRRView.doAction(connection, "INSERT", AuditRoundRobinDetails.FILL_TYPE_ALL, auditRRDetails);
                }

                //rebuild collection by pulling the data from the database for each prospect id
                ProspectDetails searchProspectDetails = new ProspectDetails();
                ProspectView prospectView = new ProspectView();
                sessionProspectView = new ProspectView();

                for (int i = 0; i < idsVector.size(); i++) {
                    searchProspectDetails.addIdToIdsVector((BigDecimal) idsVector.elementAt(i));
                    if ((i > 0) && (i % 100 == 0)) {
                        prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);
                        prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                        establishNotesCount(prospectView);

                        sessionProspectView.addElements(prospectView.getElements());
                        searchProspectDetails.setIdsVector(null);
                    }
                }

                if (searchProspectDetails.getIdsVector() != null && searchProspectDetails.getIdsVector().size() > 0) {
                    prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);
                    prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                    establishNotesCount(prospectView);

                    sessionProspectView.addElements(prospectView.getElements());
                }

                sessionProspectView.createUniqueKeyCollectionHashtable("getProspectId");
                request.getSession().setAttribute("prospectView", sessionProspectView);
            }

            commit();

        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayUpdateCheckedCollectionElements() {
        try {
            openConnection();
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            
            request.setAttribute("prospectView", sessionProspectView);
            //System.out.println("checkId - " + request.getParameter("chkId"));
            ProspectView newProspectView = new ProspectView();       
            if (sessionProspectView != null) {
                for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
                    if (request.getParameter("chkId" + String.valueOf(i)) != null &&
                    		!request.getParameter("chkId" + String.valueOf(i)).equals("")) {
                    	newProspectView.addElement((ProspectDetails) sessionProspectView.getElements().elementAt(i));
                    }
                }
            }
            newProspectView.createUniqueKeyCollectionHashtable("getProspectId");

            request.getSession().setAttribute("prospectView", newProspectView);
            request.setAttribute("prospectView", newProspectView);

//            if(request.getSession().getAttribute("isFromDuplicateSearch")== "YES"){
//            	forward("/jsp/public/duplicateSearch/displayProspects.jsp");
//            }else{
//            	forward("/jsp/public/prospect/displayProspects.jsp");
//            }
            
            loadAdditionalData(getCurrentSalesmanDetails());
            commit();

            request.getRequestDispatcher("/jsp/public/prospect/displayUpdateProspectCollection.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void routeToRightSearchResults() {
        if(request.getSession().getAttribute("isFromDuplicateSearch")== "YES"){
        	searchForProspectDuplicates();
        }else{
        	displayProspects();
        }
		
	}

	private void displaySelectProspectCampaign() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            openConnection();
            CampaignView campaignView = new CampaignView();
            CampaignDetails campaignDetails = new CampaignDetails();
            campaignDetails.setDisplayForSalesmen(CampaignDetails.YES_DISPLAY_FOR_SALESMEN);
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL,campaignDetails);
            request.setAttribute("campaignView", campaignView);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            forward("/jsp/public/prospectCampaign/displaySelectCampaign.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}
	
	private void displaySelectProspectCampaignCollection() {
        try {
            ProspectView prospectView = (ProspectView) request.getSession().getAttribute("prospectView");
            openConnection();
            CampaignView campaignView = new CampaignView();
            CampaignDetails campaignDetails = new CampaignDetails();
            campaignDetails.setDisplayForSalesmen(CampaignDetails.YES_DISPLAY_FOR_SALESMEN);
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL,campaignDetails);
            request.setAttribute("campaignView", campaignView);
            request.getSession().setAttribute("prospectView", prospectView);

            forward("/jsp/public/prospectCampaign/displaySelectCampaignCollection.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayInsertProspectCampaign() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            openConnection();
            
            CampaignView campaignView = new CampaignView();
            CampaignDetails campaignDetails = new CampaignDetails();
            campaignDetails.setCampaignId(new BigDecimal(request.getParameter("cmbCampaign")));
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignDetails);
            campaignDetails = (CampaignDetails)campaignView.getElement(0);
            
            CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
            CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
            campaignSalesActionDetails.setCampaignId(campaignDetails.getCampaignId());
            campaignSalesActionView.fillWithElements(connection, 
            		CampaignSalesActionDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
            request.setAttribute("campaignDetails", campaignDetails);
            request.setAttribute("campaignSalesActionView", campaignSalesActionView);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            forward("/jsp/public/prospectCampaign/displayInsertCampaignSalesAction.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayInsertProspectCampaignCollection() {
        try {
            ProspectView prospectView = (ProspectView) request.getSession().getAttribute("prospectView");
            openConnection();
            
            CampaignView campaignView = new CampaignView();
            CampaignDetails campaignDetails = new CampaignDetails();
            campaignDetails.setCampaignId(new BigDecimal(request.getParameter("cmbCampaign")));
            campaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, campaignDetails);
            campaignDetails = (CampaignDetails)campaignView.getElement(0);
            
            CampaignSalesActionView campaignSalesActionView = new CampaignSalesActionView();
            CampaignSalesActionDetails campaignSalesActionDetails = new CampaignSalesActionDetails();
            campaignSalesActionDetails.setCampaignId(campaignDetails.getCampaignId());
            campaignSalesActionView.fillWithElements(connection, 
            		CampaignSalesActionDetails.FILL_TYPE_ALL, campaignSalesActionDetails);
            request.setAttribute("campaignDetails", campaignDetails);
            request.setAttribute("campaignSalesActionView", campaignSalesActionView);
            request.getSession().setAttribute("prospectView", prospectView);

            forward("/jsp/public/prospectCampaign/displayInsertCampaignSalesActionCollection.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayDeleteProspectCampaign() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
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
            
            request.setAttribute("prospectCampaignDetails", prospectCampaignDetails);
            request.setAttribute("prospectSalesActionView", prospectSalesActionView);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            forward("/jsp/public/prospectCampaign/displayDeleteCampaignSalesAction.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayUpdateProspectCampaign() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
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
            
            request.setAttribute("prospectCampaignDetails", prospectCampaignDetails);
            request.setAttribute("prospectSalesActionView", prospectSalesActionView);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            forward("/jsp/public/prospectCampaign/displayUpdateCampaignSalesAction.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayInsertSalesAction() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            openConnection();
            SalesActionView salesActionView = new SalesActionView();
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setDisplayForSalesmen(SalesActionDetails.YES_DISPLAY_FOR_SALESMEN);
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
            request.setAttribute("salesActionView", salesActionView);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            forward("/jsp/public/prospectSalesAction/displayInsertUpdateProspectSalesAction.jsp");
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayUpdateSalesAction() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            openConnection();
            
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();          
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            prospectSalesActionDetails.setProspectSalesActionId(new BigDecimal(request.getParameter("dfProspectSalesActionId").toString()));
            prospectSalesActionView.fillWithElements(connection, ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
            prospectSalesActionDetails = (ProspectSalesActionDetails)prospectSalesActionView.getElement(0);
            request.setAttribute("prospectSalesActionDetails", prospectSalesActionDetails);
            
            SalesActionView salesActionView = new SalesActionView();
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setDisplayForSalesmen(SalesActionDetails.YES_DISPLAY_FOR_SALESMEN);
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
            request.setAttribute("salesActionView", salesActionView);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            request.getRequestDispatcher("/jsp/public/prospectSalesAction/displayInsertUpdateProspectSalesAction.jsp").forward(request, response);
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	private void displayDeleteSalesAction() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);//why do we do this here?
            ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
            prospectSalesActionDetails.setProspectSalesActionId(new BigDecimal(request.getParameter("dfProspectSalesActionId")));

            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            prospectSalesActionView.fillWithElements(connection, ProspectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
            
            if (prospectSalesActionView.getElements().size() == 1) {
            	prospectSalesActionDetails = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(0);
                request.setAttribute("prospectSalesActionDetails", prospectSalesActionDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            forward("/jsp/public/prospectSalesAction/displayInsertUpdateProspectSalesAction.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
		
	}

	/**
     * Method displayInsert.
     */
    private void displayInsert() {
        try {
            request.getSession().removeAttribute("prospectDetails");
            request.getSession().removeAttribute("prospectView");
            request.getSession().removeAttribute("contactView");
            request.setAttribute("updateScreen", new Boolean(false));
            openConnection();
        	removeAdditionalDataFromSession();
            loadAdditionalData(getCurrentSalesmanDetails());
            //load prospect data if update situation
            commit();
            request.getRequestDispatcher("/jsp/public/prospect/displayInsertUpdateProspect.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method displayUpdateCollectionElement.
     */
    public void displayUpdateCollectionElement() {
        try {
            openConnection();
            ProspectView sessionProspectView;
            //System.out.println("==what is the formAction? " + formAction);
            if (formAction.equals("DISPLAY_UPDATE_CURRENT_ACTIONS")){
            	sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectTodayView");
            	request.getSession().setAttribute("prospectView", sessionProspectView);
            }else{
            	sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            }
            request.setAttribute("prospectView", sessionProspectView);
            request.setAttribute("updateScreen", new Boolean(true));
            
            //bug 886 fix.  previous code called request.getSession().getAttribute("prospectDetails")
            //regardless if it was null or not.  Therefore, threw a null error
            ProspectDetails prospectDetails = null;
            if(request.getSession().getAttribute("prospectDetails") != null){
            	prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            }
            
            if (prospectDetails == null) {
            	
            	int elementIndex = sessionProspectView.getElementIndex(new BigDecimal(request.getParameter("dfProspectId")));
                
                if (elementIndex < 0) {
                    throw new ModelException("The prospect you are trying to access has been removed from collection", ModelException.RECORD_NOT_FOUND);
                }
                prospectDetails = (ProspectDetails) ((ProspectDetails) sessionProspectView.getElements().elementAt(elementIndex));
            } else {
            	if (!Utilities.nullToBlank(request.getParameter("dfChangeIndex")).equals("")) {
                    int index = sessionProspectView.getElementIndex(prospectDetails.getProspectId());
                    int newIndex = index + Integer.parseInt(request.getParameter("dfChangeIndex"));

                    if (newIndex >= 0 && newIndex < sessionProspectView.getElements().size()) {
                        prospectDetails = (ProspectDetails) sessionProspectView.getElement(index + Integer.parseInt(request.getParameter("dfChangeIndex")));
                    }
                }
            }
        	if(request.getParameter("fromCurrentActions")!=null && request.getParameter("fromCurrentActions").equals("YES")){ 	
        		request.setAttribute("fromCurrentActions", request.getParameter("fromCurrentActions"));
        	}
            request.getSession().setAttribute("prospectDetails", prospectDetails);
            request.setAttribute("prospectDetails", prospectDetails);
            
            setProspectInformation(prospectDetails);
            
            commit();

            forward("/jsp/public/prospect/displayInsertUpdateProspect.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateOneProspect.
     */
    public void displayUpdateOneProspect() {
        try {
            openConnection();
            
            ProspectView sessionProspectView = new ProspectView();
            ProspectDetails prospectDetails = new ProspectDetails();
            prospectDetails.setProspectId(new BigDecimal(request.getParameter("ProspectID")));
            sessionProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
            if (sessionProspectView.getElements().size()>0){
            	prospectDetails = (ProspectDetails) sessionProspectView.getElement(0);
            	prospectDetails.setEmailMailToString(buildEmailMailToString(prospectDetails));
            	request.setAttribute("prospectDetails", prospectDetails);
            	request.getSession().setAttribute("prospectDetails", prospectDetails);
            }
            sessionProspectView.createUniqueKeyCollectionHashtable("getProspectId");
            //int index = sessionProspectView.getElementIndex(prospectDetails.getProspectId());
            
            request.setAttribute("prospectView", sessionProspectView);
            request.getSession().setAttribute("prospectView", sessionProspectView);

            request.setAttribute("updateScreen", new Boolean(true));
            
            setProspectInformation(prospectDetails);
            
            commit();
            
            forward("/jsp/public/prospect/displayInsertUpdateProspect.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

	private void setProspectInformation(ProspectDetails prospectDetails) throws Exception {
		 //if the removeAdditionalDataFromSession is not called here, the lob, system, territory etc are not pulled from
        //session each time... it would work faster, but the data would not be the latest from the database
		removeAdditionalDataFromSession();
        loadAdditionalData(getCurrentSalesmanDetails());
        
 		request.getSession().setAttribute("prospectDetails", prospectDetails);
        request.setAttribute("prospectDetails", prospectDetails);
            
		NoteView noteView = new NoteView();
		NoteDetails noteDetails = new NoteDetails();

		noteDetails.setProspectId(prospectDetails.getProspectId());
		noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
		request.setAttribute("noteView", noteView);
		
		WebsiteView websiteView = new WebsiteView();
		WebsiteDetails websiteDetails = new WebsiteDetails();

		websiteDetails.setProspectId(prospectDetails.getProspectId());
		websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
		request.setAttribute("websiteView", websiteView);

		ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
		ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();

		prospectDocumentDetails.setProspectId(prospectDetails.getProspectId());
		prospectDocumentView.fillWithElements(connection, ProspectDocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);
		setCreationUserName(prospectDocumentView);
		request.setAttribute("prospectDocumentView", prospectDocumentView);
		
		ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
		ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();

		prospectSalesActionDetails.setProspectId(prospectDetails.getProspectId());
		prospectSalesActionDetails.setExcludeCampaignSalesActions(true);
		prospectSalesActionView.fillWithElements(connection, ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED, prospectSalesActionDetails);
		request.setAttribute("prospectSalesActionView", prospectSalesActionView);
		
		ProspectSalesActionDetails details;		
        for(int i = 0; i < prospectSalesActionView.getElements().size(); i++){
        	details = (ProspectSalesActionDetails)prospectSalesActionView.getElement(i);
        	if(details.getActionDate()!= null){
            	String isInCalendar = "YES";
            	request.setAttribute("isInCalendar", isInCalendar);
        	}
        }

        ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
        ProspectCampaignDetails searchProspectCampaignDetails = new ProspectCampaignDetails();
        searchProspectCampaignDetails.setProspectId(prospectDetails.getProspectId());
        prospectCampaignView.fillWithElements(connection, CampaignDetails.FILL_TYPE_ALL, searchProspectCampaignDetails);
        
        for(int a = 0; a < prospectCampaignView.getElements().size(); a++){
            ProspectCampaignDetails prospectCampaignDetails = (ProspectCampaignDetails)prospectCampaignView.getElement(a);
            ProspectSalesActionDetails searchProspectSalesActionDetails = new ProspectSalesActionDetails();
            ProspectSalesActionView searchProspectSalesActonView = new ProspectSalesActionView();
            searchProspectSalesActionDetails.setProspectCampaignId(prospectCampaignDetails.getProspectCampaignId());
            searchProspectSalesActonView.fillWithElements(connection, ProspectSalesActionView.FILL_TYPE_CAMPAIGN_OR_COMPLETED, searchProspectSalesActionDetails);
            
            int totalActions =  searchProspectSalesActonView.getElements().size();
            int completedActions = 0;
            
            for(int b = 0; b <  searchProspectSalesActonView.getElements().size(); b++){
            	ProspectSalesActionDetails prospectCampaignSalesActionDetails = (ProspectSalesActionDetails)searchProspectSalesActonView.getElement(b);
            	if(prospectCampaignSalesActionDetails.getActionStatus().compareTo(ProspectSalesActionDetails.STATUS_COMPLETE)==0){
            		completedActions++;
            	}
            }
            
//           System.out.println("totalActions = " + totalActions + "  - completedActions = " + completedActions);
           prospectCampaignDetails.setTotalActions(totalActions);
           prospectCampaignDetails.setActionsRemaining(totalActions - completedActions);
           if(totalActions==completedActions){
        	   prospectCampaignDetails.setStatus(ProspectCampaignDetails.CAMPAIGN_COMPLETED);
           }
//           System.out.println("campaign sales actions = " + searchProspectSalesActonView.getElements().size());
        }

        request.setAttribute("prospectCampaignView", prospectCampaignView);
		
		ContactView contactView = new ContactView();
		ContactDetails contactDetails = new ContactDetails();

		contactDetails.setProspectId(prospectDetails.getProspectId());
		contactView.fillWithElements(connection, ContactDetails.FILL_TYPE_ALL, contactDetails);
		request.setAttribute("contactView", contactView);
		
		// add display commissions here
		CommisionDetails commisionDetails = new CommisionDetails();
        if (userProfile.getUserType().compareTo(UserDetails.USER_TYPE_ADMIN)!=0) {
            commisionDetails.setUserId(this.userProfile.getUserId());
        }
        
        commisionDetails.setProspectId(prospectDetails.getProspectId());
        CommisionView commisionView = new CommisionView();
        commisionView.fillWithElements(connection, CommisionDetails.FILL_TYPE_JOIN, commisionDetails);
        request.setAttribute("commisionView", commisionView);
	}

    
    
    private void setCreationUserName(ProspectDocumentView prospectDocumentView) throws Exception{
		
		UserView userView = new UserView();
		ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
		for(int i = 0;i<prospectDocumentView.getElements().size();i++){
			prospectDocumentDetails = (ProspectDocumentDetails)prospectDocumentView.getElement(i);
			if(prospectDocumentDetails.getCreationUserId()!=null){
				UserDetails userDetails= new UserDetails();
				userDetails.setUserId(prospectDocumentDetails.getCreationUserId());
				//System.out.println("userDetails.getUserUserId()=" + userDetails.getUserId());
				userView.fillWithElements(connection, UserView.FILL_TYPE_LOGIN, userDetails);
				//System.out.println(" userView.getElements().size()= " + userView.getElements().size());
				if(userView.getElements().size()>0){
					userDetails = (UserDetails)userView.getElement(0);
					prospectDocumentDetails.setCreationUserName(userDetails.getUserName());
				}else{
					prospectDocumentDetails.setCreationUserName("Admin");
				}
			}else{
				prospectDocumentDetails.setCreationUserName("Admin");
			}//end if if prospectDocumentDetails, creation User Id is not null
		}//end of prospectDocumentView for loop
	}

	/**
     * Method displayUpdateAllCollectionElements.
     */
    private void displayUpdateAllCollectionElements() {
        try {
            openConnection();
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            
            request.setAttribute("prospectView", sessionProspectView);

            loadAdditionalData(getCurrentSalesmanDetails());
            commit();

            forward("/jsp/public/prospect/displayUpdateProspectCollection.jsp");
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }



    /**
     * Method insert.
     */
    private void insert() {
        try {
            openConnection();

            ProspectDetails prospectDetails = new ProspectDetails();
            ProspectView prospectView = new ProspectView();

            try {
                prospectDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
                prospectDetails.setCreationUserId(userProfile.getUserId());
                prospectDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                prospectDetails.setChangeUserId(userProfile.getUserId());
                ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
                // insert prospect
                autoAssignTerritory(prospectDetails);
                prospectView.doAction(connection, "INSERT", ProspectView.FILL_TYPE_BASIC, prospectDetails);
            
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            request.setAttribute("prospectDetails", prospectDetails);
            // this is where we need to set the attribute of the prospect ID so we can give an update options
            request.getRequestDispatcher("/jsp/public/prospect/prospectActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void autoAssignTerritory(ProspectDetails prospectDetails) throws Exception {
    	
		if(request.getParameter("ckAutoAssignTerritory") != null && prospectDetails.getZip() != null
				&& prospectDetails.getTerritoryId() == null) {
			
			TerritoryZipView territoryZipView = new TerritoryZipView();
			TerritoryZipDetails territoryZipDetails = new TerritoryZipDetails();
			
			if(prospectDetails.getZip().length() >= 5) {
				territoryZipDetails.setZip(prospectDetails.getZip().substring(0,5));
			} else {
				territoryZipDetails.setZip(prospectDetails.getZip());
			}
			
			territoryZipView.fillWithElements(connection, TerritoryZipDetails.FILL_TYPE_ALL, territoryZipDetails);
			if(territoryZipView.getElements().size() == 1) {
				territoryZipDetails = (TerritoryZipDetails)territoryZipView.getElement(0);
				
				TerritoryView territoryView = new TerritoryView();
                TerritoryDetails territoryDetails = new TerritoryDetails();
                territoryDetails.setTerritoryId(territoryZipDetails.getTerritoryId());
                territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
                if(territoryView.getElements().size()==1){
                    territoryDetails = (TerritoryDetails)territoryView.getElement(0);

    				prospectDetails.setTerritoryId(territoryZipDetails.getTerritoryId());
    				
                    
    				request.setAttribute("autoAssignMessage", "Prospect " + prospectDetails.getCustomerCompany() + 
    						" auto assigned to territory " + territoryDetails.getTerritory() + ".");

                }else {
        			request.setAttribute("autoAssignMessage", "Prospect " + prospectDetails.getCustomerCompany() + 
    						" could not be auto assigned a territory from zip " + prospectDetails.getZip() + 
    						", because zip associated with deleted territory.");
                }
			} else {
				request.setAttribute("autoAssignMessage", "Prospect " + prospectDetails.getCustomerCompany() + 
						" could not be auto assigned a territory from zip " + prospectDetails.getZip() + ".");
			}
			
		}		
	}
    

	/**
     * Method update.
     */
    private void update() {
        try {
            openConnection();
            

            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            // check for prospectDetails.getProspectId() not the same as dfProspectId
            //System.out.println("==1 PrspectDetails.getID= " + prospectDetails.getProspectId() + " dfProspectId= " + request.getParameter("dfProspectId"));
            if(!prospectDetails.getProspectId().equals(new BigDecimal(request.getParameter("dfProspectId")))){	
            	getCorrectProspectDetails(prospectDetails); 
            }
            //System.out.println("==2 PrspectDetails.getID= " + prospectDetails.getProspectId() + " dfProspectId= " + request.getParameter("dfProspectId"));
            if (prospectDetails != null) {
            	if(isEnabled("PROSPECT_TERRITORY_CHANGE_ENABLED")){
            		if(needsTerritoryChange(prospectDetails)){
            			setTerritoryChange(prospectDetails);
            		}
            	}
            	
                ProspectView prospectView = new ProspectView();
                
                try {

                	prospectDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                    prospectDetails.setChangeUserId(userProfile.getUserId());
                    ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
                    // insert prospect
                    prospectView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectDetails);
                    
                    ProspectDetails reloadProspectDetails = new ProspectDetails();
                    reloadProspectDetails.setProspectId(prospectDetails.getProspectId());
                    prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, reloadProspectDetails);
                    if (prospectView.getElements().size() == 1) {
                    	prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                        establishNotesCount(prospectView);

                        prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(0);
                        //update session collection
                        ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
                        int elementIndex = sessionProspectView.getElementIndex(prospectDetails.getProspectId());
                        if (elementIndex < 0) {
                            throw new ModelException("The prospect you are trying to access has been removed from collection", ModelException.RECORD_NOT_FOUND);
                        }

                        sessionProspectView.getElements().setElementAt(prospectDetails, elementIndex);
                        request.getSession().setAttribute("prospectView", sessionProspectView);
                    }
                    request.getSession().removeAttribute("contactView");
                } catch (ModelException modelException) {
                    request.setAttribute("modelException", modelException);
                }
                commit();
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void getCorrectProspectDetails(ProspectDetails prospectDetails) throws Exception{
		prospectDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
		ProspectView prospectView = new ProspectView();
		prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);
		if(prospectView.getElements().size()>0){
			prospectDetails = (ProspectDetails)prospectView.getElement(0);
		}
	}

	private boolean needsTerritoryChange(ProspectDetails prospectDetails) throws Exception {
    	BigDecimal territoryId = new BigDecimal(0);
    	if(request.getParameter("dfTerritoryId").equals(null)){
    		//System.out.println("!= null");
    		territoryId = new BigDecimal(request.getParameter("dfTerritoryId"));
    	}
    	TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
    	TerritoryView territoryView = new TerritoryView();
    	searchTerritoryDetails.setTerritoryId(territoryId);
    	territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
    	
    	if(territoryView.getElements().size() > 0){
    		return true;
    	}else{
    		return false;
    	}	
	}

	private void setTerritoryChange(ProspectDetails prospectDetails) throws Exception {

		TerritoryChangeDetails territoryChangeDetails = new TerritoryChangeDetails();
		TerritoryChangeView territoryChangeView = new TerritoryChangeView();
		territoryChangeDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
		territoryChangeDetails.setUserId(userProfile.getUserId());
		territoryChangeDetails.setNewTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
		territoryChangeDetails.setOriginalTerritoryId(prospectDetails.getTerritoryId());
		
		territoryChangeView.doAction(connection, "INSERT", TerritoryChangeView.FILL_TYPE_ALL, territoryChangeDetails);	
	}

	/**
     * Method updateCollection.
     */
    private void updateCollection() {
        try {
            openConnection();

            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            if (sessionProspectView != null) {

                Vector idsVector = new Vector();

                for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
                    ProspectDetails prospectDetails = (ProspectDetails) sessionProspectView.getElements().elementAt(i);
                    idsVector.add(prospectDetails.getProspectId());
                    
                    ProspectDetails updateProspectDetails = new ProspectDetails();
                    ProspectControllerHelper.getProspectDataFromRequest(request, updateProspectDetails, userProfile);
                    
                    updateProspectDetails.setProspectId(prospectDetails.getProspectId());
                    updateProspectDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                    updateProspectDetails.setChangeUserId(userProfile.getUserId());
                    
                    if (request.getParameter("cmbSalesAction") != null
                            && !request.getParameter("cmbSalesAction").equals("")) {
                    	updateSalesActions(updateProspectDetails);
                    }  
                    
                    sessionProspectView.doAction(connection,
                            "UPDATE",
                            ProspectView.FILL_TYPE_UPDATE_COLLECTION,
                            updateProspectDetails);
                }

                //rebuild collection by pulling the data from the database for each prospect id
                ProspectDetails searchProspectDetails = new ProspectDetails();
                ProspectView prospectView = new ProspectView();
                sessionProspectView = new ProspectView();

                for (int i = 0; i < idsVector.size(); i++) {
                    searchProspectDetails.addIdToIdsVector((BigDecimal) idsVector.elementAt(i));
                    if ((i > 0) && (i % 100 == 0)) {
                        prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);
                        prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                        establishNotesCount(prospectView);

                        sessionProspectView.addElements(prospectView.getElements());
                        searchProspectDetails.setIdsVector(null);
                    }
                }

                if (searchProspectDetails.getIdsVector() != null && searchProspectDetails.getIdsVector().size() > 0) {
                    prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);
                    prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                    establishNotesCount(prospectView);

                    sessionProspectView.addElements(prospectView.getElements());
                }

                sessionProspectView.createUniqueKeyCollectionHashtable("getProspectId");
                request.getSession().setAttribute("prospectView", sessionProspectView);
            }

            commit();

        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private void updateSalesActions(ProspectDetails updateProspectDetails) {
        try {
        	
            ProspectSalesActionDetails updateProspectSalesActionsDetails = new ProspectSalesActionDetails();
            ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
            
            ProspectControllerHelper.getProspectSalesActionFromRequest(request, updateProspectSalesActionsDetails);
            updateProspectSalesActionsDetails.setProspectId(updateProspectDetails.getProspectId());
			prospectSalesActionView.doAction(connection, "INSERT", ProspectSalesActionDetails.FILL_TYPE_ALL, updateProspectSalesActionsDetails);
		
        } catch (Exception e) {
			e.printStackTrace();
		}		
	}



	/**
     * Method delete.
     */
    private void delete() {
        try {
            openConnection();

            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            if (sessionProspectView != null) {
                int elementIndex = sessionProspectView.getElementIndex(new BigDecimal(request.getParameter("dfProspectId")));
                if (elementIndex < 0) {
                    throw new ModelException("The prospect you are trying to access has been removed from collection", ModelException.RECORD_NOT_FOUND);
                }

                ProspectDetails sessionProspectDetails = (ProspectDetails) sessionProspectView.getElements().elementAt(elementIndex);

                // check if prospect exist
                ProspectDetails prospectDetails = new ProspectDetails();
                prospectDetails.setProspectId(sessionProspectDetails.getProspectId());

                ProspectView prospectView = new ProspectView();
                prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);


                if (prospectView.getElements().size() == 0) {
                    throw new ModelException("The prospect you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                ProspectDetails foundProspectDetails = (ProspectDetails) prospectView.getElements().elementAt(0);
                // delete prospect
                try {
                    prospectView.doAction(connection, "DELETE", null, prospectDetails);

                    sessionProspectView.removeElement(elementIndex);
                    request.getSession().setAttribute("prospectView", sessionProspectView);

                } catch (SQLException sqlE) {
                    throw new ModelException("The prospect <b>" + foundProspectDetails.getCustomerCompany() + "</b> cannot be deleted. <br>The most probable reason for this is that there are notes, documents, additional contacts etc. attached to the prospect.", ModelException.UNKNOWN_ERROR);
                }

                commit();
            }
        } catch (ModelException modelException) {
            request.setAttribute("modelException", modelException);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteCascade.
     */
    private void deleteCascade() {
        try {
            openConnection();

            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            if (sessionProspectView != null) {
                int elementIndex = sessionProspectView.getElementIndex(new BigDecimal(request.getParameter("dfProspectId")));
                if (elementIndex < 0) {
                    throw new ModelException("The prospect you are trying to access has been removed from collection", ModelException.RECORD_NOT_FOUND);
                }

                ProspectDetails sessionProspectDetails = (ProspectDetails) sessionProspectView.getElements().elementAt(elementIndex);

                // check if prospect exist
                ProspectDetails prospectDetails = new ProspectDetails();
                prospectDetails.setProspectId(sessionProspectDetails.getProspectId());

                ProspectView prospectView = new ProspectView();
                prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectDetails);


                if (prospectView.getElements().size() == 0) {
                    throw new ModelException("The prospect you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                
                //delete notes
                NoteView noteView = new NoteView();
                NoteDetails noteDetails = new NoteDetails();
                noteDetails.setProspectId(prospectDetails.getProspectId());
                noteView.doAction(connection, "DELETE", null, noteDetails);
                
                //delete websites
                WebsiteView websiteView = new WebsiteView();
                WebsiteDetails websiteDetails = new WebsiteDetails();
                websiteDetails.setProspectId(prospectDetails.getProspectId());
                websiteView.doAction(connection, "DELETE", null, websiteDetails);
                
                //delete contacts
                ContactView contactView = new ContactView();
                ContactDetails contactDetails = new ContactDetails();
                contactDetails.setProspectId(prospectDetails.getProspectId());
                contactView.doAction(connection, "DELETE", null, contactDetails);
                
                //delete prospectDocuments
                ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
                ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
                prospectDocumentDetails.setProspectId(prospectDetails.getProspectId());
                prospectDocumentView.doAction(connection, "DELETE", null, prospectDocumentDetails);

                //delete prospectSalesActions
                ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
                ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
                prospectSalesActionDetails.setProspectId(prospectDetails.getProspectId());
                prospectSalesActionView.doAction(connection, "DELETE", null, prospectSalesActionDetails);
                
                //delete prospectCampaigns
                ProspectCampaignDetails prospectCampaignDetails = new ProspectCampaignDetails();
                ProspectCampaignView prospectCampaignView = new ProspectCampaignView();
                prospectCampaignDetails.setProspectId(prospectDetails.getProspectId());
                prospectCampaignView.doAction(connection, "DELETE", null, prospectCampaignDetails);
                
                // delete prospect
                prospectView.doAction(connection, "DELETE", null, prospectDetails);

                sessionProspectView.removeElement(elementIndex);
                request.getSession().setAttribute("prospectView", sessionProspectView);


                commit();
            }
        } catch (ModelException modelException) {
            request.setAttribute("modelException", modelException);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method displayInsertNote.
     */
    private void displayInsertNote() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            request.getRequestDispatcher("/jsp/shared/note/displayInsertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateNote.
     */
    private void displayUpdateNote() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            NoteDetails noteDetails = new NoteDetails();
            noteDetails.setNoteId(new BigDecimal(request.getParameter("dfNoteId")));

            NoteView noteView = new NoteView();
            noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
            if (noteView.getElements().size() == 1) {
                noteDetails = (NoteDetails) noteView.getElements().elementAt(0);
                request.setAttribute("noteDetails", noteDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/note/displayInsertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteNote.
     */
    private void displayDeleteNote() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            NoteDetails noteDetails = new NoteDetails();
            noteDetails.setNoteId(new BigDecimal(request.getParameter("dfNoteId")));

            NoteView noteView = new NoteView();
            noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
            if (noteView.getElements().size() == 1) {
                noteDetails = (NoteDetails) noteView.getElements().elementAt(0);
                request.setAttribute("noteDetails", noteDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/note/displayInsertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertWebsite.
     */
    private void displayInsertWebsite() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            request.getRequestDispatcher("/jsp/shared/website/displayInsertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateWebsite.
     */
    private void displayUpdateWebsite() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            WebsiteDetails websiteDetails = new WebsiteDetails();
            websiteDetails.setWebsiteId(new BigDecimal(request.getParameter("dfWebsiteId")));

            WebsiteView websiteView = new WebsiteView();
            websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
            if (websiteView.getElements().size() == 1) {
                websiteDetails = (WebsiteDetails) websiteView.getElements().elementAt(0);
                request.setAttribute("websiteDetails", websiteDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/website/displayInsertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteWebsite.
     */
    private void displayDeleteWebsite() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            WebsiteDetails websiteDetails = new WebsiteDetails();
            websiteDetails.setWebsiteId(new BigDecimal(request.getParameter("dfWebsiteId")));

            WebsiteView websiteView = new WebsiteView();
            websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
            if (websiteView.getElements().size() == 1) {
                websiteDetails = (WebsiteDetails) websiteView.getElements().elementAt(0);
                request.setAttribute("websiteDetails", websiteDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/website/displayInsertUpdateWebsite.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertDocument.
     */
    private void displayInsertDocument() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
           // ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            request.getSession().setAttribute("prospectDetails", prospectDetails);
            DocumentView documentView = new DocumentView();
            documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, new DocumentDetails());
            request.setAttribute("documentView", documentView);
            commit();
            request.getRequestDispatcher("/jsp/shared/prospectDocument/displayInsertUpdateProspectDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateDocument.
     */
    private void displayUpdateDocument() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            DocumentView documentView = new DocumentView();
            documentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, new DocumentDetails());
            request.setAttribute("documentView", documentView);

            ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
            prospectDocumentDetails.setProspectDocumentId(new BigDecimal(request.getParameter("dfProspectDocumentId")));

            ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
            prospectDocumentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);
            if (prospectDocumentView.getElements().size() == 1) {
                prospectDocumentDetails = (ProspectDocumentDetails) prospectDocumentView.getElements().elementAt(0);
                request.setAttribute("prospectDocumentDetails", prospectDocumentDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/prospectDocument/displayInsertUpdateProspectDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteDocument.
     */
    private void displayDeleteDocument() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
            prospectDocumentDetails.setProspectDocumentId(new BigDecimal(request.getParameter("dfProspectDocumentId")));

            ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
            prospectDocumentView.fillWithElements(connection, DocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);
            if (prospectDocumentView.getElements().size() == 1) {
                prospectDocumentDetails = (ProspectDocumentDetails) prospectDocumentView.getElements().elementAt(0);
                request.setAttribute("prospectDocumentDetails", prospectDocumentDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/prospectDocument/displayInsertUpdateProspectDocument.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertContact.
     */
    private void displayInsertContact() {
        try {
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            openConnection();
            request.getSession().setAttribute("prospectDetails", prospectDetails);

            request.getRequestDispatcher("/jsp/shared/contact/displayInsertUpdateContact.jsp").forward(request, response);
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateContact.
     */
    private void displayUpdateContact() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setContactId(new BigDecimal(request.getParameter("dfContactId")));

            ContactView contactView = new ContactView();
            contactView.fillWithElements(connection, ContactDetails.FILL_TYPE_ALL, contactDetails);
            if (contactView.getElements().size() == 1) {
                contactDetails = (ContactDetails) contactView.getElements().elementAt(0);
                request.setAttribute("contactDetails", contactDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/contact/displayInsertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteContact.
     */
    private void displayDeleteContact() {
        try {
            openConnection();
            ProspectDetails prospectDetails = (ProspectDetails) request.getSession().getAttribute("prospectDetails");
            //ProspectControllerHelper.getProspectDataFromRequest(request, prospectDetails, userProfile);
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setContactId(new BigDecimal(request.getParameter("dfContactId")));

            ContactView contactView = new ContactView();
            contactView.fillWithElements(connection, ContactDetails.FILL_TYPE_ALL, contactDetails);
            if (contactView.getElements().size() == 1) {
                contactDetails = (ContactDetails) contactView.getElements().elementAt(0);
                request.setAttribute("contactDetails", contactDetails);
            }

            request.getSession().setAttribute("prospectDetails", prospectDetails);
            commit();
            request.getRequestDispatcher("/jsp/shared/contact/displayInsertUpdateContact.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

	private BigDecimal assignToRoundRobin(ProspectDetails prospectDetails) throws Exception {

    	TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
		TerritoryView searchRoundTerritoryView = new TerritoryView();
		
		searchTerritoryDetails.setIncludeInRoundRobin(TerritoryDetails.INCLUDED_IN_ROUND_ROBIN);
		searchRoundTerritoryView.setOrderBy("t.territory_id");
		searchRoundTerritoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
		
		TerritoryDetails territoryDetails;
//		boolean setRound = false;
		TerritoryDetails firstTerritoryToUse = (TerritoryDetails)searchRoundTerritoryView.getElement(0);
		int firstId = firstTerritoryToUse.getTerritoryId().intValue();
		int nextIdToUse = firstTerritoryToUse.getTerritoryId().intValue();
		
		int currentPosition = 0;
		int nextPosition = 0;
		//System.out.println("\n\n=========== SETTING ROUND ROBIN TERRITORY ID ============\n");
		//Step 1 find the last territory that got a prospect assigned thru RR
		if(searchRoundTerritoryView.getElements().size() > 0){
			for(int i = 0; i < searchRoundTerritoryView.getElements().size(); i++){
				
				territoryDetails = (TerritoryDetails)searchRoundTerritoryView.getElement(i);
				if(territoryDetails.getRoundRobinTerritorySelected()!= null &&
						territoryDetails.getRoundRobinTerritorySelected().compareTo(TerritoryDetails.SELECTED_ROUND_ROBIN)==0){
					
					//Step 2 get the id of the next territory in the list, nextPostition is the next territory in the list
					nextPosition = i + 1;
					if(nextPosition >= searchRoundTerritoryView.getElements().size()){
						nextPosition = 0;
					}
					//Step 3 now get the territoryDetails for the next territory in the list, or the first if this was the end of the list.
					TerritoryDetails nextTerritoryDetails = (TerritoryDetails)searchRoundTerritoryView.getElement(nextPosition);
					nextIdToUse = nextTerritoryDetails.getTerritoryId().intValue();
					
					territoryDetails.setRoundRobinTerritorySelected(TerritoryDetails.NOT_SELECTED_ROUND_ROBIN);
					searchRoundTerritoryView.doAction(connection, "UPDATE", TerritoryView.FILL_TYPE_ALL, territoryDetails);
				}
			}
			
			// step 4 now set the prospect territory id to the next territory in the round robin and update that territory
			for(int j = 0; j < searchRoundTerritoryView.getElements().size(); j++){
				TerritoryDetails territoryDetailsToUse = (TerritoryDetails)searchRoundTerritoryView.getElement(j);
				
				if(nextIdToUse == territoryDetailsToUse.getTerritoryId().intValue()){
					territoryDetailsToUse.setRoundRobinTerritorySelected(TerritoryDetails.SELECTED_ROUND_ROBIN);
					searchRoundTerritoryView.doAction(connection, "UPDATE", TerritoryView.FILL_TYPE_ALL, territoryDetailsToUse);
					return territoryDetailsToUse.getTerritoryId();//new territory
				}
			}

//		System.out.println("\n\n");
		}
		return prospectDetails.getTerritoryId();//original territory
	}

}
