<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings, com.randr.webdw.util.*" %>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
<head>
    <title>Sales Portal Mobile - Main Menu </title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>


<div id="allContent"><%// note this div sets the width of the screen at 375 %>
<body>
<form name="frmHome" method="post" action="mobility">
<input type="hidden" name="formAction" value=""/> 
<input type="hidden" name="dfSearchSalesActionDate" value="<%=DateUtilities.formatDate(DateUtilities.getCurrentSQLDate(), userProfile.getDateFormat())%>"/>
<table align="left">
<tr>
	<td>
		<h3><a class="submit" href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_SEARCH"%>">
		Work with Prospects<br><img class="button" src=" <%=AppSettings.getAppWebPath()%>graphics/searchMobile.gif" alt="Prospects"/></a></h3>	
	</td>
	<td>
		<h3><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=TODAYS_SALES_ACTION&dfSearchSalesActionDate=" + DateUtilities.formatDate(DateUtilities.getCurrentSQLDate(), userProfile.getDateFormat())%>">
		Today's Sales Actions<br><img class="button" src=" <%=AppSettings.getAppWebPath()%>graphics/calendarMobile.jpg" alt="Today's Sales Actions"/></a></h3>		
	</td>
	<td>
		<h3><a href="<%=AppSettings.getParm("INFOPORTAL_LINK")%>" target="_blank">Info Portal Login<br>
		<img class="button" src=" <%=AppSettings.getAppWebPath()%>graphics/Information_mobile.jpg" alt="Infoportal"/></a></h3>	
	
	</td>
	<td>
		<h3><a class="submit" href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=FULL_SALESPORTAL"%>">
		Full Salesportal<br><img class="button" src=" <%=AppSettings.getAppWebPath()%>graphics/salesportal-banner-small.png" alt="Full Salesportal"/></a></h3>		
	</td>
</tr>

</table>
	<div id="buttonGroup">
			<center><p><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=LOGOUT"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/Logout.png" alt="Logout"/></a></p></center>
	</div>
	<div id="userProfile">
		<%=userProfile.getUserName() %> is logged in...
	</div>
</body>
</div>
</html>
