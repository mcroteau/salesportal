package com.randr.webdw.calendarSchedule;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.randr.webdw.note.NoteDetails;
import com.randr.webdw.note.NoteView;
import com.randr.webdw.territory.TerritoryDetails;
import com.randr.webdw.territory.TerritoryView;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserProfile;

/**
 * @author Ann
 *
 */
public class MonthlyCalendarSchedule extends BaseCalendarSchedule {
	
    /**
     * 
     */
    public void displayMonthView(HttpServletRequest request, HttpServletResponse response, Connection connection, 
		UserProfile userProfile, CalendarSearchCriteria calendarSearchCriteria) throws Exception{

		Timestamp current;
		GregorianCalendar cal;
		
		if(request.getSession().getAttribute("calendar")!= null){
			cal = (GregorianCalendar) request.getSession().getAttribute("calendar");
		}else{
			if(!getSelectDate(request).equals("")) {
				current = DateUtilities.getSQLTimestamp(getSelectDate(request)+ " 00:00:00", userProfile.getDateFormat());					
			} else {
				current = new Timestamp(System.currentTimeMillis());
			}
			cal = new GregorianCalendar(current.getYear()+ 1900, current.getMonth(), 1);
		}
		
		setupMonthlyAttributes(cal, request);

		TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
		searchTerritoryDetails.setTerritoryIdList(calendarSearchCriteria.getTerritoryIdList());
		TerritoryView territoryView = new TerritoryView();
		territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);
//			TerritoryView territoryView = getTerritoryView(connection); 

		CalendarScheduleView calendarScheduleView = new CalendarScheduleView();
		CalendarScheduleDetails calendarScheduleDetails = new CalendarScheduleDetails();
		
		Timestamp startDateTimestamp = new Timestamp(cal.get(GregorianCalendar.YEAR)-1900, cal.get(GregorianCalendar.MONTH), 1, 0,0,0,0);
		
		calendarScheduleDetails.setStartDate(startDateTimestamp);
		
		Map calendarViewHashMap = new HashMap();
		for(int k = 0; k < territoryView.getElements().size(); k++) {
			TerritoryDetails territoryDetails = (TerritoryDetails)territoryView.getElement(k);
			calendarScheduleDetails.setTerritoryId(territoryDetails.getTerritoryId());
			calendarScheduleDetails.setStatusIdList(calendarSearchCriteria.getStatusIdList());
			calendarScheduleDetails.setLobIdList(calendarSearchCriteria.getLobIdList());
			
			Timestamp timestamp;
			List monthlyProspects = new ArrayList();
			int prospects = 0;
			for(int i = 0; i < cal.getActualMaximum(GregorianCalendar.DATE); i++) {
				timestamp = (Timestamp)startDateTimestamp.clone();
				timestamp.setDate(timestamp.getDate() + i);				
				calendarScheduleDetails.setNextSalesActionDate(timestamp);
				calendarScheduleView.fillWithElements(connection, CalendarScheduleDetails.FILL_TYPE_DAY, calendarScheduleDetails);					

				//refactored to grab the note for imports
				//created one method that all calendars displays can call instead of writing
				//all code within each display method
				//getFinalCalendarScheduleView iterates through the view populated above
				//finds all notes associated to the same prospect then checks if the username is UserDetails.DATA_IMPORT
				//if it is, sets the field calendarScheduleDetails.setNote();
				CalendarScheduleView finalCalendarScheduleView = getFinalCalendarScheduleView(calendarScheduleView, connection);
				
				monthlyProspects.add(finalCalendarScheduleView.getElements());
				prospects += calendarScheduleView.getElements().size();
				
			}
			calendarViewHashMap.put(territoryDetails.getTerritoryId(), monthlyProspects);
		}
		
		request.getSession().setAttribute("calendar", cal);
		request.setAttribute("territoryView", territoryView);
		request.setAttribute("calendarViewHashMap", calendarViewHashMap);
		
