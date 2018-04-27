<%@page contentType="text/html; charset=iso-8859-1" language="java" 
	import="com.randr.webdw.AppSettings,
			java.text.SimpleDateFormat,
			java.sql.Timestamp,
			com.randr.webdw.prospect.ProspectDetails"%>
			
<jsp:useBean id="prospectTodayView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
<jsp:useBean id="salesActionTodayView" class="com.randr.webdw.salesAction.SalesActionView" scope="request"/>
<jsp:useBean id="territoryTodayView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="alert" class="java.lang.String" scope="request"/>
<jsp:useBean id="checkBox" class="java.lang.String" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:setProperty name="userProfile" property="*"/>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/public.css?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
<title>Current Sales Actions</title>
</head>
<body class="popup">
<%try{%>
<form name="frmPopupActions" method="post" action="current_actions">
<input type="hidden" name="formAction" value="DISPLAY_CURRENT_ACTIONS">
<input type="hidden" name="dfPopup" value="YES">
<input type="hidden" name="dfAlert" value="<%=alert%>">
<SCRIPT LANGUAGE="JavaScript">
	setTimeout('document.frmPopupActions.submit()',60000);
</SCRIPT>
<%if(prospectTodayView.getElements().size() == 0){%>
	<center><h2>No Current Sales Actions</h2></center>
<%}else{%>

<%if(alert.equals("YES")){%>

<embed src="<%=AppSettings.getAppWebPath()%>alert/oriental_gong.wav" hidden=true type="javascript:getMimeType();"></embed>  


<script language="JavaScript" type="text/javascript">

function getMimeType(){
	var mimeType = "application/x-mplayer2"; //default
	var agt=navigator.userAgent.toLowerCase();
	if (navigator.mimeTypes && agt.indexOf("windows")==-1) {
		//non-IE, no-Windows
  		var plugin=navigator.mimeTypes["audio/mpeg"].enabledPlugin;
  		if (plugin){
		 mimeType="audio/mpeg";
		}
	}//end no-Windows
		return mimeType
}//end function getMimeType

</script>
	
<%}%>


<center>
<h1>Current Sales Actions</h1>
<table class="report" id="currentSalesActionsPopup">
	<tr><td colspan="3"><center><b>Turn Off Alert</b>&nbsp;<input type="checkbox" name="ckNoise" <%=checkBox%>></center></td></tr>
	<tr>
		<th>Sales Action</th>
		<th>Prospect</th>
		<th>Note</th>
	</tr>
	<%for(int i = 0; i < prospectTodayView.getElements().size(); i++){%>
		<%ProspectDetails prospectDetails = (ProspectDetails)prospectTodayView.getElement(i);%>
		<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
			<%	String className = "";
				if(prospectDetails.getCurrentSalesActionDate() != null 
	            	&& prospectDetails.getCurrentSalesActionDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
	            	className = "passedDue";
	          } %>
			<td class="<%=className%>">
				<%if(prospectDetails.getCurrentSalesActionDate()!=null){ %>
					<%SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
	            	  SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa"); %>
	            		<%= dateFormat.format(prospectDetails.getCurrentSalesActionDate())%><br>
	            		<%= timeFormat.format(prospectDetails.getCurrentSalesActionDate())%>	

				<%}%>
				<br/>
				<%=salesActionTodayView.getSalesActionDescription(prospectDetails.getCurrentSalesActionId())%>
			</td>
			<td>
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
					<center><textarea rows="2" cols="30" readonly><%=prospectDetails.getCurrentSalesActionNote()%></textarea></center>
				<%}%>
			</td>
		</tr>
	<%}%>
	
</table>
</center>
</form>

<%}%>

<script>
	performAlertCheck();
	function performAlertCheck(){
		var form = document.frmPopupActions;
		if(document.frmPopupActions.dfAlert.value == 'YES'){
			//alert("You have Sales Actions");
		}	
	}
</script>
</body>
</html>
<%}catch(Exception e){%>
	<%e.printStackTrace();%>
<%}%>

