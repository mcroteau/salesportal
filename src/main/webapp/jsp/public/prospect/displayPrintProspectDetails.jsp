<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.note.NoteDetails,
                 com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.label.LabelView"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request"/>

<html>
<head>
<title>
    Print Prospect
</title>
<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
</head>
<body class="print">
<center>
<%
    if (request.getAttribute("modelException") != null)
    {
        ModelException modelException = (ModelException) request.getAttribute("modelException");
%>
		<p class="exception"><hr><%=modelException.getMessage()%></p>
<%
    }
    else
    {
%>

<table border="0" style="width:18cm"><tr><td>
<h1><%=prospectDetails.getCustomerCompany()%></h1>

<hr>
<table border="0" width="100%">
<tr><td valign="top" align="left">
<h2>Prospect #: <%=prospectDetails.getProspectId()%></h2>
<br>
<h2>Contact Information:</h2>
<% if (!Utilities.nullToBlank(prospectDetails.getContactName()).trim().equals("")) { %>
<%=Utilities.nullToBlank(prospectDetails.getContactName())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getContactTitle()).equals("")) { %>
<br>(<%=prospectDetails.getContactTitle()%>)
<% } %>
<br>
<% if (!Utilities.nullToBlank(prospectDetails.getAddress()).trim().equals("")) { %>
    <br><%=Utilities.nullToBlank(prospectDetails.getAddress())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getAddress2()).trim().equals("")) { %>
    <br><%=Utilities.nullToBlank(prospectDetails.getAddress2())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getCounty()).trim().equals("")) { %>
    <br><%=Utilities.nullToBlank(prospectDetails.getCounty())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getZip()).equals("")) { %>
    <br><%=Utilities.nullToBlank(prospectDetails.getZip())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getCity()).equals("")) { %>
    <%=Utilities.nullToBlank(prospectDetails.getCity())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getCountryName()).equals("")) { %>
    <br><%=Utilities.nullToBlank(prospectDetails.getCountryName())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getStateName()).equals("")) { %>
    <br><%=Utilities.nullToBlank(prospectDetails.getStateName())%>
<% } %>
<br>
<% if (!Utilities.nullToBlank(prospectDetails.getPhone()).trim().equals("")) { %>
<br>Phone: <%=Utilities.formatPhoneNumber(Utilities.nullToBlank(prospectDetails.getPhone()))%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getPhoneExt()).trim().equals("")) { %>
&nbsp;Ext: <%=Utilities.nullToBlank(prospectDetails.getPhoneExt())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getFax()).trim().equals("")) { %>
<br>Fax:&nbsp;&nbsp; <%=Utilities.formatPhoneNumber(Utilities.nullToBlank(prospectDetails.getFax()))%>
<% } %>
<br>
<% if (!Utilities.nullToBlank(prospectDetails.getEmail()).equals("")) {%>
<br><%=Utilities.nullToBlank(prospectDetails.getEmail())%>
<% } %>
<% if (!Utilities.nullToBlank(prospectDetails.getWebsite()).equals("")) {%>
<br><%=Utilities.nullToBlank(prospectDetails.getWebsite())%>
<% } %>
</td>
<td style="width:0.3cm">&nbsp;</td>
<td align="left" valign="top">
    <h2>Prospect Detail:</h2>
    <table border="0">
        <tr>
            <th align="right">L.O.B.:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getLobName())%></td>
        </tr>
        <tr>
            <th align="right">Territory:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getTerritoryName())%></td>
        </tr>
        <tr>
            <th align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%>:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getSystemTypeName())%></td>
        </tr>
        <tr>
            <th align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%>:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getSoftwareTypeName())%></td>
        </tr>
        <tr>
            <th align="right">Status Code:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getStatusName())%></td>
        </tr>
        <tr>
            <th align="right">Verified:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getVerifiedName())%></td>
        </tr>
        <tr>
            <th align="right"><%=labelView.getLabel(LabelView.USER_1)%>:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getSsa())%></td>
        </tr>
        <tr>
            <th align="right"><%=labelView.getLabel(LabelView.USER_2)%>:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getSic())%></td>
        </tr>
        <tr>
            <th align="right"><%=labelView.getLabel(LabelView.USER_3)%>:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getSystemNo())%></td>
        </tr>
        <tr>
            <th align="right">Size:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getSizeName())%></td>
        </tr>
        <tr>
            <th align="right"><%=labelView.getLabel(LabelView.USER_4)%>:</th>
            <td align="left"><%=Utilities.nullToBlank(prospectDetails.getHardwareMaintenance())%></td>
        </tr>

    </table>
</td>
</tr>
</table>
<% if (noteView.getElements().size() > 0 ) { %>
<br><br>
    <table class="printreport" width="100%">
    <tr>
        <th align=center>User</th>
        <th align=center>Date</th>
        <th align=center>Note</th>
<%--        <th align=center>Edit</th>--%>
    </tr>

        <% for (int i=0; i<noteView.getElements().size();i++)
        {
             NoteDetails noteDetails = (NoteDetails) noteView.getElements().elementAt(i);
        %>

    <tr>
        <td valign="top" align="center"><%=noteDetails.getUserName()%></td>
        <td valign="top" align="center">
            <%=DateUtilities.formatDate(noteDetails.getNoteDate(),userProfile.getDateFormat())%>
            <br>
            <%=DateUtilities.formatDate(noteDetails.getNoteDate(),"hh:mm a")%>
        </td>
        <td valign="top" width="75%"  align="left"><%=noteDetails.getNote()%></td>
<%--        <th valign="top" nowrap align="center"><a href="javascript:fOnUpdateNote(<%=noteDetails.getNoteId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>--%>
    </tr>

    <% } %>

    </table>
<% } else { %>
<hr>
<center>No notes defined for this prospect.</center>

<% } %>

<% if (prospectSalesActionView.getElements().size() > 0 ) { %>
<br><br>
    <table class="printreport" width="100%">
    <tr>
        <th align=center>User</th>
        <th align=center>Date</th>
        <th align=center>Note</th>
<%--        <th align=center>Edit</th>--%>
    </tr>

        <% for (int i=0; i<prospectSalesActionView.getElements().size();i++)
        {
             ProspectSalesActionDetails prospectSalesActionDetails  = (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
        %>

    <tr>
        <td valign="top" align="center"><%=prospectSalesActionDetails.getActionPriority()%></td>
        <td valign="top" width="75%"  align="left"><%=prospectSalesActionDetails.getAction()%></td>
        <td valign="top" align="center">
            <%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(),userProfile.getDateFormat())%>
            <br>
            <%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(),"hh:mm a")%>
        </td>
        

    </tr>

    <% } %>

    </table>
<% } else { %>
<hr>
<center>No sales actions defined for this prospect.</center>

<% } %>

</td></tr></table>
<% } %>
</center>
</body>
</html>

