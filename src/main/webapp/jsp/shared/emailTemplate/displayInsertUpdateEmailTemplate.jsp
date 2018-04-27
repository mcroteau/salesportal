<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.note.NoteDetails,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.Utilities,
                 java.util.Enumeration,
                 java.math.BigDecimal"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="emailTemplateDetails" class="com.randr.webdw.emailTemplate.EmailTemplateDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<html>

<head>
        <title>EmailTemplates</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
</head>

<body>
<center>

<%
    try
    {
        String actionDescription = "Update";
        String newFormAction="UPDATE";

        if (formAction.startsWith("DISPLAY_INSERT"))
        {
            actionDescription = "Insert";
            newFormAction="INSERT";
        }
        else if (formAction.startsWith("DISPLAY_DELETE"))
        {
            actionDescription = "Delete";
            newFormAction="DELETE";
        }
%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/emailTemplate/displayInsertUpdateEmailTemplate.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
    <center><table><tr><td align="center">

        <h1><%=actionDescription%>  Email Template</h1>
        <hr><br>
         <form name="frmEmailTemplates" method="post"
         action="email_template">
         <input type=hidden name="formAction" value="<%=newFormAction%>">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">

<% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
        <table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
    <% if (formAction.startsWith("DISPLAY_UPDATE") || formAction.startsWith("DISPLAY_DELETE")) { %>
        <tr>
            <th nowrap align="right">Email Template Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfEmailTemplateId" value="<%=Utilities.nullToBlank(emailTemplateDetails.getEmailTemplateId())%>">
                <%=Utilities.nullToBlank(emailTemplateDetails.getEmailTemplateId())%>
            </td>
        </tr>
        <tr>
            <th align="right">Created by:</th>
            <td nowrap align="left">
                <%=Utilities.nullToBlank(emailTemplateDetails.getUserName())%>
            </td>
        </tr>
        <tr>
            <th align="right">Created on:</th>
            <td nowrap align="left">
                <%=DateUtilities.formatDate(emailTemplateDetails.getEmailTemplateDate(),userProfile.getDateFormat() + "hh:mm a")%>
            </td>
        </tr>

    <%
            }
    %>
    	  <tr>
            <th align="right">Description:</th>
            <td><input type="text" name="dfDescription" size="80" maxlength="200" value="<%=Utilities.nullToBlank(emailTemplateDetails.getDescription())%>"></td>
        </tr>
        <tr>
            <th nowrap align="right" valign="top">Text:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <textarea readonly cols="90" rows="5"><%=Utilities.nullToBlank(emailTemplateDetails.getText())%></textarea>
    <% } else { %>
                <textarea name="dfText"
                    onkeypress="textCounter(this,this.form.counter,2000);"
                    cols="80" rows="15"><%=Utilities.nullToBlank(emailTemplateDetails.getText())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="4" size="4" value="<%=new BigDecimal(2000).subtract(new BigDecimal(Utilities.nullToBlank(emailTemplateDetails.getText()).length()))%>"
                onfocus = "this.blur();"> characters remaining
    <% } %>
            </td>
        </tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
		<td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmEmailTemplates.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript: if (fProcessForm()) {document.frmEmailTemplates.submit();}">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept"><%=actionDescription%>&nbsp;&nbsp;</a>
        <% } %>
            &nbsp;&nbsp;
            <a href="javascript:window.close();" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
           </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>

        </table>
        </form>
<%
        }
%>
    </td></tr></table></center>
<%
    }
    catch (Exception e)
    {
%>
    <b>Error:</b><%=e.getMessage()%>
<%
    }
%>
</center>
</body>
</html>