<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.currency.CurrencyDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="currencyDetails" class="com.randr.webdw.currency.CurrencyDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

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
     	<title><%=actionDescription%> Currency</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/currency/displayInsertUpdateCurrency.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%>Currency</h1>
     <hr><br>
     <form name="frmCurrency" method="post" action="currency" onsubmit="javascript: return fProcessForm();">
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
 					<%=currencyDetails.getCurrencyId()%>
					<input type="hidden" name="dfCurrencyId" value="<%=currencyDetails.getCurrencyId()%>">
				</td>
			</tr>
		<%
		}
		%>

		<tr>
			<th align="right">Currency Code:</th>
			<td><input type="text" name="dfCurrencyCode" size="6" maxlength="6" value="<%=Utilities.nullToBlank(currencyDetails.getCurrencyCode())%>"></td>
		</tr>
		<tr>
			<th align="right">Currency Description:</th>
			<td><input type="text" name="dfCurrencyDescription" size="40" maxlength="80" value="<%=Utilities.nullToBlank(currencyDetails.getCurrencyDescription())%>"></td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if(fProcessForm()){document.frmCurrency.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
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
	 </center>

     <script>
        document.frmCurrency.dfCurrencyCode.focus();
     </script>
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
