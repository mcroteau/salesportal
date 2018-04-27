<%@ page
	import="com.randr.webdw.AppSettings,
                 com.randr.webdw.calendarSchedule.CalendarScheduleDetails,
                 com.randr.webdw.prospect.ProspectDetails,
                 com.randr.webdw.territory.TerritoryDetails,
                 com.randr.webdw.user.UserProfile,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.label.LabelView, java.sql.Timestamp, java.util.List, java.lang.Integer"%>
                 
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>

<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="calendarViewHashMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="message" class="java.lang.String" scope="request"/>
<jsp:useBean id="firstDayOfWeek" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="lastDateOfMonth" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="monthDescription" type="java.lang.String" scope="request"/>
<jsp:useBean id="yearDescription" type="java.math.BigDecimal" scope="request"/>

<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/public.css?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">


<% try { %>
<% String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }; %>

<center><h1>Month Calendar</h1></center>
 	
<%  // Start of territory loop
	TerritoryDetails territoryDetails;
	List monthlyProspects;
	for(int territoryIndex = 0; territoryIndex < territoryView.getElements().size(); territoryIndex++) { 
		territoryDetails = (TerritoryDetails)territoryView.getElement(territoryIndex); 
		monthlyProspects = (List)calendarViewHashMap.get(territoryDetails.getTerritoryId());%>
		
<%-- Navigation bar --%>
        
        
	<table border="1" class="report" frame="box">
		<tr valign="center">
			<th colspan="2" align="left">
					&nbsp;
			</th>
			<th colspan="3" align="center">
					<H1><%=monthDescription%>&nbsp;<%=yearDescription%></H1><br>
					<h2><%=territoryDetails.getTerritory()%><h2>
			</th>
			<th colspan="2" align="right">				
					&nbsp;
			</th>

		</tr>
		<tr>
		<% for(int j=0; j < 7; j++){ %>
			<th><h2><%=days[j]%></h2></th>
		<% } %>
		</tr>
		
		<% 	int dateCount = 1;
			int calendarCount = 0;
			int firstDay = firstDayOfWeek.intValue();
			int lastDate = lastDateOfMonth.intValue();
			int rows = 5;
			if( lastDate + firstDay >= 35) rows++;
			for(int i = 0; i < rows; i++) { %>
		<tr valign="top" height=100>
			<% for(int j=0; j < 7; j++){ %>
				<td width=150>
					<table class="report">
						<tr>
							<% if(calendarCount+1 >= firstDay && dateCount <= lastDate ) { 
								List dailyProspects = (List)monthlyProspects.get(dateCount-1);%>
								<th align=left colspan="151"><%=dateCount%></th>
								</tr>
								<tr>
									<td>
									<% for(int k=0; k < dailyProspects.size(); k++){ 
										CalendarScheduleDetails calendarScheduleDetails = (CalendarScheduleDetails)dailyProspects.get(k); 
										String title="";
										if(calendarScheduleDetails.getProspectCampaignId() != null) {
											title = "Campaign " + calendarScheduleDetails.getCampaignName() + " Step " + 
											(calendarScheduleDetails.getProspectCampaignSequence().intValue() + 1) + ": " + 
											Utilities.nullToBlank(calendarScheduleDetails.getNextSalesActionName());
										} else {
											title= Utilities.nullToBlank(calendarScheduleDetails.getNextSalesActionName());
										}%>
										<table width="100%">
												<tr><td><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" 
												alt="" title="" ><b><%=title%><b></td></tr>
										</table>
										<table frame="below">
												<tr><td width=150>
														Priority: <%=Utilities.nullToBlank(calendarScheduleDetails.getActionPriority())%><br>
										     	  		<%=Utilities.nullToBlank(calendarScheduleDetails.getCustomerCompany())%>
													</td>
												</tr>
											<tr>
												<td width=150 bgcolor="<%=Utilities.nullToBlank(calendarScheduleDetails.getNextSalesActionColor())%>">
									        		
									        		<%if(calendarScheduleDetails.getHour() != null){%>
														<%=Utilities.nullToBlank(calendarScheduleDetails.getHour())%>
														<%=Utilities.nullToBlank(calendarScheduleDetails.getMinute())%>
														<%=Utilities.nullToBlank(calendarScheduleDetails.getAmPm())%>&nbsp;
										        	<%}%>
													<%=Utilities.nullToBlank(calendarScheduleDetails.getCity())%>
													</a>
					                    		</td>
					                    	</tr>
										</table>
									<% } %>
									</td>
								</tr>
							<% 	dateCount++;
								} else { %>
								<th>&nbsp;</th>
								<tr>
									<td>&nbsp;</td>
								</tr>
							<% } %>


					</table>
				</td>
			<% calendarCount++;  
			}%>
		</tr>
		<% } %>
</table>
<% } // End of territory loop %> 

    <% } catch (Exception e) { %>
        Error: <b><%=e.getMessage()%></b>
    <% e.printStackTrace(); } %>
