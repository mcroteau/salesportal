package com.randr.webdw.user;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.randr.webdw.company.CompanyDetails;
import com.randr.webdw.company.CompanyView;
import com.randr.webdw.country.CountryDetails;
import com.randr.webdw.country.CountryView;
import com.randr.webdw.international.dateFormat.DateFormatDetails;
import com.randr.webdw.international.dateFormat.DateFormatView;
import com.randr.webdw.international.timeOffset.TimeOffsetDetails;
import com.randr.webdw.international.timeOffset.TimeOffsetView;
import com.randr.webdw.lob.LobDetails;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
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
import com.randr.webdw.userLobs.UserLobsDetails;
import com.randr.webdw.userLobs.UserLobsView;
import com.randr.webdw.userStatuses.UserStatusesDetails;
import com.randr.webdw.userStatuses.UserStatusesView;
import com.randr.webdw.userTerritories.UserTerritoriesDetails;
import com.randr.webdw.userTerritories.UserTerritoriesView;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.verified.VerifiedDetails;
import com.randr.webdw.verified.VerifiedView;


/**
 */
public class UserController extends AbstractController {
    /**
     * Constructor for UserController.
     */
    public UserController() {
    }

    /**
     * Method doAction.
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public void doAction() throws java.io.IOException, javax.servlet.ServletException {
    	
    	if (isAdmin()){
    		redirectIfNotValidAdminUserProfile();
    	}
        if (formAction == null) {
            if (request.getServletPath().equals("/user")) {
                formAction = "LOGIN";
            } else {
                formAction = "DISPLAY";
            }
        }
        if (formAction.equals("LOGIN")) {
            request.getRequestDispatcher("/jsp/public/user/login.jsp").forward(request, response);
        } else if (formAction.equals("LOGOUT")) {
            request.getSession().removeAttribute("userProfile");
            request.getSession().invalidate();
            request.getRequestDispatcher("/jsp/public/user/login.jsp").forward(request, response);
        } else if (formAction.equals("DISPLAY")) {
            display();
        } else if (formAction.equals("DISPLAY_INSERT")
                   || formAction.equals("DISPLAY_UPDATE")) {
            displayInsertUpdate();
        } else if (formAction.equals("INSERT")) {
            insert();
        } else if (formAction.equals("UPDATE")) {
            update();
        } else if (formAction.equals("DELETE")) {
            delete();
        } else if (formAction.equals("DISPLAY_REPORTING")) {
            reporting();
        }

    }

	private void reporting() {
    	 try {
    		 	forward("/jsp/shared/user/displayReporting.jsp");
    	 } catch (Exception e) {
             doCatch(e);
         } 
	}

	/**
     * Method display.
     */
    private void display() {
        try {
            openConnection();

            UserDetails userDetails = new UserDetails();
            userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);
            //getUserInformation(userDetails);

            UserView userView = new UserView();

            if (Utilities.nullToBlank(request.getParameter("dfSearchSortBy")).equals("")) {
                userView.setOrderBy("c.company, u.first_name, u.last_name");
            } else {
                userView.setOrderBy(request.getParameter("dfSearchSortBy") + " " + request.getParameter("dfSearchSortDirection"));
            }
            userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN_REPORT, userDetails);
            
