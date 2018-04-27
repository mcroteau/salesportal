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
			BigDecimal amount = new BigDecimal(0); %>
	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/commision/displayCommisions.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
	 <center>

	 <h1>Display Commisions</h1>

     <form name="frmCommision" method="post" action="commision">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfCommisionId" value="">

	 </form>

	 <table>
	 <tr><td align="center">
     <%  if (isAdmin.booleanValue()) {  %>
     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new commision&nbsp; &nbsp;</a><hr><br>
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
           	<th>Reserve</th>
           	<th>Reserve Paid</th>
           	<th>Contract Out</th>
           	<th>Contract In</th>
           	<th>Money In</th>
           	<th>$ In Amt</th>
            <th>Comm. Amt</th>
            <th>Start Date</th>
            <th>Purchase Method</th>
            <th># of Units</th>
            <th># Remaining</th>
            <th># Closed</th>
            <th>Date Sold</th>
            <th>Date Billed</th>
            <th>Date Paid</th>
		
			<% if (isAdmin.booleanValue()) {  %>
				<th>OEP TRID</th>
            	<th>Creation Date</th>
            	<th>Internal Description</th>
            	<th>Update</th>
				<th>Delete</th>
			<% } %>
			

		</tr>
<%		
		CommisionDetails commisionDetails =(CommisionDetails) commisionView.getElements().elementAt(0);
		saveCurrencyCode = commisionDetails.getCurrencyCode();
		for (int i = 0; i < commisionView.getElements().size(); i++){
			commisionDetails = (CommisionDetails) commisionView.getElements().elementAt(i);
%>			
		<%if(commisionDetails.getCurrencyCode()!=null && saveCurrencyCode.compareTo(commisionDetails.getCurrencyCode())!=0){ %>

			<%  saveCurrencyCode = commisionDetails.getCurrencyCode();
			amount = new BigDecimal(0); }%> 

			
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=commisionDetails.getCommisionId()%></th>
			<%  if (isAdmin.booleanValue()) {  %>
				<td nowrap align="left"><%=commisionDetails.getUserFirstName()%> <%=commisionDetails.getUserLastName()%></td>
			<% } %>
				<td align="left"><%=Utilities.nullToBlank(commisionDetails.getProspectName())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDate1(), userProfile.getDateFormat())%></td>
                <td align="left"><%=Utilities.nullToBlank(commisionDetails.getYesNo1())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDate2(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDate3(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDate4(), userProfile.getDateFormat())%></td>
                <td nowrap align="right"><%=Utilities.formatNumber( commisionDetails.getNumeric1(), 2)%>
                <td nowrap align="right"><%=Utilities.formatNumber( commisionDetails.getAmount(), 2)%>
                <%amount = amount.add(commisionDetails.getAmount());%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDate5(), userProfile.getDateFormat())%></td>
                <td align="left"><%=Utilities.nullToBlank(commisionDetails.getDropDown1Name())%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(Utilities.nullToZero(commisionDetails.getTotalUnits()), 0)%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(Utilities.nullToZero(commisionDetails.getTotalUnitsOpen()), 0)%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(Utilities.nullToZero(commisionDetails.getTotalUnitsClosed()), 0)%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDateSold(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDateBilled(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDatePaid(), userProfile.getDateFormat())%></td>
			<%  if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0 ||isAdmin.booleanValue()) {  %>
				 <td align="left"><%=Utilities.nullToBlank(commisionDetails.getOepTransactionid())%></td>
               

				<%  if (isAdmin.booleanValue()) {  %>
                	<td nowrap align="left"><%=DateUtilities.formatDate(commisionDetails.getCreationDate(),userProfile.getDateFormat())%></td>
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
			<td colspan="2"><b>Total <%=Utilities.nullToBlank(saveCurrencyCode)%> Amount = </b></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td align="right"><b> <%=Utilities.formatNumber(amount, 2)%> </b></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0 || isAdmin.booleanValue()){ %>
            <td>&nbsp;</td>
			<td>&nbsp;</td>
			<%  if (isAdmin.booleanValue()) {  %>
            <td>&nbsp;</td>
			<td>&nbsp;</td>
			<% } %>
			<% } %>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
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
