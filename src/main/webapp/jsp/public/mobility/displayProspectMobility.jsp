<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings, 
		com.randr.webdw.util.*" %>
		
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request"/>
<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="websiteView" class="com.randr.webdw.website.WebsiteView" scope="request"/>
<jsp:useBean id="contactView" class="com.randr.webdw.contact.ContactView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
<head>
    <title>Sales Portal Mobile - Prospect General Info</title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>
<body>
<div id="allContent">

	<table class="report" border="1" cellspacing="0" cellpadding="3" width="100%" border="0">
    	<tr id="heading">
    		<th colspan="5">
    			<h2>Prospect General Info</h2>
    			<%=prospectDetails.getCustomerCompany()%>
    		</th>
    	</tr>
    	<tr>
    		<td id="label" nowrap>Company:</td>
    		<td id="content">prospect id : <%=Utilities.nullToBlank(prospectDetails.getProspectId())%>
    						&nbsp;<%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%>
    		</td>
    	</tr>
		<tr>
			<td id="label">Contact Info:</td>
			<td id="content"><%=Utilities.nullToBlank(prospectDetails.getContactName())%>
							 <br>
							 <%=Utilities.nullToBlank(prospectDetails.getContactTitle())%>
							 <br>
							 office :<%=Utilities.nullToBlank(prospectDetails.getPhone())%> 
							 <%if(prospectDetails.getPhoneExt()!=null){ %>
							 	ext: <%=Utilities.nullToBlank(prospectDetails.getPhoneExt())%>
							 	 <br>
							 <%} %>
							 <%if(prospectDetails.getCellPhone()!=null){ %>
							 	<br>
							 	cell : <%=Utilities.nullToBlank(prospectDetails.getCellPhone())%>
							 	<br>
							 <%} %>
							 <%=Utilities.nullToBlank(prospectDetails.getEmail())%>
			</td>
		</tr>
		<tr>
			<td id="label">Address Info:</td>
			<td id="content">
				<%if(prospectDetails.getAddress()!=null){ %>
					<a href="http://mobile.google.com/maps?q=<%=prospectDetails.getAddress()%>+ <%=prospectDetails.getCity()%>, <%=prospectDetails.getStateName()%>" target="_blank"><img class="button" border="1" src="<%=AppSettings.getAppWebPath()%>graphics/icons/google.gif" alt="Search Google"></a>
					<%=Utilities.nullToBlank(prospectDetails.getAddress())%>
					<%} %>
							<%if(prospectDetails.getAddress2()!=null){ %>
							 		<br>
							 		<%=Utilities.nullToBlank(prospectDetails.getAddress2())%>	 		
							<%} %>	
							<br>
							<%=Utilities.nullToBlank(prospectDetails.getCountryName())%>
							<br>
							<%if(prospectDetails.getCounty()!=null){ %>
								county : <%=Utilities.nullToBlank(prospectDetails.getCounty())%>
								<br>
							<%} %>						
							<%=Utilities.nullToBlank(prospectDetails.getCity())%>,&nbsp;<%=Utilities.nullToBlank(prospectDetails.getStateName())%>&nbsp;<%=Utilities.nullToBlank(prospectDetails.getZip())%>
							<br>
							 
			</td>
		</tr>
</table>
		<h3>Links to <%if(prospectDetails.getCustomerCompany()!=null){%>
		 				<%=prospectDetails.getCustomerCompany()%> Details 
		 			<%}else{%>
		 			 	Prospect Details 
		 			 <%} %> Below...</h3>
		 <ul>
		 	<% if (prospectSalesActionView.getElements().size() > 0) { %>
		 		<li><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT_SALES_ACTIONS&dfProspectId=" + prospectDetails.getProspectId()%>">
		   			Prospect Sales Actions...</a></li>
		 	<%} %>
		 	<%if(noteView.getElements().size() > 0){%>
		 		<li><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT_NOTES&dfProspectId=" + prospectDetails.getProspectId()%>">
					Prospect Notes...</a></li>
			<%} %>
			<%if(websiteView.getElements().size() > 0){%>
		 	<li><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT_WEBSITES&dfProspectId=" + prospectDetails.getProspectId()%>">
					Prospect Websites...</a></li>
			<%} %>
			<%if(contactView.getElements().size() > 0){%>
		 	<li><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT_CONTACTS&dfProspectId=" + prospectDetails.getProspectId()%>">
					Prospect Contacts...</a></li>	
			<%} %>
			<li><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT_DETAILS&dfProspectId=" + prospectDetails.getProspectId()%>">
					Prospect Detail Info...</a></li>
		</ul>
	<div id="buttonGroup">
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=MAIN_MENU"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/mainMenu.png" alt="Logout"/></a>
			
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=LOGOUT"%>">
			<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/Logout.png" alt="Logout"/></a>
	</div>
	<div id="userProfile">
		<%=userProfile.getUserName() %> is logged in...
	</div>
</div>

</body>
</html>	
