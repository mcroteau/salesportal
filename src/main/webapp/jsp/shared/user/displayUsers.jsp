<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.math.BigDecimal,
                com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.label.LabelView" %>

<jsp:useBean id="userView" class="com.randr.webdw.user.UserView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

<%
    try
    {
        String popup = "true";
        if (!((Boolean) request.getAttribute("isAdmin")).booleanValue())
        {
            popup = "false";
        }
%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/user/displayUsers.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<center>
<h1>Display <%=UserDetails.getUserTypeDescription(UserDetails.USER_TYPE_SALESMAN, true)%></h1>
<form name="frmUser" method="post" action="user">
<input type="hidden" name="formAction" value="">
<input type="hidden" name="dfUserId" value="">
</form>

<table>
<tr><td align="center">

<p align="right">
<a class="button" href="javascript:fOnInsert(<%=popup%>,'DISPLAY_INSERT');">&nbsp; &nbsp;Add new <%=UserDetails.getUserTypeDescription(UserDetails.USER_TYPE_SALESMAN, false).toLowerCase()%>&nbsp; &nbsp;</a>
</p>

<hr><br>
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
                    <th colspan="4">User Data</th>
                    <th colspan="8">User Authorization Limit</th>
                    <th colspan="2">Action</th>
                    </tr>
                    <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>User Name</th>
                    <%--                    <th>Password</th>--%>
                    <th>Sales Co.</th>
                    <th>Co. Limited?</th>
                    <th>L.O.B.</th>
                    <th>Territory</th>
                    <th><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></th>
                    <th><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></th>
                    <th nowrap>ID/Status</th>
                    <th>Verified</th>
                    <th>Size</th>
                    <th>Country / State</th>
                    <th>Edit</th>
                    <th>Del</th>
                    </tr>
                        <%
            for (int i = 0; i < userView.getElements().size(); i++)
            {
                UserDetails userDetails = (UserDetails) userView.getElements().elementAt(i);
                        %>
                       <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
                        <th valign="top" nowrap align="right"><%=userDetails.getUserId()%></th>


                        <td valign="top" align="left"><%=userDetails.getFirstName()%> <%=userDetails.getLastName()%></td>
                        <td valign="top" align="right">
                        <a href="mailto:<%=Utilities.nullToBlank(userDetails.getEmail())%>"><%=Utilities.nullToBlank(userDetails.getEmail())%></a>
                        </td>
                        <td valign="top" nowrap align="left"><%=userDetails.getUserName()%></td>
<%--                        <td valign="top" nowrap align="left"><%=userDetails.getPassword()%></td>--%>

                        <td valign="top" align="left"><%=userDetails.getCompanyName()%></td>
                        <td valign="top" align="left">
                        <% if(userDetails.getLimitCompanyView()!= null){ 
	      					if(userDetails.getLimitCompanyView().compareTo(UserDetails.LIMIT_COMPANY)==0){ %>
                        		Yes
                        <% } else { %>
                        		No 
                        <%  } } %>
                        </td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getLobName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getTerritoryName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getSystemTypeName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getSoftwareTypeName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getStatusName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getVerifiedName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getSizeName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(userDetails.getCountryName())%><br><%=Utilities.nullToBlank(userDetails.getStateName())%></td>
                        <th valign="top" nowrap align="center"><a href="javascript:fOnUpdate(<%=userDetails.getUserId()%>,<%=popup%>,'DISPLAY_UPDATE');"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>
                        <th valign="top" nowrap align="center"><a href="javascript:if(confirm('<%=userDetails.getFirstName()+" "+userDetails.getLastName()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=userDetails.getUserId()%>,<%=popup%>,'DELETE');}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Record"></a></th>

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
