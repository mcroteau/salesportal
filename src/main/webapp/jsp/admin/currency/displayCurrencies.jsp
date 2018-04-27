<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.currency.CurrencyDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="currencyView" class="com.randr.webdw.currency.CurrencyView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Currency codes</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/currency/displayCurrencies.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Display Currency Codes</h1>

     <form name="frmCurrency" method="post" action="currency">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfCurrencyId" value="">

	 </form>

	 <table>
	 <tr><td align="center">

     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new Currency Code&nbsp; &nbsp;</a><hr><br>
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
			<th>Code</th>
			<th>Description</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<%
		for (int i = 0; i < currencyView.getElements().size(); i++)
	  	{
			CurrencyDetails currencyDetails = (CurrencyDetails) currencyView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=currencyDetails.getCurrencyId()%></th>
				<td nowrap align="left"><%=currencyDetails.getCurrencyCode()%></td>
				<td nowrap align="left"><%=currencyDetails.getCurrencyDescription()%></td>

				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=currencyDetails.getCurrencyId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('L.O.B. <%=currencyDetails.getCurrencyCode()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=currencyDetails.getCurrencyId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
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
