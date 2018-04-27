<%@ page
	import="com.randr.webdw.AppSettings,
                 com.randr.webdw.calendarSchedule.CalendarScheduleDetails,
                 com.randr.webdw.prospect.ProspectDetails,
                 com.randr.webdw.territory.TerritoryDetails,
                 com.randr.webdw.calendarSchedule.CalendarScheduleView,
                 com.randr.webdw.territory.TerritoryDetails,
                 com.randr.webdw.user.UserProfile,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.label.LabelView, java.sql.Timestamp, java.util.List"%>
                 
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>

<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="weeklyDates" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="calendarViewHashMap" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="message" class="java.lang.String" scope="request"/>

<jsp:useBean id="calendarDate" class="java.lang.String" scope="request"/>
<jsp:useBean id="refreshInterval" class="java.lang.String" scope="session"/>

<head>
<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/public.css?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">

<script language="JavaScript">

function getParam(name){

	var regexS = "[\\?&]"+name+"=([^&#]*)";
	var regex = new RegExp( regexS );
	var tmpURL = window.location.href;
	var results = regex.exec( tmpURL );
	if( results == null ){
		return "";
	}else{
		return results[1];
	}
}

var refreshinterval=<%=refreshInterval%>;
var starttime;
var nowtime;
var reloadseconds=0;
var secondssinceloaded=0;

function starttime() { 
	var offset = getParam('offset');
	window.scrollBy(0,offset);
	starttime=new Date();
	starttime=starttime.getTime();
	countdown();
} 

function countdown() {
	
	nowtime= new Date();
	nowtime=nowtime.getTime();
	secondssinceloaded=(nowtime-starttime)/1000;
	reloadseconds=Math.round(refreshinterval-secondssinceloaded);
	
	if (refreshinterval>=secondssinceloaded) {
		var timer=setTimeout("countdown()",1000)
	
	} else {
		clearTimeout(timer);
		var offset = window.pageYOffset;
		var url = window.location.href.toString();
		var r = url.replace(/[&]offset.*/g, "");
		r = r.replace("CHANGE_VIEW_WEEK_MULTIPLE","DISPLAY");
		var x = "<%=calendarDate%>";
		
		if(x != "") {
			r = r.replace(/dfSelectDate=[\d]+%2F[\d]+%2F[\d]+[&]/,"dfSelectDate=" + x + "&");
		}
		
		r = r.replace(/direction=PREV/,"calendarType=WEEK&dfCalTerritoryId=all");
		r = r.replace(/direction=NEXT/,"calendarType=WEEK&dfCalTerritoryId=all");
		url = r + "&offset=" + offset;		
		window.location.replace(url);
	}
}

window.onload=starttime();
</script>
</head>

<!-- <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/calendarSchedule/multipleTerritoryWeekCalendarSchedule.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script> -->

<% try { %>
<% String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }; %>
 
 <form name="frmProspect" method="post" action="prospect">
 <input type="hidden" name="dfSearchProspectId" value="">
 <input type="hidden" name="formAction" value="">
 <input type="hidden" name="dfSelectDate" value="<%=request.getParameter("dfSelectDate")%>" >
 <input type="hidden" name="dfCalTerritoryId" value="<%=request.getParameter("dfCalTerritoryId")%>" >
 <input type="hidden" name="cmbSearchStatusId" value="<%=request.getParameter("cmbSearchStatusId")%>" >
 <input type="hidden" name="direction" value="" >
 
<%-- Navigation bar --%>

<center><H1>Weekly Calendar View</H1></center>
<%  // Start of territory loop
	TerritoryDetails territoryDetails;
	List weeklyProspects;
	for(int territoryIndex = 0; territoryIndex < territoryView.getElements().size(); territoryIndex++) { 
		territoryDetails = (TerritoryDetails)territoryView.getElement(territoryIndex); 
		weeklyProspects = (List)calendarViewHashMap.get(territoryDetails.getTerritoryId());%>
		
        
        
	<table border="1" class="report">
	<tr valign="center">
			<th colspan="2" align="left">
					&nbsp;
			</th>
			<th colspan="3" align="center">
					<h2><%=territoryDetails.getTerritory()%><h2>
			</th>
			<th colspan="2" align="right">				
					&nbsp;
			</th>
		
	</tr>
		<tr valign="center">
		<% 	Timestamp day;
			for(int i=0; i < 7; i++){ 
				day = (Timestamp)weeklyDates.get(i); %>
				<th><h2><%=days[i]%><br><%=DateUtilities.formatDate(DateUtilities.getSqlDate(day),userProfile.getDateFormat())%></h2></th>
		<%  }%>
		</tr>
		<tr valign="top" height=100>
		<% List dailyProspects;
			for(int i=0; i < 7; i++){ 
				dailyProspects = (List)weeklyProspects.get(i);  %>
				<td width=150>
				<% for(int j=0; j < dailyProspects.size(); j++) { 
					CalendarScheduleDetails calendarScheduleDetails = (CalendarScheduleDetails)dailyProspects.get(j);
						String title="";
						if(calendarScheduleDetails.getProspectCampaignId() != null) {
							title = "Campaign " + calendarScheduleDetails.getCampaignName() + " Step " + 
							(calendarScheduleDetails.getProspectCampaignSequence().intValue() + 1) + ": " + 
							Utilities.nullToBlank(calendarScheduleDetails.getNextSalesActionName());
						} else {
							title= Utilities.nullToBlank(calendarScheduleDetails.getNextSalesActionName());
						}%>
					<table width="100%">
							<tr><td width=150><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="" title="" ><b><%=title%><b></td></tr>
					</table>
					<table frame="below">
							<tr><td width=150>
									Priority: <%=Utilities.nullToBlank(calendarScheduleDetails.getActionPriority())%><br>
									<%=Utilities.nullToBlank(calendarScheduleDetails.getCustomerCompany())%><br>
									<%=Utilities.nullToBlank(calendarScheduleDetails.getContactName())%><br>
									<%=Utilities.nullToBlank(calendarScheduleDetails.getPhone())%><br>
									<%if(calendarScheduleDetails.getActionNote() != null){%>
					                	<%=Utilities.nullToBlank(calendarScheduleDetails.getActionNote())%>
					                <% } %>
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
		<%  }%>	
		</tr> 
</table>

<% } // End of territory loop %> 
</form>	

<form name="frmPrintWeeklyForm" method="post" action="territory_calendar" target="print_week_calendar">
 	<input type="hidden" name="formAction" value="DISPLAY">
 	<input type="hidden" name="calendarType" value="WEEK">
 	<input type="hidden" name="print" value="TRUE" />
 	<input type="hidden" name="dfCalTerritoryId" value=""/>
</form> 
<!--  <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/thirdPartyUtilities/wz_tooltip.js?reload=<%=String.valueOf((new java.util.Date()).getTime())%>"></script> -->
 	            
    <% } catch (Exception e) { %>
        Error: <b><%=e.getMessage()%></b>
    <% e.printStackTrace(); } %>

