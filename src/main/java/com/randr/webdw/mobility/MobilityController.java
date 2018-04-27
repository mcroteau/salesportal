package com.randr.webdw.mobility;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;

import com.randr.webdw.calendarSchedule.CalendarScheduleDetails;
import com.randr.webdw.calendarSchedule.CalendarScheduleView;
import com.randr.webdw.contact.ContactDetails;
import com.randr.webdw.contact.ContactView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.prospect.ProspectBaseController;
import com.randr.webdw.prospect.ProspectController;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.prospectDocument.ProspectDocumentDetails;
import com.randr.webdw.prospectDocument.ProspectDocumentView;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails;
import com.randr.webdw.prospectSalesAction.ProspectSalesActionView;
import com.randr.webdw.staticPages.MenuView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.util.CollectionUtilities;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.website.WebsiteDetails;
import com.randr.webdw.website.WebsiteView;

public class MobilityController extends ProspectBaseController{
	
	private String prospectId = "";
	private static boolean mobility = false;
    /**
     * Constructor for StaticPagesController.
     */
    public MobilityController() {
//    	System.out.println("inside MobilityController constructor");
    }

    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	if(formAction.equals("LOGIN")){
    		doLogin();
    	}else if (formAction.equals("DISPLAY_SEARCH")) {
    		displaySearch();
    	}else if (formAction.equals("SEARCH")) {
    		searchProspects();
    	}else if(formAction.equals("DISPLAY_PROSPECT")){
    		displayProspect();
    	}else if(formAction.equals("DISPLAY_PROSPECT_DETAILS")){
    		displayProspectDetails();
    	}else if(formAction.equals("DISPLAY_PROSPECT_NOTES")){
    		displayProspectNotes();
    	}else if(formAction.equals("DISPLAY_PROSPECT_SALES_ACTIONS")){
    		displayProspectSalesActions();
    	}else if(formAction.equals("DISPLAY_PROSPECT_SALES_ACTION_NOTES")){
    		displayProspectSalesActionNotes();
    	}else if(formAction.equals("DISPLAY_PROSPECT_WEBSITES")){
    		displayProspectWebsites();
    	}else if(formAction.equals("DISPLAY_PROSPECT_CONTACTS")){
    		displayProspectContacts();
    	}else if(formAction.equals("TODAYS_SALES_ACTION")){
    		displayTodaysSalesActions();
    	}else if(formAction.equals("MAIN_MENU")){
    		displayMainMenu();
    	}else if(formAction.equals("FULL_SALESPORTAL")){
    		displayMenu();
    		request.getRequestDispatcher("/jsp/public/home.jsp").forward(request, response);
    	}else if(formAction.equals("LOGOUT")){
            request.getSession().removeAttribute("userProfile");
            request.getSession().invalidate();
            request.getRequestDispatcher("/jsp/public/user/login.jsp").forward(request, response);	
    	}
    }

	private void displayProspectSalesActionNotes() {
		try {
			openConnection();
			loadProspectDetails();
			forward("/jsp/public/mobility/displayProspectSalesActionNotes.jsp");
			
			finallyMethod();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void displayTodaysSalesActions() {
		try{
			openConnection();

			Timestamp todaysDate = DateUtilities.getSQLTimestamp(request.getParameter("dfSearchSalesActionDate")+ " 00:00:00", userProfile.getDateFormat());	

			CalendarScheduleDetails calendarScheduleDetails = new CalendarScheduleDetails();
			CalendarScheduleView calendarScheduleView = new CalendarScheduleView();
			
			calendarScheduleDetails.setNextSalesActionDate(todaysDate);
			
//			calendarScheduleDetails.setTerritoryIdList();
//			calendarScheduleDetails.setStatusIdList(getStatuses(request));
			
			calendarScheduleView.fillWithElements(connection, CalendarScheduleDetails.FILL_TYPE_DAY, calendarScheduleDetails);
			
			for(int i=0; i<calendarScheduleView.getElements().size(); i++){
				CalendarScheduleDetails details = (CalendarScheduleDetails) calendarScheduleView.getElement(i);
			}
			
			request.setAttribute("calendarScheduleView", calendarScheduleView);
			forward("/jsp/public/mobility/displayTodaysSalesActions.jsp");
			finallyMethod();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void displayMainMenu() {
		try {
			forward("/jsp/public/mobility/mainMenuMobility.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void displayProspectContacts() {
		try {
			openConnection();
			loadProspectDetails();
			forward("/jsp/public/mobility/displayProspectContactInfoMobility.jsp");
			
			finallyMethod();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void displayProspectWebsites() {
		try {
			openConnection();
			loadProspectDetails();
			forward("/jsp/public/mobility/displayProspectWebsiteInfoMobility.jsp");
			
			finallyMethod();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void displayProspectSalesActions() {
		try {
			openConnection();
			loadProspectDetails();
			forward("/jsp/public/mobility/displayProspectSalesActionInfoMobility.jsp");
			
			finallyMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void displayProspectNotes() {
		try {
			
			openConnection();
			loadProspectDetails();
			forward("/jsp/public/mobility/displayProspectNoteInfoMobility.jsp");
			
			finallyMethod();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void displayProspectDetails() {
		try {			
			openConnection();
			BigDecimal prospectId = new BigDecimal(request.getParameter("dfProspectId"));
			ProspectDetails searchProspectDetails = new ProspectDetails();
			ProspectView prospectView = new ProspectView();
			
			searchProspectDetails.setProspectId(prospectId);
			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);		
			
			ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElement(0);
			setProspectInformation(prospectDetails, prospectId);
			forward("/jsp/public/mobility/displayProspectDetailInfoMobility.jsp");
			
			finallyMethod();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void displayProspect() {
		try {
			openConnection();
			
			BigDecimal prospectId = new BigDecimal(request.getParameter("dfProspectId"));
			ProspectDetails searchProspectDetails = new ProspectDetails();
			ProspectView prospectView = new ProspectView();
			
			searchProspectDetails.setProspectId(prospectId);
			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);		
			
			ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElement(0);
			setProspectInformation(prospectDetails, prospectId);

			forward("/jsp/public/mobility/displayProspectMobility.jsp");
			finallyMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void setProspectInformation(ProspectDetails prospectDetails, BigDecimal prospectId) {
		try {
			removeAdditionalDataFromSession();
	        loadAdditionalData(getCurrentSalesmanDetails());
	 
	 		request.getSession().setAttribute("prospectDetails", prospectDetails);
	        request.setAttribute("prospectDetails", prospectDetails);
	            
			NoteView noteView = new NoteView();
			NoteDetails noteDetails = new NoteDetails();
	
			noteDetails.setProspectId(prospectId);
			noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
			request.setAttribute("noteView", noteView);
			
			WebsiteView websiteView = new WebsiteView();
			WebsiteDetails websiteDetails = new WebsiteDetails();
	
			websiteDetails.setProspectId(prospectId);
			websiteView.fillWithElements(connection, WebsiteDetails.FILL_TYPE_ALL, websiteDetails);
			request.setAttribute("websiteView", websiteView);
	
//			ProspectDocumentView prospectDocumentView = new ProspectDocumentView();
//			ProspectDocumentDetails prospectDocumentDetails = new ProspectDocumentDetails();
//	
//			prospectDocumentDetails.setProspectId(prospectId);
//			prospectDocumentView.fillWithElements(connection, ProspectDocumentDetails.FILL_TYPE_ALL, prospectDocumentDetails);
//			request.setAttribute("prospectDocumentView", prospectDocumentView);
			
			ProspectSalesActionView prospectSalesActionView = new ProspectSalesActionView();
			ProspectSalesActionDetails prospectSalesActionDetails = new ProspectSalesActionDetails();
	
			prospectSalesActionDetails.setProspectId(prospectId);
			prospectSalesActionView.fillWithElements(connection, prospectSalesActionDetails.FILL_TYPE_ALL, prospectSalesActionDetails);
			request.setAttribute("prospectSalesActionView", prospectSalesActionView);
			
			ContactView contactView = new ContactView();
			ContactDetails contactDetails = new ContactDetails();
	
			contactDetails.setProspectId(prospectId);
			contactView.fillWithElements(connection, ContactDetails.FILL_TYPE_ALL, contactDetails);
			request.setAttribute("contactView", contactView);
			
//			System.out.println("noteView = " + noteView.getElements().size());
//			System.out.println("websiteView = " + websiteView.getElements().size() + "\n" +
//					"prospectSalesActionView = " +prospectSalesActionView.getElements().size() + "\n" +
//					"contactView = " + contactView.getElements().size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void doLogin() throws ServletException, IOException {
		this.setMobility(true);
    	this.request.getSession().setAttribute("mobility", new Boolean (this.isMobility()));
		if (request.getServletPath().equals("/home")) {
			if (request.getParameter("initialProspectId") != null) {
				request.getRequestDispatcher("/prospect_search?formAction=DISPLAY&dfSearchProspectId=" + request.getParameter("initialProspectId")).forward(request, response);
			} else {
				request.getRequestDispatcher("/jsp/public/mobility/mainMenuMobility.jsp").forward(request, response);
			}
		}else {
			request.getRequestDispatcher("/jsp/public/mobility/mainMenuMobility.jsp").forward(request, response);
		}		
	}

	private void displaySearch() {
        try {
            request.getSession().removeAttribute("searchProspectDetails");
            request.getSession().removeAttribute("prospectDetails");
            request.getSession().removeAttribute("prospectView");

            openConnection();
            loadAdditionalData(getCurrentSalesmanDetails());
            // forward request
            if (isAdmin()) {
            	forward("/jsp/admin/mobility/displaySearchProspectsMobility.jsp");
            } else {
                forward("/jsp/public/mobility/displaySearchProspectsMobility.jsp");
            }
            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
	}

	private void searchProspects() {
		this.setMobility(true);
		displayProspects();	
    }

	public static boolean isMobility() {
		return mobility;
	}

	public void setMobility(boolean mobility) {
		this.mobility = mobility;
	}
	
    /**
     * Method removeAdditionalDataFromSession.
     * @throws Exception
     */
    public void removeAdditionalDataFromSession() throws Exception {
        request.getSession().removeAttribute("companyView");
        request.getSession().removeAttribute("territoryView");
        request.getSession().removeAttribute("lobView");
        request.getSession().removeAttribute("countryView");
        request.getSession().removeAttribute("stateView");
        request.getSession().removeAttribute("systemTypeView");
        request.getSession().removeAttribute("softwareTypeView");
        request.getSession().removeAttribute("statusView");
        request.getSession().removeAttribute("verifiedView");
        request.getSession().removeAttribute("salesActionView");
    }
    
    public void loadProspectDetails(){
    	try {
			BigDecimal prospectId = new BigDecimal(request.getParameter("dfProspectId"));
			ProspectDetails searchProspectDetails = new ProspectDetails();
			ProspectView prospectView = new ProspectView();
			
			searchProspectDetails.setProspectId(prospectId);
			prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);		
			
			ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElement(0);
			setProspectInformation(prospectDetails, prospectId);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    }
    /**
     * Method displayProspects.
     */
    public void displayProspects() {
        try {
            openConnection();
            
            request.getSession().removeAttribute("prospectDetails");
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");

            
            if (sessionProspectView == null) {

                ProspectDetails searchProspectDetails = (ProspectDetails) request.getSession().getAttribute("searchProspectDetails");

                if (searchProspectDetails == null) {
                    searchProspectDetails = new ProspectDetails();
                    getProspectSearchInformation(searchProspectDetails, getCurrentSalesmanDetails());
                    request.getSession().setAttribute("searchProspectDetails", searchProspectDetails);
                }
                ProspectView prospectView = new ProspectView();
                prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);
 
                try {
                    if (prospectView.getElements().size() == 0) {
                        throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                    }
                    if (searchProspectDetails.getSearchNextSalesActionDate()!=null || searchProspectDetails.getSearchNextSalesActionId()!=null ){
                    	Set uniqueProspectIdSet = createUniqueSearchSetProspectId(prospectView);
	                    ProspectDetails prospectDetails = new ProspectDetails();
	                    prospectDetails.setIdsVector(CollectionUtilities.setToVector(uniqueProspectIdSet));
	                    prospectView = new ProspectView();
	                    prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, prospectDetails);
                    }
                    prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                    sortProspects(prospectView);
                    request.setAttribute("prospectView", prospectView);
                    request.getSession().setAttribute("prospectView", prospectView);
                    
                } catch (ModelException modelException) {
                    request.setAttribute("modelException", modelException);
                }
                commit();
            } else {
                sortProspects(sessionProspectView);
                request.getSession().setAttribute("prospectView", sessionProspectView);
                request.setAttribute("prospectView", sessionProspectView);
            }

            forward("/jsp/public/mobility/displayProspectSearchResultMobility.jsp");

        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
    
    public void getProspectSearchInformation(ProspectDetails prospectDetails, UserDetails salesmanDetails) {
    	
//    	System.out.println("inside getProspectInfo");
//    	System.out.println("country = " + request.getParameter("dfSearchCountryId"));
//    	System.out.println("state = " + request.getParameter("dfSearchStateId"));
//    	System.out.println("company = " + request.getParameter("dfSearchCustomerCompany"));
    	
		if (request.getParameter("dfSearchProspectId") != null
				&& !request.getParameter("dfSearchProspectId").equals("")) {
			prospectDetails.setProspectId(new BigDecimal(request.getParameter("dfSearchProspectId")));
		}
		
		if (request.getParameter("dfSearchCompanyId") != null
				&& !request.getParameter("dfSearchCompanyId").equals("")) {
			//user selected an option
			prospectDetails.setCompanyId(new BigDecimal(request.getParameter("dfSearchCompanyId")));
		} else {
			//user did not select an option, check the user authority level
			if (salesmanDetails != null) {
				prospectDetails.setCompanyId(salesmanDetails.getCompanyId());
			}
		}
		
		if (request.getParameter("dfSearchContactName") != null
				&& !request.getParameter("dfSearchContactName").equals("")) {
		
			prospectDetails.setContactName(request.getParameter("dfSearchContactName"));
		}
		
		if (request.getParameter("dfSearchCountryId") != null
				&& !request.getParameter("dfSearchCountryId").equals("")) {
			prospectDetails.setCountryId(new BigDecimal(request.getParameter("dfSearchCountryId")));
		} else {
			//user did not select an option, check the user authority level
			if (salesmanDetails != null) {
				prospectDetails.setCountryId(salesmanDetails.getCountryId());
			}
		}
		
		if (request.getParameter("dfSearchStateId") != null
				&& !request.getParameter("dfSearchStateId").equals("")) {
			prospectDetails.setStateId(new BigDecimal(request.getParameter("dfSearchStateId")));
		} else {
			if (salesmanDetails != null) {
				prospectDetails.setStateId(salesmanDetails.getStateId());
			}
		}
		
		if (request.getParameter("dfSearchCity") != null
				&& !request.getParameter("dfSearchCity").equals("")) {
			prospectDetails.setCity(request.getParameter("dfSearchCity"));
		}
		
		if (request.getParameter("dfSearchZip") != null
				&& !request.getParameter("dfSearchZip").equals("")) {
			prospectDetails.setZip(request.getParameter("dfSearchZip"));
		
		}
		
		if (request.getParameter("dfSearchTerritoryId") != null
				&& !request.getParameter("dfSearchTerritoryId").equals("")) {
			prospectDetails.setTerritoryId(new BigDecimal(request.getParameter("dfSearchTerritoryId")));
		} else {
			//user did not select an option, check the user authority level
			if (salesmanDetails != null) {
				prospectDetails.setTerritoryId(salesmanDetails.getTerritoryId());
			}
		}
		
		if (request.getParameter("dfSearchSalesActionDate") != null
				&& request.getParameter("dfSearchSalesActionDate") != null
				&& !request.getParameter("dfSearchSalesActionDate").equals("")) {
			prospectDetails.setSearchNextSalesActionDate(DateUtilities.getSQLDate(request.getParameter("dfSearchSalesActionDate"),userProfile.getDateFormat()));  
		}
		
		if (request.getParameter("dfSearchCustomerCompany") != null
				&& !request.getParameter("dfSearchCustomerCompany").equals("")) {
			prospectDetails.setCustomerCompany(request.getParameter("dfSearchCustomerCompany"));
		}
    }	
    
    /**
     * Method displayMenu.
     */
    private void displayMenu() {
        try {
        	System.out.println("userProfile.getUserType()"+userProfile.getUserType());
           // if (MenuView.getMenu(userProfile.getUserType(), request) == null) {
                MenuView.refreshMenu(userProfile.getUserType(), request);
            //}

           // request.getRequestDispatcher("/jsp/public/menu.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
