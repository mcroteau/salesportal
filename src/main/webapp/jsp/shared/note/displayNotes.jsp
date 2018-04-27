<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.note.NoteDetails,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.util.DateUtilities,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.util.Enumeration" %>

<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<% try { %>
<html>

<head>
        <title>Prospect Notes</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
        <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/note/displayInsertUpdateNote.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

</head>

<body>
<center>
<h1>Prospect Notes</h1>
<% if (prospectDetails.getProspectId() != null){ %>
    <h2><%=prospectDetails.getCustomerCompany()%></h2>
<% } %>
<form name="frmNotes" action="note">
    <input type="hidden" name="formAction" value="">
    <input type="hidden" name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
    <input type="hidden" name="dfNoteId" value="">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">

</form>

<a href="javascript:fOnAddNote();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/new.gif" alt="New">&nbsp;Add Note</a><br><br>
<hr><br>
<%  if (request.getAttribute("modelException") != null) {
        ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
        <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%">
    <tr>
        <th align=center>User</th>
        <th align=center>Date</th>
        <th align=center>Note</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
    </tr>
        <% for (int i=0; i<noteView.getElements().size();i++) {
                NoteDetails noteDetails = (NoteDetails) noteView.getElements().elementAt(i); %>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td valign="top" align="center"><%=noteDetails.getUserName()%></td>
        <td valign="top" align="center"><%=DateUtilities.formatDate(noteDetails.getNoteDate(),userProfile.getDateFormat()+ " hh:mm a")%></td>
        <td valign="top" width="75%"  align="left"><textarea readonly cols="90" rows="5"><%=noteDetails.getNote()%></textarea><td>
        <td valign="top"><a href="javascript:fOnUpdateNote(<%=noteDetails.getNoteId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Note"></a></td>
        <td valign="top"><a href="javascript:fOnDeleteNote(<%=noteDetails.getNoteId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Note"></a></td>
    </tr>
        <% } %>
    </table>
<% } %>
    <br><br>
    <a href="javascript:window.close()" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Close&nbsp;&nbsp;</a>
    </center>
    </body>
    </html>
    <% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {} %>