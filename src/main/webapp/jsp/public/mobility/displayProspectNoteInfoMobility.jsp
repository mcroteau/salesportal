<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings, 
		com.randr.webdw.note.NoteDetails,
		com.randr.webdw.util.*" %>
		
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request"/>
<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="websiteView" class="com.randr.webdw.website.WebsiteView" scope="request"/>
<jsp:useBean id="contactView" class="com.randr.webdw.contact.ContactView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
<head>
    <title>Sales Portal Mobile - Prospect Notes</title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>


<div id="allContent">
<body>
    <% if (noteView.getElements().size() > 0)  { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" border="0">
     	<tr id="heading">
    		<th colspan="5">
    			<h2>Prospect Notes</h2>
    			<%=prospectDetails.getCustomerCompany()%>
    		</th>
    	</tr>
    	<tr>
        	<th align=center>User & Date</th>
        <th align=center>Note</th>
	    </tr>
	        <% for (int i=0; i<noteView.getElements().size();i++) {
	                    NoteDetails noteDetails = (NoteDetails) noteView.getElements().elementAt(i);
	        %>
	    <tr <% if (i%2==0) {%> 
	    		class="roweven" 
	    	<%} else { %> 
	    		class="rowodd"
	    	<%}%>>
	        <td valign="top" align="center" nowrap><b><%=noteDetails.getUserName()%></b>
	        		<br><%=DateUtilities.formatDate(noteDetails.getNoteDate(),userProfile.getDateFormat())%>
	                <br><%=DateUtilities.formatDate(noteDetails.getNoteDate(),"hh:mm a")%>
	        </td>
	        <td valign="top" width="75%"  align="left"><%=noteDetails.getNote()%></td>
	    </tr>
	    <% } %>
    </table>
    <% } %>

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
			<li><a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=DISPLAY_PROSPECT&dfProspectId=" + prospectDetails.getProspectId()%>">
					Prospect General Info...</a></li>
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
</body>
</div>
</html>