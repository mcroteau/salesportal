<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.size.SizeDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="sizeView" class="com.randr.webdw.size.SizeView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Sizes</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/size/displaySizes.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Sizes</h1>

     <form name="frmSize" method="post" action="size">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfSizeId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new size&nbsp; &nbsp;</a><hr><br>
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
			<th>Size Name</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<%
		for (int i = 0; i < sizeView.getElements().size(); i++)
	  	{
			SizeDetails sizeDetails = (SizeDetails) sizeView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=sizeDetails.getSizeId()%></th>
				<td nowrap align="left"><%=sizeDetails.getSize()%></td>

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=sizeDetails.getSizeId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Size <%=sizeDetails.getSize()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=sizeDetails.getSizeId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
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
