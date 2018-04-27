<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@ page import="com.randr.webdw.AppSettings,
				 com.randr.webdw.user.UserDetails,
				 java.math.BigDecimal,
				 com.randr.webdw.util.Utilities" %>


<jsp:useBean id="dbErrorMsg" class="java.lang.String" scope="request"/>
<jsp:useBean id="customQueryDetails" class="com.randr.webdw.customQuery.CustomQueryDetails" scope="request"/>

<% try{ %>
<html>
<head>
<title>Upload Query Result</title>
     <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
	 </head>
	 <body>
	 <center>
	 <h1>Create Custom SQL Query</h1>
	 <br>
	<%
	if (dbErrorMsg.equals("INVALID_UPLOAD"))
	{
%>
Error: Either the specified file does not exist on your hard drive or it does not have an apropriate file extension (<i>.sql</i>)
<br><br>
Please <a href="javascript:history.go(-1)">go back</a> and specify a valid file.
		<%   }
	else
	{
%>
<p align="center">
<b>Query succesfully created.</b>
</p><br><br>
<table border="0" cellspacing="0" cellpadding="4">
<tr>
<th align="left">Query Id: </th><td><%=customQueryDetails.getCustomQueryNo()%></td>
</tr>
<tr>
<th align="left">Query Name: </th><td><%=customQueryDetails.getCustomQueryName()%></td>
</tr>
<tr>
<th align="left">Query Description: </th><td><%=customQueryDetails.getCustomQueryDescription()%></td>
</tr>
<tr>
<th align="left">File uploaded as: </th><td><%=customQueryDetails.getCustomQueryFileName()%></td>
</tr>
</table>
<br><br><hr width="400"><br>
<form name="frmUpload"
	method="post"
	action="<%=AppSettings.getAppWebPath()%>admin/custom_queries">
<input type="hidden" name="formAction" value="AUTHORIZE_USERS">
<input type="hidden" name="dfQueryNo" value="<%=customQueryDetails.getCustomQueryNo()%>">
<input type="button" class="button" name="Upload" value="Upload Another Query" onclick="javascript:document.frmUpload.formAction.value='DISPLAY_UPLOAD'; document.frmUpload.submit();">
&nbsp; &nbsp; &nbsp; &nbsp;
<input type="button" class="button" name="Authorize" value="Authorize Users" onclick="javascript:document.frmUpload.formAction.value='DISPLAY_AUTHORIZE_USERS'; document.frmUpload.submit();">
</form>
<%
	}
%>
</center>
</body>
</html>

	<% } catch (Exception e) { %>
	Error: <b><%=e.getMessage()%></b>
	<%   e.printStackTrace();
} finally {} %>