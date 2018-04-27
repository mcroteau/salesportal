<%@ page import="com.randr.webdw.AppSettings,
				 com.randr.webdw.customQuery.CustomQueryDetails,
				 com.randr.webdw.customQueryAuthorization.CustomQueryAuthorizationDetails"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="customQueryVector" class="java.util.Vector" scope="request"/>
<jsp:useBean id="adminSite" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<% try{ %>

	<%
	String urlMap = null;
	if (adminSite.booleanValue()==true)
	{
		urlMap=AppSettings.getAppWebPath() + "admin/custom_queries";
	}
	else
	{
		urlMap=AppSettings.getAppWebPath() + "custom_queries";
	}
	%>
	 <center>
	 <h1 style="font-family: arial; font-size: 13px">Custom Queries</h1>
     <form name="frmQuery" method="post" action="<%=urlMap%>">
	 	<input type="hidden" name="formAction" value="">
	 	<input type="hidden" name="dfQueryNo" value="">

	<% if (customQueryVector.size() > 0) { %>
	 <table border="1" class="report" border="1" cellspacing="0" cellpadding="3">
	 <tr>
	 <% if (adminSite.booleanValue()==true) { %><th class="dark">Query Id</th><% } %>
	 <th width="200" class="dark">Query Name</th>
	 <th class="dark">Query Description</th>
	 <% if (adminSite.booleanValue()==true) { %><th class="dark">Query File Name</th><% } %>
	 <% if (adminSite.booleanValue()==true) { %><th class="dark" align="center" valign="top" colspan="3">Maintenance</th><% } %>
	 </tr>
	 <%
		for (int i = 0; i < customQueryVector.size(); i++)
		{
			CustomQueryDetails customQueryDetails = (CustomQueryDetails) customQueryVector.elementAt(i);
	 %>
	 <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
	 <% if (adminSite.booleanValue()==true) { %><td align="right" valign="top"><%=customQueryDetails.getCustomQueryNo()%></td><% } %>
	 <td align="left" valign="top">
	 <a href="javascript:document.frmQuery.formAction.value='EXECUTE_QUERY'; document.frmQuery.dfQueryNo.value='<%=customQueryDetails.getCustomQueryNo()%>'; document.frmQuery.submit();">
 		<%=customQueryDetails.getCustomQueryName()%>
	 </a>
	 </td>
	 <td align="left" valign="top"><%=customQueryDetails.getCustomQueryDescription()%></td>
	 <% if (adminSite.booleanValue()==true) { %><td align="left" valign="top"><%=customQueryDetails.getCustomQueryFileName()%></td><% } %>
	 <% if (adminSite.booleanValue()==true) { %>
	 	<td align="left" valign="top">
		 	<input type="button" onclick="javascript:document.frmQuery.formAction.value='DISPLAY_UPDATE_QUERY'; document.frmQuery.dfQueryNo.value='<%=customQueryDetails.getCustomQueryNo()%>'; document.frmQuery.submit();" value="Change" style="font-family: arial; font-size: 11px">
		</td>

	 	<td align="left" valign="top">
		 	<input type="button" onclick="javascript:document.frmQuery.formAction.value='DISPLAY_AUTHORIZE_USERS'; document.frmQuery.dfQueryNo.value='<%=customQueryDetails.getCustomQueryNo()%>'; document.frmQuery.submit();" value="Authorize" style="font-family: arial; font-size: 11px">
		</td>
	 	<td align="left" valign="top">
		 	<input type="button" onclick="javascript:if(confirm('Are you sure you want to permanently delete this query:\nQuery Id: <%=customQueryDetails.getCustomQueryNo()%>\nQuery Name: <%=customQueryDetails.getCustomQueryName()%>')==true){document.frmQuery.formAction.value='DELETE_QUERY'; document.frmQuery.dfQueryNo.value='<%=customQueryDetails.getCustomQueryNo()%>'; document.frmQuery.submit();}" value="Delete" style="font-family: arial; font-size: 11px">
		</td>

 	<% } %>
	 </tr>
	 <%
		}
	 %>
	 </table><br><br><br>
	 <% }
	else
	{
	%>
		<br><br>
		<hr width="500">
		<br>
		<b><p class="exception">No custom queries available</p></b>
	<%
	}
	%>
	</form>
	 </center>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {
   } %>