		if(Utilities.nullToBlank(request.getParameter("print")).equals("TRUE")) {
			forward("/jsp/shared/calendarSchedule/printingMonthCalendarSchedule.jsp", request, response);
		} else if(Utilities.nullToBlank(request.getParameter("print2")).equals("TRUE")) {
			forward("/jsp/shared/calendarSchedule/printingMonthCalendarScheduleWithNotes.jsp", request, response);	
		} else {
			forward("/jsp/shared/calendarSchedule/monthCalendarSchedule.jsp", request,response);	
		}
									
		
	}
    
	/**
	 * 
	 */
	public void changeViewMonth(HttpServletRequest request, HttpServletResponse response, Connection connection, 
			UserProfile userProfile, CalendarSearchCriteria calendarSearchCriteria) throws Exception {

		
			Timestamp current;
		
			current = DateUtilities.getSQLTimestamp(getSelectDate(request)+ " 00:00:00", userProfile.getDateFormat());						
			
			GregorianCalendar cal = (GregorianCalendar) request.getSession().getAttribute("calendar");
			
			if(request.getParameter("direction").equals(DIRECTION_PREVIOUS)) {
				cal.add(GregorianCalendar.MONTH, PREV_MONTH);
			} else if (request.getParameter("direction").equals(DIRECTION_NEXT)) {
				cal.add(GregorianCalendar.MONTH, NEXT_MONTH);
			}
			
			setupMonthlyAttributes(cal, request);
			
			TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
			searchTerritoryDetails.setTerritoryIdList(calendarSearchCriteria.getTerritoryIdList());
			TerritoryView territoryView = new TerritoryView();
			territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);

			Timestamp startDateTimestamp = new Timestamp(cal.get(GregorianCalendar.YEAR) -1900, cal.get(GregorianCalendar.MONTH), 1, 0,0,0,0);
			
			CalendarScheduleView calendarScheduleView = new CalendarScheduleView();
			CalendarScheduleDetails calendarScheduleDetails = new CalendarScheduleDetails();			
			calendarScheduleDetails.setStartDate(DateUtilities.getWeekStartTimestamp(startDateTimestamp));

			
			Map calendarViewHashMap = new HashMap();
			for(int k = 0; k < territoryView.getElements().size(); k++) {
				TerritoryDetails territoryDetails = (TerritoryDetails)territoryView.getElement(k);
				calendarScheduleDetails.setTerritoryId(territoryDetails.getTerritoryId());
				calendarScheduleDetails.setLobIdList(calendarSearchCriteria.getLobIdList());
				calendarScheduleDetails.setStatusIdList(calendarSearchCriteria.getStatusIdList());

				Timestamp timestamp;
				List monthlyProspects = new ArrayList();
				for(int i = 0; i < cal.getActualMaximum(GregorianCalendar.DATE); i++) {
					timestamp = (Timestamp)startDateTimestamp.clone();
					timestamp.setDate(timestamp.getDate() + i);				
					calendarScheduleDetails.setNextSalesActionDate(timestamp);
					calendarScheduleView.fillWithElements(connection, CalendarScheduleDetails.FILL_TYPE_DAY, calendarScheduleDetails);					
				
					//refactored to grab the note for imports
					//created one method that all calendars displays can call instead of writing
					//all code within each display method
					//getFinalCalendarScheduleView iterates through the view populated above
					//finds all notes associated to the same prospect then checks if the username is UserDetails.DATA_IMPORT
					//if it is, sets the field calendarScheduleDetails.setNote();
					CalendarScheduleView finalCalendarScheduleView = getFinalCalendarScheduleView(calendarScheduleView, connection);
									
					monthlyProspects.add(finalCalendarScheduleView.getElements());
				}
				calendarViewHashMap.put(territoryDetails.getTerritoryId(), monthlyProspects);
			}
			
			request.setAttribute("territoryView", territoryView);
			request.setAttribute("calendarViewHashMap", calendarViewHashMap);
			forward("/jsp/shared/calendarSchedule/monthCalendarSchedule.jsp", request,response);							
				
	}

	/**
	 * @param cal
	 */
	private void setupMonthlyAttributes(GregorianCalendar cal, HttpServletRequest request) {
		request.getSession().setAttribute("calendar", cal);		
		request.setAttribute("firstDayOfWeek", new Integer(cal.get(GregorianCalendar.DAY_OF_WEEK)));
		request.setAttribute("lastDateOfMonth", new Integer(cal.getActualMaximum(GregorianCalendar.DATE)));
		request.setAttribute("monthDescription", months[cal.get(GregorianCalendar.MONTH)]);
		request.setAttribute("yearDescription", new BigDecimal(cal.get(GregorianCalendar.YEAR)));		
	}
}
