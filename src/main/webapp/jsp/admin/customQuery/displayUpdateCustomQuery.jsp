
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings"%>

<jsp:useBean id="userView" class="com.randr.webdw.user.UserView" scope="request"/>
<jsp:useBean id="customQueryDetails" class="com.randr.webdw.customQuery.CustomQueryDetails" scope="request"/>


<% try{ %>

<html>
<head>
	<title>Update Query</title>
	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
</head>
<body>
	 <center>
		 <h1>Update Query</h1>

		 <form  name="frmUpload"
		 		method="post"
		 		action="<%=AppSettings.getAppWebPath()%>admin/custom_queries">
		 	<input type="hidden" name="formAction" value="UPDATE_QUERY">
		 	<input type="hidden" name="dfQueryNo" value="<%=customQueryDetails.getCustomQueryNo()%>">
			<table border="0" cellspacing="0" cellpadding="4">
				<tr>
					<th align="right" valign="top">Query No.:</th>
					<td><%=customQueryDetails.getCustomQueryNo()%></td>
				</tr>
				<tr>
					<th align="right" valign="top">Query Name:</th>
					<td><input type="text" name="dfQueryName" size="30" maxlength="30" value="<%=customQueryDetails.getCustomQueryName()%>"></td>
				</tr>
				<tr>
					<th align="right" valign="top">Query Description:</th>
					<td>
						<input type="text" name="dfQueryDescription" size="55" maxlength="255" value="<%=customQueryDetails.getCustomQueryDescription()%>">
					</td>
				</tr>
				<tr>
					<th align="right" valign="top">Query Content:</th>
					<td><textarea name="dfQueryContent" cols="50" rows="7"><%=customQueryDetails.getCustomQueryContent()%></textarea></td>
				</tr>
			</table>
			<br><br><hr width="600"><br>
			<table border="0" cellspacing="0" cellpadding="4">
			<tr>
			<td><input type="submit" value="Update Query" name="btnUpdate" class="button"></td>
			<td><input type="button" value="Cancel" name="btnCancel" class="button" onclick="javascript:history.go(-1)"></td>
			</tr></table>
		 </form>

	 </center>
</body>
</html>

<% } catch (Exception e) { %>
	Error: <b><%=e.getMessage()%></b>
	<%   e.printStackTrace();
} finally {} %>