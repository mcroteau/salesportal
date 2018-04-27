<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings,
		java.text.SimpleDateFormat,
        java.sql.Timestamp,
        com.randr.webdw.calendarSchedule.CalendarScheduleDetails, 
		com.randr.webdw.util.*" %>
		
<jsp:useBean id="calendarScheduleView" class="com.randr.webdw.calendarSchedule.CalendarScheduleView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Sales Portal Mobile - Today's Prospect Sales Actions</title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>


<div id="allContent">
  
  <body>
  
     	<table class="report" border="1" cellspacing="0" cellpadding="3" width="100%" border="0">
    	<tr id="heading">
    		<th colspan="5">
    			<h2>Today's Sales Actions</h2>
    			<%=DateUtilities.formatDate(DateUtilities.getCurrentSQLDate(), userProfile.getDateFormat())%>
    		</th>
    	</tr>
    			<tr>
    				<th align=center>Priority</th>
        			<th align=center>Sales Action Info</th>
        			<th align=center>Prospect Info</th>

    			</tr>
			    <% for (int i=0; i<calendarScheduleView.getElements().size();i++) {
			            	CalendarScheduleDetails calendarScheduleDetails 
			            	= (CalendarScheduleDetails) calendarScheduleView.getElements().elementAt(i);
			            	SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
			            	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
			            	%>
    			<tr <% if (i%2==0) {%> 
    					class="roweven" 
    				<%} else { %> 
    					class="rowodd"
    				<%}%>>
    				<td width="10%">
    					<b>
    					<%=calendarScheduleDetails.getActionPriority()%>
    					</b>
    				</td>
        			<td valign="top" align="center" nowrap>
            			<%=calendarScheduleDetails.getNextSalesActionName() %>
            			<br>
            			Priority : <%=calendarScheduleDetails.getActionPriority()%>&nbsp;Time : <%=timeFormat.format(calendarScheduleDetails.getNextSalesActionDate())%>
        			</td>
        			<td valign="top" align="left" >
            		<a class="submit" href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT&dfProspectId=" + calendarScheduleDetails.getProspectId()%>">
							<%=calendarScheduleDetails.getProspectId()%> - <%=calendarScheduleDetails.getCustomerCompany()%></a>
						<br>
						<%if(calendarScheduleDetails.getContactName()!=null){ %>
							<%=calendarScheduleDetails.getContactName()%>
						<%} %>
						<br>
						<%=calendarScheduleDetails.getPhone()%><%if(calendarScheduleDetails.getPhoneExt()!=null){ %>
																	&nbsp;ext :&nbsp;<%=calendarScheduleDetails.getPhoneExt()%>
																<%} %>
						
        			</td>
			</tr> 
        		<%if(calendarScheduleDetails.getActionNote()!=null){ %> 
				<tr id="notes">
					<td colspan="1" id="label">
						
					</td>
					<td colspan="5" width="90%" id="notes">
						Notes : <%=calendarScheduleDetails.getActionNote() %>
					</td>
				</tr>

			<%} %> 
		<%}%>
	</table>
		<div id="buttonGroup">
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=MAIN_MENU"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/mainMenu.png" alt="Logout"/></a>
			
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=LOGOUT"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/Logout.png" alt="Logout"/></a>
		</div>
	<div id="userProfile">
		<%=userProfile.getUserName() %> is logged in...
	</div>
  </body>
  </div>
</html>
