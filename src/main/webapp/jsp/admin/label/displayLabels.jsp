<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.label.LabelDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Labels</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/label/displayLabels.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

     </head>

     <body>
	 <center>

	 <h1>Display Labels</h1>

     <form name="frmLabel" method="post" action="label">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfLabelId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
<%--
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new country&nbsp; &nbsp;</a><hr><br>
     </p>
--%>

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
			<th>Label Default</th>
            <th>Label Display</th>
            <th>Update</th>
		</tr>
<%
		for (int i = 0; i < labelView.getElements().size(); i++)
	  	{
			LabelDetails labelDetails = (LabelDetails) labelView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=labelDetails.getLabelId()%></th>
				<td nowrap align="left"><%=labelDetails.getLabelDefault()%></td>
                <td nowrap align="left"><%=labelDetails.getLabelActual()%></td>
				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=labelDetails.getLabelId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
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
