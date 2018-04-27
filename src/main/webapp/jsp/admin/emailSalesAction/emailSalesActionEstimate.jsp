<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.*,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="emailSalesActionDetails" class="com.randr.webdw.emailSalesAction.EmailSalesActionDetails" scope="request"/>
<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>

<% try{ %>

     <html>
     <head>
     	<title>Email Sales Action Number of Prospects Estimate</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

	
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	</script>
		
		
     </head>

     <body>
	 <center>
	 <h1>Email Sales Action Number of Prospects Estimate</h1>
     <hr><br>
   


<% if (request.getAttribute("modelException") != null) {
		ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
		<table class="form">
        <tr><td colspan="3">&nbsp;</td></tr>
	
			<tr>
				<th align="right" colspan="2">Id:</th>
				<td>
 					<%=emailSalesActionDetails.getEmailActionId()%>
					<input type="hidden" name="dfEmailSalesActionId" value="<%=emailSalesActionDetails.getEmailActionId()%>">
				</td>
			</tr>

		<tr>
			<th align="right" colspan="2">Description:</th>
			<td><input type="text" name="dfEmailSalesActionDescription" size="50" maxlength="100" value="<%=Utilities.nullToBlank(emailSalesActionDetails.getEmailActionDescription())%>"></td>
		</tr>
		<tr>
			<th align="right" colspan="2">Email Draft # to use (get this from Order Portal:</th>
			<td><input type="text" name="dfEmailDraftToUse" size="5" maxlength="9" value="<%=Utilities.nullToBlank(emailSalesActionDetails.getEmailDraftToUse())%>"></td>
		</tr>
	
		
		<tr>
			<th align="right" colspan="2">Number of Prospects Meeting Selection Criteria:</th>
			<td>
			 <% if(prospectView.getElements().size()>0) { %>
					<%=prospectView.getElements().size() %>
			<% }else{ %>
					No Prospects match selection criteria.
			<% } %>
             </td>
		</tr>
		

        <tr><td colspan="2">&nbsp;</td></tr>
		</table>
		
<% } %>
	 </form>
	 </center>
 
     </body>
</html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% 	e.printStackTrace(); } %>