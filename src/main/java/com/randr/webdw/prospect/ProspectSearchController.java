package com.randr.webdw.prospect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.randr.webdw.AppSettings;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.status.StatusView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.util.CollectionUtilities;
import com.randr.webdw.util.DateUtilities;


// import org.apache.xpath.operations.String;




/**
 */
public class ProspectSearchController extends ProspectBaseController {


    /**
     * Constructor for ProspectSearchController.
     */
    public ProspectSearchController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {
        if (formAction == null || formAction.equals("DISPLAY_SEARCH")) {
        	request.getSession().removeAttribute("isFromDuplicateSearch");
            displaySearch();
        } else if (formAction.equals("DISPLAY")) {
        	displayProspects();
        } else if (formAction.equals("REDO_DISPLAY")) {
            request.getSession().removeAttribute("prospectView");
            if(request.getSession().getAttribute("isFromDuplicateSearch")== "YES"){
            	searchForProspectDuplicates();
            }else{
            	displayProspects();
            }
        } else if (formAction.equals("REMOVE_SELECTED")) {
            removeSelected();
           
        } else if (formAction.equals("DISPLAY_FROM_CALENDAR")) {
        	request.getSession().removeAttribute("prospectView");
        	request.getSession().removeAttribute("prospectDetails");
        	request.getSession().removeAttribute("searchProspectDetails");
            displayProspects();
        } else if (formAction.equals("DISPLAY_SEARCH_DUPLICATES")) {
        	request.getSession().setAttribute("isFromDuplicateSearch", "YES");     	
        	displaySearchDuplicates();      	
        } else if (formAction.equals("SEARCH_FOR_PROSPECT_DUPLICATES")) {
        	searchForProspectDuplicates();
        } else if (formAction.equals("DELETE_SELECTED")){
        	deleteSelected();
        	searchForProspectDuplicates();
        } else if(formAction.equals("DISPLAY_CURRENT_ACTIONS")){
        	displayCurrentActions();
        }
    }

