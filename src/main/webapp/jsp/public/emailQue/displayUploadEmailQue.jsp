<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.prospect.ProspectDetails,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.math.BigDecimal,
                java.text.SimpleDateFormat,
                com.randr.webdw.util.DateUtilities,
                java.util.Enumeration,
                com.randr.webdw.util.CollectionUtilities,
                com.randr.webdw.label.LabelView" %>

<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="sortByField" class="java.lang.String" scope="request"/>
<jsp:useBean id="sortByDirection" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Prospects", "","", "")%>

<% try { %>
<style type="text/css">
ul.emailQueue{text-align: left;}
ul.emailQueue li{list-style: none;}

</style>

<div id="manualEmailQueue">

<form name="frmProspectUpload" method="post" action="email_que_transfer">
<input type="hidden" name="formAction" value="NOTIFY_EMAIL_QUE_UPLOAD">


<h1>Send Order Portal Email</h1>

<p>Sales Portal will now allow you to send prospects to your Order Portal application to add the prospects to 
an email que.  </p>

<table id="top">
	<tr>
		<th>From Email Address</th>
		<td><input type="text" name="dfFromAddress"></td>
	</tr>
	<tr>
		<th>Email Draft Id</th>
		<td><input type="text" name="dfEmailDraftId"></td>
	</tr>
</table>

<input type="hidden" name="uploadProspectView" value="<%=prospectView%>">
<p>List of Prospects to Send to Order Portal:</p>

<table class="emailList">
	<tr>
		<th>Company</th>
		<th>Contact</th>
		<th>Email</th>
		<th>Opt Out?<th>
	</tr>
	
<%for(int i = 0; i < prospectView.getElements().size(); i++){ 
	ProspectDetails details = (ProspectDetails)prospectView.getElement(i);%>
	<tr>
		<td><%=Utilities.nullToBlank(details.getCustomerCompany())%></td>
		<td><%=Utilities.nullToBlank(details.getContactName())%></td>
		<td><%=Utilities.nullToBlank(details.getEmail())%></td>
		<td><%if (details.getOptOutDate()!=null){ %>
			Yes
		<% }else{ %>
			No
		<% } %></td>
	</tr>
<%}%>

	<tr>
	<td colspan="4"> ** Opted Out Prosects will not be sent </td>
	</tr>
</table>

<input type="submit" value="Upload to Order Portal">

</form>

</div>
<% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>
    
<%=AppSettings.getFooter(userProfile)%>