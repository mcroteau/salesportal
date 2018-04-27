<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.document.DocumentDetails,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.Utilities,
                 java.util.Enumeration,
                 java.math.BigDecimal,
                 com.randr.webdw.util.CollectionUtilities"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="documentDetails" class="com.randr.webdw.document.DocumentDetails" scope="request"/>
<jsp:useBean id="documentView" class="com.randr.webdw.document.DocumentView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="dbErrorMsg" class="java.lang.String" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<html>
<head>
        <title>Prospect Documents</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
</head>
<body>
<center>
<%  try {
        String actionDescription = "Update";
        String newFormAction="UPDATE";

        if (formAction.startsWith("DISPLAY_INSERT")){
            actionDescription = "Insert";
            newFormAction="INSERT";
        } else if (formAction.startsWith("DISPLAY_DELETE")) {
            actionDescription = "Delete";
            newFormAction="DELETE";
        }%>
<h1><%=actionDescription%>  Document</h1>

<%    if (dbErrorMsg.equals("")) { %>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"> </script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/document/displayInsertUpdateDocument.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"> </script>
<center><table><tr><td align="center">
<hr><br>
<form name="frmDocuments" method="post" action="document"
    <% if (formAction.startsWith("DISPLAY_INSERT")
           || formAction.startsWith("DISPLAY_UPDATE")) { %>
        enctype="multipart/form-data"
    <% } %>>
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
    <% if (formAction.startsWith("DISPLAY_UPDATE")
           || formAction.startsWith("DISPLAY_DELETE")) { %>
        <tr>
            <th nowrap align="right">Prospect Document Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfDocumentId" value="<%=Utilities.nullToBlank(documentDetails.getDocumentId())%>">
                <%=Utilities.nullToBlank(documentDetails.getDocumentId())%>
            </td>
        </tr>
    <% } %>
    <tr>
            <th nowrap align="right" valign="top">File:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(documentDetails.getFileName())%>
    <% } else { %>
        <%=Utilities.nullToBlank(documentDetails.getFileName())%><br>
        <input type="file" name="dfFileName" size="50" maxlength="250" value="<%=Utilities.nullToBlank(documentDetails.getFileName())%>">
    <% } %>
            </td>
        </tr>
        <tr>
            <th nowrap align="right" valign="top">Description:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(documentDetails.getDescription())%>
    <% } else { %>
                <textarea name="dfDescription"
                    onkeypress="textCounter(this,this.form.counter,500);"
                    cols="50" rows="12"><%=Utilities.nullToBlank(documentDetails.getDescription())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="3" size="3" value="<%=new BigDecimal(500).subtract(new BigDecimal(Utilities.nullToBlank(documentDetails.getDescription()).length()))%>"
                onfocus = "this.blur();"> characters remaining
    <% } %>
            </td>
        </tr>
    <tr>
    <th nowrap align="right">Document Type:</th>
    <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=DocumentDetails.getDocumentTypeDescription(documentDetails.getDocumentType())%>
    <% } else { %>
        <select name="dfDocumentType">
            <% for (int i = 0; i < DocumentDetails.typeDescription.length; i++) { %>
                <option value="<%=i+1%>"
                    <% if (documentDetails.getDocumentType()!=null
                           && documentDetails.getDocumentType().intValue()==i+1){ %> selected <% } %>>
                    <%=DocumentDetails.getDocumentTypeDescription(new BigDecimal(i+1))%>
            <% } %>
        </select>
    <% } %>
    </td>
    </tr>


        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
		<td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmDocuments.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript: if (fProcessForm()) {document.frmDocuments.submit();}">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } %>
           &nbsp;&nbsp;
           <a href="javascript:history.go(-1);" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>

        </table>
        </form>
<%
        }
%>
    </td></tr></table></center>
<%     } else { %>
<p class="exception"><%=dbErrorMsg%></p>
<%    }
    }  catch (Exception e) {
%>
    <b>Error:</b><%=e.getMessage()%>
<% } %>
</center>
</body>
</html>