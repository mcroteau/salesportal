package com.randr.webdw.calendarSchedule;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.userTerritories.UserTerritoriesDetails;
import com.randr.webdw.userTerritories.UserTerritoriesView;
import com.randr.webdw.util.DateUtilities;

public class BaseCalendarSchedule {
	
	public static final int NEXT_MONTH = 1;
	public static final int PREV_MONTH = -1;
	public static final int PREV_WEEK = -7;
	public static final int NEXT_WEEK = 7;
	public static final int NUMBER_OF_DAYS_IN_WEEK = 7;
	
	public static final String DIRECTION_PREVIOUS = "PREV";
	public static final String DIRECTION_NEXT = "NEXT";
	
	public static final String[] months = {"January", "Febuary", "March", "April",
		"May", "June", "July", "August", "September", "October", "November", "December"};
	
	public void forward(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.getRequestDispatcher(path).forward(request, response);
    }

    protected String getTerritoryId(HttpServletRequest request) {
		if (request.getParameter("dfCalTerritoryId")!=null){
			return request.getParameter("dfCalTerritoryId");
		}else {
			return ((CalendarSearchCriteria)request.getSession().getAttribute("calendarSearchCritieria")).getTerritoryId();
		}
	}
    
    protected String getStatusId(HttpServletRequest request) {
    	
		if (request.getParameter("dfCalStatusId")!=null){
			return request.getParameter("dfCalStatusId");
		}else {
			return ((CalendarSearchCriteria)request.getSession().getAttribute("calendarSearchCritieria")).getStatusId();
		}
	}
    
    protected String getSelectDate(HttpServletRequest request) {
		if (request.getParameter("dfSelectDate")!=null){
			return request.getParameter("dfSelectDate");
		}else {
			return ((CalendarSearchCriteria)request.getSession().getAttribute("calendarSearchCritieria")).getSelectDate();
		}
	}

    protected void saveCalendarSearchCriteria(HttpServletRequest request) {
		CalendarSearchCriteria calendarSearchCritieria = new CalendarSearchCriteria();
		calendarSearchCritieria.setTerritoryId(request.getParameter("dfCalTerritoryId"));  
		
		calendarSearchCritieria.setCalendarType(request.getParameter("calendarType"));
		calendarSearchCritieria.setStatusId(request.getParameter("cmbSearchStatusId"));
		calendarSearchCritieria.setSelectDate(request.getParameter("dfSelectDate"));
		request.getSession().setAttribute("calendarSearchCritieria", calendarSearchCritieria);
		
	}

    protected void destroyCalendarSearchCriteria(HttpServletRequest request) {
		request.getSession().removeAttribute("calendarSearchCritiera");	
	}
    
    protected TerritoryDetails getTerritoryDetails(Connection connection, BigDecimal territoryId) 
	throws Exception {		
		TerritoryDetails territoryDetails = new TerritoryDetails();
		territoryDetails.setTerritoryId(territoryId);
		TerritoryView territoryView = new TerritoryView();
		territoryView.fillWithElements(connection, TerritoryDetails.FILL_TYPE_ALL, territoryDetails); 
		
		return (TerritoryDetails)territoryView.getElement(0);
	
    }
    
	protected TerritoryView getTerritoryView(Connection connection) throws Exception{
		TerritoryView territoryView = new TerritoryView();
		territoryView.fillWithElements(connection, 
				TerritoryDetails.FILL_TYPE_ALL, new TerritoryDetails()); 
		return territoryView;
	}
	
	protected List getWeeklyDates(Timestamp startDateTimestamp) throws Exception{
		Timestamp timestamp;
		List weeklyDates = new ArrayList();
		for(int i = 0; i < 7; i++) {
			timestamp = DateUtilities.getWeekStartTimestamp(startDateTimestamp);
			timestamp.setDate(timestamp.getDate() + i);
			weeklyDates.add(timestamp);
		}
		return weeklyDates;
	}
	
	protected List getTerritories(HttpServletRequest request) throws Exception{
		
		List territoryIdList = new ArrayList();
		
        if (request.getParameter("dfCalTerritoryId") != null
                && !request.getParameter("dfCalTerritoryId").equals("")) {
            	
            String[] territoriesSelected = request.getParameterValues("dfCalTerritoryId");

            if(territoriesSelected != null && territoriesSelected.length > 0){
    	        for(int i = 0; i < territoriesSelected.length; i++){
    	        	territoryIdList.add(new BigDecimal(territoriesSelected[i]));
    	        }
            }      
        } 		
	
        return territoryIdList;
	}
	
	protected List getStatuses(HttpServletRequest request) throws Exception{
		
		List statusIdList = new ArrayList();
        if (request.getParameter("dfCalStatusId") != null
                && !request.getParameter("dfCalStatusId").equals("")) {
            	
            String[] statusesSelected = request.getParameterValues("dfCalStatusId");

            if(statusesSelected != null && statusesSelected.length > 0){
    	        for(int i = 0; i < statusesSelected.length; i++){
    	        	statusIdList.add(new BigDecimal(statusesSelected[i]));
    	        }
            }       
        } 		
	
        return statusIdList;
	}
	
	public CalendarScheduleView getFinalCalendarScheduleView(CalendarScheduleView calendarScheduleView, Connection connection) throws Exception{
		CalendarScheduleView finalCalendarScheduleView = new CalendarScheduleView();
		if(calendarScheduleView.getElements().size() > 0){
			for(int m = 0; m < calendarScheduleView.getElements().size(); m++){
				CalendarScheduleDetails calendarDetails = (CalendarScheduleDetails)calendarScheduleView.getElement(m);
				NoteDetails searchNoteDetails = new NoteDetails();
				NoteView searchNoteView = new NoteView();
				
				searchNoteDetails.setProspectId(calendarDetails.getProspectId());
				searchNoteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, searchNoteDetails);
				
//				System.out.println("NOTE SIZE FOR PROSPECT " + calendarDetails.getProspectId() + "  =  " + searchNoteView.getElements().size());
				calendarDetails.setNote(addCalendarDetailsNote(searchNoteView));
				
				
				finalCalendarScheduleView.addElement(calendarDetails);
			}
		}
		return finalCalendarScheduleView;
	}

	public static String addCalendarDetailsNote(NoteView searchNoteView) throws Exception{
		if(searchNoteView.getElements().size() > 0){
			for(int n = 0; n < searchNoteView.getElements().size(); n++){
				NoteDetails noteDetails = (NoteDetails)searchNoteView.getElement(n);
				
				
				if(noteDetails.getUserName().equals(UserDetails.DATA_IMPORT)){
//					System.out.println("equals note = " + noteDetails.getNote());
					//calendarDetails.setNote(noteDetails.getNote());
					return noteDetails.getNote();
				}
			}
			
		}
		return null;
	}

	
}
