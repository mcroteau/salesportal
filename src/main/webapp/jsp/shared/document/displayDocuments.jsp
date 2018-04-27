<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.document.DocumentDetails,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.util.DateUtilities,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.util.Enumeration" %>

<jsp:useBean id="documentView" class="com.randr.webdw.document.DocumentView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<% try { %>
<html>

<head>
        <title>Prospect Documents</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
        <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/document/displayInsertUpdateDocument.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

</head>

<body>
<center>
<h1>Prospect Documents</h1>

<br><br>
<form name="frmDocuments" action="document" method="post">
    <input type="hidden" name="formAction" value="">
    <input type="hidden" name="dfProspectId" value="<%=Utilities.nullToBlank(request.getParameter("dfProspectId"))%>">
    <input type="hidden" name="dfDocumentId" value="">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("formAction")%>">
<input type="hidden" name="oldAction" value="prospect">
</form>

<a href="javascript:fOnAddDocument();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/new.gif" alt="New">&nbsp;Add Document</a><br><br>
<%  if (request.getAttribute("modelException") != null) {
        ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
        <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="80%">
    <tr>
        <th align=center colspan="2">File Name</th>
        <th align=center width="50%">Description</th>
        <th align=center>Type</th>
        <th align=center>Creation Date & User</th>
        <th align=center width="10">Edit</th>
        <th align=center width="10">Del</th>
    </tr>
        <% for (int i=0; i<documentView.getElements().size();i++) {
            DocumentDetails documentDetails = (DocumentDetails) documentView.getElements().elementAt(i);
            String imgFileName = "unknown.gif";
            if (documentDetails.getFileType() != null){
                imgFileName = documentDetails.getFileType()+".gif";
            }
        %>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td width="10" valign="top">
            <img src="<%=AppSettings.getAppWebPath()%>graphics/filetypes/<%=imgFileName%>">
        </td>
        <td valign="top" nowrap align="left">
            <% String link=AppSettings.getAppWebPath() + "doc/"+ documentDetails.getFileName();%>
            <a href="<%=link%>" target="_blank">
                <%=documentDetails.getFileName()%>
            <a>
        </td>
        <td valign="top" align="left"><%=documentDetails.getDescription()%></td>
        <td valign="top" align="left"><%=DocumentDetails.getDocumentTypeDescription(documentDetails.getDocumentType())%></td>
        <td valign="top" align="left"><%=DateUtilities.formatDate(documentDetails.getCreationDate(), userProfile)%>
        &nbsp;By:&nbsp;<%=documentDetails.getCreationUserName()%>
        </td>
        <td valign="top"><a href="javascript:fOnUpdateDocument(<%=documentDetails.getDocumentId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Document"></a></td>
        <td valign="top"><a href="javascript:fOnDeleteDocument(<%=documentDetails.getDocumentId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Document"></a></td>
    </tr>
        <% } %>
    </table>
<% } %>
    <br>
    </center>
    </body>
    </html>
    <% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {} %>