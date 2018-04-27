<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.state.StateDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.CollectionUtilities" %>

<jsp:useBean id="countryView" class="com.randr.webdw.country.CountryView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Search States</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>

	 <h1>Search States</h1>
     <table><tr><td>
        <hr><br>
     <form name="frmCountry" method="get" action="state">

	 <input type="hidden" name="formAction" value="DISPLAY">
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
    <table>
    <tr>
        <td colspan="2" align="right">
            <a href="javascript:document.frmCountry.submit();" class="button">&nbsp; &nbsp; Display States &nbsp; &nbsp;</a>
        </td>
    </tr>
    <tr><td colspan="2"></td></tr>
    <tr><td colspan="2"></td></tr>

    <tr>
        <th>Country:</th>
        <td>
            <select name="dfCountryId">
                <%=CollectionUtilities.getDropDownOptions(countryView.getElements(), "getCountryId", "getCountry")%>
            </select>
        </td>
    </tr>

    </table>

<%
    }
%>
     </form>
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
