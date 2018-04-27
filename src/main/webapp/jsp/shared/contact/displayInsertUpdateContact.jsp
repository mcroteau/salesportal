<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.contact.ContactDetails,
                 com.randr.webdw.contact.ContactView,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.Utilities,
                 java.util.Enumeration,
                 java.math.BigDecimal"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="contactDetails" class="com.randr.webdw.contact.ContactDetails" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<html>

<head>
        <title>Prospect Contacts</title>
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
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/contact/displayInsertUpdateContact.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
    <center><table><tr><td align="center">

        <h1><%=actionDescription%>  Contact</h1>
        <hr><br>
         <form name="frmContacts" method="post"
         action="contact">
         <input type=hidden name="formAction" value="<%=newFormAction%>">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">



<input type=hidden name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
<% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
        <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
        <table class="form">
        <td colspan="2" align="center">
        <% if (((Boolean) request.getAttribute("isAdmin")).booleanValue()) { %>
                &nbsp; &nbsp;
                <a class="button" href="javascript: history.go(-1);">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
        <% } %>
        </td>
        </tr>

        <%      if (formAction.startsWith("DISPLAY_UPDATE") || formAction.startsWith("DISPLAY_DELETE")) { %>
        <tr>
            <th nowrap align="right">Contact Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfContactId" value="<%=Utilities.nullToBlank(contactDetails.getContactId())%>">
                <%=Utilities.nullToBlank(contactDetails.getContactId())%>
            </td>
        </tr>
        <%}%>
        <%  if (formAction.startsWith("DISPLAY_DELETE")) {%>
        <tr>
            <th nowrap align="right">Contact Name:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getContactName())%>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Contact Title:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getContactTitle())%>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Phone:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getPhone())%>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Phone Ext:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getPhoneExt())%>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Cell Phone:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getCellPhone())%>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Fax:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getFax())%>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Email:</th>
            <td nowrap align="left"><%=Utilities.nullToBlank(contactDetails.getEmail())%>
            </td>
        </tr>
        <%} else {%>

        <tr>
            <th nowrap align="right">Contact Name:</th>
            <td nowrap align="left">
                <input type="text" name="dfContactName"
                value="<%=Utilities.nullToBlank(contactDetails.getContactName())%>" maxlength="50" size="40">
            </td>
        </tr>
        <tr>
                <th nowrap align="right">Contact Title:</th>
                <td nowrap align="left">
                    <input type="text" name="dfContactTitle"
                    value="<%=Utilities.nullToBlank(contactDetails.getContactTitle())%>" maxlength="50" size="40">
                </td>
        </tr>
        <tr>
                <th nowrap align="right">Phone:</th>
                <td nowrap align="left">
                    <input type="text" name="dfPhone"
                    value="<%=Utilities.nullToBlank(Utilities.formatPhoneNumber(contactDetails.getPhone()))%>"
                        maxlength="30" size="15"  onblur="javascript:this.value=formatPhoneNumber(this.value);">
                        <% if ((contactDetails!=null)&&(!Utilities.nullToBlank(contactDetails.getPhone()).equals(""))) { %>
                            <a href="http://www.google.com/search?q=<%=Utilities.nullToBlank(Utilities.formatPhoneNumber(contactDetails.getPhone()))%>" target="_blank"><img border="1" src="<%=AppSettings.getAppWebPath()%>graphics/icons/google.gif" alt="Search Google" valign="bottom"></a>
                            <a href="javascript:launchApp('dial.exe <%=Utilities.replace(contactDetails.getPhone(),"-","")%>');"><img border="0" src="<%=AppSettings.getAppWebPath()%>graphics/icons/phone.gif" alt="Dial Number"  valign="bottom"></a>
                        <% } %>
                </td>
        </tr>
        <tr>
                <th nowrap align="right">Phone Extension:</th>
                <td nowrap align="left">
                    <input type="text" name="dfPhoneExt"
                    value="<%=Utilities.nullToBlank(contactDetails.getPhoneExt())%>" maxlength="5" size="10">
                    &nbsp;&nbsp;<b>Cell Phone:</b>
                    <input type="text" name="dfCellPhone"
                    value="<%=Utilities.nullToBlank(contactDetails.getCellPhone())%>" maxlength="30" size="15">
                </td>
        </tr>
        <tr>
                <th nowrap align="right">Fax:</th>
                <td nowrap align="left">
                    <input type="text" name="dfFax"
                    value="<%=Utilities.nullToBlank(Utilities.formatPhoneNumber(contactDetails.getFax()))%>"
                        maxlength="30" size="15" onblur="javascript:this.value=formatPhoneNumber(this.value);">
                </td>
        </tr>
        <tr>
                <th nowrap align="right">E-mail:</th>
                <td nowrap align="left">
                    <input type="text" name="dfEmail" value="<%=Utilities.nullToBlank(contactDetails.getEmail())%>" maxlength="50" size="40">
                    <% if ((contactDetails!=null)&&(!Utilities.nullToBlank(contactDetails.getEmail()).equals(""))) { %>
                        <a href="mailto:<%=Utilities.nullToBlank(contactDetails.getEmail())%>"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/email.gif" alt="send email"></a>
                    <% } %>
                </td>
        </tr>
        <%}%>
        <tr><td colspan="2">&nbsp;</td></tr>

        <tr><td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmContacts.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript: if (fProcessForm()) {document.frmContacts.submit();}">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } %>
                   &nbsp;&nbsp;
                   <a href="javascript:window.close();" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>

        </td></tr>

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