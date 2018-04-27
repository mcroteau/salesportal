<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.website.WebsiteDetails,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.Utilities,
                 java.util.Enumeration,
                 java.math.BigDecimal"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="websiteDetails" class="com.randr.webdw.website.WebsiteDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<html>

<head>
        <title>Prospect Websites</title>
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

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"> </script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/website/displayInsertUpdateWebsite.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"> </script>

<center><table><tr><td align="center">
<h1><%=actionDescription%>  Website</h1>
<hr><br>
<form name="frmWebsites" method="post" action="website">
<input type=hidden name="formAction" value="<%=newFormAction%>">
<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">
<input type=hidden name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
<%
        if (request.getAttribute("modelException") != null)
        {
            ModelException modelException = (ModelException) request.getAttribute("modelException");
%>
		<p class="exception"><%=modelException.getMessage()%></p>
<%
        }
        else
        {
%>

        <table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
    <%
            if (formAction.startsWith("DISPLAY_UPDATE") || formAction.startsWith("DISPLAY_DELETE"))
            {
    %>
        <tr>
            <th nowrap align="right">Website Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfWebsiteId" value="<%=Utilities.nullToBlank(websiteDetails.getWebsiteId())%>">
                <%=Utilities.nullToBlank(websiteDetails.getWebsiteId())%>
            </td>
        </tr>
    <%
            }
    %>
    <tr>
            <th nowrap align="right" valign="top">URL:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(websiteDetails.getUrl())%>
    <% } else { %>
                <input type="test" name="dfUrl" size="50" maxlength="250" value="<%=Utilities.nullToBlank(websiteDetails.getUrl())%>">
    <% } %>
            </td>
        </tr>
    <tr>
            <th nowrap align="right" valign="top">Description:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(websiteDetails.getDescription())%>
    <% } else { %>
                <textarea name="dfDescription"
                    onkeypress="textCounter(this,this.form.counter,500);"
                    cols="50" rows="12"><%=Utilities.nullToBlank(websiteDetails.getDescription())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="3" size="3" value="<%=new BigDecimal(500).subtract(new BigDecimal(Utilities.nullToBlank(websiteDetails.getDescription()).length()))%>"
                onfocus = "this.blur();"> characters remaining
    <% } %>
            </td>
        </tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
		<td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmWebsites.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript: if (fProcessForm()) {document.frmWebsites.submit();}">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
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