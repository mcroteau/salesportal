<%@ page
	import="com.randr.webdw.AppSettings,
                 com.randr.webdw.calendarSchedule.CalendarScheduleDetails,
                 com.randr.webdw.calendarSchedule.CalendarScheduleView,
                 com.randr.webdw.territory.TerritoryDetails,
                 com.randr.webdw.user.UserProfile,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.label.LabelView"%>
                 
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>

<jsp:useBean id="territoryDetails" class="com.randr.webdw.territory.TerritoryDetails" scope="request"/>
<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="statusView" class="com.randr.webdw.status.StatusView" scope="request"/>
<jsp:useBean id="lobView" class="com.randr.webdw.lob.LobView" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Calendar Select", "","", "")%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
	 	
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
    
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/calendarSchedule/calendarSchedule.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>    

<% try { %>

 	<center><h1>Calendar Select</h1>
 	
	<center>
	<table border="1" class="form">

    <form name="frmCalendar" action="territory_calendar" method="GET">  
    	<input type="hidden" name="formAction" value=""/> 
    	<input type="hidden" name="dfSearchTerritoryId" value=""/> 
    	<input type="hidden" name="dfSearchSalesActionDate" value=""/> 
    	<input type="hidden" name="dfSearchStatusId" value=""/>
   
    <tr>
    	<th>Select Territory:</th>
			<td>
	  			<select name="dfCalTerritoryId" class="salesmen" multiple size="5">
        		<%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          territoryView.getElements())%>
         		</select>
			</td>
	</tr>
	<tr>
    	<th>Select ID/Status:</th>
			<td>
	  			<select name="dfCalStatusId" class="salesmen" multiple size="5">
        		<%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus", statusView.getElements())%>
               
         		</select>
			</td>
	</tr>
    <tr>
    	<th>Line of Business:</th>
			<td>
	  			<select name="dfCalLobId" class="salesmen" multiple size="5">
        		<%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          lobView.getElements())%>
         		</select>
			</td>
	</tr>
	<tr>
		<th><center>Select Calendar View Type</th><br>
     		<td>
				<input type="radio" name="calendarType" value="DAY"  />Day
				<input type="radio" name="calendarType" value="WEEK" />Week
				<input type="radio" name="calendarType" value="MONTH" checked />Month*
			</td>
	</tr>
	<tr>
		<th>Select Start Date:</th>
        	<td>
            	<center>
            	<input type="text" name="dfSelectDate" size="12" onchange="javascript:void(0);" 
            		value="<%=DateUtilities.formatDate(DateUtilities.getCurrentSQLDate(), userProfile.getDateFormat())%>">
              <% if(Utilities.nullToBlank(userProfile.getDateFormat()).equals("MM/dd/yyyy")) {%>  
            	<a href="javascript:selectDate.popup();">
            		<img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date">
            	</a>
            <%} %>
        	</td>
    </tr>
    
    <tr align=center>
    		<td colspan="2" align=center>
    			<center>
            	<a class="button" href="javascript: fOnSearchResults();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/search.gif" alt="Search">Search&nbsp;&nbsp;</a>
			</td>
	</tr>
	<tr align=center>
    		<td colspan="2" align=center>Searching across all territories in the month and the week view<br>may take several seconds (even up to a minute for very large datasets).</td>
	</tr>
   </form>
</table>
<h3>** Calendar will only display prospects which are associated with a territory **
<script>
    var selectDate = new calendar2(document.forms['frmCalendar'].elements['dfSelectDate']);
	selectDate.year_scroll = true;
	selectDate.time_comp = false;
</script>

<%=AppSettings.getFooter(userProfile)%>        
<% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>

