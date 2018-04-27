package com.randr.webdw.prospect;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.randr.webdw.AppSettings;
import com.randr.webdw.company.CompanyDetails;
import com.randr.webdw.company.CompanyView;
import com.randr.webdw.contact.ContactDetails;
import com.randr.webdw.contact.ContactView;
import com.randr.webdw.country.CountryDetails;
import com.randr.webdw.country.CountryView;
import com.randr.webdw.lob.LobDetails;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.salesAction.SalesActionDetails;
import com.randr.webdw.salesAction.SalesActionView;
import com.randr.webdw.size.SizeDetails;
import com.randr.webdw.size.SizeView;
import com.randr.webdw.softwareType.SoftwareTypeDetails;
import com.randr.webdw.softwareType.SoftwareTypeView;
import com.randr.webdw.state.StateDetails;
import com.randr.webdw.state.StateView;
import com.randr.webdw.status.StatusDetails;
import com.randr.webdw.status.StatusView;
import com.randr.webdw.systemType.SystemTypeDetails;
import com.randr.webdw.systemType.SystemTypeView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserView;
import com.randr.webdw.userLobs.UserLobsDetails;
import com.randr.webdw.userLobs.UserLobsView;
import com.randr.webdw.userStatuses.UserStatusesDetails;
import com.randr.webdw.userStatuses.UserStatusesView;
import com.randr.webdw.userTerritories.UserTerritoriesDetails;
import com.randr.webdw.userTerritories.UserTerritoriesView;
import com.randr.webdw.util.CollectionUtilities;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.verified.VerifiedDetails;
import com.randr.webdw.verified.VerifiedView;


/**
 */
/**
 * @author Ann
 *
 */
public abstract class ProspectBaseController extends AbstractController {
	String searchBy = "";
    /**
     * Method getCurrentSalesmanDetails.
     * @return UserDetails
     * @throws Exception
     */
    protected UserDetails getCurrentSalesmanDetails() throws Exception {
        UserView userView = new UserView();
        UserDetails currentSalesmanProspectDetails = new UserDetails();
        
        currentSalesmanProspectDetails.setUserId(userProfile.getUserId());
        userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, currentSalesmanProspectDetails);
        
        if (userView.getElements().size() == 1) {
            // load current salesman prospect profile
            currentSalesmanProspectDetails = (UserDetails) userView.getElements().elementAt(0);
        } else {
            currentSalesmanProspectDetails = null;
        }

        return currentSalesmanProspectDetails;
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
                ProspectView prospectView;
                
                if (searchProspectDetails.getSearchNextSalesActionDate()!=null || searchProspectDetails.getSearchNextSalesActionId()!=null ){
                	prospectView = new ProspectView();
                	prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_SALES_ACTION_SEARCH, searchProspectDetails);
                }else{
                	if(hasRandomTerritorySearch(searchProspectDetails) && formAction.equals("DISPLAY")){
                		//System.out.println("here... random territory");
                		ProspectView randomProspectView = new ProspectView();
                		randomProspectView.fillWithElements(connection, ProspectView.FILL_TYPE_RANDOM, searchProspectDetails);
//                		if(request.getParameter("chkIncludeRecentlyUpdated")!=null && 
//                				request.getParameter("chkIncludeRecentlyUpdated").equals("YES")){
//                			prospectView = randomProspectView;   			
//                		}else{
//                			prospectView = removeProspectsWithNewUpdateDates(randomProspectView);
//                		}
                		prospectView = randomProspectView;  
                	}else{
                		prospectView = new ProspectView();
                		
                		prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, searchProspectDetails);
                	}
                }	
                
                try {
                    
                    
                    if ((searchProspectDetails.getSearchNextSalesActionDate()!=null || searchProspectDetails.getSearchNextSalesActionId()!=null)
                    		&& prospectView.getElements().size()>0){
                    	Set uniqueProspectIdSet = createUniqueSearchSetProspectId(prospectView);
	                    ProspectDetails prospectDetails = new ProspectDetails();
	                    prospectDetails.setIdsVector(CollectionUtilities.setToVector(uniqueProspectIdSet));
	                    prospectView = new ProspectView();
	                    prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, prospectDetails);
                    }
