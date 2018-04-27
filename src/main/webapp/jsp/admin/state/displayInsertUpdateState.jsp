<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.state.StateDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.CollectionUtilities" %>
<jsp:useBean id="countryView" class="com.randr.webdw.country.CountryView" scope="request"/>
<jsp:useBean id="stateDetails" class="com.randr.webdw.state.StateDetails" scope="request"/>
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
     	<title><%=actionDescription%> State</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/state/displayInsertUpdateState.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> State</h1>
     <hr><br>
     <form name="frmState" method="post" action="state" onsubmit="javascript: return fProcessForm();">
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
 					<%=stateDetails.getStateId()%>
					<input type="hidden" name="dfStateId" value="<%=stateDetails.getStateId()%>">
				</td>
			</tr>
		<%
		}
		%>
		<tr>
			<th align="right">Country:</th>
			<td>
            <select name="dfCountryId">
            <%
                if (formAction.equals("DISPLAY_INSERT"))
                {
            %>
                <%=CollectionUtilities.getDropDownOptions(countryView.getElements(), "getCountryId", "getCountry")%>
            <%
                }
                else
                {
            %>
                <%=CollectionUtilities.getDropDownOptions(countryView.getElements(), "getCountryId", "getCountry", stateDetails.getCountryId())%>
            <%
                }
            %>
            </select>

            </td>
		</tr>

		<tr>
			<th align="right">State Name:</th>
			<td><input type="text" name="dfState" size="40" maxlength="30" value="<%=Utilities.nullToBlank(stateDetails.getState())%>"></td>
		</tr>
		<tr>
			<th align="right">State Code:</th>
			<td><input type="text" name="dfStateCode" size="4" maxlength="2" value="<%=Utilities.nullToBlank(stateDetails.getStateCode())%>"></td>
		</tr>

        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if (fProcessForm()) {document.frmState.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
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
        document.frmState.dfState.focus();
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
