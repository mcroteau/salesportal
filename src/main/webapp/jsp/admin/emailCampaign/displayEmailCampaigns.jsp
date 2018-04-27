<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.emailSalesAction.emailCampaign.EmailCampaignDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
				 java.text.SimpleDateFormat,
                java.math.BigDecimal" %>

<jsp:useBean id="emailCampaignView" class="com.randr.webdw.emailSalesAction.emailCampaign.EmailCampaignView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<% try{ 

		SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("aa");

%>

     <html>
     <head>
     	<title>Display Email Campaigns</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/emailSalesAction/displayEmailCampaigns.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Email Campaigns</h1>

     <form name="frmEmailCampaign" method="post" action="email_campaign">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfEmailCampaignId" value="">

	 </form>

	 <table>
	 <tr><td align="center">

     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new Email Campaign &nbsp; &nbsp;</a><hr><br>
     </p>


<% if (request.getAttribute("modelException") != null) {
		ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
		<table class="report" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th>Id</th>
			<th>Campaign</th>
			<th>Email Campaign Description</th>
			<th>Use Verified<br> Yes Only</th>
            <th>Use Salesman Email<br> as From Email</th>
            <th>Select Prospects<br> using Campaign Start Date</th>
            <th>Date Created</th>
            <th>Campaign<br>Start Date</th>
            <th>Campaign<br>End Date</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<% for (int i = 0; i < emailCampaignView.getElements().size(); i++) {
			EmailCampaignDetails emailCampaignDetails = (EmailCampaignDetails) emailCampaignView.getElements().elementAt(i); %>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=emailCampaignDetails.getEmailCampaignId()%></th>
				<th nowrap align="right"><%=emailCampaignDetails.getCampaignId()%>/<%=emailCampaignDetails.getCampaignName()%></th>
				<td nowrap align="left"><%=Utilities.nullToBlank(emailCampaignDetails.getEmailCampaignDescription())%></td> 
				
				 <td nowrap align="center"><% if (emailCampaignDetails.getVerifiedYesNo().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
				  <td nowrap align="center"><% if (emailCampaignDetails.getUseSalesmanFromEmail().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
				  <td nowrap align="center"><% if (emailCampaignDetails.getUseCampaignStartDate().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
 				<td nowrap align="left">
	            	<% if (emailCampaignDetails.getCreationDate()!= null) { %>
	            	<%= dateFormat.format(emailCampaignDetails.getCreationDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(emailCampaignDetails.getCreationDate())%>
	            	<% }else{  %>
	            		&nbsp;
	            	<%  } %>
	            </td>
	            <td nowrap align="left">
	            	<% if (emailCampaignDetails.getStartEmailDate()!= null) { %>
	            	<%= dateFormat.format(emailCampaignDetails.getStartEmailDate())%>
	            	<% }else{  %>
	            		&nbsp;
	            	<%  } %>
	            </td>
				<td nowrap align="left">
	            	<% if (emailCampaignDetails.getEndEmailDate()!= null) { %>
	            	<%= dateFormat.format(emailCampaignDetails.getEndEmailDate())%>
	            	<% }else{  %>
	            		&nbsp;
	            	<%  } %>
	            </td>
           

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=emailCampaignDetails.getEmailCampaignId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Email Campaign <%=emailCampaignDetails.getEmailCampaignId()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=emailCampaignDetails.getEmailCampaignId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
				<td><input type="submit" value="Est. Prospects" onclick="javascript: fOnEstimateProspects(<%=emailCampaignDetails.getEmailCampaignId()%>)"></td>
				<%--  <td><input type="submit" value="Display Prospects" onclick="javascript: fOnDisplayProspects(<%=emailCampaignDetails.getEmailCampaignId()%>)"></td> --%>
			</tr>
<% } %>
		</table>
<% } %>
	 </td></tr></table>
	 <br><center>Make sure your Order Portal and Data Migration Portal settings are correct and what you want<br>Especially in Order Portal Clear Email Queue and Auto Send Emails </center>
	 <br><br>Automated Campaigns will be added to prospects that meet the selection criteria and have not received this campaign.
	 <br>If you selected Yes, use start date, then the creation date of the prospect must be on or after the start date of the<br> campaign.  The last date  prospects will get the campaign
	  will be on the end date.
	 </center>
     </body>
     </html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>
