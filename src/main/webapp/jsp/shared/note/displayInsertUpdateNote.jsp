<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.note.NoteDetails,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.Utilities,
                 java.util.Enumeration,
                 java.math.BigDecimal"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="noteDetails" class="com.randr.webdw.note.NoteDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<html>

<head>
        <title>Prospect Notes</title>
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
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/note/displayInsertUpdateNote.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
    <center><table><tr><td align="center">

        <h1><%=actionDescription%>  Note</h1>
        <hr><br>
         <form name="frmNotes" method="post"
         action="note">
         <input type=hidden name="formAction" value="<%=newFormAction%>">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">

<input type=hidden name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
<% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
        <table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
    <% if (formAction.startsWith("DISPLAY_UPDATE") || formAction.startsWith("DISPLAY_DELETE")) { %>
        <tr>
            <th nowrap align="right">Note Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfNoteId" value="<%=Utilities.nullToBlank(noteDetails.getNoteId())%>">
                <%=Utilities.nullToBlank(noteDetails.getNoteId())%>
            </td>
        </tr>
        <tr>
            <th align="right">Created by:</th>
            <td nowrap align="left">
                <%=Utilities.nullToBlank(noteDetails.getUserName())%>
            </td>
        </tr>
        <tr>
            <th align="right">Created on:</th>
            <td nowrap align="left">
                <%=DateUtilities.formatDate(noteDetails.getNoteDate(),userProfile.getDateFormat() + "hh:mm a")%>
            </td>
        </tr>

    <%
            }
    %>
        <tr>
            <th nowrap align="right" valign="top">Note:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <textarea readonly cols="90" rows="5"><%=Utilities.nullToBlank(noteDetails.getNote())%></textarea>
    <% } else { %>
                <textarea name="dfNote"
                    onkeypress="textCounter(this,this.form.counter,2000);"
                    cols="80" rows="15"><%=Utilities.nullToBlank(noteDetails.getNote())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="4" size="4" value="<%=new BigDecimal(2000).subtract(new BigDecimal(Utilities.nullToBlank(noteDetails.getNote()).length()))%>"
                onfocus = "this.blur();"> characters remaining
    <% } %>
            </td>
        </tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
		<td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmNotes.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript: if (fProcessForm()) {document.frmNotes.submit();}">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept"><%=actionDescription%>&nbsp;&nbsp;</a>
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