//                  here search for contacts that match contact select criteria andd add to the prospectView
                    if (request.getParameter("dfSearchProspectContactName")!= null || request.getParameter("dfSearchProspectContactPhone") != null){
                    	ContactDetails contactDetails = new ContactDetails();
                    	ContactView contactView = new ContactView();
                    	 if (request.getParameter("dfSearchProspectContactName")!= null) {//this is a checkbox
                    		 contactDetails.setContactName(request.getParameter("dfSearchContactName"));             
                    	 }

                    	 if (request.getParameter("dfSearchProspectContactPhone") != null) {//this is a checkbox
                    		 contactDetails.setPhone(request.getParameter("dfSearchPhone"));             
                    	 }
                    	 contactView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, contactDetails);
                    	
                    	Set uniqueProspectIdSet = createUniqueSearchSetProspectId(prospectView,contactView);
                    	
	                    ProspectDetails prospectDetails = new ProspectDetails();
	                    prospectDetails.setIdsVector(CollectionUtilities.setToVector(uniqueProspectIdSet));
	                    prospectView = new ProspectView();
	                    prospectView.fillWithElements(connection, ProspectView.FILL_TYPE_REPORT, prospectDetails);
                    }
                    //moved because we may be picking up records in the contaact search
                    if (prospectView.getElements().size() == 0) {
                        throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                    }
                    
                    prospectView.createUniqueKeyCollectionHashtable("getProspectId");
                    establishNotesCount(prospectView);
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

            if (request.getParameter("initialProspectId") != null) {
                request.getRequestDispatcher("/prospect?formAction=DISPLAY_UPDATE&dfProspectId=" + request.getParameter("initialProspectId")).forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/prospect/displayProspects.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


	private ProspectView removeProspectsWithNewUpdateDates(ProspectView searchProspectView) throws Exception {
		// Method was created to remove prospects that 
    	Calendar todaysDate = getGregorianCalendarDate(DateUtilities.getCurrentSQLTimestamp());

    	ProspectView prospectView = new ProspectView();
    	for(int i = 0; i < searchProspectView.getElements().size(); i++){
    		ProspectDetails prospectDetails = (ProspectDetails)searchProspectView.getElement(i);
    		Calendar prospectUpdateDate;
    		
    		if(prospectDetails.getChangeDate()!= null){
    			prospectUpdateDate = getGregorianCalendarDate(prospectDetails.getChangeDate());
    		}else{
    			prospectUpdateDate = getGregorianCalendarDate(DateUtilities.getCurrentSQLTimestamp());
    		}
        	
    		long diffMillis = todaysDate.getTimeInMillis() - prospectUpdateDate.getTimeInMillis();
        	long diffHours = diffMillis/(60*60*1000);    
        	
        	//System.out.println(prospectDetails.getProspectId() + " " + prospectDetails.getChangeDate() + " diffHours " + diffHours);

    		//System.out.println();
    		if(diffHours >= 24 || prospectDetails.getChangeDate() == null){
    			prospectView.addElement(prospectDetails);
    		}
    	}
    	
		return prospectView;
	}


	private boolean hasRandomTerritorySearch(ProspectDetails searchProspectDetails) throws Exception {
		
    	TerritoryDetails searchTerritoryDetails;
    	TerritoryView territoryView;

    	if(searchProspectDetails.getTerritoryIdList()!= null){
	    	for(int i = 0; i < searchProspectDetails.getTerritoryIdList().size(); i++){
	    		
	    		BigDecimal prospectTerrId = new BigDecimal(searchProspectDetails.getTerritoryIdList().get(i).toString());
	    		searchTerritoryDetails = new TerritoryDetails();
	    		territoryView = new TerritoryView();
	    		searchTerritoryDetails.setTerritoryId(prospectTerrId);
	    		territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
	    		
	    		TerritoryDetails details;
	    		if(territoryView.getElements().size() > 0){
	    			for(int j = 0; j < territoryView.getElements().size(); j++){
	    				details = (TerritoryDetails)territoryView.getElement(j);
	    				if(details.getRandomSequence()!= null &&
	    						details.getRandomSequence().compareTo(TerritoryDetails.RANDOM) == 0){
	    					searchProspectDetails.setSearchLimit(details.getMaxProspectDisplay());
	    					return true;
	    				}
	    			}
	    		}
	    	}
    	}
    	
    	return false;
	}

	//mikes new
    public Set createUniqueSearchSetProspectId(ProspectView prospectView)throws Exception{
		Set set = new HashSet();
	    ProspectDetails prospectDetails;
		for(int i = 0; i < prospectView.getElements().size();i++) {
			prospectDetails = (ProspectDetails)prospectView.getElement(i);
			set.add(prospectDetails.getProspectId());
		}
		return set;
	}
    
    public Set createUniqueSearchSetProspectId(ProspectView prospectView, ContactView contactView)throws Exception{
		Set set = new HashSet();
	    ProspectDetails prospectDetails;
		for(int i = 0; i < prospectView.getElements().size();i++) {
			prospectDetails = (ProspectDetails)prospectView.getElement(i);
			set.add(prospectDetails.getProspectId());
		}
		ContactDetails contactDetails;
		for(int i = 0; i < contactView.getElements().size();i++) {
			contactDetails = (ContactDetails)contactView.getElement(i);
			set.add(contactDetails.getProspectId());
		}
		return set;
	}
    
	protected Calendar getGregorianCalendarDate(Timestamp sqlTimestamp) {
    	Calendar date = new GregorianCalendar(
    			sqlTimestamp.getYear(),
    			sqlTimestamp.getMonth(),
    			sqlTimestamp.getDate(),
    			sqlTimestamp.getHours(),
    			sqlTimestamp.getMinutes()
    			);
		return date;
	}


    /**
     * Method sortProspects.
     * @param prospectView ProspectView
     * @throws Exception
     */
    public void sortProspects(ProspectView prospectView) throws Exception {

        if (!Utilities.nullToBlank(request.getParameter("dfSortBy")).equals("") &&
            !Utilities.nullToBlank(request.getParameter("dfSortDirection")).equals("")) {

            request.setAttribute("sortByField", request.getParameter("dfSortBy"));
            request.setAttribute("sortByDirection", request.getParameter("dfSortDirection"));

            CollectionUtilities.sortVector(prospectView.getElements(),
                                           request.getParameter("dfSortBy"),
                                           Integer.parseInt(request.getParameter("dfSortDirection")),
                                           false);
        }
    }

    /**
     * Method getProspectSearchInformation.
     * @param prospectDetails ProspectDetails
     * @param salesmanDetails UserDetails
     * @throws Exception 
     */
    public void getProspectSearchInformation(ProspectDetails prospectDetails,
                                             UserDetails salesmanDetails) throws Exception {
        if (request.getParameter("dfSearchProspectId") != null
            && !request.getParameter("dfSearchProspectId").equals("")) {
            prospectDetails.setProspectId(new BigDecimal(request.getParameter("dfSearchProspectId")));
        }

       // need to add company to the selection screen
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
        
        if (request.getParameter("dfSearchEmail") != null
                && !request.getParameter("dfSearchEmail").equals("")) {
                prospectDetails.setEmail(request.getParameter("dfSearchEmail"));             
        }

        if (request.getParameter("dfSearchTerritoryId") != null
            && !request.getParameter("dfSearchTerritoryId").equals("")) {
        	
            String[] territoriesSelected = request.getParameterValues("dfSearchTerritoryId");
            List territoryList = new ArrayList();
            if(territoriesSelected != null && territoriesSelected.length > 0){
    	        for(int i = 0; i < territoriesSelected.length; i++){
    	        	territoryList.add(new BigDecimal(territoriesSelected[i]));
    	        }
    	        prospectDetails.setTerritoryIdList(territoryList);
            }
            
        } else {
            //user did not select an option, check the user authority level
            if (salesmanDetails != null) {
            	if(salesmanDetails.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
            			salesmanDetails.getSpecificTerritories()==null ||
            			salesmanDetails.getSpecificTerritories().compareTo(UserDetails.NO_SPECIFIC_TERRITORIES) == 0){
            		prospectDetails.setTerritoryIdList(null);
            	}else{
	    			TerritoryView salesmanTerritoriesView = getSalesmanTerritoriesView(salesmanDetails.getUserId());
	    			
					if(salesmanTerritoriesView.getElements().size()> 0){
						prospectDetails.setTerritoryIdList(salesmanTerritoriesView.getTerritoryIdsList());	
					}
            	}
            }
        }

        if (request.getParameter("dfSearchStatusId") != null
                && !request.getParameter("dfSearchStatusId").equals("")) {

            String[] statusesSelected = request.getParameterValues("dfSearchStatusId");
            List statusList = new ArrayList();
            if(statusesSelected != null && statusesSelected.length > 0){
    	        for(int i = 0; i < statusesSelected.length; i++){
    	        	statusList.add(new BigDecimal(statusesSelected[i]));
    	        }
    	        prospectDetails.setStatusIdList(statusList);
            }
            
        } else {
            //user did not select an option, check the user authority level
        	
        	if (salesmanDetails != null) {
            	if(salesmanDetails.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
            			salesmanDetails.getSpecificStatuses()==null ||
        				salesmanDetails.getSpecificStatuses().compareTo(UserDetails.NO_SPECIFIC_STATUSES) == 0 ){

            		prospectDetails.setStatusIdList(null);
            	}else{
	    			StatusView salesmanStatusesView = getSalesmanStatusesView(salesmanDetails.getUserId());
	
					if(salesmanStatusesView.getElements().size()> 0){
						prospectDetails.setStatusIdList(salesmanStatusesView.getStatusIdsList());				
					}
            	}
            }
        }
        

        if (request.getParameter("dfSearchLobId") != null
                && !request.getParameter("dfSearchLobId").equals("")) {
            	
                String[] lobSelected = request.getParameterValues("dfSearchLobId");
                
                List lobList = new ArrayList();
                
                if(lobSelected != null && lobSelected.length > 0){
        	        for(int i = 0; i < lobSelected.length; i++){
        	        	lobList.add(new BigDecimal(lobSelected[i]));
        	        }
        	        prospectDetails.setLobIdList(lobList);
                }
            
        } else {
            //user did not select an option, check the user authority level
            //user did not select an option, check the user authority level
        	if (salesmanDetails != null) {
        		//System.out.println("salesmanDetails.getUserType() = " + salesmanDetails.getUserType());
            
        		if(salesmanDetails.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
        				salesmanDetails.getSpecificLobs()==null  ||
        				salesmanDetails.getSpecificLobs().compareTo(UserDetails.NO_SPECIFIC_LOBS) == 0 ){

            		prospectDetails.setLobIdList(null);
            	}else{
//            		System.out.println("should be here now...");
	    			LobView salesmanLobsView = getSalesmanLobsView(salesmanDetails.getUserId());
	
					if(salesmanLobsView.getElements().size()> 0){
						prospectDetails.setLobIdList(salesmanLobsView.getLobIdsList());				
					}
            	}
            }
        }


        if (request.getParameter("dfSearchSoftwareTypeId") != null
            && request.getParameter("dfSearchSoftwareTypeId") != null
            && !request.getParameter("dfSearchSoftwareTypeId").equals("")) {
            prospectDetails.setSoftwareTypeId(new BigDecimal(request.getParameter("dfSearchSoftwareTypeId")));
        } else {
            //user did not select an option, check the user authority level
            if (salesmanDetails != null) {
                prospectDetails.setSoftwareTypeId(salesmanDetails.getSoftwareTypeId());
            }
        }

        if (request.getParameter("dfSearchSystemTypeId") != null
            && !request.getParameter("dfSearchSystemTypeId").equals("")) {
            prospectDetails.setSystemTypeId(new BigDecimal(request.getParameter("dfSearchSystemTypeId")));
        } else {
            //user did not select an option, check the user authority level
            if (salesmanDetails != null) {
                prospectDetails.setSystemTypeId(salesmanDetails.getSystemTypeId());
            }
        }

        if (request.getParameter("dfSearchVerifiedId") != null
            && !request.getParameter("dfSearchVerifiedId").equals("")) {
            prospectDetails.setVerifiedId(new BigDecimal(request.getParameter("dfSearchVerifiedId")));
        } else {
            //user did not select an option, check the user authority level
            if (salesmanDetails != null) {
                prospectDetails.setVerifiedId(salesmanDetails.getVerifiedId());
            }
        }
        if (request.getParameter("dfSearchSizeId") != null
            && !request.getParameter("dfSearchSizeId").equals("")) {
            prospectDetails.setSizeId(new BigDecimal(request.getParameter("dfSearchSizeId")));
        } else {
            //user did not select an option, check the user authority level
            if (salesmanDetails != null) {
                prospectDetails.setSizeId(salesmanDetails.getSizeId());
            }
        }
        if (request.getParameter("dfSearchSalesActionId") != null
            && !request.getParameter("dfSearchSalesActionId").equals("")) {
        	prospectDetails.setSearchNextSalesActionId(new BigDecimal(request.getParameter("dfSearchSalesActionId")));
        }
        if (request.getParameter("dfSearchSalesActionDate") != null
            && !request.getParameter("dfSearchSalesActionDate").equals("")) {
        	prospectDetails.setSearchNextSalesActionDate(DateUtilities.getSQLDate(request.getParameter("dfSearchSalesActionDate"),userProfile.getDateFormat()));  
        }
        
        if (request.getParameter("dfSearchSalesActionDateEnd") != null
                && !request.getParameter("dfSearchSalesActionDateEnd").equals("")) {
            	prospectDetails.setSearchNextSalesActionDateEnd(DateUtilities.getSQLDate(request.getParameter("dfSearchSalesActionDateEnd"),userProfile.getDateFormat()));  
        }
        
        if(prospectDetails.getSearchNextSalesActionDate()!=null && prospectDetails.getSearchNextSalesActionDateEnd()==null){
        	prospectDetails.setSearchNextSalesActionDateEnd(prospectDetails.getSearchNextSalesActionDate());
        }
        
        if(prospectDetails.getSearchNextSalesActionDate()==null && prospectDetails.getSearchNextSalesActionDateEnd()!=null){
        	prospectDetails.setSearchNextSalesActionDate(prospectDetails.getSearchNextSalesActionDateEnd());
        }
        
        if (request.getParameter("dfSearchCustomerCompany") != null
            && !request.getParameter("dfSearchCustomerCompany").equals("")) {
            prospectDetails.setCustomerCompany(request.getParameter("dfSearchCustomerCompany"));
        }
        
        //add search by change date
        if (request.getParameter("dfSearchChangeDate") != null && !request.getParameter("dfSearchChangeDate").equals("")) {
        	 if (request.getParameter("dfSearchExactChangeDate") != null) {//this is a checkbox
        		 prospectDetails.setSearchExactChangeDate(DateUtilities.getSQLDate(request.getParameter("dfSearchChangeDate"),userProfile.getDateFormat()));
        	 }else{
        		 prospectDetails.setSearchChangeDate(DateUtilities.getSQLDate(request.getParameter("dfSearchChangeDate"),userProfile.getDateFormat()));  
        	 }
        }
        
        if (request.getParameter("dfSearchPhone") != null
                && !request.getParameter("dfSearchPhone").equals("")) {
                prospectDetails.setPhone(request.getParameter("dfSearchPhone"));             
        }
        if (request.getParameter("dfSearchTerritoryIsNull") != null
            && !request.getParameter("dfSearchTerritoryIsNull").equals("")) {
            prospectDetails.setTerritoryIsNull(true);
        } else {
            //user did not select an option, check the user authority level
        	 prospectDetails.setTerritoryIsNull(false);
        }
        if (request.getParameter("dfSearchLobIsNull") != null
            && !request.getParameter("dfSearchLobIsNull").equals("")) {
            prospectDetails.setLobIsNull(true);
        } else {
            //user did not select an option, check the user authority level
        	 prospectDetails.setLobIsNull(false);
        }
        if (request.getParameter("dfSearchStatusIsNull") != null
            && !request.getParameter("dfSearchStatusIsNull").equals("")) {
            prospectDetails.setStatusIsNull(true);
        } else {
            //user did not select an option, check the user authority level
        	 prospectDetails.setStatusIsNull(false);
        }        
        
        if (request.getParameter("dfSearchGenericText") != null
                && !request.getParameter("dfSearchGenericText").equals("")) {
            	
            	prospectDetails.setGenericSearchText(request.getParameter("dfSearchGenericText"));
        }
        
        if (request.getParameter("dfSearchGenericNumeric") != null
                && !request.getParameter("dfSearchGenericNumeric").equals("")) {
            prospectDetails.setGenericSearchNumeric(new BigDecimal(request.getParameter("dfSearchGenericNumeric")));
        } 
        
        if (request.getParameter("dfSearchGenericDate") != null
                && !request.getParameter("dfSearchGenericDate").equals("")) {

            prospectDetails.setGenericSearchDate(DateUtilities.getSQLDate(request.getParameter("dfSearchGenericDate"), salesmanDetails.getDateFormat()));
        } 
        
        //if user type = 3, force a name to be entered in either generice text, contact name or company name
       // System.out.println("usertype= " + userProfile.getUserType());
        if(userProfile.getUserType().compareTo(new BigDecimal(3))==0){
        	if (request.getParameter("dfSearchContactName").equals("") ) {
        		prospectDetails.setContactName("9X9X9X9X");
            }
        }
    }
    
    
    public TerritoryView getSalesmanTerritoriesView(BigDecimal salesmanId) throws Exception {
    	
        TerritoryView sessionTerritoryView = (TerritoryView) request.getSession().getAttribute("territoryView");
        TerritoryView territoryView = new TerritoryView();
        
        if (sessionTerritoryView == null) {
        	
	        UserTerritoriesDetails searchUserTerritoriesDetails = new UserTerritoriesDetails();
	        UserTerritoriesView userTerritoriesView = new UserTerritoriesView();
	        
	        searchUserTerritoriesDetails.setUserId(salesmanId);
	        userTerritoriesView.fillWithElements(connection, UserTerritoriesView.FILL_TYPE_ALL, searchUserTerritoriesDetails);
	        UserTerritoriesDetails userTerritoriesDetails;
	        
	        for(int i = 0; i < userTerritoriesView.getElements().size(); i++){
	        	userTerritoriesDetails = (UserTerritoriesDetails)userTerritoriesView.getElement(i);
        	
	            TerritoryView searchTerritoryView = new TerritoryView();
	            TerritoryDetails searchTerritoryDetails = new TerritoryDetails();

	            searchTerritoryDetails.setTerritoryId(userTerritoriesDetails.getTerritoryId());
	            searchTerritoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, searchTerritoryDetails);
        	
                TerritoryDetails territoryDetails = (TerritoryDetails)searchTerritoryView.getElement(0);
                territoryView.addElement(territoryDetails);
	        }
	        
	        return territoryView;

        }else{	
        	return sessionTerritoryView;
        }
	}
    
    public StatusView getSalesmanStatusesView(BigDecimal salesmanId) throws Exception {
    	
        StatusView sessionStatusView = (StatusView) request.getSession().getAttribute("statusView");
        StatusView statusView = new StatusView();
        
        if (sessionStatusView == null) {
        	
	        UserStatusesDetails searchUserStatusesDetails = new UserStatusesDetails();
	        UserStatusesView userStatusesView = new UserStatusesView();
	        
	        searchUserStatusesDetails.setUserId(salesmanId);
	        userStatusesView.fillWithElements(connection, UserStatusesView.FILL_TYPE_ALL, searchUserStatusesDetails);
	        UserStatusesDetails userStatusesDetails;
	        
	        for(int i = 0; i < userStatusesView.getElements().size(); i++){
	        	userStatusesDetails = (UserStatusesDetails)userStatusesView.getElement(i);

	            StatusView searchStatusView = new StatusView();
	            StatusDetails searchStatusDetails = new StatusDetails();

	            searchStatusDetails.setStatusId(userStatusesDetails.getStatusId());
	            searchStatusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, searchStatusDetails);
        	
                StatusDetails statusDetails = (StatusDetails)searchStatusView.getElement(0);
                statusView.addElement(statusDetails);
	        }
	        
	        return statusView;

        }else{	
        	return sessionStatusView;
        }
	}

    public LobView getSalesmanLobsView(BigDecimal salesmanId) throws Exception {
    	
    	LobView sessionLobView = (LobView) request.getSession().getAttribute("lobView");
        LobView lobView = new LobView();
        
        if (sessionLobView == null) {
        	
	        UserLobsDetails searchUserLobsDetails = new UserLobsDetails();
	        UserLobsView userLobsView = new UserLobsView();
	        
	        searchUserLobsDetails.setUserId(salesmanId);
	        userLobsView.fillWithElements(connection, UserLobsView.FILL_TYPE_ALL, searchUserLobsDetails);
	        UserLobsDetails userLobsDetails;
	        
	        for(int i = 0; i < userLobsView.getElements().size(); i++){
	        	userLobsDetails = (UserLobsDetails)userLobsView.getElement(i);
        	
	            LobView searchLobView = new LobView();
	            LobDetails searchLobDetails = new LobDetails();

	            searchLobDetails.setLobId(userLobsDetails.getLobId());
	            searchLobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, searchLobDetails);
        	
                LobDetails lobDetails = (LobDetails)searchLobView.getElement(0);
                lobView.addElement(lobDetails);
	        }
	        
	        return lobView;

        }else{	
        	return sessionLobView;
        }
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


    /**
     * Method loadAdditionalData.
     * @param salesmanDetails UserDetails
     * @throws Exception
     */
    public void loadAdditionalData(UserDetails salesmanDetails) throws Exception {
        // company

        CompanyView companyView = (CompanyView) request.getSession().getAttribute("companyView");

        if (companyView == null) {
            companyView = new CompanyView();

            CompanyDetails companyDetails = new CompanyDetails();
            if(salesmanDetails.getCompanyId()!=null && 
            		Utilities.nullToZero(salesmanDetails.getLimitCompanyView()).compareTo(UserDetails.LIMIT_COMPANY)==0){//salesman is limited
            	companyDetails.setCompanyId(salesmanDetails.getCompanyId());//otherwise this is just the default company to display on the search screen
            }
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, companyDetails);
            request.getSession().setAttribute("companyView", companyView);
        }
        request.setAttribute("companyView", companyView);

