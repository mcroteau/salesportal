<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.systemType.SystemTypeDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.label.LabelView" %>
<jsp:useBean id="systemTypeDetails" class="com.randr.webdw.systemType.SystemTypeDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

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
     	<title><%=actionDescription%> <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/systemType/displayInsertUpdateSystemType.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></h1>
     <hr><br>
     <form name="frmSystemType" method="post" action="system_type" onsubmit="javascript: return fProcessForm();">
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
 					<%=systemTypeDetails.getSystemTypeId()%>
					<input type="hidden" name="dfSystemTypeId" value="<%=systemTypeDetails.getSystemTypeId()%>">
				</td>
			</tr>
		<%
		}
		%>

		<tr>
			<th align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%> Name:</th>
			<td><input type="text" name="dfSystemType" size="40" maxlength="80" value="<%=Utilities.nullToBlank(systemTypeDetails.getSystemType())%>"></td>
		</tr>

        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if (fProcessForm()) {document.frmSystemType.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
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
        document.frmSystemType.dfSystemType.focus();
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
