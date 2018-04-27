<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java" %>

<jsp:useBean id="dbErrorMsg" class="java.lang.String" scope="request"/>


<% try{ %>

     <html>
     <head>
     <title>Update CustomQuery</title>
    <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
     </head>
     <body>
			<center>
			<h1>Update Query</h1>
			<br><br><br>

		<%
		if (dbErrorMsg.equals("ERROR"))
	{
		%>
			<b>The query could not be updated.</b>
		<%
	}
	else
	{
		%>
			<b>Query succesfully updated.</b>
		<%
	}
		%>
			<br><br><br><hr width="400"> <br>

			<form name="frmQuery" method="post" action="<%=AppSettings.getAppWebPath()%>admin/custom_queries">
				<input type="hidden" name="formAction" value="">
				&nbsp; &nbsp; &nbsp; &nbsp;
				<input type="button" value="Display All Queries" onclick="javascript:document.frmQuery.formAction.value='DISPLAY_QUERIES';document.frmQuery.submit();">
			</form>
			</center>

     </body>
     </html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {} %>