//        // line of business
//        LobView lobView = (LobView) request.getSession().getAttribute("lobView");
//        if (lobView == null) {
//            lobView = new LobView();
//            LobDetails lobDetails = new LobDetails();
//            if (salesmanDetails != null) {
//                lobDetails.setLobId(salesmanDetails.getLobId());
//                lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);
//            }
//            request.getSession().setAttribute("lobView", lobView);
//        }
//        request.setAttribute("lobView", lobView);

// country
        CountryView countryView = (CountryView) request.getSession().getAttribute("countryView");
        if (countryView == null) {
            countryView = new CountryView();
            CountryDetails countryDetails = new CountryDetails();
           //TODO if (salesmanDetails != null) {
             //   countryDetails.setCountryId(salesmanDetails.getCountryId());
                countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);
           // }
            request.getSession().setAttribute("countryView", countryView);
        }
        request.setAttribute("countryView", countryView);

// state
        StateView stateView = (StateView) request.getSession().getAttribute("stateView");
        if (stateView == null) {
            stateView = new StateView();
            StateDetails stateDetails = new StateDetails();
            if (salesmanDetails != null) {
                stateDetails.setStateId(salesmanDetails.getStateId());
                stateView.fillWithElements(connection, StateDetails.FILL_TYPE_ALL, stateDetails);
            }
            request.getSession().setAttribute("stateView", stateView);
        }
        request.setAttribute("stateView", stateView);
