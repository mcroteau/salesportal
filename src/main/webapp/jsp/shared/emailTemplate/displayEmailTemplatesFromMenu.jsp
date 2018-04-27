<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.emailTemplate.EmailTemplateDetails,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.util.DateUtilities,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.util.Enumeration" %>

<jsp:useBean id="emailTemplateView" class="com.randr.webdw.emailTemplate.EmailTemplateView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Email Templates", "","", "")%>
<% try { %>
<html>

<head>
        <title>Email Templates</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
        <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/emailTemplate/displayEmailTemplates.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

</head>

<body>
<center>
<h1>Email Templates</h1>

<form name="frmEmailTemplates" action="email_template">
    <input type="hidden" name="formAction" value="">
    <input type="hidden" name="dfEmailTemplateId" value="">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">

</form>

<a href="javascript:fOnInsert();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/new.gif" alt="New">&nbsp;Add Email Template</a><br><br>
<hr><br>
<%  if (request.getAttribute("modelException") != null) {
        ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
        <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%">
    <tr>
        <th align=center>User</th>
        <th align=center>Date</th>
        <th align=center>Description</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
    </tr>
        <% for (int i=0; i<emailTemplateView.getElements().size();i++) {
                EmailTemplateDetails emailTemplateDetails = (EmailTemplateDetails) emailTemplateView.getElements().elementAt(i); %>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td valign="top" align="center"><%=emailTemplateDetails.getUserName()%></td>
        <td valign="top" align="center"><%=DateUtilities.formatDate(emailTemplateDetails.getEmailTemplateDate(),userProfile.getDateFormat()+ " hh:mm a")%></td>
        <td valign="top" align="left"><%=emailTemplateDetails.getDescription()%></td>
        
   		<td nowrap align="center"><a href="javascript:fOnUpdate(<%=emailTemplateDetails.getEmailTemplateId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
	    <td nowrap align="center"><a href="javascript:if(confirm('Template <%=emailTemplateDetails.getEmailTemplateId()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=emailTemplateDetails.getEmailTemplateId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
    </tr>
        <% } %>
    </table>
<% } %>
   
    </center>
    </body>
    </html>

    <% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {} %>

<%=AppSettings.getFooter(userProfile)%>
