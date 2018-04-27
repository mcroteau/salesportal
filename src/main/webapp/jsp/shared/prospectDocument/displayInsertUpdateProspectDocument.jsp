<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.document.DocumentDetails,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.util.Utilities,
                 java.util.Enumeration,
                 java.math.BigDecimal,
                 com.randr.webdw.util.CollectionUtilities"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="prospectDocumentDetails" class="com.randr.webdw.prospectDocument.ProspectDocumentDetails" scope="request"/>
<jsp:useBean id="documentView" class="com.randr.webdw.document.DocumentView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

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
        }
%>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"> </script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospectDocument/displayInsertUpdateProspectDocument.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"> </script>
<center><table><tr><td align="center">
<h1><%=actionDescription%>  Document</h1>
<hr><br>
<form name="frmProspectDocument" method="post" action="prospect_document"
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
            <th  align="right">Prospect Document Id:</th>
            <td  align="left">
                <input type="hidden" name="dfProspectDocumentId" value="<%=Utilities.nullToBlank(prospectDocumentDetails.getProspectDocumentId())%>">
                <%=Utilities.nullToBlank(prospectDocumentDetails.getProspectDocumentId())%>
            </td>
        </tr>
    <% } %>

    <% if (formAction.startsWith("DISPLAY_INSERT")
           || formAction.startsWith("DISPLAY_UPDATE")) { %>
        <tr>
            <th  align="right">
                <input type="radio" name="dfOperationType" value="SELECT_EXISTING_FILE" checked
                    onclick="javascript: disableEnableUploadFields();">
                Select existing file:
            </th>
            <td  align="left">
                <select name="dfDocumentId">
                    <option value="">
                    <%=CollectionUtilities.getDropDownOptions(documentView.getElements(),
                                                              "getDocumentId",
                                                              "getFileName",
                                                              prospectDocumentDetails.getDocumentId())%>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"><hr></td>
        </tr>
    <tr>
            <th  align="right" valign="top">
                <input type="radio" name="dfOperationType" value="UPLOAD_NEW_FILE"
                    onclick="javascript: disableEnableUploadFields();">
                Upload a new file:
            </th>
            <td  align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(prospectDocumentDetails.getFileName())%>
    <% } else { %>
        <input type="file" name="dfFileName" size="50" disabled
                maxlength="250"
                value="<%=Utilities.nullToBlank(prospectDocumentDetails.getFileName())%>">
    <% } %>
            </td>
        </tr>
 <%} else { %>
    <tr>
            <th  align="right" valign="top">File name:</th>
            <td  align="left">
                <%=prospectDocumentDetails.getFileName()%>
            </td>
    </tr>
 <% } %>
        <tr>
            <th  align="right" valign="top">Description:</th>
            <td  align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(prospectDocumentDetails.getDescription())%>
    <% } else { %>
                <textarea name="dfDescription"
                    onkeypress="textCounter(this,this.form.counter,500);"
                    cols="50" rows="12"><%=Utilities.nullToBlank(prospectDocumentDetails.getDescription())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="3" size="3" value="<%=new BigDecimal(500).subtract(new BigDecimal(Utilities.nullToBlank(prospectDocumentDetails.getDescription()).length()))%>"
                onfocus = "this.blur();"> characters remaining
    <% } %>
            </td>
        </tr>
    <tr>
    <th  align="right">Document Type:</th>
    <td  align="left">
    <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
                <input type="hidden" name="dfDocumentType" value="<%=Utilities.nullToBlank(prospectDocumentDetails.getDocumentType())%>">
                <%=Utilities.nullToBlank(DocumentDetails.getDocumentTypeDescription(prospectDocumentDetails.getDocumentType()))%>
    <% } else { %>
        <select name="dfDocumentType">
            <% for (int i = 0; i < DocumentDetails.typeDescription.length; i++) { %>
                <option value="<%=i+1%>"
                    <% if (prospectDocumentDetails.getDocumentType()!=null
                        && prospectDocumentDetails.getDocumentType().intValue()==i+1){ %> selected <% } %>>
                    <%=DocumentDetails.getDocumentTypeDescription(new BigDecimal(i+1))%>
            <% } %>
        </select>
    <% } %>
    </td>
    </tr>
        <tr>
            <td colspan="2"><hr></td>
        </tr>
		<tr>
		<td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmProspectDocument.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript: if (fProcessForm()) {document.frmProspectDocument.submit();}">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } %>
           &nbsp;&nbsp;
           <a href="javascript:window.close();" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>

            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>

        </table>
        </form>
<% } %>
    </td></tr></table></center>
<% } catch (Exception e) { %>
    <b>Error:</b><%=e.getMessage()%>
<% } %>
</center>
</body>
</html>