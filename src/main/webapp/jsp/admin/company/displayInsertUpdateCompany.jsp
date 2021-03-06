<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.util.CollectionUtilities,
				com.randr.webdw.company.CompanyDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="companyDetails" class="com.randr.webdw.company.CompanyDetails" scope="request"/>
<jsp:useBean id="dateFormatView" class="com.randr.webdw.international.dateFormat.DateFormatView" scope="request"/>
<jsp:useBean id="timeOffsetView" class="com.randr.webdw.international.timeOffset.TimeOffsetView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<% try{ %>

	<%

	String actionDescription = "Update";
	String newFormAction="UPDATE";

	if (formAction.equals("DISPLAY_INSERT"))
	{
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
	%>
     <html>
     <head>
     	<title><%=actionDescription%> Company</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/company/displayInsertUpdateCompany.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Company</h1>
     <hr><br>
     <form name="frmCompany" method="post" action="company" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">



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
		if (formAction.equals("DISPLAY_UPDATE"))
		{
		%>
			<tr>
				<th align="right">Id:</th>
				<td>
 					<%=companyDetails.getCompanyId()%>
					<input type="hidden" name="dfCompanyId" value="<%=companyDetails.getCompanyId()%>">
				</td>
			</tr>
		<%
		}
		%>

		<tr>
			<th align="right">Company Name:</th>
			<td><input type="text" name="dfCompany" size="40" maxlength="80" value="<%=Utilities.nullToBlank(companyDetails.getCompany())%>"></td>
		</tr>
		
		<tr>
            <th nowrap align="right">Date Format:</th>
            <td nowrap align="left">
                <select name="cmbDataFormat">
                     <option value="">Please select ...</option>
                <%=CollectionUtilities.getDropDownOptions(dateFormatView.getElements(),
                                                          "getDateFormatId", "getDateFormat",
                                                          companyDetails.getDateFormatId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>
		
	
		<tr>
			<th align="right">Time Offset:</th>
			<td nowrap align="left">
				<select name="cmbTimeOffset">
					<option value="">Please select ...</option>				
				 <%=CollectionUtilities.getDropDownOptions(timeOffsetView.getElements(),
                                                          "getTimeOffsetId", "getTimeOffset",
                                                          companyDetails.getTimeOffsetId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>

        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if (fProcessForm()) {document.frmCompany.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>


		</table>
<%
	}
%>
	 </form>
	 </center>

     <script>
        document.frmCompany.dfCompany.focus();
     </script>
     </body>
     </html>

<%
	}
	catch (Exception e)
	{
%>
     Error: <b><%=e.getMessage()%></b>
<%
		e.printStackTrace();
    }
	finally
	{

   	} %>
