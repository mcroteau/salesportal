<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@ page import="com.randr.webdw.user.UserDetails,
				 java.math.BigDecimal,
				 com.randr.webdw.util.Utilities,
				 com.randr.webdw.AppSettings" %>


<jsp:useBean id="userView" class="com.randr.webdw.user.UserView" scope="request"/>
<jsp:useBean id="customQueryDetails" class="com.randr.webdw.customQuery.CustomQueryDetails" scope="request"/>

<% try{ %>

	<html>
	<head>
		<title>Authorize Users</title>
        <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
	</head>
	<body>
	<center>
    <table width="70%"><tr><td>
	<center><h1>Authorize Users</h1></center>
     <form name="frmUpload"
	 			method="post"
			 	action="<%=AppSettings.getAppWebPath()%>admin/custom_queries">
     	<input type="hidden" name="formAction" value="AUTHORIZE_USERS">
     	<input type="hidden" name="dfQueryNo" value="<%=customQueryDetails.getCustomQueryNo()%>">

		<table border="0">
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
		<th align="left">File Uploaded as: </th><td><%=customQueryDetails.getCustomQueryFileName()%></td>
		</tr>
		</table>
		<br>

		<table border="0" width="100%">
		<tr>
			<td align="left"><input type="submit" name="dfSubmit" value="Apply User Authorizations"></td>
			<td align="right"><a href="javascript:document.frmUpload.formAction.value='DISPLAY_QUERIES'; document.frmUpload.submit()">Display Queries</a></td>
		</tr>
		</table>
		<hr><br>

        <%
        BigDecimal prevUserType = new BigDecimal(0);
        String userTypeDescriptionPlural = null;
		int i = 0;
		for (i = 0; i < userView.getElements().size(); i++)
		{
			UserDetails userDetails = (UserDetails) userView.getElements().elementAt(i);
			if (!userDetails.getUserType().equals(prevUserType)) {
                if (!prevUserType.equals(new BigDecimal(0))) { %>
				  </table><br><br>
<%              }
                userTypeDescriptionPlural = userDetails.getUserTypeDescription(userDetails.getUserType(), true);
                 %>

				<table border="1" class="report" border="1" cellspacing="0" cellpadding="3">
				<tr><th colspan="6" class="dark"><%=userTypeDescriptionPlural%></th></tr>
<%              prevUserType = userDetails.getUserType();
            } %>

		<tr>
		<th class="dark" nowrap width="5%">
			<input type="checkbox" name="dfCheckBox<%=(i+1)%>" <% if (userDetails.getCustomQueryNo() != null) {%> checked <% }%>>
			<input type="hidden" name="dfCustomerNo<%=(i+1)%>" value="<%=userDetails.getUserId()%>">
		</td>

		<td nowrap width="35%"><%=userDetails.getUserName()%></td>
		<td><%=userDetails.getFirstName()+" "+userDetails.getLastName()%></td>

        </tr>
		<%
		}
		%>
		</table>
        <br><hr>
		<p align="left"><input type="submit" name="dfSubmit" value="Apply User Authorizations"></p>
		<br><br>
		<input type="hidden" name="dfRows" value="<%=(i+1)%>">
		</form>
		</center>
        </td></tr></table>
		</body>
		</html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {} %>