package com.randr.webdw.calendarSchedule;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.randr.webdw.AppSettings;
import com.randr.webdw.lob.LobDetails;
import com.randr.webdw.lob.LobView;
import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.status.StatusDetails;
import com.randr.webdw.status.StatusView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.userLobs.UserLobsDetails;
import com.randr.webdw.userLobs.UserLobsView;
import com.randr.webdw.userStatuses.UserStatusesDetails;
import com.randr.webdw.userStatuses.UserStatusesView;
import com.randr.webdw.userTerritories.UserTerritoriesDetails;
import com.randr.webdw.userTerritories.UserTerritoriesView;

/**
 * User: Administrator
 * Date: Dec 29, 2006
 * @author randr
 * @version $Revision: 1.32 $
 */

public class CalendarScheduleController extends AbstractController{
	public static final int NEXT_MONTH = 1;
	public static final int PREV_MONTH = -1;
	public static final int PREV_WEEK = -7;
	public static final int NEXT_WEEK = 7;
	public static final int NUMBER_OF_DAYS_IN_WEEK = 7;

	public static final String DEFAULT_REFRESH_INTERVAL = "30";

	public static final String DIRECTION_PREVIOUS = "PREV";
	public static final String DIRECTION_NEXT = "NEXT";
	
	public static final String[] months = {"January", "Febuary", "March", "April",
		"May", "June", "July", "August", "September", "October", "November", "December"};
	
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
    	
    	
    	String refreshInterval;
    	
    	refreshInterval = (String)request.getSession().getAttribute("refreshInterval");
    	
    	if(refreshInterval == null || refreshInterval.trim().equals("")) {
    		refreshInterval = AppSettings.getParm("CALENDAR_REFRESH_SECONDS");
    		if(refreshInterval == null || refreshInterval.trim().equals("")) {
    			refreshInterval = DEFAULT_REFRESH_INTERVAL;
    		}
    		request.getSession().setAttribute("refreshInterval", refreshInterval.toString());
    	}

		try {

	    	if(refreshInterval == null || refreshInterval.trim().equals("")) {
	    		refreshInterval = AppSettings.getParm("CALENDAR_REFRESH_SECONDS");
	    		if(refreshInterval == null || refreshInterval.trim().equals("")) {
	    			refreshInterval = DEFAULT_REFRESH_INTERVAL;
	    		}
	    		request.getSession().setAttribute("refreshInterval", refreshInterval.toString());
	    	}
			
	    	if(formAction.equals("DISPLAY_CALENDAR_SELECTION")){
	    		displayCalendarSelection();
			} else if ((formAction == null) || (formAction.equals("DISPLAY"))) {
				displayCalendar();
	        } else if(formAction.equals("CHANGE_VIEW_WEEK_MULTIPLE")){
	        	changeViewWeek(getCalendarSearchCriteria());
	        } else if(formAction.equals("CHANGE_VIEW_MONTH_MULTIPLE")){
	        	changeViewMonth(getCalendarSearchCriteria());
	        }
	    	
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    

	private void displayCalendar() {
		try {
			openConnection();

			List territoryIdList = getTerritoryIdList();
			List statusIdList = getStatusIdList();
			List lobIdList = getLobIdList();
			
			String calendarType = getCalendarType(territoryIdList, statusIdList, lobIdList);
			
			CalendarSearchCriteria calendarSearchCriteria = getCalendarSearchCriteria();
	    	
			if (calendarType.equals("WEEK")){
	        	displayWeekView(calendarSearchCriteria);
	        } else if (calendarType.equals("MONTH")){
	        	displayMonthView(calendarSearchCriteria);
	        } 
			
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			finallyMethod();
		}
		
	}


	private void displayCalendarSelection() {
		try { 
			destroySessionAttributes();
			destroyCalendarSearchCriteria();
			openConnection();
			
			BigDecimal salesmanId = userProfile.getUserId();
			
			if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
					userProfile.getSpecificTerritories()==null ||
					userProfile.getSpecificTerritories().compareTo(UserDetails.NO_SPECIFIC_TERRITORIES) == 0){
				
				TerritoryDetails territoryDetails = new TerritoryDetails();
				if(userProfile.getAllowCalendarViewOfAllTerritories().compareTo(new BigDecimal(0)) == 0) {
					territoryDetails.setTerritoryId(userProfile.getTerritoryId());
				}	
				request.setAttribute("territoryView", getTerritoryView(territoryDetails));
				
			}else{
				//gettting salesman specific territories
				TerritoryView salesmanTerritoriesView = getSalesmanTerritoriesView(salesmanId);
				request.setAttribute("territoryView", salesmanTerritoriesView);
			}
				
			if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
					userProfile.getSpecificLobs()==null ||
					userProfile.getSpecificLobs().compareTo(UserDetails.NO_SPECIFIC_LOBS) == 0){
				
				LobDetails lobDetails = new LobDetails();
				if(userProfile.getLobId() != null) {
					lobDetails.setLobId(userProfile.getLobId());
				}
				request.setAttribute("lobView", getLobView(lobDetails));
				
			}else{
				//gettting salesman specific territories
				LobView salesmanLobsView = getSalesmanLobsView(salesmanId);
				request.setAttribute("lobView", salesmanLobsView);
			}
			
			if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
					userProfile.getSpecificStatuses()==null ||
					userProfile.getSpecificStatuses().compareTo(UserDetails.NO_SPECIFIC_STATUSES) == 0){	
				StatusDetails statusDetails = new StatusDetails();
				if(userProfile.getStatusId() != null) {
					statusDetails.setStatusId(userProfile.getStatusId());
				}	
				request.setAttribute("statusView", getStatusView(statusDetails));
				
			}else{
				//getting salesman specific statuses
				StatusView salesmanStatusesView = getSalesmanStatusesView(salesmanId);
				request.setAttribute("statusView", salesmanStatusesView);
			}