// systemType
        SystemTypeView systemTypeView = (SystemTypeView) request.getSession().getAttribute("systemTypeView");
        if (systemTypeView == null) {
            systemTypeView = new SystemTypeView();
            SystemTypeDetails systemTypeDetails = new SystemTypeDetails();
            if (salesmanDetails != null) {
                systemTypeDetails.setSystemTypeId(salesmanDetails.getSystemTypeId());
                systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);
            }
            request.getSession().setAttribute("systemTypeView", systemTypeView);
        }
        request.setAttribute("systemTypeView", systemTypeView);

// softwareType
        SoftwareTypeView softwareTypeView = (SoftwareTypeView) request.getSession().getAttribute("softwareTypeView");
        if (softwareTypeView == null) {
            softwareTypeView = new SoftwareTypeView();
            SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();
            if (salesmanDetails != null) {
                softwareTypeDetails.setSoftwareTypeId(salesmanDetails.getSoftwareTypeId());
                softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);
            }
            request.getSession().setAttribute("softwareTypeView", softwareTypeView);
        }
        request.setAttribute("softwareTypeView", softwareTypeView);

//      salesman territories
        if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
        		userProfile.getSpecificTerritories()==null ||
        		userProfile.getSpecificTerritories().compareTo(UserDetails.NO_SPECIFIC_TERRITORIES) == 0){
			TerritoryView territoryView = (TerritoryView) request.getSession().getAttribute("territoryView");
	        if (territoryView == null) {
	            territoryView = new TerritoryView();
	            TerritoryDetails territoryDetails = new TerritoryDetails();
	            if (salesmanDetails != null) {
	                territoryDetails.setTerritoryId(salesmanDetails.getTerritoryId());
	                territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
	            }
	            request.getSession().setAttribute("territoryView", territoryView);
	        }
	        request.setAttribute("territoryView", territoryView);
        }else{
	
	        TerritoryView salesmanTerritoriesView = getSalesmanTerritoriesView(salesmanDetails);
	        request.getSession().setAttribute("territoryView", salesmanTerritoriesView);
	        request.setAttribute("territoryView", salesmanTerritoriesView);
        }
        
    	// status
        if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
        		userProfile.getSpecificStatuses()==null  ||
        		userProfile.getSpecificStatuses().compareTo(UserDetails.NO_SPECIFIC_STATUSES) == 0){
	 		StatusView statusView = (StatusView) request.getSession().getAttribute("statusView");
	        if (statusView == null) {
	            statusView = new StatusView();
	            StatusDetails statusDetails = new StatusDetails();
	            if (salesmanDetails != null) {
	                statusDetails.setStatusId(salesmanDetails.getStatusId());
	                statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);
	            }
	            request.getSession().setAttribute("statusView", statusView);
	        }
	        // however, if this is not formAction= DISPLAY_SEARCH, and userProfile.getLimitProspectSearchView = 1, then we need to redo the search
	        
	        request.setAttribute("statusView", statusView);
        }else{
        	StatusView salesmanStatusesView;
        	if(formAction.equals("DISPLAY_ONE_PROSPECT") ||
        			formAction.equals("DISPLAY_UPDATE_CHECKED_COLLECTION") ||
        			formAction.equals("DISPLAY_UPDATE_COLLECTION") ||
        			formAction.equals("DISPLAY_UPDATE")){
        		StatusDetails statusDetails = new StatusDetails();
        		salesmanStatusesView = new StatusView();
        		salesmanStatusesView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, statusDetails);
        	}else{
        		salesmanStatusesView = getSalesmanStatusesView(salesmanDetails);
        	}
	        request.getSession().setAttribute("statusView", salesmanStatusesView);
	        request.setAttribute("statusView", salesmanStatusesView);
        }
        
    	// lob
        if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) ||
        		userProfile.getSpecificLobs()==null ||
        		userProfile.getSpecificLobs().compareTo(UserDetails.NO_SPECIFIC_LOBS) == 0){
	 		LobView lobView = (LobView) request.getSession().getAttribute("lobView");
	        if (lobView == null) {
	        	lobView = new LobView();
	            LobDetails lobDetails = new LobDetails();
	            if (salesmanDetails != null) {
	            	lobDetails.setLobId(salesmanDetails.getLobId());
	            	lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);
	            }
	            request.getSession().setAttribute("lobView", lobView);
	        }
	        request.setAttribute("lobView", lobView);
        }else{

        	LobView salesmanLobsView = getSalesmanLobsView(salesmanDetails);
	        request.getSession().setAttribute("lobView", salesmanLobsView);
	        request.setAttribute("lobView", salesmanLobsView);
        }
        
