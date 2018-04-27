<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.verified.VerifiedDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="verifiedView" class="com.randr.webdw.verified.VerifiedView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Verifications</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/verified/displayVerifications.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Verifications</h1>

     <form name="frmVerified" method="post" action="verified">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfVerifiedId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new verified&nbsp; &nbsp;</a><hr><br>
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
			<th>Verified Name</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<%
		for (int i = 0; i < verifiedView.getElements().size(); i++)
	  	{
			VerifiedDetails verifiedDetails = (VerifiedDetails) verifiedView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=verifiedDetails.getVerifiedId()%></th>
				<td nowrap align="left"><%=verifiedDetails.getVerified()%></td>

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=verifiedDetails.getVerifiedId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Verified <%=verifiedDetails.getVerified()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=verifiedDetails.getVerifiedId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
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
