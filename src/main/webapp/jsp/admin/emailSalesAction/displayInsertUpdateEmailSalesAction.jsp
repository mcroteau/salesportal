<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.*,
				com.randr.webdw.emailSalesAction.EmailSalesActionDetails,
				java.text.SimpleDateFormat,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="emailSalesActionDetails" class="com.randr.webdw.emailSalesAction.EmailSalesActionDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="lobView" class="com.randr.webdw.lob.LobView" scope="request"/>
<jsp:useBean id="statusView" class="com.randr.webdw.status.StatusView" scope="request"/>
<jsp:useBean id="emailStatusesView" class="com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesView" scope="request"/>
<jsp:useBean id="emailTerritoriesView" class="com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesView" scope="request"/>
<jsp:useBean id="emailLobsView" class="com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsView" scope="request"/>

<% try{ %>

	<%

	String actionDescription = "Update";
	String newFormAction="UPDATE";

	if (formAction.equals("DISPLAY_INSERT"))
	{
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
	
	  SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("aa");
	%>
     <html>
     <head>
     	<title><%=actionDescription%> Email Sales Action</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/emailSalesAction/displayInsertUpdateEmailSalesAction.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	</script>
		
		<script src="<%=AppSettings.getAppWebPath()%>scripts/salesAction/201a.js" type="text/javascript"></script>
	<script language="JavaScript">
var rows = document.getElementById('dataRollOver').rows;
for (i=0;i<rows.length;i++) {
	rows[i].onmouseover = highlight;
	rows[i].onmouseout = dehighlight;
}


function highlight() { 
	this.className = "highlight"; 
}
function dehighlight() {
 	this.className = ""; 
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}
</script>		
		
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Email Sales Action (will not change already sent emails)</h1>
     <hr><br>
      <a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>
     <div id="moreInfo" style=display:none>
     	
     		<h2>Insert and Update Email Sales Actions</h2>
     		  
     		<p>Email Sales Actions are one time sales actions that select a group of prospects based on your selection criteria<br>
     		and then that group of prospects are sent over to the Order Portal and an email is automatically sent out.  The email 
     		<br>sales actions require the Web Business suite to be installed as they use the Order Portal and Data Migration portal<br>
     		to send the emails.  All portals are available on Sourceforge.net.</p>
     		
     		<p>Based on the timing setup in the Configuration Email Options, a background process runs.  This process selects prospects <br>
     		based on the selection criteria. You can view a list of all prospects who received the email.  When you set up the Email<br>
     		sales action you can see an estimate of the number of prospects who match your selection criteria.  After the email is sent <br>
     		a completed sales action is setup for the prospect so you have a history when viewing the prospect.</p>
     		  
     		<p>Here is an explanation for each field:</p>
     		
     		<ol class="moreInfo">
     			<li>The email sales action id will be assigned automatically by the system</li>
     			<li>Enter a description.</li>
     			<li>Email Draft to use.  Enter the email draft number from the Order Portal.  If there is an email draft number entered <br>
     			an email will be automatically sent to the prospect on the date.  This is done through the Order Portal, so may sure<br>
     			the settings in your Order Portal and Data Migration Portal are correct.</li>
     			<li>Enter the date to send the emails.</li>
     			<li>If you want the Verified code to be used as part of the selection criteria, select Yes here.  The actual code to use<br>
     			is setup in the Configuration, Email Options</li>
     			<li>If you want the Territory owner email to be used as the from email on any email, then select Yes here </li>
     			<li>Creation date of this automated campaign</li>
     			<li>Actual sent date is the date the email sales action was sent.  After the email is sent, you can not alter this sales action.</li>
     			<li>Select the Lead Sources to be used in selecting prospects for this automated campaign.</li>
     			<li>Select the Territories to be used in selecting prospects for this automated campaign.</li>
     			<li>Select the Status Ids to be used in selecting prospects for this automated campaign.</li>
     			
     			<li>To Save changes, Click Update</li>
     		
     		
     		</ol>
     		
     		<p></p>
     		
     		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
     	
     </div>
     <form name="frmEmailSalesAction" method="post" action="email_sales_action" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">



<% if (request.getAttribute("modelException") != null) {
		ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
		<table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
		<% if (formAction.equals("DISPLAY_UPDATE")) { %>
			<tr>
				<th align="right">Id:</th>
				<td>
 					<%=emailSalesActionDetails.getEmailActionId()%>
					<input type="hidden" name="dfEmailSalesActionId" value="<%=emailSalesActionDetails.getEmailActionId()%>">
				</td>
			</tr>
		<% } %>
		<tr>
			<th align="right">Description:</th>
			<td><input type="text" name="dfEmailSalesActionDescription" size="50" maxlength="100" value="<%=Utilities.nullToBlank(emailSalesActionDetails.getEmailActionDescription())%>"></td>
		</tr>
		<tr>
			<th align="right">Email Draft # to use (get this from Order Portal:</th>
			<td><input type="text" name="dfEmailDraftToUse" size="5" maxlength="9" value="<%=Utilities.nullToBlank(emailSalesActionDetails.getEmailDraftToUse())%>"></td>
		</tr>
		<tr>
			<th align="right">Date to Send this Email:</th>
			<td>
			<%String date;
	                	if(emailSalesActionDetails.getSendEmailDate() != null && !emailSalesActionDetails.getSendEmailDate().equals("")){
	                		date = dateFormat.format(emailSalesActionDetails.getSendEmailDate());
	                	}else{
	                		date = dateFormat.format(DateUtilities.getCurrentSQLDate());
	                	}
	                 %>
	                 <input type="text" name="dfSendEmailDate" size="12" value="<%=date%>" onchange="javascript:void(0);">
	               
	                <a href="javascript:calSendEmailDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
	            <font color="blue" size="+1">**</font>
	            </td>
		</tr>
		
		<tr>
			<th align="right">Use Prospect Verified Code <%=Utilities.nullToBlank(emailSalesActionDetails.getVerifiedYesNo())%>:</th>
			<td>
                <select name="dfVerifiedYesNo">
                 <% if (Utilities.nullToZero(emailSalesActionDetails.getVerifiedYesNo()).equals(EmailSalesActionDetails.IGNORE_VERIFIED_CODE)) {%>
                    	<option value="1" >YES - Send if Verified is Yes
                    	<option value="0" selected>NO - Ignore Verified code
                     <% }else{ %>
                     	<option value="1" selected>YES - Send if Verified is Yes
                    	<option value="0" >NO - Ignore Verified code
                <% }  %>
                </select></td>
		</tr>
		
		<tr>
			<th align="right">Use Salesman Email as From Email <%=Utilities.nullToBlank(emailSalesActionDetails.getUseSalesmanFromEmail()) %>:</th>
			<td>
                <select name="dfUseSalesmanFromEmail">
                <% if (Utilities.nullToZero(emailSalesActionDetails.getUseSalesmanFromEmail()).equals(EmailSalesActionDetails.DO_NOT_USE_SALESMAN_EMAIL)) {%>
                    <option value="1" >YES - Use Salesman Email
                    <option value="0" selected>NO - Use From Email in OP
                <% }else{ %>
                    <option value="1" selected>YES - Use Salesman Email
                    <option value="0" >NO - Use From Email in OP
                <% }  %>
                </select></td>
		</tr>
		
		<tr>
			<th align="right">Date this was Created:</th>
			<td nowrap align="left">
	            	<% if (emailSalesActionDetails.getCreationDate()!= null) { %>
	            	<%= dateFormat.format(emailSalesActionDetails.getCreationDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(emailSalesActionDetails.getCreationDate())%>
	            	<% }else{  %>
	            		<%=dateFormat.format(DateUtilities.getCurrentSQLDate())%>
	            	<%  } %>
	        </td>
	     </tr>
	        <tr>
			<th align="right">Actual Date Sent:</th>
			
			<td nowrap align="left">
	            	<% if (emailSalesActionDetails.getActualEmailDate()!= null) { %>
	            	<%= dateFormat.format(emailSalesActionDetails.getActualEmailDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(emailSalesActionDetails.getActualEmailDate())%>
	            	<% }else{  %>
	            		Not Sent Yet
	            	<%  } %>
	            </td>
 		</tr>
 		
	        <tr><td colspan="2">&nbsp;</td></tr>
	  
	    <tr>
            <th nowrap align="center" colspan="2">Select Specific Line of Business:</th>
        </tr>       		
	   
	      <%if (formAction.startsWith("DISPLAY_UPDATE")){ %> 
		<tr>
            <td nowrap align="center" colspan="2">
                <select name="dfLobId" class="salesmen" multiple  size="5">
                <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          emailLobsView.getEmailLobsIdsVector())
                %>
                </select>
            </td>
        </tr>
	<%}else{%>    
        <tr>
            <td nowrap align="center" colspan="2">
                <select name="dfLobId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          emailLobsView.getElements())
                %>
                </select>
            </td>
        </tr>
      <%} %>
      <tr>
            <th nowrap align="center" colspan="2">Select Specific Territories:</th>
        </tr> 
	      <%if (formAction.startsWith("DISPLAY_UPDATE")){ %> 
		<tr>
            <td nowrap align="center" colspan="2">
                <select name="dfTerritoryId" class="salesmen" multiple  size="5">
                <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          emailTerritoriesView.getEmailTerritoriesIdsVector())
                %>
                </select>
            </td>
        </tr>
	<%}else{%>    
        <tr>
            <td nowrap align="center" colspan="2">
                <select name="dfTerritoryId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          emailTerritoriesView.getElements())
                %>
                </select>
            </td>
        </tr>
      <%} %>
      <tr>
            <th nowrap align="center" colspan="2">Select Specific Status Ids:</th>
        </tr> 
	      <%if (formAction.startsWith("DISPLAY_UPDATE")){ %> 
		<tr>
            <td nowrap align="center" colspan="2">
                <select name="dfStatusId" class="salesmen" multiple  size="5">
                <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus",
                                                          emailStatusesView.getEmailStatusesIdsVector())
                %>
                </select>
            </td>
        </tr>
	<%}else{%>    
        <tr>
            <td nowrap align="center" colspan="2">
                <select name="dfStatusId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus",
                                                          emailStatusesView.getElements())
                %>
                </select>
            </td>
        </tr>
      <%} %>
      
	 
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
                <a class="button" href="javascript: if(fProcessForm()){document.frmEmailSalesAction.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		</table>
		<center>Only prospects that match all selection criteria and have an email address will be included<br></center>
		<center><B><font style="font-size:14px;color:#008000;font-family:Arial"># Not meant for bulk or mass email mailings, please use a bulk email service.<BR>  Please be sure you are in compliance with all regulations regarding emails.</b></font></center>
<% } %>
	 </form>
	 </center>
     <script>
        document.frmEmailSalesAction.dfEmailSalesActionDescription.focus();
     </script>
      <script>
			var calSendEmailDate = new calendar2(document.forms['frmEmailSalesAction'].elements['dfSendEmailDate']);
			calSendEmailDate.year_scroll = true;
			calSendEmailDate.time_comp = false;
        </script>
     </body>
</html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% 	e.printStackTrace(); } %>