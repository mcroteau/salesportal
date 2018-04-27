<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.prospect.ProspectDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.CollectionUtilities" %>

<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request"/>
<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="websiteView" class="com.randr.webdw.website.WebsiteView" scope="request"/>
<jsp:useBean id="contactView" class="com.randr.webdw.contact.ContactView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<%!
	String prospectId = ""; 
	%>
<% try { %>
<html>
<head>
    <title>Sales Portal Mobile - Prospect Search Results</title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>
<body>
<form name="frmProspect" method="post" action="">
<input type="hidden" name="formAction" value="">
<input type="hidden" name="oldFormAction" value="DISPLAY">
<input type="hidden" name="oldAction" value="prospect_search">
<input type="hidden" name="dfSortBy" value="">
<input type="hidden" name="dfSortDirection" value="">

<div id="allContent">
    	<center><h2>Prospects</h2></center>
		<td align="center">
		    <% if (request.getAttribute("modelException") != null) {
		            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		                    <p class="exception"><%=modelException.getMessage()%></p>
		    <% } %>
		
		    <% if (prospectView.getElements().size() > 0) { %>
	                 <table class="report" border="1" cellspacing="0" cellpadding="3" width="100%">
	                 <tr>
	
	                 <th rowspan="2">Id</th>
	                 <th rowspan="2">View</th>
	
	
	                 <th>Prospect</th>
	                 </tr>
	                 
	                 <tr>
	                 
	                
	                 <% for (int i = 0; i < prospectView.getElements().size(); i++) {
	                     ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i); %>
	                     <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
	                     <th valign="top" nowrap align="center">
	                             <br>
	                             <%=prospectDetails.getProspectId()%>
	                     	</th>
	                     <th valign="top" nowrap align="center">
	                     <table border="0" cellspacing="0" cellpadding="3" width="100%">
	                         <tr>
	                         	<th valign="top">
	                         	<a class="submit" href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT&dfProspectId=" + prospectDetails.getProspectId()%>">
	                         	<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/searchMobile.gif" alt="View Prospect"></a>
	                         	</th>
	                         </tr>
						</table>
	                     </th>
	
	                     <td valign="top" align="left">
	                     <a class="submit" href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT&dfProspectId=" + prospectDetails.getProspectId()%>">
	                     <%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%></a>
	                     <% if (prospectDetails.getContactName() != null) { %>
	                     	<br>
	                         <%=Utilities.nullToBlank(prospectDetails.getContactName())%>
	                     <% } %>
	                     <% if (prospectDetails.getContactTitle() != null) { %>
	                         <br>(<%=Utilities.nullToBlank(prospectDetails.getContactTitle())%>)
	                     <% } %>
	
	                     <% if (prospectDetails.getPhone() != null) { %>
	                         <br>Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getPhone()))%>
	                         <%if (prospectDetails.getPhoneExt() != null){%>
	                             <br>Phone Ext: <%=Utilities.nullToBlank(prospectDetails.getPhoneExt())%>
	                         <% } %>
	                     <% } %>
	                     <% if (prospectDetails.getCellPhone() != null) { %>
	                         <br>Cell Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getCellPhone()))%>
	                     <% } %>
	                     </td>
	                     </tr>
	                     <% } %>
	               <% } %>   
	               <br>

	     </table> 
	     <br>
	     <center>Records selected: <%=prospectView.getElements().size()%></center>
			</td>
		</tr>
	</table>
	<div id="buttonGroup">
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=MAIN_MENU"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/mainMenu.png" alt="Logout"/></a>
			
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=LOGOUT"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/Logout.png" alt="Logout"/></a>
	</div>
	<div id="userProfile">
		<%=userProfile.getUserName() %> is logged in...
	</div>	
	
	</form>
	</div>        
	</body>
</html>
    <% } catch (Exception e) { %>
        Error: <b><%=e.getMessage()%></b>
    <% e.printStackTrace(); } %>
   

