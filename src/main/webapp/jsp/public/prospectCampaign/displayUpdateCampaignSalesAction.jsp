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
		<input type=hidden name="formAction" value="UPDATE">
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
				<th>OP Email<br> Draft</th>
				<th>Note</th>
				<th>Status</th>
			<tr>
		<% String pastDueStyle = ""; %>
		<% for(int i = 0; i < prospectSalesActionView.getElements().size(); i++) { 
				ProspectSalesActionDetails prospectSalesActionDetails = (ProspectSalesActionDetails)prospectSalesActionView.getElement(i); %>
				<% if(prospectSalesActionDetails.getActionDate() != null 
            		&& prospectSalesActionDetails.getActionDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0 
            		&& prospectSalesActionDetails.getActionStatus().compareTo(new BigDecimal(0)) ==0) {
            		pastDueStyle = "style=\"{background-color: #FF6666}\"";
            	} else {
            		pastDueStyle = "";
            	}%>
		        <tr <%=pastDueStyle %>>
		            <td nowrap align="left"><b>Step <%= prospectSalesActionDetails.getProspectCampaignSequence().intValue() +1 %> : <%=Utilities.nullToBlank(prospectSalesActionDetails.getAction())%></b></td>
					<%if(prospectSalesActionDetails.getMandatoryDate() != null &&
						prospectSalesActionDetails.getMandatoryDate().compareTo(new BigDecimal(1)) == 0) { %>
			        	<td >
			               <input id="dfDateReq_<%=i %>" type="text" style="{background-color: #FF6666}" name="dfSalesActionDate_<%=i %>" size="12" value="<%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>" onchange="javascript:void(0);"><a href="javascript:calSalesActionDate_<%=i%>.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
			            </td>
		            <% } else { %>
		            	<td <%=pastDueStyle %>>
		               		<input type="text" name="dfSalesActionDate_<%=i %>" size="12" value="<%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>" onchange="javascript:void(0);"><a href="javascript:calSalesActionDate_<%= i%>.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
		            	</td>
		            <% } %>
				   
		           <td nowrap align="left">

            	<% if(prospectSalesActionDetails.getActionDate() != null
            		&& prospectSalesActionDetails.getMandatoryDate().compareTo(new BigDecimal(1)) == 0) { %>
	                <input id="dfHourReq_<%=i %>" size="2" style="{background-color: #FF6666}" name="dfHour_<%=i %>" value="<%=Utilities.nullToBlank(hourFormat.format(prospectSalesActionDetails.getActionDate()))%>">
	                &nbsp;:&nbsp;
	         		<input id="dfHourReq_<%=i %>" size="2" style="{background-color: #FF6666}" name="dfMinute_<%=i %>" value="<%=Utilities.nullToBlank(minuteFormat.format(prospectSalesActionDetails.getActionDate()))%>">
         		<% } else { %>
         			<input size="2" name="dfHour_<%=i %>" value="">
                	&nbsp;:&nbsp;
         			<input size="2" name="dfMinute_<%=i %>" value="">
         		<% } %>
				<%if(prospectSalesActionDetails.getActionDate() != null){%>
					<% if(amPmFormat.format(prospectSalesActionDetails.getActionDate()).toUpperCase().equals("PM")){%>
						<input type="radio" name="time_<%=i %>" value="AM" onclick="selectTime();">
            			&nbsp;&nbsp;AM&nbsp;&nbsp;
            			<input type="radio" name="time_<%=i %>" value="PM" onclick="selectTime();" checked>
                		&nbsp;&nbsp;PM
					<%}else{%>
						<input type="radio" name="time_<%=i %>" value="AM" onclick="selectTime();"  checked>
            			&nbsp;&nbsp;AM&nbsp;&nbsp;
            			<input type="radio" name="time_<%=i %>" value="PM" onclick="selectTime();">
                		&nbsp;&nbsp;PM				
					<%}%>
				<%}else{%> 
						<input type="radio" name="time_<%=i %>" value="AM" onclick="selectTime();"  checked>
            			&nbsp;&nbsp;AM&nbsp;&nbsp;
            			<input type="radio" name="time_<%=i %>" value="PM" onclick="selectTime();">
                		&nbsp;&nbsp;PM	
               	<%}%> 	 		
					</td>
					<td><%=Utilities.nullToBlank(prospectSalesActionDetails.getEmailDraftToUse())%></td>
		           <td nowrap align="left">
		            	<textarea name="dfActionNote_<%=i %>"
                    	onkeypress="textCounter(this,this.form.counter_<%=i %>,200);"
                    	cols="50" rows="3"><%=Utilities.nullToBlank(prospectSalesActionDetails.getActionNote())%></textarea>
                    	<br><input id="flat"
	            type="text" name="counter_<%=i %>" maxlength="3" size="3" value="<%=new BigDecimal(200).subtract(new BigDecimal(Utilities.nullToBlank(prospectSalesActionDetails.getActionNote()).length()))%>"
                onfocus = "this.blur();"> characters remaining
		           </td>

					<%if(prospectSalesActionDetails.getActionStatus().compareTo(new BigDecimal(1))==0){ %>
			     		<td>
							<select name="cmbActionStatus_<%=i %>">
							<option value="Active" >Active</option>
							<option value="Completed" selected>Completed</option>				
							</select>
						</td>
					<%}else{ %>
						<td>
							<select name="cmbActionStatus_<%=i %>">
							<option value="Active" selected >Active</option>
							<option value="Completed" >Completed</option>				
							</select>
						</td>
					<% }%>
					<input type="hidden" name="cmbPriority_<%=i %>" value="1">
					<input type="hidden" name="dfProspectSalesActionId_<%=i %>" value="<%=prospectSalesActionDetails.getProspectSalesActionId()%>">


			<script>
				var calSalesActionDate_<%= i%> = new calendar2(document.forms['frmCampaignSalesAction'].elements['dfSalesActionDate_<%= i%>']);
				calSalesActionDate.year_scroll = true;
				calSalesActionDate.time_comp = false;
		    </script>
		
			</tr>
		       <% } %>
		       
		       <tr>
		       	<td colspan="4"><input type="button" name="btnSubmit" value="Update" onclick="fSubmit()" /></td>
		       </tr>
		<% } %>
        </table>
        <h3>** Rows in <span style="{color:#FF6666;}">RED</span> are past due **
        <br>
        ** Dates and Time in <span style="{color:#FF6666;}">RED</span> are mandatory **</h3>

        </form>

    </td></tr></table></center>
<% } catch (Exception e) { %>
    <b>Error:</b><%=e.getMessage()%>
<% } %>
</center>
</body>
</html>