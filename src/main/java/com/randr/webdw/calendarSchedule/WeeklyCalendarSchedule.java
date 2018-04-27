package com.randr.webdw.calendarSchedule;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.randr.webdw.user.UserDetails;
import com.randr.webdw.user.UserProfile;
import com.randr.webdw.util.CollectionUtilities;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;

public class WeeklyCalendarSchedule extends BaseCalendarSchedule {

	public void displayWeekView(HttpServletRequest request, HttpServletResponse response, Connection connection, 
			UserProfile userProfile, CalendarSearchCriteria calendarSearchCriteria)  throws Exception{
			Timestamp currentWeek;
			GregorianCalendar cal;
			CalendarScheduleView calendarScheduleView = new CalendarScheduleView();
			CalendarScheduleDetails calendarScheduleDetails = new CalendarScheduleDetails();
			
			if(request.getSession().getAttribute("calendar")!= null){
				cal = (GregorianCalendar) request.getSession().getAttribute("calendar");
			}else{
				currentWeek = DateUtilities.getSQLTimestamp(getSelectDate(request)+ " 00:00:00", userProfile.getDateFormat());
				cal = new GregorianCalendar(currentWeek.getYear() + 1900, 
						currentWeek.getMonth(), currentWeek.getDate());
			}
			
			Timestamp startDateTimestamp = new Timestamp(cal.get(GregorianCalendar.YEAR) -1900, cal.get(GregorianCalendar.MONTH), cal.get(GregorianCalendar.DATE), 0,0,0,0);
			if(request.getSession().getAttribute("calendar") != null){
				calendarScheduleDetails.setStartDate(DateUtilities.getWeekStartTimestamp(startDateTimestamp));
			}else{
				calendarScheduleDetails.setStartDate(DateUtilities.getWeekStartTimestamp(DateUtilities.getSQLTimestamp(getSelectDate(request)+ " 00:00:00", userProfile.getDateFormat())));
			}
			
		
			TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
			searchTerritoryDetails.setTerritoryIdList(calendarSearchCriteria.getTerritoryIdList());
			TerritoryView territoryView = new TerritoryView();
			territoryView.fillWithElements(connection,TerritoryDetails.FILL_TYPE_ALL, searchTerritoryDetails);

			Map calendarViewHashMap = new HashMap();
			for(int k = 0; k < territoryView.getElements().size(); k++) {
				TerritoryDetails territoryDetails = (TerritoryDetails)territoryView.getElement(k);
				
				Timestamp timestamp;
				List weeklyDates = new ArrayList();	
				List weeklyProspects = new ArrayList();
				for(int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
					timestamp = DateUtilities.getWeekStartTimestamp(startDateTimestamp);;
					timestamp.setDate(timestamp.getDate() + i);
//					System.out.println("territoryId inside for - " + territoryDetails.getTerritoryId());
					calendarScheduleDetails.setTerritoryId(territoryDetails.getTerritoryId());
					calendarScheduleDetails.setStatusIdList(calendarSearchCriteria.getStatusIdList());
					//new for lead sources on stone
					calendarScheduleDetails.setLobIdList(calendarSearchCriteria.getLobIdList());
					
					calendarScheduleDetails.setNextSalesActionDate(timestamp);
					calendarScheduleView.fillWithElements(connection, CalendarScheduleDetails.FILL_TYPE_DAY, calendarScheduleDetails);
					
					//refactored to grab the note for imports
					//created one method that all calendars displays can call instead of writing
					//all code within each display method
					//getFinalCalendarScheduleView iterates through the view populated above
					//finds all notes associated to the same prospect then checks if the username is UserDetails.DATA_IMPORT
					//if it is, sets the field calendarScheduleDetails.setNote();
					CalendarScheduleView finalCalendarScheduleView = getFinalCalendarScheduleView(calendarScheduleView, connection);
					
					weeklyDates.add(timestamp);
					weeklyProspects.add(finalCalendarScheduleView.getElements());
				}
				calendarViewHashMap.put(territoryDetails.getTerritoryId(), weeklyProspects);
				request.setAttribute("weeklyDates", weeklyDates);
			}
			request.getSession().setAttribute("calendar", cal);;
			request.setAttribute("territoryView", territoryView);
			request.setAttribute("calendarViewHashMap", calendarViewHashMap);
			
			if(Utilities.nullToBlank(request.getParameter("print")).equals("TRUE")) {
				forward("/jsp/shared/calendarSchedule/printingWeekCalendarSchedule.jsp", request, response);
			} else if(Utilities.nullToBlank(request.getParameter("print2")).equals("TRUE")) {
				forward("/jsp/shared/calendarSchedule/printingWeekCalendarScheduleWithNotes.jsp", request, response);	
			} else {
				forward("/jsp/shared/calendarSchedule/weekCalendarSchedule.jsp", request, response);
			}
			
		
	}
	