	private void displayCurrentActions() {
        try {
            openConnection();

            clearProspectDetailsSession();
//            if(request.getSession().getAttribute("prospectDetails") != null){
//            	request.getSession().removeAttribute("prospectDetails");
//            }
            	    	
	    	//getting territory owner
	    	TerritoryView searchTerritoryView = new TerritoryView();
	    	TerritoryDetails searchTerritoryDetails = new TerritoryDetails();	    	

	    	String currentSearchBy = request.getParameter("currentSearchBy");
	    	String currentSearchByTitle = "";
	    	if(currentSearchBy != null){
		    	if(currentSearchBy.equals("serviceManager")){
//		    		System.out.println("\n\nSearch Service\n\n");
		    		searchTerritoryDetails.setServiceManagerUserId(userProfile.getUserId());
		    		currentSearchByTitle = "for Territories Im Service Manager of";
		    	}
		    	
		    	if(currentSearchBy.equals("salesManager")){
//		    		System.out.println("\n\nSearch Sales\n\n");
		    		searchTerritoryDetails.setSalesManagerUserId(userProfile.getUserId());
		    		currentSearchByTitle = "for Territories In Sales Manager of";
		    	}
		    	
		    	if(currentSearchBy.equals("territoryOwner")){
//		    		System.out.println("\n\nSearch territoryOwner\n\n");
		    		searchTerritoryDetails.setOwnerUserId(userProfile.getUserId());
		    		currentSearchByTitle = "for My Territories";
		    	}
		    	request.setAttribute("currentSearchBy", currentSearchBy);
	    	
	    	}else{
	    		searchTerritoryDetails.setOwnerUserId(userProfile.getUserId());
	    		currentSearchByTitle = "for My Territories";
	    		request.setAttribute("currentSearchBy", "territoryOwner");
	    	}
	    	
	    	request.setAttribute("currentSearchByTitle", currentSearchByTitle);
//	    	System.out.println("searchTerritoryDetails = " + searchTerritoryDetails.toString());
	    	
	    	searchTerritoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
	    	
	    	//System.out.println("searchTerritoryView = " + searchTerritoryView.getElements().size());
	    	if(searchTerritoryView.getElements().size() > 0){
	    		//this next section, I will get all territories
	    		//that the salesman who is logged in owns
		    	List territoryIdList = new ArrayList();
//		    	System.out.println("searchTerritoryView.getElements().size() = " + searchTerritoryView.getElements().size());
		    	for(int i = 0; i < searchTerritoryView.getElements().size(); i++){
			    	TerritoryDetails territoryDetails = (TerritoryDetails)searchTerritoryView.getElement(i);
//		    		System.out.println("" + searchTerritoryView.getTerritoryName(territoryDetails.getTerritoryId()));

		    		territoryIdList.add(territoryDetails.getTerritoryId());
		    	}
		    	
	            ProspectDetails searchProspectDetails = new ProspectDetails();
		    	ProspectView searchProspectView = new ProspectView();
		    	searchProspectDetails.setTerritoryIdList(territoryIdList);
		    	
		    	searchProspectDetails.setCurrentSalesActionStatus(SalesActionDetails.ACTIVE);
		    	searchProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_SALES_ACTION_SEARCH, searchProspectDetails);
		    		    	
//		    	System.out.println("prospectView.getElements().size() = " + prospectView.getElements().size());
//		    	long todaysDate = DateUtilities.getCurrentSQLDate().getTime()/(24*60*60*1000);
		    	Calendar todaysDate = getGregorianCalendarDate(DateUtilities.getCurrentSQLTimestamp());
		    	
//		    	System.out.println("todaysDate = ");
		    	ProspectView currentProspectView = new ProspectView();
		    	for(int i=0; i < searchProspectView.getElements().size(); i++){
		    		ProspectDetails prospectDetails = (ProspectDetails)searchProspectView.getElement(i);
		    		
		    		if(prospectDetails.getCurrentSalesActionDate()!=null){;
		    			Calendar actionDate = getGregorianCalendarDate(prospectDetails.getCurrentSalesActionDate());
		    			long diffMillis = todaysDate.getTimeInMillis() - actionDate.getTimeInMillis();
		    			long diffDays = diffMillis/(24*60*60*1000);  
		        	//System.out.println(prospectDetails.getProspectId() + " " + prospectDetails.getCurrentSalesActionDate() + " diffDays " + diffDays);
			    		if(request.getParameter("includePassedDue")!= null && request.getParameter("includePassedDue").equals("YES")){
				    		if(diffDays >= 0 ){
				    			currentProspectView.addElement(prospectDetails);
				    		}
			    		}else{
				    		if(diffDays == 0){
				    			currentProspectView.addElement(prospectDetails);
				    		}
			    		}
		    		}
		    	}
//		    	
		    	
//		    	for(int i = 0; i < currentProspectView.getElements().size(); i++){
//		    		ProspectDetails prospectDetails = (ProspectDetails)currentProspectView.getElement(i);
//		    		System.out.println("action = " + prospectDetails.getCurrentSalesActionDate());
//		    	}
		    	
		    	if(request.getParameter("dfPopup")!= null && request.getParameter("dfPopup").equals("YES")){
		    		int reminders = 0;
		    		String alertUser = "";
		    		int alertMinutes = 0;
		    		if(!AppSettings.getParm("SALES_ACTION_ALERT_USER_MINUTES").equals("")){
		    			alertMinutes = Integer.parseInt(AppSettings.getParm("SALES_ACTION_ALERT_USER_MINUTES"));
		    		}else{
		    			alertMinutes = 15;
		    		}
		    			
		    		for(int i = 0; i < currentProspectView.getElements().size(); i++){
			    		ProspectDetails prospectDetails = (ProspectDetails)currentProspectView.getElement(i);

			    		//Timer is set in AppConfig
			    		//Default settings is at 15 minutes
			    		long salesTime = prospectDetails.getCurrentSalesActionDate().getTime();
			    		long currentTime = DateUtilities.getCurrentSQLTimestamp().getTime();
			    		
			    		long difference = salesTime - currentTime;
			    		long minutes = difference/ 60000;

			    		if(minutes <= alertMinutes){
			    			reminders++;
			    		}
			    	}
		    		String checkBox = "checked";
//		    		System.out.println("noise?? - " + request.getParameter("ckNoise"));
		    		if(request.getParameter("ckNoise") == null){
//		    			System.out.println("noise again - " + request.getParameter("ckNoise"));
		    			if(reminders > 0){
			    			alertUser = "YES";	
			    		}
		    			checkBox = "";	
		    		}
		    		request.setAttribute("checkBox", checkBox);
		    		request.setAttribute("alert", alertUser);
		    	}
		    	
		    	SalesActionDetails salesActionDetails = new SalesActionDetails();
		    	SalesActionView salesActionView = new SalesActionView();
		    	salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
		    	
		    	TerritoryDetails territoryDetails = new TerritoryDetails();
		    	TerritoryView territoryView = new TerritoryView();	
		    	territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, territoryDetails);
		    	
		    	CollectionUtilities.sortVector(currentProspectView.getElements(), "getCurrentSalesActionDate",1, false);
		    	
		    	request.setAttribute("territoryTodayView", territoryView);
		    	request.setAttribute("salesActionTodayView", salesActionView);
		    	request.setAttribute("prospectTodayView", currentProspectView);

		    	currentProspectView.createUniqueKeyCollectionHashtable("getProspectId");
		    	request.getSession().setAttribute("prospectTodayView", currentProspectView);
	    	}

	    	commit();
	    	
