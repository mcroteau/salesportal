<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.state.StateDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.country.CountryException" %>

<jsp:useBean id="stateView" class="com.randr.webdw.state.StateView" scope="request"/>
<jsp:useBean id="countryDetails" class="com.randr.webdw.country.CountryDetails" scope="request"/>


<% try{ %>

<html>
<head>
<title>Display States</title>
<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/state/displayStates.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
</head>

<body>
<center>

<h1>Display States</h1>
<%
    if (request.getAttribute("countrylException") != null)
    {
        CountryException countryException = (CountryException) request.getAttribute("countrylException");
%>
        <p class="exception"><%=countryException.getMessage()%></p>
<%
    }
    else
    {
%>
        <h2><%=Utilities.nullToBlank(countryDetails.getCountry())%></h2>

        <form name="frmState" method="post" action="state">
            <input type="hidden" name="formAction" value="">
            <input type="hidden" name="dfStateId" value="">
        </form>

        <table>
            <tr><td align="center">
                    <p align="right">
                        <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new state&nbsp; &nbsp;</a><hr><br>
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
                            <th>State Name</th>
                            <th>State Code</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                        <%
                        for (int i = 0; i < stateView.getElements().size(); i++)
                        {
                            StateDetails stateDetails = (StateDetails) stateView.getElements().elementAt(i);
                        %>
                            <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
                                <th nowrap align="right"><%=stateDetails.getStateId()%></th>
                                <td nowrap align="left"><%=stateDetails.getState()%></td>
                                <td nowrap align="left"><%=stateDetails.getStateCode()%></td>

                                <td nowrap align="center"><a href="javascript:fOnUpdate(<%=stateDetails.getStateId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
                                <td nowrap align="center"><a href="javascript:if(confirm('State <%=stateDetails.getState()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=stateDetails.getStateId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
                            </tr>
                        <%
                        }
                        %>
                            </table>
                        <%
                    }
                    %>
                    </td></tr></table>

            <%
            }
            %>
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
