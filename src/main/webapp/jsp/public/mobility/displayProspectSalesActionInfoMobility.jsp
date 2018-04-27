<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings,
		java.text.SimpleDateFormat,
        java.sql.Timestamp,
        com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails, 
		com.randr.webdw.util.*" %>
		
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request"/>
<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="websiteView" class="com.randr.webdw.website.WebsiteView" scope="request"/>
<jsp:useBean id="contactView" class="com.randr.webdw.contact.ContactView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
<head>
    <title>Sales Portal Mobile - Prospect Sales Actions</title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>


<div id="allContent">
<body>

   	<table class="report" border="1" cellspacing="0" cellpadding="3" width="100%" border="0">
    	<tr id="heading">
    		<th colspan="5">
    			<h2>Prospect Sales Action Info</h2>
    			<%=prospectDetails.getCustomerCompany()%>
    		</th>
    	</tr>
    			<tr>
        			<th align=center>Priority</th>
        			<th align=center>Action</th>
        			<th align=center>Date/Time</th>

    			</tr>
    			
    		<% for (int i=0; i<prospectSalesActionView.getElements().size();i++) {
            	ProspectSalesActionDetails prospectSalesActionDetails 
            	= (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
            	SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
            	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            	String startStr = "";
            	String endStr = "";
            	if(prospectSalesActionDetails.getActionDate() != null 
            		&& prospectSalesActionDetails.getActionDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
            		startStr = "<font color=\"red\">";
            		endStr = "</font>";
            	}
        	%>
    			<tr <% if (i%2==0) {%> 
    					class="roweven" 
    				<%} else { %> 
    					class="rowodd"
    				<%}%>>
        			<td valign="top" align="center" nowrap width="10%">
            			<b>
            			<% if (prospectSalesActionDetails.getActionPriority() != null) { %>
                 			<%=startStr %><%=prospectSalesActionDetails.getActionPriority()%><%=endStr %>
            			<% } %>
            			</b>
        			</td>
        			<td valign="top" align="left" >
            			<% if (prospectSalesActionDetails.getAction() != null) { %>
							<%=startStr %><%=prospectSalesActionDetails.getAction()%><%=endStr %>
            			<% } %>
        			</td>
        			<td valign="top" align="left">
        					<% if(prospectSalesActionDetails.getActionDate() != null) {%>
            					<%=startStr %><%= dateFormat.format(prospectSalesActionDetails.getActionDate())%><br>
            					<%= timeFormat.format(prospectSalesActionDetails.getActionDate())%><%=endStr %>
            				<% } else {%>
            					<%=startStr %>No date or time associated with Sales Action.<%=endStr %>
            				<% } %>
        			</td>
        		<%if(prospectSalesActionDetails.getActionNote()!=null){ %> 
				<tr id="notes">
					<td colspan="1" id="label">
						
					</td>
					<td colspan="5" width="90%" id="notes">
						Notes : <%=prospectSalesActionDetails.getActionNote() %>
					</td>
				</tr>

			<%} %>
			</tr>
		<%}%>
	</table>

		<h3>Links to <%if(prospectDetails.getCustomerCompany()!=null){%>
		 				<%=prospectDetails.getCustomerCompany()%> Details 
		 			<%}else{%>
		 			 	Prospect Details 
		 			 <%} %> Below...</h3>
		 <ul>
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
