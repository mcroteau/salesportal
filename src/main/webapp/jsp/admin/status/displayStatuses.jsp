<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.status.StatusDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="statusView" class="com.randr.webdw.status.StatusView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Statuses</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/status/displayStatuses.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Statuses</h1>

     <form name="frmStatus" method="post" action="status">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfStatusId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new status&nbsp; &nbsp;</a><hr><br>
     </p>

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
		<table class="report" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th>Id</th>
			<th>Status Name</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<%
		for (int i = 0; i < statusView.getElements().size(); i++)
	  	{
			StatusDetails statusDetails = (StatusDetails) statusView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=statusDetails.getStatusId()%></th>
				<td nowrap align="left"><%=statusDetails.getStatus()%></td>

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=statusDetails.getStatusId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Status <%=statusDetails.getStatus()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=statusDetails.getStatusId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
			</tr>
<%
		}
%>
		</table>
<%
	}
%>
	 </td></tr></table>
	 </center>
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