// verified
        VerifiedView verifiedView = (VerifiedView) request.getSession().getAttribute("verifiedView");
        if (verifiedView == null) {
            verifiedView = new VerifiedView();
            VerifiedDetails verifiedDetails = new VerifiedDetails();
            if (salesmanDetails != null) {
                verifiedDetails.setVerifiedId(salesmanDetails.getVerifiedId());
                verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);
            }
            request.getSession().setAttribute("verifiedView", verifiedView);
        }
        request.setAttribute("verifiedView", verifiedView);
// size
        SizeView sizeView = (SizeView) request.getSession().getAttribute("sizeView");
        if (sizeView == null) {
            sizeView = new SizeView();
            SizeDetails sizeDetails = new SizeDetails();
            if (salesmanDetails != null) {
                sizeDetails.setSizeId(salesmanDetails.getSizeId());
                sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);
            }
            request.getSession().setAttribute("sizeView", sizeView);
        }
        request.setAttribute("sizeView", sizeView);
// salesAction
        SalesActionView salesActionView = (SalesActionView) request.getSession().getAttribute("salesActionView");
        if (salesActionView == null) {
            salesActionView = new SalesActionView();
            SalesActionDetails salesActionDetails = new SalesActionDetails();
            salesActionDetails.setDisplayForSalesmen(SalesActionDetails.YES_DISPLAY_FOR_SALESMEN);
            salesActionView.fillWithElements(connection, SalesActionDetails.FILL_TYPE_ALL, salesActionDetails);
            request.getSession().setAttribute("salesActionView", salesActionView);
        }
        request.setAttribute("salesActionView", salesActionView);
        
    }
    
    private StatusView getSalesmanStatusesView(UserDetails salesmanDetails) throws Exception {
    	
        StatusView sessionStatusView = (StatusView) request.getSession().getAttribute("statusView");
        StatusView statusView = new StatusView();
        
        if (sessionStatusView == null) {
        	
	        UserStatusesDetails searchUserStatusesDetails = new UserStatusesDetails();
	        UserStatusesView userStatusesView = new UserStatusesView();
	        
	        searchUserStatusesDetails.setUserId(salesmanDetails.getUserId());
	        userStatusesView.fillWithElements(connection, UserStatusesView.FILL_TYPE_ALL, searchUserStatusesDetails);
	        UserStatusesDetails userStatusesDetails;
	        
	        for(int i = 0; i < userStatusesView.getElements().size(); i++){
	        	userStatusesDetails = (UserStatusesDetails)userStatusesView.getElement(i);
        	
	            StatusView searchStatusView = new StatusView();
	            StatusDetails searchStatusDetails = new StatusDetails();

	            searchStatusDetails.setStatusId(userStatusesDetails.getStatusId());
	            searchStatusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, searchStatusDetails);
        	
                StatusDetails statusDetails = (StatusDetails)searchStatusView.getElement(0);
                statusView.addElement(statusDetails);
	        }
	        
	        return statusView;

        }else{	
        	return sessionStatusView;
        }
	}
    
    private TerritoryView getSalesmanTerritoriesView(UserDetails salesmanDetails) throws Exception {
    	
        TerritoryView sessionTerritoryView = (TerritoryView) request.getSession().getAttribute("territoryView");
        TerritoryView territoryView = new TerritoryView();
        
        if (sessionTerritoryView == null) {
        	
	        UserTerritoriesDetails searchUserTerritoriesDetails = new UserTerritoriesDetails();
	        UserTerritoriesView userTerritoriesView = new UserTerritoriesView();
	        
	        searchUserTerritoriesDetails.setUserId(salesmanDetails.getUserId());
	        userTerritoriesView.fillWithElements(connection, UserTerritoriesView.FILL_TYPE_ALL, searchUserTerritoriesDetails);
	        UserTerritoriesDetails userTerritoriesDetails;
	        
	        for(int i = 0; i < userTerritoriesView.getElements().size(); i++){
	        	userTerritoriesDetails = (UserTerritoriesDetails)userTerritoriesView.getElement(i);
        	
	            TerritoryView searchTerritoryView = new TerritoryView();
	            TerritoryDetails searchTerritoryDetails = new TerritoryDetails();

	            searchTerritoryDetails.setTerritoryId(userTerritoriesDetails.getTerritoryId());
	            searchTerritoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, searchTerritoryDetails);
        	
	            if(searchTerritoryView.getElements().size() > 0){
//		            System.out.println("searchTerritoryView = " + searchTerritoryView.getElements().size());
	                TerritoryDetails territoryDetails = (TerritoryDetails)searchTerritoryView.getElement(0);
	                territoryView.addElement(territoryDetails);
	            }

	        }
	        
	        return territoryView;

        }else{	
        	return sessionTerritoryView;
        }
	}
    
    private LobView getSalesmanLobsView(UserDetails salesmanDetails) throws Exception {
    	
    	LobView sessionLobView = (LobView) request.getSession().getAttribute("lobView");
    	LobView lobView = new LobView();
        
        if (sessionLobView == null) {
        	
	        UserLobsDetails searchUserLobsDetails = new UserLobsDetails();
	        UserLobsView userLobsView = new UserLobsView();
	        
	        searchUserLobsDetails.setUserId(salesmanDetails.getUserId());
	        userLobsView.fillWithElements(connection, UserLobsView.FILL_TYPE_ALL, searchUserLobsDetails);
	        UserLobsDetails userLobsDetails;
	        
	        for(int i = 0; i < userLobsView.getElements().size(); i++){
	        	userLobsDetails = (UserLobsDetails)userLobsView.getElement(i);
        	
	        	LobView searchLobView = new LobView();
	            LobDetails searchLobDetails = new LobDetails();

	            searchLobDetails.setLobId(userLobsDetails.getLobId());
	            searchLobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, searchLobDetails);
        	
                LobDetails lobDetails = (LobDetails)searchLobView.getElement(0);
                lobView.addElement(lobDetails);
	        }
	        
	        return lobView;

        }else{	
        	return sessionLobView;
        }
	}

	/**
     * Method establishNotesCount.
     * @param prospectView ProspectView
     * @throws Exception
     * added here to also build the email MailTo string
     */
    public void establishNotesCount(ProspectView prospectView) throws Exception {
        NoteView noteView = new NoteView();
        int prospectsCount = prospectView.getElements().size();
        Vector prospectIdsVector = new Vector();
        for (int i = 0; i < prospectsCount; i++) {
            ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i);
            prospectIdsVector.addElement(prospectDetails.getProspectId());
            if (prospectIdsVector.size() > 0
                && ((i > 0 && i % MAX_SELECT_IN_LIST_SIZE == 0) || (i == prospectsCount - 1))) {
                NoteView partialNoteView = new NoteView();
                NoteDetails noteDetails = new NoteDetails();
                noteDetails.setProspectIdsVector(prospectIdsVector);
                partialNoteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
                noteView.addElements(partialNoteView.getElements());
                prospectIdsVector = new Vector();
            }
        }
        noteView.createUniqueKeyCollectionHashtable("getNoteId");