			commit();
			
			forward("/jsp/shared/calendarSchedule/calendarSchedule.jsp");
			
		} catch (Exception e) {
			doCatch(e);
		}finally {
			finallyMethod();
		}
		
	}
	
	private void displayWeekView(CalendarSearchCriteria calendarSearchCriteria) throws Exception {
	
		WeeklyCalendarSchedule weeklyCalendarSchedule = new WeeklyCalendarSchedule();
		weeklyCalendarSchedule.displayWeekView(request, response, connection, userProfile, calendarSearchCriteria);		
	}
    
	private void changeViewWeek(CalendarSearchCriteria calendarSearchCriteria) {
		try { 
			openConnection();
			
			WeeklyCalendarSchedule weeklyCalendarSchedule = new WeeklyCalendarSchedule();
			weeklyCalendarSchedule.changeViewWeek(request, response, connection, userProfile, calendarSearchCriteria);
			
			commit();
		} catch (Exception e) {
			doCatch(e);
		}finally {
			finallyMethod();
		}
	}

    private void displayMonthView(CalendarSearchCriteria calendarSearchCriteria) throws Exception {

		MonthlyCalendarSchedule monthlyCalendarSchedule = new MonthlyCalendarSchedule();
		monthlyCalendarSchedule.displayMonthView(request, response, connection, userProfile, calendarSearchCriteria);	
	}
	
	private void changeViewMonth(CalendarSearchCriteria calendarSearchCriteria) {

		try { 
			openConnection();
			
			MonthlyCalendarSchedule monthlyCalendarSchedule = new MonthlyCalendarSchedule();
			monthlyCalendarSchedule.changeViewMonth(request, response, connection, userProfile, calendarSearchCriteria);
			
			commit();							
		} catch (Exception e) {
			doCatch(e);
		}finally {
			finallyMethod();
		}	
		if(request.getParameter("direction").equals(DIRECTION_PREVIOUS)) {
		} else if (request.getParameter("direction").equals(DIRECTION_NEXT)) {
		}
		
	}
	
	private TerritoryView getTerritoryView(TerritoryDetails territoryDetails) throws Exception{
		TerritoryView territoryView = new TerritoryView();
		territoryView.fillWithElements(connection, 
				TerritoryDetails.FILL_TYPE_ALL, territoryDetails); 
		return territoryView;
	}
	
	
	private StatusView getStatusView(StatusDetails statusDetails) throws Exception{
		StatusView statusView = new StatusView();
		statusView.fillWithElements(connection, 
				StatusDetails.FILL_TYPE_ALL, statusDetails); 
		return statusView;
	}
	
	private LobView getLobView(LobDetails lobDetails) throws Exception{
		LobView lobView = new LobView();
		lobView.fillWithElements(connection, 
				LobDetails.FILL_TYPE_ALL, lobDetails); 
		return lobView;
	}
	
	private void destroySessionAttributes() {
		request.getSession().removeAttribute("calendar");
	}
	
    private String getCalendarType(List territoryIdList, List statusIdList, List lobIdList) {

    	if (request.getParameter("calendarType")!=null){
			destroyCalendarSearchCriteria();
			saveCalendarSearchCriteria(territoryIdList, statusIdList, lobIdList);
			return request.getParameter("calendarType");
		}else {
			return getCalendarSearchCriteria().getCalendarType();
		}
	}
    
    private List getTerritoryIdList() throws Exception {

		List territoryIdList = new ArrayList();
		
        if (request.getParameter("dfCalTerritoryId") != null
                && !request.getParameter("dfCalTerritoryId").equals("")) {
            	
            String[] territoriesSelected = request.getParameterValues("dfCalTerritoryId");

            if(territoriesSelected != null && territoriesSelected.length > 0){
    	        for(int i = 0; i < territoriesSelected.length; i++){
    	        	territoryIdList.add(new BigDecimal(territoriesSelected[i]));
    	        }
            }
        } else {
            //user did not select an option, check the user authority level
            if (userProfile != null) {
            	if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
            			userProfile.getSpecificTerritories()==null ||
            			userProfile.getSpecificTerritories().compareTo(UserDetails.NO_SPECIFIC_TERRITORIES) == 0){
            		
            		return territoryIdList;
            	}else{
	    			TerritoryView salesmanTerritoriesView = getSalesmanTerritoriesView(userProfile.getUserId());
	    			
	    			TerritoryDetails salesmanTerritoryDetails;
					if(salesmanTerritoriesView.getElements().size()> 0){
						for(int i = 0; i < salesmanTerritoriesView.getElements().size(); i++ ){
							salesmanTerritoryDetails = (TerritoryDetails)salesmanTerritoriesView.getElement(i);
							territoryIdList.add(salesmanTerritoryDetails.getTerritoryId());
						}
					}
            	}
            }
        }
        return territoryIdList;

	}
    
    private List getStatusIdList() throws Exception {
		List statusIdList = new ArrayList();
		//System.out.println("dfCalStatusId=" + request.getParameter("dfCalStatusId"));
        if (request.getParameter("dfCalStatusId") != null
                && !request.getParameter("dfCalStatusId").equals("")) {
            //System.out.println("getStatusIdList() - in if statement");	
            String[] statusesSelected = request.getParameterValues("dfCalStatusId");

            if(statusesSelected != null && statusesSelected.length > 0){
    	        for(int i = 0; i < statusesSelected.length; i++){
    	        	statusIdList.add(new BigDecimal(statusesSelected[i]));
    	        }
            }       
        } else {
            //user did not select an option, check the user authority level
            if (userProfile != null) {
            	if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
            			userProfile.getSpecificStatuses()==null ||
            			userProfile.getSpecificStatuses().compareTo(UserDetails.NO_SPECIFIC_TERRITORIES) == 0){
            		
            		return statusIdList;
            		
            	}

    			StatusView salesmanStatusesView = getSalesmanStatusesView(userProfile.getUserId());
    			
    			StatusDetails salesmanStatusesDetails;
    			
				if(salesmanStatusesView.getElements().size()> 0){
					for(int i = 0; i < salesmanStatusesView.getElements().size(); i++ ){
						salesmanStatusesDetails = (StatusDetails)salesmanStatusesView.getElement(i);
						statusIdList.add(salesmanStatusesDetails.getStatusId());
					}
				}
            	
            }
        }
        return statusIdList;      
	}
 
    private List getLobIdList() throws Exception {
		List lobIdList = new ArrayList();
        if (request.getParameter("dfCalLobId") != null
                && !request.getParameter("dfCalLobId").equals("")) {
            	
            String[] lobsSelected = request.getParameterValues("dfCalLobId");
           // System.out.println("lobs=" + request.getParameterValues("dfCalLobId").length);
            if(lobsSelected != null && lobsSelected.length > 0){
    	        for(int i = 0; i < lobsSelected.length; i++){
    	        	lobIdList.add(new BigDecimal(lobsSelected[i]));
    	        }
            }       
        } else {
            //user did not select an option, check the user authority level
            if (userProfile != null) {
            	if(userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN) || 
            			userProfile.getSpecificLobs()==null  ||
            			userProfile.getSpecificLobs().compareTo(UserDetails.NO_SPECIFIC_LOBS) == 0){
            		
            		return lobIdList;
            	}

    			LobView salesmanLobsView = getSalesmanLobsView(userProfile.getUserId());		
    			LobDetails salesmanLobsDetails;
    			
				if(salesmanLobsView.getElements().size()> 0){
					for(int i = 0; i < salesmanLobsView.getElements().size(); i++ ){
						salesmanLobsDetails = (LobDetails)salesmanLobsView.getElement(i);
						lobIdList.add(salesmanLobsDetails.getLobId());
					}
				}
            	
            }
        }
            return lobIdList;      
	}
    
    
    private void saveCalendarSearchCriteria(List territoryIdList, List statusIdList, List lobIdList) {

		CalendarSearchCriteria calendarSearchCritieria = new CalendarSearchCriteria();
		calendarSearchCritieria.setTerritoryId(request.getParameter("dfCalTerritoryId"));   
		
		calendarSearchCritieria.setCalendarType(request.getParameter("calendarType"));
		calendarSearchCritieria.setStatusId(request.getParameter("cmbSearchStatusId"));
		calendarSearchCritieria.setSelectDate(request.getParameter("dfSelectDate"));

		calendarSearchCritieria.setTerritoryIdList(territoryIdList);
		calendarSearchCritieria.setStatusIdList(statusIdList);
		calendarSearchCritieria.setLobIdList(lobIdList);
		
		request.getSession().setAttribute("calendarSearchCritieria", calendarSearchCritieria);		
	}

	private void destroyCalendarSearchCriteria() {
		request.getSession().removeAttribute("calendarSearchCritiera");	
	}
	
	protected CalendarSearchCriteria getCalendarSearchCriteria(){
		return (CalendarSearchCriteria)request.getSession().getAttribute("calendarSearchCritieria");
	}
	
    private StatusView getSalesmanStatusesView(BigDecimal salesmanId) throws Exception {
    	
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

    private LobView getSalesmanLobsView(BigDecimal salesmanId) throws Exception {
    	
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
    
    private TerritoryView getSalesmanTerritoriesView(BigDecimal salesmanId) throws Exception {
    	
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
}
