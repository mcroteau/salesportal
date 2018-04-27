<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.commision.CommisionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.DateUtilities,
                com.randr.webdw.util.CollectionUtilities" %>
<jsp:useBean id="commisionDetails" class="com.randr.webdw.commision.CommisionDetails" scope="request"/>
<jsp:useBean id="userView" class="com.randr.webdw.user.UserView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="currencyView" class="com.randr.webdw.currency.CurrencyView" scope="request"/>

<% try{ %>

	<%

	String actionDescription = "Update";
	String newFormAction="UPDATE";

	if (formAction.equals("DISPLAY_INSERT"))
	{
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
	%>
     <html>
     <head>
     	<title><%=actionDescription%> Commision</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/commision/displayInsertUpdateCommision.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

        <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Revenue/Commission</h1>
     <hr><br>
     <form name="frmCommision" method="post" action="commision" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">



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
	
		<table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
		<%
		if (formAction.equals("DISPLAY_UPDATE"))
		{
		%>
			<tr>
				<th align="right">Id:</th>
				<td>
 					<%=commisionDetails.getCommisionId()%>
					<input type="hidden" name="dfCommisionId" value="<%=commisionDetails.getCommisionId()%>">
				</td>
			</tr>
		<%
		}
		%>
		<tr>
			<th align="right">Salesman:</th>
			<td><select name="dfUserId">
                <%=CollectionUtilities.getDropDownOptions(userView.getElements(), "getUserId", "getUserName", commisionDetails.getUserId())%>
            </select></td>
		</tr>
		<tr>
			<th align="right">ProspectId:</th>
			<td><input type="text" name="dfProspectId" size="10" maxlength="10"
            value="<%=Utilities.nullToBlank(commisionDetails.getProspectId())%>"></td>
		</tr>
		<tr>
			<th align="right">Prospect Name:</th>
			<td align="left"><%=Utilities.nullToBlank(commisionDetails.getProspectName())%></td>
		</tr>
		
		<tr>
			<th align="right">Description:</th>
			<td><textarea name="dfDescription" cols="50" rows="5"><%=Utilities.nullToBlank(commisionDetails.getDescription())%></textarea></td>
		</tr>
		<tr>
			<th align="right">Revenue Amt.:</th>
			<td><input type="text" name="dfRevenue" size="15" maxlength="10"
            value="<%=Utilities.nullToBlank(commisionDetails.getRevenue())%>"></td>
		</tr>
		<tr>
			<th align="right">Comm. Amount:</th>
			<td><input type="text" name="dfAmount" size="15" maxlength="10"
            value="<%=Utilities.nullToBlank(commisionDetails.getAmount())%>"></td>
		</tr>
		<tr>
			<th align="right">Currency:</th>
			<td><select name="dfCurrencyId">
                <%=CollectionUtilities.getDropDownOptions(currencyView.getElements(), "getCurrencyId", "getCurrencyCode", commisionDetails.getCurrencyId())%>
            </select></td>
		</tr>
		<tr>
			<th align="right">Expected Close Date</th>
			<td><input type="text" name="dfCloseDate" size="15" maxlength="10"
            value="<%=DateUtilities.formatDate(commisionDetails.getExpectedCloseDate(), userProfile.getDateFormat())%>" onchange="javascript:void(0);">
            <a href="javascript:calCloseDate.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
		</tr>
		<tr>
			<th align="right">Date Sold</th>
			<td><input type="text" name="dfDateSold" size="15" maxlength="10"
            value="<%=DateUtilities.formatDate(commisionDetails.getDateSold(), userProfile.getDateFormat())%>" onchange="javascript:void(0);">
            <a href="javascript:calDateSold.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
		</tr>
		<tr>
			<th align="right">Cancel Date</th>
			<td><input type="text" name="dfCancelDate" size="15" maxlength="10"
            value="<%=DateUtilities.formatDate(commisionDetails.getCancelDate(), userProfile.getDateFormat())%>" onchange="javascript:void(0);">
            <a href="javascript:calCancelDate.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
		</tr>
		<tr>
			<th align="right">Date Billed</th>
			<td><input type="text" name="dfDateBilled" size="15" maxlength="10"
            value="<%=DateUtilities.formatDate(commisionDetails.getDateBilled(), userProfile.getDateFormat())%>" onchange="javascript:void(0);">
            <a href="javascript:calDateBilled.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
		</tr>
		<tr>
			<th align="right">Date Paid</th>
			<td><input type="text" name="dfDatePaid" size="15" maxlength="10"
            value="<%=DateUtilities.formatDate(commisionDetails.getDatePaid(), userProfile.getDateFormat())%>" onchange="javascript:void(0);">
            <a href="javascript:calDatePaid.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
		</tr>
		<tr>
			<th align="right">PO #:</th>
			<td><input type="text" name="dfPONumber" size="50" maxlength="50"
            value="<%=Utilities.nullToBlank(commisionDetails.getPoNumber())%>"></td>
		</tr>
		
		<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0){ %>
		<tr>
			<th align="right">Check #:</th>
			<td><input type="text" name="dfCheckNumber" size="25" maxlength="25"
            value="<%=Utilities.nullToBlank(commisionDetails.getCheckNumber())%>"></td>
		</tr>
		<tr>
			<th align="right">Check Amount ($):</th>
			<td><input type="text" name="dfCheckAmount" size="15" maxlength="10"
            value="<%=Utilities.nullToBlank(commisionDetails.getCheckAmount())%>"></td>
		</tr>
		<tr>
			<th align="right">Internal Description:</th>
			<td><textarea name="dfInternalDescription" cols="50" rows="5"><%=Utilities.nullToBlank(commisionDetails.getInternalDescription())%></textarea></td>
		</tr>
		<%}else{ %>
		<tr>
			
			<td><input type="hidden" name="dfCheckNumber" 
            value="<%=Utilities.nullToBlank(commisionDetails.getCheckNumber())%>"></td>
		</tr>
		<tr>
			
			<td><input type="hidden" name="dfCheckAmount" 
            value="<%=Utilities.nullToBlank(commisionDetails.getCheckAmount())%>"></td>
		</tr>
		<tr>
			
			<td><input type="hidden" name="dfInternalDescription" ><%=Utilities.nullToBlank(commisionDetails.getInternalDescription())%></textarea></td>
		</tr>
		<% }%>
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if (fProcessForm()) {document.frmCommision.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>


		</table>
<%
	}
%>
	 </form>

<script>
			
			var calCloseDate = new calendar2(document.forms['frmCommision'].elements['dfCloseDate']);
			calCloseDate.year_scroll = true;
			calCloseDate.time_comp = false;
			
			var calDateSold = new calendar2(document.forms['frmCommision'].elements['dfDateSold']);
			calDateSold.year_scroll = true;
			calDateSold.time_comp = false;
			
			var calCancelDate = new calendar2(document.forms['frmCommision'].elements['dfCancelDate']);
			calCancelDate.year_scroll = true;
			calCancelDate.time_comp = false;

			var calDateBilled = new calendar2(document.forms['frmCommision'].elements['dfDateBilled']);
			calDateBilled.year_scroll = true;
			calDateBilled.time_comp = false;

			var calDatePaid = new calendar2(document.forms['frmCommision'].elements['dfDatePaid']);
			calDatePaid.year_scroll = true;
			calDatePaid.time_comp = false;

        </script>
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