//        createUniqueSearchSetNoteId(noteView);
        noteView.createSearchKeyCollectionHashtable("getProspectId");

        for (int i = 0; i < prospectsCount; i++) {
            ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i);
            Vector notesVector = noteView.getElements(prospectDetails.getProspectId());
            prospectDetails.setCountNotes(new Integer(notesVector.size()));
            prospectDetails.setEmailMailToString(buildEmailMailToString(prospectDetails));
        }
    }   
    
	/**
	 * @param prospectDetails
	 * @return
	 * Build the string that is used in the MailTo: tag.  this builds cc;, bcc; subject and body
	 */
	public static String buildEmailMailToString(ProspectDetails prospectDetails) {
		// here is an example:
		//<a href="mailto:<%=Utilities.nullToBlank(prospectDetails.getEmail())%>
		//       ?subject=<%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%> 
		//       Request on www.championarrowhead.com&bcc=john4@randrinc.com&body=Thank you for your request or question.">
		// we will build what comes after the <a href="mailto:<%=Utilities.nullToBlank(prospectDetails.getEmail())%>
		
		return buildEmailMailToString(prospectDetails,AppSettings.getParm("CC_EMAILS"),
				AppSettings.getParm("BCC_EMAILS"),AppSettings.getParm("INCLUDE_CONTACT_NAME_IN_SUBJECT"),AppSettings.getParm("SUBJECT_TEXT"),
				AppSettings.getParm("BODY_TEXT"));
	}

	public static String buildEmailMailToString(ProspectDetails prospectDetails, String ccEmails, String bccEmails, String includeContactNameInSubject, 
			String subjectText, String bodyText) {
		
		String contactName = prospectDetails.getCustomerCompany();
		String mailToValue= "";
		boolean atLeastOneParm = false;
		boolean includesContactName = false;
		//Example=?subject=Contact Name subject text&cc=me@myemail.com&bcc=you@youremail.com&body=body text
		if(ccEmails.compareToIgnoreCase("")!=0){
			if(atLeastOneParm){
				mailToValue +="&";
			}else{
				mailToValue +="?";
				atLeastOneParm = true;
			}
			mailToValue+="cc=" + ccEmails;
			//System.out.println("mailtoValue after ccEmails= "+ mailToValue);
		}
		if(bccEmails.compareToIgnoreCase("")!=0){
			if(atLeastOneParm){
				mailToValue +="&";
			}else{
				mailToValue +="?";
				atLeastOneParm = true;
			}
			mailToValue+="bcc=" + bccEmails;
			//System.out.println("mailtoValue after bccEmails= "+ mailToValue);
		}
		if(includeContactNameInSubject.equalsIgnoreCase("YES") || includeContactNameInSubject.equalsIgnoreCase("True")){
			if(atLeastOneParm){
				mailToValue +="&";
			}else{
				mailToValue +="?";
				atLeastOneParm = true;
			}
			mailToValue+="subject=" + contactName;
			includesContactName = true;
			//System.out.println("mailtoValue after contactName= "+ mailToValue);
		}
		if(subjectText.compareToIgnoreCase("")!=0){
			if(atLeastOneParm){
				if(!includesContactName){
					mailToValue +="&";
				}
			}else{
				mailToValue +="?";
				atLeastOneParm = true;
			}
			if(includesContactName){
				mailToValue +=" " + subjectText;
			}else{
				mailToValue +="subject=" + subjectText;
			}
			includesContactName = true;
			//System.out.println("mailtoValue after subjectText= "+ mailToValue);
		}
		if(bodyText.compareToIgnoreCase("")!=0){
			if(atLeastOneParm){
				mailToValue +="&";
			}else{
				mailToValue +="?";
				atLeastOneParm = true;
			}
			mailToValue+="body=" + bodyText;
			//System.out.println("mailtoValue after bodyText= "+ mailToValue);
		}
		return mailToValue;
	}

	
	protected void searchForProspectDuplicates() {
		try {			
			openConnection();
			
			BigDecimal fillType = new BigDecimal(0);
			
			if(request.getSession().getAttribute("searchBy") != null){
				searchBy = request.getSession().getAttribute("searchBy").toString();
			}
			
			if(request.getParameter("rdSearchDuplicates")!= null){
				searchBy = request.getParameter("rdSearchDuplicates");
				request.getSession().removeAttribute("searchBy");
				request.getSession().setAttribute("searchBy", searchBy);
			}
		
			ProspectDetails searchDuplicatesDetails = new ProspectDetails();
			ProspectView searchDuplicatesView = new ProspectView();

			if(searchBy.toString().equals("companyName")){
				fillType = ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_COMPANY_NAME;
			}else if(searchBy.toString().equals("contactName")){
				fillType = ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_CONTACT_NAME;
			}else if(searchBy.toString().equals("email")){
				fillType = ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_EMAIL;
			}else if(searchBy.toString().equals("phoneNumber")){
				fillType = ProspectView.FILL_TYPE_ELIMINATE_DUPLICATES_PHONE_NUMBER;
			}

			try{
				searchDuplicatesView.fillWithElements(connection, fillType, searchDuplicatesDetails);
				searchDuplicatesView.createUniqueKeyCollectionHashtable("getProspectId");

				if (searchDuplicatesView.getElements().size() == 0) {
                    throw new ModelException("No duplicates found....", ModelException.RECORD_NOT_FOUND);
                }
				establishNotesCount(searchDuplicatesView);
				
			} catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
			
		    commit();		
			
			request.getSession().setAttribute("prospectView", searchDuplicatesView);
			request.setAttribute("prospectView", searchDuplicatesView);
			
			forward("/jsp/public/duplicateSearch/displayProspects.jsp");
			
		} catch (Exception e) {	
			e.printStackTrace();
		} finally{
			finallyMethod();
		}
	} 
	
	/**
     * Method deleteSelected.
     */
    protected void deleteSelected() {
        try {
        	openConnection();
        	
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");      
            ProspectView prospectView = new ProspectView();
            
            if (sessionProspectView != null) {
            	ProspectDetails duplicateProspectDetails;
                for (int i = 0; i < sessionProspectView.getElements().size(); i++) {
                	
                    if (request.getParameter("chkId" + String.valueOf(i)) != null) {
                    	ProspectDetails foundProspectDetails = (ProspectDetails)sessionProspectView.getElement(i);
                    	duplicateProspectDetails = new ProspectDetails();
                    	duplicateProspectDetails.setProspectId(new BigDecimal(request.getParameter("chkId" + String.valueOf(i))));
                    	 // delete prospect
                        try {
                        	prospectView.doAction(connection, "DELETE", null, duplicateProspectDetails);
                        } catch (SQLException sqlE) {
                            throw new ModelException("The prospect <b>" + foundProspectDetails.getCustomerCompany() + "</b> cannot be deleted. <br>The most probable reason for this is that there are notes, documents, additional contacts etc. attached to the prospect.", ModelException.UNKNOWN_ERROR);                         
                        }
                        commit();
//                    	checkToSeeIfExistsNow(duplicateProspectDetails);
                    }
                }
            }

            searchForProspectDuplicates();
            
        } catch (ModelException modelException) {
            request.setAttribute("modelException", modelException);
        } catch (Exception e) {
            doCatch(e);
        } finally {
        	finallyMethod();
        }
    }
}
