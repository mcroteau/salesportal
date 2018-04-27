<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.softwareType.SoftwareTypeDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.label.LabelView" %>

<jsp:useBean id="softwareTypeView" class="com.randr.webdw.softwareType.SoftwareTypeView" scope="request"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>


<% try{ %>

     <html>
     <head>
     	<title>Display SoftwareTypes</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/softwareType/displaySoftwareTypes.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display <%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></h1>

     <form name="frmSoftwareType" method="post" action="software_type">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfSoftwareTypeId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new <%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%>&nbsp; &nbsp;</a><hr><br>
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
			<th><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%> Name</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<%
		for (int i = 0; i < softwareTypeView.getElements().size(); i++)
	  	{
			SoftwareTypeDetails softwareTypeDetails = (SoftwareTypeDetails) softwareTypeView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=softwareTypeDetails.getSoftwareTypeId()%></th>
				<td nowrap align="left"><%=softwareTypeDetails.getSoftwareType()%></td>

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=softwareTypeDetails.getSoftwareTypeId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('SoftwareType <%=softwareTypeDetails.getSoftwareType()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=softwareTypeDetails.getSoftwareTypeId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
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
