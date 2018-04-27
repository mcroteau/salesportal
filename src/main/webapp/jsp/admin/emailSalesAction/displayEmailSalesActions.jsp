<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.emailSalesAction.EmailSalesActionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
				 java.text.SimpleDateFormat,
                java.math.BigDecimal" %>

<jsp:useBean id="emailSalesActionView" class="com.randr.webdw.emailSalesAction.EmailSalesActionView" scope="request"/>
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
     	<title>Display Email Sales Actions</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/emailSalesAction/displayEmailSalesActions.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Email Sales Actions</h1>

     <form name="frmEmailSalesAction" method="post" action="email_sales_action">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfEmailSalesActionId" value="">

	 </form>

	 <table>
	 <tr><td align="center">

     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new Email Sales Action &nbsp; &nbsp;</a><hr><br>
     </p>


<% if (request.getAttribute("modelException") != null) {
		ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
		<table class="report" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th>Id</th>
			<th>Email Action Description</th>
			<th>OP Email<br>Draft Id</th>
            <th>Use Verified<br> Yes Only</th>
            <th>Use Salesman Email<br> as From Email</th>
            <th>Date Created</th>
            <th>Date to<br> Sent Email</th>
            <th>Actual<br>Sent Date</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<% for (int i = 0; i < emailSalesActionView.getElements().size(); i++) {
			EmailSalesActionDetails emailSalesActionDetails = (EmailSalesActionDetails) emailSalesActionView.getElements().elementAt(i); %>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=emailSalesActionDetails.getEmailActionId()%></th>

				<td nowrap align="left"><%=Utilities.nullToBlank(emailSalesActionDetails.getEmailActionDescription())%></td> 
				
				<td nowrap align="left"><%=Utilities.nullToBlank(emailSalesActionDetails.getEmailDraftToUse())%></td> 
				 <td nowrap align="center"><% if (emailSalesActionDetails.getVerifiedYesNo().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
				  <td nowrap align="center"><% if (emailSalesActionDetails.getUseSalesmanFromEmail().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
 				<td nowrap align="left">
	            	<% if (emailSalesActionDetails.getCreationDate()!= null) { %>
	            	<%= dateFormat.format(emailSalesActionDetails.getCreationDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(emailSalesActionDetails.getCreationDate())%>
	            	<% }else{  %>
	            		&nbsp;
	            	<%  } %>
	            </td>
	            <td nowrap align="left">
	            	<% if (emailSalesActionDetails.getSendEmailDate()!= null) { %>
	            	<%= dateFormat.format(emailSalesActionDetails.getSendEmailDate())%>
	            	<% }else{  %>
	            		&nbsp;
	            	<%  } %>
	            </td>
				<td nowrap align="left">
	            	<% if (emailSalesActionDetails.getActualEmailDate()!= null) { %>
	            	<%= dateFormat.format(emailSalesActionDetails.getActualEmailDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(emailSalesActionDetails.getActualEmailDate())%>
	            	<% }else{  %>
	            		&nbsp;
	            	<%  } %>
	            </td>
           

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=emailSalesActionDetails.getEmailActionId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Email Sales Action <%=emailSalesActionDetails.getEmailActionId()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=emailSalesActionDetails.getEmailActionId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
				<td><input type="submit" value="Est. Prospects" onclick="javascript: fOnEstimateProspects(<%=emailSalesActionDetails.getEmailActionId()%>)"></td>
				<td><input type="submit" value="Display Prospects" onclick="javascript: fOnDisplayProspects(<%=emailSalesActionDetails.getEmailActionId()%>)"></td>
			</tr>
<% } %>
		</table>
<% } %>
	 </td></tr></table>
	 <br><center>Make sure your Order Portal and Data Migration Portal settings are correct and what you want<br>Especially in Order Portal Clear Email Queue and Auto Send Emails </center>
	 </center>
     </body>
     </html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>