            try {
                if (userView.getElements().size() < 1) {
                    throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                }
                
                request.setAttribute("userView", userView);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/user/displayUsers.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayInsertUpdate.
     */
    private void displayInsertUpdate() {
        try {
            openConnection();
            // prepare data that is mandatory for both salesmen and customers:
            loadAdditionalData(new UserDetails());

            //load user data if update situation
            if (formAction.equals("DISPLAY_UPDATE")) {
                try {
                    UserDetails userDetails = new UserDetails();

                    if (isAdmin() && request.getParameter("dfUserId") == null) {
                        throw new ModelException("Id parameter passed incorrectly.", ModelException.RECORD_NOT_FOUND);
                    }

                    if (!isAdmin()) {
                        userDetails.setUserId(userProfile.getUserId());
                    } else {
                        userDetails.setUserId(new BigDecimal(request.getParameter("dfUserId")));
                    }

                    UserView userView = new UserView();
                    userView.fillWithElements(connection, UserView.FILL_TYPE_SALESMAN, userDetails);

                    if (userView.getElements().size() != 1) {
                        throw new ModelException("User not found.", ModelException.RECORD_NOT_FOUND);
                    }
                    
                    userDetails = (UserDetails) userView.getElements().elementAt(0);
                    
                    UserTerritoriesView salesmanTerritoriesView = getUserTerritoriesView(userDetails);
                    UserStatusesView salesmanStatusesView = getUserStatusesView(userDetails);
                    UserLobsView salesmanLobsView = getUserLobsView(userDetails);
//                    
//                    TerritoryDetails territoryDetails = new TerritoryDetails();
//                    TerritoryView territoriesView = new TerritoryView();
//                    
//                    UserStatusesView salesmanStatusesView = getUserStatusesView(userDetails);
                    
                    
                    request.setAttribute("userTerritoriesView", salesmanTerritoriesView);
                    request.setAttribute("userStatusesView", salesmanStatusesView);
                    request.setAttribute("userLobsView", salesmanLobsView);
                    
                    request.setAttribute("userDetails", userDetails);
                    
                } catch (ModelException modelException) {
                    request.setAttribute("modelException", modelException);
                }
            }
            commit();
            if (!isAdmin()) {
                request.getRequestDispatcher("/jsp/public/user/displayInsertUpdateUser.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/admin/user/displayInsertUpdateUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    private UserLobsView getUserLobsView(UserDetails userDetails) throws Exception {
		UserLobsDetails userLobsDetails = new UserLobsDetails();
		UserLobsView userLobsView = new UserLobsView();
		userLobsDetails.setUserId(userDetails.getUserId());
		userLobsView.fillWithElements(connection, UserLobsView.FILL_TYPE_ALL, userLobsDetails);
		
		return userLobsView;
	}
    
    private UserStatusesView getUserStatusesView(UserDetails userDetails) throws Exception {
		UserStatusesDetails userStatusesDetails = new UserStatusesDetails();
		UserStatusesView userStatusesView = new UserStatusesView();
		userStatusesDetails.setUserId(userDetails.getUserId());
		userStatusesView.fillWithElements(connection, UserStatusesView.FILL_TYPE_ALL, userStatusesDetails);
		
		return userStatusesView;
	}

	private UserTerritoriesView getUserTerritoriesView(UserDetails userDetails) throws Exception {
		UserTerritoriesDetails userTerritoriesDetails = new UserTerritoriesDetails();
		UserTerritoriesView userTerritoriesView = new UserTerritoriesView();
		userTerritoriesDetails.setUserId(userDetails.getUserId());
		userTerritoriesView.fillWithElements(connection, UserTerritoriesView.FILL_TYPE_ALL, userTerritoriesDetails);
		
		return userTerritoriesView;
	}

	/**
     * Method loadAdditionalData.
     * @param userDetails UserDetails
     * @throws Exception
     */
    private void loadAdditionalData(UserDetails userDetails) throws Exception {
        // company
        CompanyView companyView = new CompanyView();
        CompanyDetails companyDetails = new CompanyDetails();
        if (userDetails != null) {
            companyDetails.setCompanyId(userDetails.getCompanyId());
            companyView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, companyDetails);
        }
        request.setAttribute("companyView", companyView);

        // territory
        TerritoryView territoryView = new TerritoryView();
        TerritoryDetails territoryDetails = new TerritoryDetails();
        if (userDetails != null) {
            territoryDetails.setTerritoryId(userDetails.getTerritoryId());
            territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails);
        }
        request.setAttribute("territoryView", territoryView);

        // line of business
        LobView lobView = new LobView();
        LobDetails lobDetails = new LobDetails();
        if (userDetails != null) {

            lobDetails.setLobId(userDetails.getLobId());
            lobView.fillWithElements(connection, LobDetails.FILL_TYPE_ALL, lobDetails);
        }
        request.setAttribute("lobView", lobView);

        // country
        CountryView countryView = new CountryView();
        CountryDetails countryDetails = new CountryDetails();

        if (userDetails != null) {
            countryDetails.setCountryId(userDetails.getCountryId());
            countryView.fillWithElements(connection, CountryDetails.FILL_TYPE_ALL, countryDetails);
        }
        request.setAttribute("countryView", countryView);

        // state
        StateView stateView = new StateView();
        StateDetails stateDetails = new StateDetails();

        if (userDetails != null) {
            stateDetails.setStateId(userDetails.getStateId());
            stateView.fillWithElements(connection, StateDetails.FILL_TYPE_JOIN_COUNTRY, stateDetails);
        }
        request.setAttribute("stateView", stateView);

        // systemType
        SystemTypeView systemTypeView = new SystemTypeView();
        SystemTypeDetails systemTypeDetails = new SystemTypeDetails();

        if (userDetails != null) {
            systemTypeDetails.setSystemTypeId(userDetails.getSystemTypeId());
            systemTypeView.fillWithElements(connection, SystemTypeDetails.FILL_TYPE_ALL, systemTypeDetails);
        }
        request.setAttribute("systemTypeView", systemTypeView);

        // softwareType
        SoftwareTypeView softwareTypeView = new SoftwareTypeView();
        SoftwareTypeDetails softwareTypeDetails = new SoftwareTypeDetails();

        if (userDetails != null) {
            softwareTypeDetails.setSoftwareTypeId(userDetails.getSoftwareTypeId());
            softwareTypeView.fillWithElements(connection, SoftwareTypeDetails.FILL_TYPE_ALL, softwareTypeDetails);
        }
        request.setAttribute("softwareTypeView", softwareTypeView);

        // status
        StatusView statusView = new StatusView();
        StatusDetails statusDetails = new StatusDetails();

        if (userDetails != null) {
            statusDetails.setStatusId(userDetails.getStatusId());
            statusView.fillWithElements(connection, StatusDetails.FILL_TYPE_ALL, new StatusDetails());
        }
        request.setAttribute("statusView", statusView);

        // verified
        VerifiedView verifiedView = new VerifiedView();
        VerifiedDetails verifiedDetails = new VerifiedDetails();

        if (userDetails != null) {
            verifiedDetails.setVerifiedId(userDetails.getVerifiedId());
            verifiedView.fillWithElements(connection, VerifiedDetails.FILL_TYPE_ALL, verifiedDetails);
        }
        request.setAttribute("verifiedView", verifiedView);

        // size
        SizeView sizeView = new SizeView();
        SizeDetails sizeDetails = new SizeDetails();

        if (userDetails != null) {
            sizeDetails.setSizeId(userDetails.getSizeId());
            sizeView.fillWithElements(connection, SizeDetails.FILL_TYPE_ALL, sizeDetails);
        }
        request.setAttribute("sizeView", sizeView);
    
        DateFormatView dateFormatView = new DateFormatView();
        DateFormatDetails dateFormatDetails = new DateFormatDetails();
        
        if (userDetails != null) {
        	dateFormatDetails.setDateFormat(userDetails.getDateFormat());
        	dateFormatView.fillWithElements(connection, CompanyDetails.FILL_TYPE_ALL, new DateFormatDetails());
        }
        
        request.setAttribute("dateFormatView", dateFormatView);
        
        TimeOffsetView timeOffsetView = new TimeOffsetView();
        TimeOffsetDetails timeOffsetDetails = new TimeOffsetDetails();
  
        
        if (userDetails != null) {
        	timeOffsetDetails.setTimeOffset(userDetails.getTimeOffsetId());
        	timeOffsetView.fillWithElements(connection,CompanyDetails.FILL_TYPE_ALL, new TimeOffsetDetails());
        }
        request.setAttribute("timeOffsetView", timeOffsetView);
        
    }

    /**
     * Method getUserInformation.
     * @param userDetails UserDetails
     */
    private void getUserInformation(UserDetails userDetails) {
    	
        if (request.getParameter("ckAllowAllTerritoriesCalendar") != null) {
        	userDetails.setAllowCalendarViewOfAllTerritories(new BigDecimal(1));
        } else {
        	 userDetails.setAllowCalendarViewOfAllTerritories(new BigDecimal(0));
        }
        
        if (request.getParameter("ckSpecificTerritories") != null
                && !request.getParameter("ckSpecificTerritories").equals("")) {

    		if(new BigDecimal(request.getParameter("ckSpecificTerritories")).compareTo(UserDetails.SPECIFIC_TERRITORIES) == 0){
    			userDetails.setSpecificTerritories(UserDetails.SPECIFIC_TERRITORIES);
    		}              
        }
        
        if (request.getParameter("ckSpecificStatuses") != null
                && !request.getParameter("ckSpecificStatuses").equals("")) {

    		if(new BigDecimal(request.getParameter("ckSpecificStatuses")).compareTo(UserDetails.SPECIFIC_STATUSES) == 0){
    			userDetails.setSpecificStatuses(UserDetails.SPECIFIC_STATUSES);
    		}              
        }
        
        if (request.getParameter("ckSpecificLobs") != null
                && !request.getParameter("ckSpecificLobs").equals("")) {

    		if(new BigDecimal(request.getParameter("ckSpecificLobs")).compareTo(UserDetails.SPECIFIC_LOBS) == 0){
    			userDetails.setSpecificLobs(UserDetails.SPECIFIC_LOBS);
    		}              
        }
        
        if (request.getParameter("dfCompanyId") != null
            && !request.getParameter("dfCompanyId").equals("")) {
            userDetails.setCompanyId(new BigDecimal(request.getParameter("dfCompanyId")));
        }
        if (request.getParameter("dfFirstName") != null
            && !request.getParameter("dfFirstName").equals("")) {
            userDetails.setFirstName(request.getParameter("dfFirstName"));
        }
        if (request.getParameter("dfLastName") != null
            && !request.getParameter("dfLastName").equals("")) {
            userDetails.setLastName(request.getParameter("dfLastName"));
        }
        if (request.getParameter("dfEmail") != null
            && !request.getParameter("dfEmail").equals("")) {
            userDetails.setEmail(request.getParameter("dfEmail"));
        }
//        if (request.getParameter("dfTerritoryId") != null
//            && !request.getParameter("dfTerritoryId").equals("")) {
//            userDetails.setTerritoryId(new BigDecimal(request.getParameter("dfTerritoryId")));
//        }
//        if (request.getParameter("dfLobId") != null
//            && !request.getParameter("dfLobId").equals("")) {
//            userDetails.setLobId(new BigDecimal(request.getParameter("dfLobId")));
//        }
//        if (request.getParameter("dfStatusId") != null
//            && !request.getParameter("dfStatusId").equals("")) {
//            userDetails.setStatusId(new BigDecimal(request.getParameter("dfStatusId")));
//        }
        if (request.getParameter("dfSoftwareTypeId") != null
            && request.getParameter("dfSoftwareTypeId") != null
            && !request.getParameter("dfSoftwareTypeId").equals("")) {
            userDetails.setSoftwareTypeId(new BigDecimal(request.getParameter("dfSoftwareTypeId")));
        }
        if (request.getParameter("dfSystemTypeId") != null
            && !request.getParameter("dfSystemTypeId").equals("")) {
            userDetails.setSystemTypeId(new BigDecimal(request.getParameter("dfSystemTypeId")));
        }
        if (request.getParameter("dfVerifiedId") != null
            && !request.getParameter("dfVerifiedId").equals("")) {
            userDetails.setVerifiedId(new BigDecimal(request.getParameter("dfVerifiedId")));
        }
        if (request.getParameter("dfSizeId") != null
            && !request.getParameter("dfSizeId").equals("")) {
            userDetails.setSizeId(new BigDecimal(request.getParameter("dfSizeId")));
        }
        if (request.getParameter("dfCountryId") != null
            && !request.getParameter("dfCountryId").equals("")) {
            userDetails.setCountryId(new BigDecimal(request.getParameter("dfCountryId")));
        }
        if (request.getParameter("dfStateId") != null
            && !request.getParameter("dfStateId").equals("")) {
            userDetails.setStateId(new BigDecimal(request.getParameter("dfStateId")));
        }
        if (request.getParameter("cmbDateFormat") != null
                && !request.getParameter("cmbDateFormat").equals("")) {
        		userDetails.setDateFormatId(new BigDecimal(request.getParameter("cmbDateFormat")));
        }
        
        if (request.getParameter("cmbTimeOffset") != null
                && !request.getParameter("cmbTimeOffset").equals("")) {
        		userDetails.setTimeOffsetId(new BigDecimal(request.getParameter("cmbTimeOffset")));
        }
        
       // System.out.println("limit = " + request.getParameter("ckLimitProspectSearchView"));
        if (request.getParameter("ckLimitProspectSearchView") != null
                && !request.getParameter("ckLimitProspectSearchView").equals("")) {
        	
    		if(new BigDecimal(request.getParameter("ckLimitProspectSearchView")).compareTo(UserDetails.LIMIT_VIEW) == 0){
    			userDetails.setLimitProspectSearchView(UserDetails.LIMIT_VIEW);
    		} 
        		
        }
        //System.out.println("limit company = " + request.getParameter("ckLimitCompanyView"));
        if (request.getParameter("ckLimitCompanyView") != null
                && !request.getParameter("ckLimitCompanyView").equals("")) {
        	
    		if(new BigDecimal(request.getParameter("ckLimitCompanyView")).compareTo(UserDetails.LIMIT_COMPANY) == 0){
    			userDetails.setLimitCompanyView(UserDetails.LIMIT_COMPANY);
    		} 
        		
        }
      
    }

    /**
     * Method insert.
     */
    private void insert() {
        try {
            openConnection();

            UserDetails userDetails = new UserDetails();
            UserView userView = new UserView();
            String user = request.getParameter("dfUserName");

            try {

                userDetails.setUserName(user);
                userView.fillWithElements(connection, UserView.FILL_TYPE_LOGIN, userDetails);

                if (userView.getElements().size() > 0) {
                    throw new ModelException("A salesman with this Login User Name has already been created.", ModelException.DUPLICATE_RECORD);
                }
                
                userDetails.setPassword(request.getParameter("dfPassword"));


                userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);
                userDetails.setCreationDate(DateUtilities.getCurrentSQLTimestamp());
                userDetails.setActive(UserDetails.USER_ACTIVE);

                getUserInformation(userDetails);
                
                if(userDetails.getSpecificStatuses()==null){
                	userDetails.setSpecificStatuses(UserDetails.NO_SPECIFIC_STATUSES);
                	deleteUserStatuses(userDetails.getUserId());
                }
                
                if(userDetails.getSpecificTerritories() == null){
                	userDetails.setSpecificTerritories(UserDetails.NO_SPECIFIC_TERRITORIES);
                	deleteUserTerritories(userDetails.getUserId());
                }
                
                if(userDetails.getSpecificLobs() == null){
                	userDetails.setSpecificLobs(UserDetails.NO_SPECIFIC_LOBS);
                	deleteUserLobs(userDetails.getUserId());
                }

//              insert user
                userView.doAction(connection, "INSERT", UserView.FILL_TYPE_ALL, userDetails);
                
                
                //had to create logic where if the specific statuses and specific territories were = to null, then automatically set the
                //the userDetails.setSpecificStatuses and userDetails.setSpecificTerritories to NO_SPECIFIC_STATUSES & NO_SPECIFIC_TERRITORIES
                if(userDetails.getSpecificStatuses()!= null && userDetails.getSpecificStatuses().compareTo(UserDetails.SPECIFIC_STATUSES)==0){
                	setUserStatuses(user);
                }
                
                if(userDetails.getSpecificTerritories()!= null && userDetails.getSpecificTerritories().compareTo(UserDetails.SPECIFIC_TERRITORIES)==0){
                	setUserTerritories(user);
                }

            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/user/userActionResult.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


//
//	private void deleteSpecificTerritories(UserDetails userDetails) throws Exception {
//		UserTerritoriesDetails salesmanTerritoriesDetails = new UserTerritoriesDetails();
//		UserTerritoriesView userTerritoriesView = new UserTerritoriesView();
//		
//		salesmanTerritoriesDetails.setUserId(userDetails.getUserId());
//		userTerritoriesView.doAction(connection, "DELETE", UserTerritoriesView.FILL_TYPE_ALL, salesmanTerritoriesDetails);
//	}
//
//	private void deleteSpecificStatuses(UserDetails userDetails) throws Exception {
//		UserStatusesDetails salesmanStatusesDetails = new UserStatusesDetails();
//		UserStatusesView userStatusesView = new UserStatusesView();
//		
//		salesmanStatusesDetails.setUserId(userDetails.getUserId());
//		userStatusesView.doAction(connection, "DELETE", UserTerritoriesView.FILL_TYPE_ALL, salesmanStatusesDetails);
//		
//	}

	private BigDecimal getUserId(String user) throws Exception {
		BigDecimal userId = new BigDecimal(0);
		
		UserDetails searchUserDetails = new UserDetails();
		UserView userView = new UserView();
		
		searchUserDetails.setUserName(user);
		userView.fillWithElements(connection, UserView.FILL_TYPE_ALL, searchUserDetails);
		
		UserDetails userDetails = (UserDetails)userView.getElement(0);

		userId = userDetails.getUserId();
		
		return userId;
	}
    private void setUserStatuses(String user) throws Exception {
    	BigDecimal userId = getUserId(user);
        UserStatusesDetails userStatusesDetails;
        UserStatusesView userStatusesView;
        
        String[] statusesSelected = request.getParameterValues("dfStatusId");

        if(statusesSelected!= null){
	        for(int i = 0; i < statusesSelected.length; i++){
	        	userStatusesDetails = new UserStatusesDetails();
	        	userStatusesView = new UserStatusesView();
	        	userStatusesDetails.setStatusId(new BigDecimal(statusesSelected[i]));
	        	userStatusesDetails.setUserId(userId);
	        	userStatusesView.doAction(connection, "INSERT", UserStatusesView.FILL_TYPE_ALL, userStatusesDetails);
	        }
        }
		
	}
    
    private void updateUserStatuses(BigDecimal userId) throws Exception {
		deleteUserStatuses(userId);
        UserStatusesDetails userStatusesDetails;
        UserStatusesView userStatusesView;
        
        String[] statusesSelected = request.getParameterValues("dfStatusId");

        if(statusesSelected!= null && statusesSelected.length > 0){
	        for(int i = 0; i < statusesSelected.length; i++){
	        	userStatusesDetails = new UserStatusesDetails();
	        	userStatusesView = new UserStatusesView();
	        	userStatusesDetails.setStatusId(new BigDecimal(statusesSelected[i]));
	        	userStatusesDetails.setUserId(userId);
	        	userStatusesView.doAction(connection, "INSERT", UserStatusesView.FILL_TYPE_ALL, userStatusesDetails);
	        }
        }
		
	}
	private void setUserTerritories(String user) throws Exception {

		BigDecimal userId = getUserId(user);
        UserTerritoriesDetails userTerritoryDetails;
        UserTerritoriesView userTerritoryView;
        
        String[] territoriesSelected = request.getParameterValues("dfTerritoryId");

        if(territoriesSelected != null && territoriesSelected.length > 0){
	        for(int i = 0; i < territoriesSelected.length; i++){
	        	userTerritoryDetails = new UserTerritoriesDetails();
	        	userTerritoryView = new UserTerritoriesView();
	        	userTerritoryDetails.setTerritoryId(new BigDecimal(territoriesSelected[i]));
	        	userTerritoryDetails.setUserId(userId);
	        	userTerritoryView.doAction(connection, "INSERT", UserTerritoriesView.FILL_TYPE_ALL, userTerritoryDetails);
	        }
        }
		
	}

	private void updateUserTerritories(BigDecimal userId) throws Exception {

		deleteUserTerritories(userId);
        UserTerritoriesDetails userTerritoryDetails;
        UserTerritoriesView userTerritoryView;
        
        String[] territoriesSelected = request.getParameterValues("dfTerritoryId");

        if(territoriesSelected != null){
	        for(int i = 0; i < territoriesSelected.length; i++){
	        	userTerritoryDetails = new UserTerritoriesDetails();
	        	userTerritoryView = new UserTerritoriesView();
	        	userTerritoryDetails.setTerritoryId(new BigDecimal(territoriesSelected[i]));
	        	userTerritoryDetails.setUserId(userId);
	        	userTerritoryView.doAction(connection, "INSERT", UserTerritoriesView.FILL_TYPE_ALL, userTerritoryDetails);
	        }
        }
	}

	private void setUserLobs(String user) throws Exception {

		BigDecimal userId = getUserId(user);
        UserLobsDetails userLobDetails;
        UserLobsView userLobView;
        
        String[] lobsSelected = request.getParameterValues("dfLobId");

        if(lobsSelected != null && lobsSelected.length > 0){
	        for(int i = 0; i < lobsSelected.length; i++){
	        	userLobDetails = new UserLobsDetails();
	        	userLobView = new UserLobsView();
	        	userLobDetails.setLobId(new BigDecimal(lobsSelected[i]));
	        	userLobDetails.setUserId(userId);
	        	userLobView.doAction(connection, "INSERT", UserLobsView.FILL_TYPE_ALL, userLobDetails);
	        }
        }
		
	}
	
	private void updateUserLobs(BigDecimal userId) throws Exception {

		deleteUserLobs(userId);
        UserLobsDetails userLobDetails;
        UserLobsView userLobView;
        
        String[] lobsSelected = request.getParameterValues("dfLobId");

        if(lobsSelected != null){
	        for(int i = 0; i < lobsSelected.length; i++){
	        	userLobDetails = new UserLobsDetails();
	        	userLobView = new UserLobsView();
	        	userLobDetails.setLobId(new BigDecimal(lobsSelected[i]));
	        	userLobDetails.setUserId(userId);
	        	userLobView.doAction(connection, "INSERT", UserLobsView.FILL_TYPE_ALL, userLobDetails);
	        }
        }
	}
	
	private void deleteUserTerritories(BigDecimal userId) throws Exception {
		UserTerritoriesDetails userTerritoriesDetails = new UserTerritoriesDetails();
		UserTerritoriesView userTerritoriesView = new UserTerritoriesView();;
		
		userTerritoriesDetails.setUserId(userId);
		userTerritoriesView.doAction(connection, "DELETE", UserTerritoriesView.FILL_TYPE_ALL, userTerritoriesDetails);
	}
	
	private void deleteUserStatuses(BigDecimal userId) throws Exception {
		UserStatusesDetails userStatusesDetails = new UserStatusesDetails();
		UserStatusesView userStatusesView = new UserStatusesView();;
		
		userStatusesDetails.setUserId(userId);
		userStatusesView.doAction(connection, "DELETE", UserStatusesView.FILL_TYPE_ALL, userStatusesDetails);
	}

	private void deleteUserLobs(BigDecimal userId) throws Exception {
		UserLobsDetails userLobsDetails = new UserLobsDetails();
		UserLobsView userLobsView = new UserLobsView();;
		
		userLobsDetails.setUserId(userId);
		userLobsView.doAction(connection, "DELETE", UserLobsView.FILL_TYPE_ALL, userLobsDetails);
	}
	
	/**
     * Method update.
     */
    private void update() {
        try {
            openConnection();

            UserDetails userDetails = new UserDetails();
            UserView userView = new UserView();

            try {
                if (!request.getParameter("dfUserName").equals(request.getParameter("dfOldUserName"))) {
                    userDetails.setUserName(request.getParameter("dfUserName"));
                    userView.fillWithElements(connection, UserView.FILL_TYPE_LOGIN, userDetails);

                    if (userView.getElements().size() > 0) {
                        throw new ModelException("A salesman with this Login User Name has already been created.", ModelException.DUPLICATE_RECORD);
                    }
                    userProfile = (UserProfile) request.getSession().getAttribute("userProfile");
                    userProfile.setUserName(userDetails.getUserName());
                    request.getSession().setAttribute("userProfile", userProfile);
                }
                if (!request.getParameter("dfPassword").equals("")) {
                    userDetails.setPassword(request.getParameter("dfPassword"));
                    userProfile = (UserProfile) request.getSession().getAttribute("userProfile");
                    userProfile.setPassword(userDetails.getPassword());
                    request.getSession().setAttribute("userProfile", userProfile);

                }


                userDetails.setUserId(new BigDecimal(request.getParameter("dfUserId")));
                userDetails.setUserType(UserDetails.USER_TYPE_SALESMAN);
                userDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                getUserInformation(userDetails);
                
                BigDecimal userId = userDetails.getUserId();
                
               // System.out.println("adminUpdate = " + request.getParameter("adminUpdate"));
                if(request.getParameter("adminUpdate").equals("true")){
                	//System.out.println("\n\n== TRUE ==\n\n");
                    if(userDetails.getSpecificStatuses()!= null && 
                    		userDetails.getSpecificStatuses().compareTo(UserDetails.SPECIFIC_STATUSES)==0){
                    	updateUserStatuses(userId);
                    }else{
                    	userDetails.setSpecificStatuses(UserDetails.NO_SPECIFIC_STATUSES);
                    	deleteUserStatuses(userId);
                    }
                    
                    if(userDetails.getSpecificTerritories()!= null && 
                    		userDetails.getSpecificTerritories().compareTo(UserDetails.SPECIFIC_TERRITORIES)==0){
                    	updateUserTerritories(userId);
                    }else{
                    	userDetails.setSpecificTerritories(UserDetails.NO_SPECIFIC_TERRITORIES);
                    	deleteUserTerritories(userId);
                    }
                    
                    if(userDetails.getSpecificLobs()!= null && 
                    		userDetails.getSpecificLobs().compareTo(UserDetails.SPECIFIC_LOBS)==0){
                    	updateUserLobs(userId);
                    }else{
                    	userDetails.setSpecificLobs(UserDetails.NO_SPECIFIC_LOBS);
                    	deleteUserLobs(userId);
                    }
                    
                }

                
                
                if (isAdmin()) {
                    userView.doAction(connection, "UPDATE", UserView.FILL_TYPE_ALL, userDetails);
                } else {
                    userView.doAction(connection, "UPDATE", UserView.FILL_TYPE_ACCOUNT, userDetails);
                }
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            if (isAdmin()) {
                request.getRequestDispatcher("/jsp/admin/user/userActionResult.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/jsp/public/user/userActionResult.jsp").forward(request, response);
            }
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method delete.
     */
    private void delete() {
        try {
            openConnection();

            // check if user exist
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(new BigDecimal(request.getParameter("dfUserId")));

            UserView userView = new UserView();
            userView.fillWithElements(connection, UserView.FILL_TYPE_LOGIN, userDetails);

            deleteUserStatuses(userDetails.getUserId());
            deleteUserTerritories(userDetails.getUserId());
            try {
                if (userView.getElements().size() == 0) {
                    throw new ModelException("The user you are trying to delete can no longer be found in the database.", ModelException.RECORD_NOT_FOUND);
                }
                // delete user
                try {
                    userView.doAction(connection, "DELETE", null, userDetails);
                } catch (SQLException sqlE) {
                    throw new ModelException("The user cannot be deleted. <br>The most probable reason for this is that there are prospects created by this user.", ModelException.UNKNOWN_ERROR);
                }


            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            commit();
            // forward request
            request.getRequestDispatcher("/jsp/admin/user/userActionResult.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }
}
