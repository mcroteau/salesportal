<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.contact.ContactDetails,
                 com.randr.webdw.contact.ContactView,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.*,
                 java.util.Enumeration,
                 java.sql.Timestamp,
                 java.text.SimpleDateFormat,
                 java.math.BigDecimal,
                 com.randr.webdw.prospectSalesAction.ProspectSalesActionView,
                 com.randr.webdw.prospectSalesAction.*"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="session"/>
<jsp:useBean id="prospectCampaignDetails" class="com.randr.webdw.prospectCampaign.ProspectCampaignDetails" scope="request"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request" />
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<html>

<head>
        <title>Campaign Sales Action</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">

</head>

<body>
<center>

<% try { %>

<%      SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("aa"); %>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospectCampaign/displayInsertCampaign.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

    <center><table><tr><td align="center">

        <h1>Campaign Sales Action - <%=prospectCampaignDetails.getCampaign()%></h1>
        <hr><br>
		<form name="frmCampaignSalesAction" method="post" action="prospect_campaign">
		<input type=hidden name="formAction" value="DELETE">
		<input type="hidden" name="dfProspectCampaignId" value="<%=prospectCampaignDetails.getProspectCampaignId()%>">
		<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
		<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">
		<input type=hidden name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">

<% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
      <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
        <table class="form" style="{border-style: solid; border-width: 1px}">
        	<tr>
				<th>Campaign Sales Action</th>
				<th>Date</th>
				<th>Time</th>
				<th>Note</th>
				<th>Status</th>
			<tr>
		<% String pastDueStyle = ""; %>
		<% for(int i = 0; i < prospectSalesActionView.getElements().size(); i++) { 
				ProspectSalesActionDetails prospectSalesActionDetails = (ProspectSalesActionDetails)prospectSalesActionView.getElement(i); %>
				<% if(prospectSalesActionDetails.getActionDate() != null 
            		&& prospectSalesActionDetails.getActionDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
            		pastDueStyle = "style=\"{background-color: red}\"";
            	} else {
            		pastDueStyle = "";
            	}%>
		        <tr>
		            <td nowrap align="left"><b>Step <%= i + 1 %> : <%=Utilities.nullToBlank(prospectSalesActionDetails.getAction())%></b></td>
					<%if(prospectSalesActionDetails.getMandatoryDate() != null &&
						prospectSalesActionDetails.getMandatoryDate().compareTo(new BigDecimal(1)) == 0) { %>
			        	<td <%=pastDueStyle %>>
			               <%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>
			            </td>
		            <% } else { %>
		            	<td <%=pastDueStyle %>>
		               		<%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>
		            	</td>
		            <% } %>
				   
					  <td nowrap align="left">
					<% if(prospectSalesActionDetails.getActionDate() != null) { %>
	                <%=Utilities.nullToBlank(hourFormat.format(prospectSalesActionDetails.getActionDate()))%>
	                &nbsp;:&nbsp;
	         		<%=Utilities.nullToBlank(minuteFormat.format(prospectSalesActionDetails.getActionDate()))%>
					<% } %>
				<%if(prospectSalesActionDetails.getActionDate() != null){%>
					<% if(amPmFormat.format(prospectSalesActionDetails.getActionDate()).toUpperCase().equals("PM")){%>
						PM
					<%}else{%>
						AM				
					<%}%>
				<%}%> 
	 		
					</td>
		           <td nowrap align="left">
		            	<%=Utilities.nullToBlank(prospectSalesActionDetails.getActionNote())%>
		           </td>

					<%if(prospectSalesActionDetails.getActionStatus().compareTo(new BigDecimal(1))==0){ %>
			     		<td>
							Completed
						</td>
					<%}else{ %>
						<td>
							Active
							
						</td>
					<% }%>
					<input type="hidden" name="cmbPriority_<%=i %>" value="1">
					<input type="hidden" name="dfProspectSalesActionId_<%=i %>" value="<%=prospectSalesActionDetails.getProspectSalesActionId()%>">
		
			</tr>
		       <% } %>
		       
		       <tr>
		       	<td colspan="4"><input type="button" name="btnSubmit" value="Delete Campaign" onclick="fSubmitDelete()" /></td>
		       </tr>
		<% } %>
        </table>
        </form>

    </td></tr></table></center>
<% } catch (Exception e) { %>
    <b>Error:</b><%=e.getMessage()%>
<% } %>
</center>
</body>
</html>