	    	if(request.getParameter("dfPopup")!= null && request.getParameter("dfPopup").equals("YES")){
	    		forward("/jsp/public/currentActions/displayCurrentActionsPopup.jsp");
	    	}else{
	    		forward("/jsp/public/currentActions/displayCurrentActions.jsp");
	    	}
	    	
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	finallyMethod();
        }
		
	}
	
	private void clearProspectDetailsSession() {
		request.getSession().removeAttribute("prospectDetails");
	}

	private void setUserStatuses(ProspectDetails searchProspectDetails) throws Exception {
	    if (userProfile != null) {
	    	if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
	    			userProfile.getSpecificStatuses()== null ||
	    			userProfile.getSpecificStatuses().compareTo(UserDetails.NO_SPECIFIC_STATUSES) == 0){
	    		searchProspectDetails.setStatusIdList(null);
	    	}else{
				StatusView salesmanStatusesView = getSalesmanStatusesView(userProfile.getUserId());

				if(salesmanStatusesView.getElements().size()> 0){
					searchProspectDetails.setStatusIdList(salesmanStatusesView.getStatusIdsList());				
				}
	    	}
	    }
//	    System.out.println("getStatusesIdsList = " + searchProspectDetails.getStatusIdList());
		
	}
	
	private void setUserLobs(ProspectDetails searchProspectDetails) throws Exception {
	    if (userProfile != null) {
	    	if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
	    			userProfile.getSpecificLobs()==null ||
	    			userProfile.getSpecificLobs().compareTo(UserDetails.NO_SPECIFIC_LOBS) == 0){
	    		searchProspectDetails.setLobIdList(null);
	    	}else{
				LobView salesmanLobView = getSalesmanLobsView(userProfile.getUserId());

				if(salesmanLobView.getElements().size()> 0){
					searchProspectDetails.setLobIdList(salesmanLobView.getLobIdsList());				
				}
	    	}
	    }
	   // System.out.println("getLobsIdsList = " + searchProspectDetails.getLobIdList());
		
	}
	
	private void setUserTerritories(ProspectDetails searchProspectDetails) throws Exception {
		
		if (userProfile != null) {
	    	if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
	    			userProfile.getSpecificTerritories()==null  ||
	    			userProfile.getSpecificTerritories().compareTo(UserDetails.NO_SPECIFIC_TERRITORIES) == 0){
	    		searchProspectDetails.setTerritoryIdList(null);
	    	}else{
				TerritoryView salesmanTerritoriesView = getSalesmanTerritoriesView(userProfile.getUserId());
				
				if(salesmanTerritoriesView.getElements().size()> 0){
					searchProspectDetails.setTerritoryIdList(salesmanTerritoriesView.getTerritoryIdsList());	
				}
	    	}
	    }
    
//		System.out.println("getTerritoryIdList = " + searchProspectDetails.getTerritoryIdList());

	}

	private void displaySearchDuplicates() {
    	try {
			forward("/jsp/public/duplicateSearch/displaySearchDuplicates.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
  

    
    /**
     * Method displaySearch.
     */
    private void displaySearch() {
        try {
        	request.getSession().removeAttribute("searchProspectDetails");
            request.getSession().removeAttribute("prospectDetails");
            request.getSession().removeAttribute("prospectView");
            removeAdditionalDataFromSession();
            openConnection();

            loadAdditionalData(getCurrentSalesmanDetails());
			
            // forward request
            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/prospect/displaySearchProspects.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/prospect/displaySearchProspects.jsp").forward(request, response);
            }

            commit();
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }    
    
    /**
     * Method removeSelected.
     */
    private void removeSelected() {
        try {

            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            ProspectView newProspectView = new ProspectView();
            
            if (sessionProspectView != null) {
                for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
                    if (request.getParameter("chkId" + String.valueOf(i)) == null) {
                    	newProspectView.addElement((ProspectDetails) sessionProspectView.getElements().elementAt(i));
                    }
                }
            }
            newProspectView.createUniqueKeyCollectionHashtable("getProspectId");

            request.getSession().setAttribute("prospectView", newProspectView);
            request.setAttribute("prospectView", newProspectView);

            if(request.getSession().getAttribute("isFromDuplicateSearch")== "YES"){
            	forward("/jsp/public/duplicateSearch/displayProspects.jsp");
            }else{
            	forward("/jsp/public/prospect/displayProspects.jsp");
            }
          
        } catch (Exception e) {
            doCatch(e);
        } finally {

        }
    }
    
    /**
     * Method removeSelected.
     */
    private void changeAllSelected() {
        try {

            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
            ProspectView newProspectView = new ProspectView();
            
            if (sessionProspectView != null) {
                for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
                    if (request.getParameter("chkId" + String.valueOf(i)) == null) {
                    	newProspectView.addElement((ProspectDetails) sessionProspectView.getElements().elementAt(i));
                    }
                }
            }
            newProspectView.createUniqueKeyCollectionHashtable("getProspectId");

            request.getSession().setAttribute("prospectView", newProspectView);
            request.setAttribute("prospectView", newProspectView);

            if(request.getSession().getAttribute("isFromDuplicateSearch")== "YES"){
            	forward("/jsp/public/duplicateSearch/displayProspects.jsp");
            }else{
            	forward("/jsp/public/prospect/displayProspects.jsp");
            }
          
        } catch (Exception e) {
            doCatch(e);
        } finally {

        }
    }
}
