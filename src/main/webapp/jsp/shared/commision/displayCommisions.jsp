<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.commision.CommisionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
				java.math.BigDecimal,
                com.randr.webdw.util.DateUtilities" %>

<jsp:useBean id="commisionView" class="com.randr.webdw.commision.CommisionView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="isAdmin" type="java.lang.Boolean" scope="request"/>


<% try{ %>
	<%  	String saveCurrencyCode = ""; 
			BigDecimal revenue = new BigDecimal(0);
			BigDecimal amount = new BigDecimal(0); %>
	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/commision/displayCommisions.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
	 <center>

	 <h1>Display Revenue/Commissions</h1>

     <form name="frmCommision" method="post" action="commision">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfCommisionId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
     <%  if (isAdmin.booleanValue()) {  %>
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new commission&nbsp; &nbsp;</a><hr><br>
     </p>
     <% } %>

	<%if (request.getAttribute("modelException") != null){
		ModelException modelException = (ModelException) request.getAttribute("modelException");%>
		<p class="exception"><%=modelException.getMessage()%></p>
	<%}else{%>
		<table class="report" border="1" cellspacing="0" cellpadding="3" width="80%">
		<tr>
			<th>Id</th>
		<%  if (isAdmin.booleanValue()) {  %>
			<th>Salesman</th>
		<% } %>
			 <th>Prospect</th>
            <th>Description</th>
            <th>Revenue Amt.</th>
            <th>Com. Amount</th>
             <th>Exp. Close Date</th>
             <th>Date Sold</th>
            <th>Cancel Date</th>
            <th>Date Billed</th>
            <th>Date Paid</th>
		<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0){ %>
            <th>Check Number</th>
            <th>Check Amount</th>
			<% if (isAdmin.booleanValue()) {  %>
            	<th>Creation Date</th>
            	<th>Internal Description</th>
			<% } %>
		<% } %>
			<th>Update</th>
			<th>Delete</th>

		</tr>
<%		
		CommisionDetails commisionDetails =(CommisionDetails) commisionView.getElements().elementAt(0);
		saveCurrencyCode = commisionDetails.getCurrencyCode();
		for (int i = 0; i < commisionView.getElements().size(); i++){
			commisionDetails = (CommisionDetails) commisionView.getElements().elementAt(i);
%>			
		<%if(commisionDetails.getCurrencyCode()!=null && saveCurrencyCode.compareTo(commisionDetails.getCurrencyCode())!=0){ 
		// this is the total row%>

			<tr class="roweven">
			<td >&nbsp;</td>
			<%  if (isAdmin.booleanValue()) {  %>
			<td>&nbsp;</td>
			<%}%>
			<td colspan="2"><b>Total <%=saveCurrencyCode%>: </b></td>
			<td align="right"><b> <%=Utilities.formatNumber(revenue, Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%> </b></td>
			<td align="right"><b> <%=Utilities.formatNumber(amount, Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%> </b></td>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("NO")==0){ %>
				 <td colspan="7">
			<% }else { %>	
				<%  if (isAdmin.booleanValue()) {  %>
						<td colspan="11">
					<% }else { %>	
						<td colspan="9">
					<%  } %>
			<%  } %> 
			</tr>
			<%  saveCurrencyCode = commisionDetails.getCurrencyCode();
			revenue = new BigDecimal(0); 
			amount = new BigDecimal(0);}%> 

			
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=commisionDetails.getCommisionId()%></th>
			<%  if (isAdmin.booleanValue()) {  %>
				<td nowrap align="left"><%=commisionDetails.getUserFirstName()%> <%=commisionDetails.getUserLastName()%></td>
			<% } %>
				<td align="left"><%=Utilities.nullToBlank(commisionDetails.getProspectName())%></td>
                <td align="left"><%=Utilities.nullToBlank(commisionDetails.getDescription())%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(commisionDetails.getRevenue(), Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%>
                <%revenue = revenue.add(commisionDetails.getRevenue());%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(commisionDetails.getAmount(), Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%>
                <%amount = amount.add(commisionDetails.getAmount());%></td>
            
                
                 <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getExpectedCloseDate(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDateSold(), userProfile.getDateFormat())%></td>
                 <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getCancelDate(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDateBilled(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDatePaid(), userProfile.getDateFormat())%></td>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0){ %>
                <td nowrap align="left"><%=Utilities.nullToBlank(commisionDetails.getCheckNumber())%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(commisionDetails.getCheckAmount(), 2)%></td>

				<%  if (isAdmin.booleanValue()) {  %>
                	<td nowrap align="left"><%=DateUtilities.formatDate(commisionDetails.getCreationDate(),userProfile.getDateFormat()+" hh:mm a")%></td>
                	<td align="left"><%=commisionDetails.getInternalDescription()%></td>
				<% } %>
			<% } %>
				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=commisionDetails.getCommisionId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Commision <%=commisionDetails.getCommisionId()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=commisionDetails.getCommisionId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>

			</tr>
			
<%} // end of for loop%>
		<tr class="roweven">
			<td >&nbsp;</td>
			<%  if (isAdmin.booleanValue()) {  %>
			<td>&nbsp;</td>
			<%}%>
			<td colspan="2"><b>Total <%=saveCurrencyCode%>: </b></td>
			<td align="right"><b> <%=Utilities.formatNumber(revenue, Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%> </b></td>
			<td align="right"><b> <%=Utilities.formatNumber(amount, Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%> </b></td>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("NO")==0){ %>
				 <td colspan="7">
			<% }else { %>	
				<%  if (isAdmin.booleanValue()) {  %>
						<td colspan="11">
					<% }else { %>	
						<td colspan="9">
					<%  } %>
			<%  } %>
			</tr>
		

				
	</table>
<%}%>

	
			

	 </td>
	</tr>
</table>
</center>
<%}catch (Exception e){%>
     Error: <b><%=e.getMessage()%></b>
	<%e.printStackTrace();
}finally{

} %>
