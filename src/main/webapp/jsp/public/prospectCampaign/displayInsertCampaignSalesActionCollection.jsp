<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.contact.ContactDetails,
                 com.randr.webdw.contact.ContactView,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.*,
                 java.util.Enumeration,
                 java.text.SimpleDateFormat,
                 java.math.BigDecimal,
                 com.randr.webdw.prospect.ProspectDetails,
                 com.randr.webdw.prospectSalesAction.ProspectSalesActionView,
                 com.randr.webdw.campaignSalesAction.*"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="session"/>
<jsp:useBean id="campaignDetails" class="com.randr.webdw.campaign.CampaignDetails" scope="request"/>
<jsp:useBean id="campaignSalesActionView" class="com.randr.webdw.campaignSalesAction.CampaignSalesActionView" scope="request" />
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<html>

<head>
        <title>Campaign Sales Action for Collection</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">

</head>

<body>
<center>

<% try { %>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospectCampaign/displayInsertCampaign.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

    <center><table><tr><td align="center">

        <h1>Campaign Sales Action - <%=campaignDetails.getCampaign()%></h1>
        <hr><br>
		<form name="frmCampaignSalesAction" method="post" action="prospect_campaign">
		<input type=hidden name="formAction" value="INSERT_COLLECTION">
		<input type="hidden" name="campaignId" value="<%=campaignDetails.getCampaignId()%>">
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
				<th>OP Email<br> Draft</th>
				<th>Note</th>
			<tr>
		<% for(int i = 0; i < campaignSalesActionView.getElements().size(); i++) { 
				CampaignSalesActionDetails campaignSalesActionDetails = (CampaignSalesActionDetails)campaignSalesActionView.getElement(i); %>
	
		        <tr>
		            <td nowrap align="left"><b>Step <%= i + 1 %> : <%=Utilities.nullToBlank(campaignSalesActionDetails.getSalesActionDescription())%></b></td>
					<%if(campaignSalesActionDetails.getSalesActionMandatoryDate() != null &&
						campaignSalesActionDetails.getSalesActionMandatoryDate().compareTo(new BigDecimal(1)) == 0) { %>
			        	<td>
			               <input id="dfDateReq_<%=i %>" type="text" style="{background-color: #FF6666}" name="dfSalesActionDate_<%=i %>" size="12" value="" onchange="javascript:void(0);"><a href="javascript:calSalesActionDate_<%=i%>.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
			            </td>
		            <% } else { %>
		            	<td>
		               		<input type="text" name="dfSalesActionDate_<%=i %>" size="12" value="" onchange="javascript:void(0);"><a href="javascript:calSalesActionDate_<%= i%>.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
		            	</td>
		            <% } %>
				   
		           <td nowrap align="left">
		           <% if(campaignSalesActionDetails.getSalesActionMandatoryDate().compareTo(new BigDecimal(1)) == 0) { %>
						<input id="dfHourReq_<%=i %>" size="2" style="{background-color: #FF6666}" name="dfHour_<%=i %>" value="">&nbsp;:&nbsp;
						<input id="dfMinReq_<%=i %>" size="2" style="{background-color: #FF6666}" name="dfMinute_<%=i %>" value="">
			       <%}else{ %>
			           	<input size="2" name="dfHour_<%=i %>" value="">&nbsp;:&nbsp;
						<input size="2" name="dfMinute_<%=i %>" value="">
					<%} %>
						<input type="radio" name="time_<%=i %>" value="AM" onclick="selectTime();"  checked>AM&nbsp;
			           	<input type="radio" name="time_<%=i %>" value="PM" onclick="selectTime();">PM
					</td>
					<td><%=Utilities.nullToBlank(campaignSalesActionDetails.getEmailDraftToUse())%></td>
		           <td nowrap align="left">
		           <textarea name="dfActionNote_<%=i %>"
		                   onkeypress="textCounter(this,this.form.counter_<%=i %>,200);"
		                   cols="50" rows="3"></textarea><br>
		           <input id="flat" type="text" name="counter_<%=i %>" maxlength="3" size="3" value="200"
                onfocus = "this.blur();"> chars remaining
		           </td>

					<input type="hidden" name="cmbStatus_<%=i %>" value="Active">
					<input type="hidden" name="cmbPriority_<%=i %>" value="1">


			<script>
				var calSalesActionDate_<%= i%> = new calendar2(document.forms['frmCampaignSalesAction'].elements['dfSalesActionDate_<%= i%>']);
				calSalesActionDate.year_scroll = true;
				calSalesActionDate.time_comp = false;
		    </script>
		
			</tr>
		       <% } %>
		       
		       <tr>
		       	<td colspan="4"><input type="button" name="btnSubmit" value="Save" onclick="fSubmit()" /></td>
		       </tr>
		<% } %>
        </table>
        <br>
        <h3>** Dates and Time in <span style="{color:#FF6666;}">RED</span> are mandatory **</h3>
        </form>

    </td></tr></table></center>
<% } catch (Exception e) { %>
    <b>Error:</b><%=e.getMessage()%>
<% } %>
</center>
</body>
</html>