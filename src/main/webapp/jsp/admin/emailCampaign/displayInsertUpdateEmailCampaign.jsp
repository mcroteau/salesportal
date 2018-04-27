<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.*,
				com.randr.webdw.emailSalesAction.emailCampaign.EmailCampaignDetails,
				java.text.SimpleDateFormat,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="emailCampaignDetails" class="com.randr.webdw.emailSalesAction.emailCampaign.EmailCampaignDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="lobView" class="com.randr.webdw.lob.LobView" scope="request"/>
<jsp:useBean id="statusView" class="com.randr.webdw.status.StatusView" scope="request"/>
<jsp:useBean id="emailStatusesView" class="com.randr.webdw.emailSalesAction.emailSalesActionStatuses.EmailSalesActionStatusesView" scope="request"/>
<jsp:useBean id="emailTerritoriesView" class="com.randr.webdw.emailSalesAction.emailSalesActionTerritories.EmailSalesActionTerritoriesView" scope="request"/>
<jsp:useBean id="emailLobsView" class="com.randr.webdw.emailSalesAction.emailSalesActionLobs.EmailSalesActionLobsView" scope="request"/>
<jsp:useBean id="campaignView" class="com.randr.webdw.campaign.CampaignView" scope="request" />

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
     	<title><%=actionDescription%> Email Campaign</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/emailSalesAction/displayInsertUpdateEmailCampaign.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
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
	 <h1><%=actionDescription%> Email Campaign (will not change already sent emails)</h1>
     <hr><br>
      <a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>
     <div id="moreInfo" style=display:none>
     	
     		<h2>Insert and Update Automated Campaigns</h2>
     		  
     		<p>Automated Campaigns are campaigns that are automatically added to a prospect, based on the selection criteria you set up.<br>
     		If a prospect has already had this campaign it will not be created again.  If you delete the campaign from a prospect <br>
     		then they will get it set up again.  The dates for the individual sales action are set up automatically based on the <br>
     		numbers of days you enter for each sales action as you set up the campaign.</p>
     		
     		<p>Originally this started out as email campaigns, but instead we added the ability to make any sales action an email <br>
     		sales action.  So now a campaign can have regular sales actions and email sales actions.  The email sales actions <br>
     		require the Web Business suite to be installed as they use the Order Portal and Data Migration portal to send the <br>
     		emails.  All portals are available on Sourceforge.net.</p>
     		
     		<p>Based on the timing setup in the Configuration Email Options, a background process runs.  This process selects prospects <br>
     		based on the selection criteria.  If a prospect meets the criteria and they have not had this campaign already, the campaign<br>
     		is setup with all the sales action.  This will go on every day starting with the start date of the campaign through the <br>
     		end date of the campaign.  Once the campaign is setup for a prospect, you can do normal editing as you would for any<br>
     		campaign.</p>
     		  
     		<p>Here is an explanation for each field:</p>
     		
     		<ol class="moreInfo">
     			<li>The automated campaign id will be assigned automatically by the system</li>
     			<li>select the campaign from the selection box</li>
     			<li>Enter a description.</li>
     			<li>Enter the start date of this automated Campaign</li>
     			<li>Enter the end date of this automated Campaign</li>
     			<li>If you want the Verified code to be used as part of the selection criteria, select Yes here.  The actual code to use<br>
     			is setup in the Configuration, Email Options</li>
     			<li>If you want the Territory owner email to be used as the from email on any email, then select Yes here </li>
     			<li>If you only want to select prospects where creation date is = or after Campaign start date, then select Yes here </li>
     			<li>Creation date of this automated campaign</li>
     			<li>Select the Lead Sources to be used in selecting prospects for this automated campaign.</li>
     			<li>Select the Territories to be used in selecting prospects for this automated campaign.</li>
     			<li>Select the Status Ids to be used in selecting prospects for this automated campaign.</li>
     			
     			<li>To Save changes, Click Update</li>
     		
     		</ol>
     		
     		<p></p>
     		
     		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
     	
     </div>
     <form name="frmEmailCampaign" method="post" action="email_campaign" onsubmit="javascript: return fProcessForm();">
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
 					<%=emailCampaignDetails.getEmailCampaignId()%>
					<input type="hidden" name="dfEmailCampaignId" value="<%=emailCampaignDetails.getEmailCampaignId()%>">
				</td>
			</tr>
			<tr>
			<th align="right">Campaign:</th>
			<td>
			<select name="cmbCampaign" >
	            	
	                	<%=CollectionUtilities.getDropDownOptions(campaignView.getElements(),
	                		"getCampaignId", "getCampaign", emailCampaignDetails.getCampaignId()) %>
	        </select>
			</td>
		</tr>
		<% }else {%>
		<tr>
			<th align="right">Select Campaign:</th>
			<td>
			<select name="cmbCampaign" >
	            	<option value="">Please Select ...</option>
	                	<%=CollectionUtilities.getDropDownOptions(campaignView.getElements(),
	                		"getCampaignId", "getCampaign", "emailCampaignDetails.getCampaignId()") %>
	        </select>
			</td>
		</tr>
		<% } %>
		<tr>
			<th align="right">Description:</th>
			<td><input type="text" name="dfEmailCampaignDescription" size="50" maxlength="100" value="<%=Utilities.nullToBlank(emailCampaignDetails.getEmailCampaignDescription())%>"></td>
		</tr>
		
		<tr>
			<th align="right">Start Date of this Email Campaign:</th>
			<td>
			<%String date;
	                	if(emailCampaignDetails.getStartEmailDate() != null && !emailCampaignDetails.getStartEmailDate().equals("")){
	                		date = dateFormat.format(emailCampaignDetails.getStartEmailDate());
	                	}else{
	                		date = dateFormat.format(DateUtilities.getCurrentSQLDate());
	                	}
	                 %>
	                 <input type="text" name="dfStartEmailDate" size="12" value="<%=date%>" onchange="javascript:void(0);">
	               
	                <a href="javascript:calStartEmailDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
	            <font color="blue" size="+1">**</font>
	            </td>
		</tr>
		
		<tr>
			<th align="right">End Date of this Email Campaign:</th>
			<td>
			<%String date2;
	                	if(emailCampaignDetails.getEndEmailDate() != null && !emailCampaignDetails.getEndEmailDate().equals("")){
	                		date = dateFormat.format(emailCampaignDetails.getEndEmailDate());
	                	}else{
	                		date = dateFormat.format(DateUtilities.getCurrentSQLDate());
	                	}
	                 %>
	                 <input type="text" name="dfEndEmailDate" size="12" value="<%=date%>" onchange="javascript:void(0);">
	               
	                <a href="javascript:calEndEmailDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
	            <font color="blue" size="+1">**</font>
	            </td>
		</tr>
		
		<tr>
			<th align="right">Use Prospect Verified Code:</th>
			<td>
                <select name="dfVerifiedYesNo">
                 <% if (Utilities.nullToZero(emailCampaignDetails.getVerifiedYesNo()).equals(EmailCampaignDetails.IGNORE_VERIFIED_CODE)) {%>
                  		<option value="1" >YES - Send if Verified is Yes
                    	<option value="0" selected>NO - Ignore Verified code
                     <% }else{ %>
                     	<option value="1"selected>YES - Send if Verified is Yes
                    	<option value="0">NO - Ignore Verified code
                <% }  %>
                </select></td>
		</tr>
		
		<tr>
			<th align="right">Use Salesman Email as From Email:</th>
			<td>
                <select name="dfUseSalesmanFromEmail">
                <% if (Utilities.nullToZero(emailCampaignDetails.getUseSalesmanFromEmail()).equals(EmailCampaignDetails.DO_NOT_USE_SALESMAN_EMAIL)) {%>
                    <option value="1" >YES - Use Salesman Email
                    <option value="0"selected>NO - Use From Email in OP
                <% }else{ %>
                    <option value="1" selected>YES - Use Salesman Email
                    <option value="0" >NO - Use From Email in OP
                <% }  %>
                </select></td>
		</tr>
		
			<tr>
			<th align="right">Use Start Date to Filter Prospects:</th>
			<td>
                <select name="dfUseCampaignStartDate">
                <% if (Utilities.nullToZero(emailCampaignDetails.getUseCampaignStartDate()).equals(EmailCampaignDetails.YES_USE_CAMPAIGN_START_DATE)) {%>
                    <option value="1"selected >YES - Use select prospects based on Campaign Start date
                    <option value="0">NO - select all prospects matching criteria
                <% }else{ %>
                    <option value="1" >YES - Use select prospects based on Campaign Start date
                    <option value="0"selected >NO - select all prospects matching criteria
                <% }  %>
                </select></td>
		</tr>
		
		<tr>
			<th align="right">Date this was Created:</th>
			<td nowrap align="left">
	            	<% if (emailCampaignDetails.getCreationDate()!= null) { %>
	            	<%= dateFormat.format(emailCampaignDetails.getCreationDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(emailCampaignDetails.getCreationDate())%>
	            	<% }else{  %>
	            		<%=dateFormat.format(DateUtilities.getCurrentSQLDate())%>
	            	<%  } %>
	        </td>
	     </tr>
	       
 		
	        <tr><td colspan="2">&nbsp;</td></tr>
	  
	    <tr>
            <th nowrap align="center" colspan="2">Select Specific Lead Source:</th>
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
                <a class="button" href="javascript: if(fProcessForm()){document.frmEmailCampaign.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
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
        document.frmEmailCampaign.dfEmailCampaignDescription.focus();
     </script>
      <script>
			var calStartEmailDate = new calendar2(document.forms['frmEmailCampaign'].elements['dfStartEmailDate']);
			calStartEmailDate.year_scroll = true;
			calStartEmailDate.time_comp = false;
        </script>
        <script>
			var calEndEmailDate = new calendar2(document.forms['frmEmailCampaign'].elements['dfEndEmailDate']);
			calEndEmailDate.year_scroll = true;
			calEndEmailDate.time_comp = false;
        </script> 
     </body>
</html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% 	e.printStackTrace(); } %>