	public void changeViewWeek(HttpServletRequest request, HttpServletResponse response, Connection connection, 
			UserProfile userProfile, CalendarSearchCriteria calendarSearchCriteria)  throws Exception{
		
			GregorianCalendar cal = (GregorianCalendar) request.getSession().getAttribute("calendar");
			
			if(request.getParameter("direction").equals(DIRECTION_PREVIOUS)) {
				cal.add(GregorianCalendar.DAY_OF_MONTH, PREV_WEEK);
			} else if (request.getParameter("direction").equals(DIRECTION_NEXT)) {
				cal.add(GregorianCalendar.DAY_OF_MONTH, NEXT_WEEK);
			}
			
			Timestamp startDateTimestamp = new Timestamp(cal.get(GregorianCalendar.YEAR) -1900, cal.get(GregorianCalendar.MONTH), cal.get(GregorianCalendar.DATE), 0,0,0,0);
			
			CalendarScheduleView calendarScheduleView = new CalendarScheduleView();
			CalendarScheduleDetails calendarScheduleDetails = new CalendarScheduleDetails();
			
			calendarScheduleDetails.setStartDate(DateUtilities.getWeekStartTimestamp(startDateTimestamp));
			
			TerritoryDetails searchTerritoryDetails = new TerritoryDetails();
			searchTerritoryDetails.setTerritoryIdList(calendarSearchCriteria.getTerritoryIdList());
			TerritoryView territoryView = new TerritoryView();
			territoryView.fillWithElements(connection, TerritoryView.FILL_TYPE_ALL, searchTerritoryDetails);

			List weeklyProspects = null;
			Map calendarViewHashMap = new HashMap();
			for(int k = 0; k < territoryView.getElements().size(); k++) {
				TerritoryDetails territoryDetails = (TerritoryDetails)territoryView.getElement(k);
				weeklyProspects = new ArrayList();
				Timestamp timestamp;

				for(int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
					timestamp = DateUtilities.getWeekStartTimestamp(startDateTimestamp);
					timestamp.setDate(timestamp.getDate() + i);
					calendarScheduleDetails.setTerritoryId(territoryDetails.getTerritoryId());
					calendarScheduleDetails.setStatusIdList(calendarSearchCriteria.getStatusIdList());
					//new for lead sources on stone
					calendarScheduleDetails.setLobIdList(calendarSearchCriteria.getLobIdList());
					
					calendarScheduleDetails.setNextSalesActionDate(timestamp);
					calendarScheduleView.fillWithElements(connection, CalendarScheduleDetails.FILL_TYPE_DAY, calendarScheduleDetails);
					
					//refactored to grab the note for imports
					//created one method that all calendars displays can call instead of writing
					//all code within each display method
					//getFinalCalendarScheduleView iterates through the view populated above
					//finds all notes associated to the same prospect then checks if the username is UserDetails.DATA_IMPORT
					//if it is, sets the field calendarScheduleDetails.setNote();
					CalendarScheduleView finalCalendarScheduleView = getFinalCalendarScheduleView(calendarScheduleView, connection);
					
					weeklyProspects.add(finalCalendarScheduleView.getElements());
				}
				
				calendarViewHashMap.put(territoryDetails.getTerritoryId(), weeklyProspects);
			}
			request.getSession().setAttribute("calendar", cal);

			request.setAttribute("calendarDate", (cal.get(GregorianCalendar.MONTH)+1) + "%2F" + cal.get(GregorianCalendar.DAY_OF_MONTH) + "%2F" + cal.get(GregorianCalendar.YEAR));
			
			request.setAttribute("weeklyDates", getWeeklyDates(startDateTimestamp));
			request.setAttribute("weeklyProspects", weeklyProspects);
			request.setAttribute("calendarViewHashMap", calendarViewHashMap);
			request.setAttribute("territoryView", territoryView);

			
			forward("/jsp/shared/calendarSchedule/weekCalendarSchedule.jsp", request, response);
		
	}
}
