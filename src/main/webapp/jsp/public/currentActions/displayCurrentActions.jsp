<%@page contentType="text/html; charset=iso-8859-1" language="java" 
	import="com.randr.webdw.AppSettings,
			java.text.SimpleDateFormat,
			java.sql.Timestamp,
			com.randr.webdw.prospect.ProspectDetails,
			com.randr.webdw.util.Utilities"%>
			
<jsp:useBean id="prospectTodayView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
<jsp:useBean id="salesActionTodayView" class="com.randr.webdw.salesAction.SalesActionView" scope="request"/>
<jsp:useBean id="territoryTodayView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="currentSearchBy" class="java.lang.String" scope="request"/>
<jsp:useBean id="currentSearchByTitle" class="java.lang.String" scope="request"/>


<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:setProperty name="userProfile" property="*"/>

<%=AppSettings.getHeader(request,userProfile,"Todays Actions","","","")%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/currentActions/displayCurrentActions.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<form name="frmPopupActions" method="post" action="current_actions">
<input type="hidden" name="formAction" value="DISPLAY_CURRENT_ACTIONS">
<input type="hidden" name="dfPopup" value="YES">
</form>

<%try{%>
<form name="frmCurrentActions" method="post" action="">
<input type="hidden" name="formAction" value="">
<input type="hidden" name="fromCurrentActions" value="YES">
<input type="hidden" name="dfProspectId" value="">
<input type="hidden" name="includePassedDue" value="">
<input type="hidden" name="serviceManager" value="">
<input type="hidden" name="salesManager" value="">
<input type="hidden" name="owner" value="">
<input type="hidden" name="fromCurrentActionsScreen" value="YES">
<input type="hidden" name="currentSearchBy" value="<%=currentSearchBy%>">


<h1>Todays Actions <%=currentSearchByTitle%></h1>


<br/>
<%if(prospectTodayView.getElements().size() == 0){%>

<table class="currentActionsMenu">
	<tr>
		<td class="cLeft">
			<ul>
				<li><a href="javascript:fTerritoryOwnerSA();">Show My Territories </a></li>			
				<li><a href="javascript:fSalesManagerSA();">Show Sales Manager Territories</a></li>
				<li><a href="javascript:fServiceManagerSA();">Show Service Manager Territories</a></li>
			</ul>
		</td>
		<td class="cRight">
		<a href="javascript:fPopupView();" title="Popup Sales Action Alarm">
				<img src="<%=AppSettings.getGraphicsPath()%>icons/alarm.png" alt="Delete"
				onmouseover="this.src='<%=AppSettings.getGraphicsPath()%>icons/alarm-hover.png'"
				onmouseout="this.src='<%=AppSettings.getGraphicsPath()%>icons/alarm.png'"><span id="salesActionAlarm">Sales Action Alarm</span></a></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<br/>
		<a href="javascript:fIncludePassedDue('<%=currentSearchBy%>');" class="include" title="Click to Include Passed Due Sales Actions">
		Include Passed Due Sales Actions</a>
		</td>
	</tr>
	<tr><td colspan="2" align="right">
		<a href="javascript:fOpenHelp('<%=AppSettings.getAppCompleteWebPath(request)%>webpages/help_files/currentActions/currentActionsHelp.html','730','750')" >help</a>
		</td>
	</tr>
</table>
<br/><br/>
	<h2>No Current Sales Actions</h2>
<%}else{%>
<table class="currentActionsMenu">
	<tr>
		<td class="cLeft">
			<ul>
				<li><a href="javascript:fTerritoryOwnerSA();">Show My Territories </a></li>			
				<li><a href="javascript:fSalesManagerSA();">Show Sales Manager Territories</a></li>
				<li><a href="javascript:fServiceManagerSA();">Show Service Manager Territories</a></li>
			</ul>
		</td>
		<td class="cRight">
		<a href="javascript:fPopupView();" title="Popup Sales Action Alarm">
				<img src="<%=AppSettings.getGraphicsPath()%>icons/alarm.png" alt="Delete"
				onmouseover="this.src='<%=AppSettings.getGraphicsPath()%>icons/alarm-hover.png'"
				onmouseout="this.src='<%=AppSettings.getGraphicsPath()%>icons/alarm.png'"><span id="salesActionAlarm">Sales Action Alarm</span></a></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<br/>
		<a href="javascript:fIncludePassedDue('<%=currentSearchBy%>');" class="include" title="Click to Include Passed Due Sales Actions">
		Include Passed Due Sales Actions</a>
		</td>
	</tr>
	<tr><td colspan="2" align="right">
		<a href="javascript:fOpenHelp('<%=AppSettings.getAppCompleteWebPath(request)%>webpages/help_files/currentActions/currentActionsHelp.html','730','750')" >help</a>
		</td>
	</tr>
</table>

<table class="report" id="currentSalesActions">
	<tr>
		<th>Date/Time</th>
		<th>Action</th>
		<th>Prospect</th>
		<th>Note</th>
	</tr>
	<%for(int i = 0; i < prospectTodayView.getElements().size(); i++){%>
		<%ProspectDetails prospectDetails = (ProspectDetails)prospectTodayView.getElement(i);
			String prospectId = prospectDetails.getProspectId().toString();%>
			
		<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
			<%	String className = "";
				if(prospectDetails.getCurrentSalesActionDate() != null 
	            	&& prospectDetails.getCurrentSalesActionDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
	            	className = "passedDue";
	          } %>
			<td class="<%=className%>">
				<%if(prospectDetails.getCurrentSalesActionDate()!=null){ %>
					<%SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
	            	  SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa"); 
	            	  String startStr = "";
	            	  String endStr = "";
	            	  %>
	            	
	            	 <a href="javascript:fOnUpdate('<%=prospectId%>');" title="Update Prospect"> 
	            		<%= dateFormat.format(prospectDetails.getCurrentSalesActionDate())%><br>
	            		<%= timeFormat.format(prospectDetails.getCurrentSalesActionDate())%>	
	            	</a>	
				<%}%>
			</td>
			<td><%=salesActionTodayView.getSalesActionDescription(prospectDetails.getCurrentSalesActionId())%></td>
			<td><%=prospectDetails.getProspectId()%>&nbsp;
				<%if(prospectDetails.getCustomerCompany()!=null){%>
					<%=prospectDetails.getCustomerCompany()%><br/>
				<%}%>
				<%if(prospectDetails.getContactName()!=null){%>
					<%=prospectDetails.getContactName()%><br/>
				<%}%>
				<%if(prospectDetails.getPhone()!=null){%>
					<%=prospectDetails.getPhone()%><br/>
				<%}%>
				<%if(prospectDetails.getTerritoryId()!=null){%>
					<b>Territory:&nbsp;</b><%=territoryTodayView.getTerritoryName(prospectDetails.getTerritoryId())%>
				<%}%>
			</td>
			<td>
				<%if(prospectDetails.getCurrentSalesActionNote()!=null){%>
					<center><textarea rows="3" cols="50" readonly><%=prospectDetails.getCurrentSalesActionNote()%></textarea></center>
				<%}%>
			</td>
		</tr>
	<%}%>

</table>


</form>
<%}%>



<%}catch(Exception e){%>
	<%e.printStackTrace();%>
<%}%>

<%=AppSettings.getFooter(userProfile)%>

