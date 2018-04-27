<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.salesAction.SalesActionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                java.math.BigDecimal" %>

<jsp:useBean id="salesActionView" class="com.randr.webdw.salesAction.SalesActionView" scope="request"/>
<% try{ %>

     <html>
     <head>
     	<title>Display Sales Actions</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/salesAction/displaySalesActions.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Sales Actions</h1>

     <form name="frmSalesAction" method="post" action="sales_action">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfSalesActionId" value="">

	 </form>

	 <table>
	 <tr><td align="center">

     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new Sales Action &nbsp; &nbsp;</a><hr><br>
     </p>


<% if (request.getAttribute("modelException") != null) {
		ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
		<table class="report" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th>Id</th>
			<th>Action Name</th>
            <th>Action Date<br>Is Mandatory</th>
            <th>Display for<br>Salesmen?</th>
            <th>OP Email<br>Draft Id</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<% for (int i = 0; i < salesActionView.getElements().size(); i++) {
			SalesActionDetails salesActionDetails = (SalesActionDetails) salesActionView.getElements().elementAt(i); %>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=salesActionDetails.getActionId()%></th>

				<!--  <td nowrap align="left"><%=salesActionDetails.getAction()%></td> 
				 -->

				<td nowrap align="left" style="color: blue; background-color:<%=Utilities.nullToBlank(salesActionDetails.getColor())%>"><%=salesActionDetails.getAction()%></td>

                <td nowrap align="center"><% if (salesActionDetails.getMandatoryDate().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
                <td nowrap align="left">
				<%if(salesActionDetails.getDisplayForSalesmen().equals(SalesActionDetails.YES_DISPLAY_FOR_SALESMEN)){ %>
					Yes
				<%}else{ %>
					No
				<% } %>
				</td>
				<td nowrap align="left"><%=Utilities.nullToBlank(salesActionDetails.getEmailDraftToUse())%></td> 
				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=salesActionDetails.getActionId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Sales Action <%=salesActionDetails.getAction()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=salesActionDetails.getActionId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
			</tr>
<% } %>
		</table>
<% } %>
	 </td></tr></table>
	 </center>
     </body>
     </html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>
