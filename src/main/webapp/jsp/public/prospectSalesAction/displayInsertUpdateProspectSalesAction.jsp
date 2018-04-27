<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.contact.ContactDetails,
                 com.randr.webdw.contact.ContactView,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.*,
                 java.util.Enumeration,
                 java.text.SimpleDateFormat,
                 java.math.BigDecimal,
                 com.randr.webdw.prospectSalesAction.ProspectSalesActionView"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="prospectSalesActionDetails" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails" scope="request"/>
<jsp:useBean id="salesActionView" class="com.randr.webdw.salesAction.SalesActionView" scope="request" />
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<html>

<head>
        <title>Prospect Sales Action</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">

</head>

<body>
<center>

<%
    try
    {
        String actionDescription = "Update";
        String newFormAction="UPDATE";

        if (formAction.startsWith("DISPLAY_INSERT")) {
            actionDescription = "Insert";
            newFormAction="INSERT";
        }
        else if (formAction.startsWith("DISPLAY_DELETE")) {
            actionDescription = "Delete";
            newFormAction="DELETE";
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat amPmFormat = new SimpleDateFormat("aa");
%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospectSalesAction/displayInsertUpdateProspectSalesAction.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<%if(salesActionView.getElements().size()>0){%> 
<script>
   <%=salesActionView.getDateMandatoryArray()%>
   <%=salesActionView.getEmailDraftArray()%>
</script>
<%}%> 
    <center><table><tr><td align="center">

        <h1><%=actionDescription%>  Sales Action</h1>
        <hr><br>
		<form name="frmProspectSalesAction" method="post" action="prospect_sales_action">
		<input type=hidden name="formAction" value="<%=newFormAction%>">

		<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
		<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">

		<input type=hidden name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
		<input type=hidden name="dfProspectSalesActionId" value="<%=request.getParameter("dfProspectSalesActionId")%>">

<% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
      <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
        <table class="form">
        <tr>
        <td colspan="2" align="center">
        <% if (((Boolean) request.getAttribute("isAdmin")).booleanValue()) { %>
                &nbsp; &nbsp;
                <a class="button" href="javascript: history.go(-1);">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
        <% } %>
        </td>
        </tr>

        <% if (formAction.startsWith("DISPLAY_UPDATE") || formAction.startsWith("DISPLAY_DELETE")) { %>
	        <tr>
	            <th nowrap align="right">Sales Action Id:</th>
	            <td nowrap align="left">
	                <input type="hidden" name="dfprospectSalesActionId" value="<%=Utilities.nullToBlank(prospectSalesActionDetails.getProspectSalesActionId())%>">
	                <%=Utilities.nullToBlank(prospectSalesActionDetails.getProspectSalesActionId())%>
	            </td>
	         </tr>
	         <tr>
	           <th nowrap align="right">OP Draft Email #:</th>
	           <input type=hidden name="dfEmailDraftToUse" value="<%=Utilities.nullToBlank(prospectSalesActionDetails.getEmailDraftToUse())%>">
	            <td><%=Utilities.nullToBlank(prospectSalesActionDetails.getEmailDraftToUse())%></td>
	        </tr>
        <%}%>
        <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>

	        <tr>
	            <th nowrap align="right">Priority:</th>
	            <td nowrap align="left">
	            	<%=prospectSalesActionDetails.getActionPriority() %>
	            </td>
	        </tr>
	        <tr>
	            <th nowrap align="right">Sales Action:</th>
	            <td nowrap align="left">
	            	<%=prospectSalesActionDetails.getAction() %>
	            </td>
	        </tr>
	        <tr>
	            <th nowrap align="right">Date/Time:</th>
	            <td nowrap align="left">
	            	<% if (prospectSalesActionDetails.getActionDate() != null) { %>
	            	<%= dateFormat.format(prospectSalesActionDetails.getActionDate())%>&nbsp;&nbsp;&nbsp;
	            	<%= timeFormat.format(prospectSalesActionDetails.getActionDate())%>
	            	<% }  %>
	            </td>
	        </tr>

        <%} else {%>
        
	        <tr>
	            <th nowrap align="right">Priority:</th>
	            <td nowrap align="left">
	            	<select name="cmbPriority" >
	            	<%=ProspectSalesActionView.getActionPriorityDropDown(prospectSalesActionDetails) %>
	                </select>
	            </td>
	        </tr>
       
	        <tr>
	            <th nowrap align="right">Sales Action:</th>
	            <td nowrap align="left">
	            	<select name="cmbSalesAction" onchange="javascript:fSelectSalesAction()">
	            	<option value="">-</option>
	                	<%=CollectionUtilities.getDropDownOptions(salesActionView.getElements(),
	                		"getActionId", "getAction", prospectSalesActionDetails.getSalesActionId()) %>
	                </select>
	                <%String date;
	                	if(prospectSalesActionDetails.getActionDate() != null && !prospectSalesActionDetails.getActionDate().equals("")){
	                		date = DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy");
	                	}else{
	                		date = DateUtilities.formatDate(DateUtilities.getCurrentSQLDate(), "MM/dd/yyyy");
	                	}
	                 %>
	                &nbsp;<font color="blue" size="+1">**</font>&nbsp;<b>Date:</b> <input type="text" name="dfSalesActionDate" size="12" value="<%=date%>" onchange="javascript:void(0);">
	                <input type="hidden" name="dfOldSalesActionDate" value="<%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>">
	                <a href="javascript:calSalesActionDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
	            <font color="blue" size="+1">**</font>
	            </td>
	        </tr>
		
        <tr>
           	<th nowrap align="right">Time:</th>
            <td nowrap align="left">
            	<% if(prospectSalesActionDetails.getActionDate() != null) { %>
	                <input size="2" name="dfHour" value="<%=Utilities.nullToBlank(hourFormat.format(prospectSalesActionDetails.getActionDate()))%>">
	                &nbsp;<BIG><b>:</b></BIG>&nbsp;
	         		<input size="2" name="dfMinute" value="<%=Utilities.nullToBlank(minuteFormat.format(prospectSalesActionDetails.getActionDate()))%>">
         		<% } else { %>
         			<input size="2" name="dfHour" value="">
                	&nbsp;<BIG><b>:</b></BIG>&nbsp;
         			<input size="2" name="dfMinute" value="">
         		<% } %>
				<%if(prospectSalesActionDetails.getActionDate() != null){%>
					<% if(amPmFormat.format(prospectSalesActionDetails.getActionDate()).toUpperCase().equals("PM")){%>
						<input type="radio" name="time" value="AM" onclick="selectTime();">
            			&nbsp;&nbsp;AM&nbsp;&nbsp;
            			<input type="radio" name="time" value="PM" onclick="selectTime();" checked>
                		&nbsp;&nbsp;PM
					<%}else{%>
						<input type="radio" name="time" value="AM" onclick="selectTime();"  checked>
            			&nbsp;&nbsp;AM&nbsp;&nbsp;
            			<input type="radio" name="time" value="PM" onclick="selectTime();">
                		&nbsp;&nbsp;PM				
					<%}%>
				<%}else{%> 
						<input type="radio" name="time" value="AM" onclick="selectTime();"  checked>
            			&nbsp;&nbsp;AM&nbsp;&nbsp;
            			<input type="radio" name="time" value="PM" onclick="selectTime();">
                		&nbsp;&nbsp;PM	
               	<%}%> 		
			</td>
		
		</tr>
		   <tr>
            <th nowrap align="right" valign="top">Action Note:</th>
            <td nowrap align="left">
    <%  if (formAction.startsWith("DISPLAY_DELETE")) { %>
        <%=Utilities.nullToBlank(prospectSalesActionDetails.getActionNote())%>
    <% } else { %>
                <textarea name="dfActionNote"
                    onkeypress="textCounter(this,this.form.counter,200);"
                    cols="50" rows="4"><%=Utilities.nullToBlank(prospectSalesActionDetails.getActionNote())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="3" size="3" value="<%=new BigDecimal(200).subtract(new BigDecimal(Utilities.nullToBlank(prospectSalesActionDetails.getActionNote()).length()))%>"
                onfocus = "this.blur();"> characters remaining
    <% } %>
            </td>
       
        </tr>
       <% if (formAction.startsWith("DISPLAY_UPDATE") || formAction.startsWith("DISPLAY_DELETE")) { %>
	         
        <tr>
        <th><center>Action Status:</th><br>
        <%if(prospectSalesActionDetails.getActionStatus().compareTo(new BigDecimal(1))==0){ %>
     		<TD>
				<select name="cmbActionStatus">
				<option value="Active" >Active</option>
				<option value="Completed" selected>Completed</option>				
				</select>
			</TD>
		<%}else{ %>
			<TD>
				<select name="cmbActionStatus">
				<option value="Active" selected >Active</option>
				<option value="Completed" >Completed</option>				
				</select>
			</TD>
		<% }%>
        </tr>
       <% }%>
		<script>
			var calSalesActionDate = new calendar2(document.forms['frmProspectSalesAction'].elements['dfSalesActionDate']);
			calSalesActionDate.year_scroll = true;
			calSalesActionDate.time_comp = false;
        </script>
        <%}%>
        <tr><td colspan="2">&nbsp;</td></tr>

        <tr><td colspan="2" align="center">
        <% if (formAction.startsWith("DISPLAY_DELETE")) { %>
            <a class="button" href="javascript: document.frmProspectSalesAction.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } else { %>
            <a class="button" href="javascript:insertSalesAction();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
        <% } %>
                   &nbsp;&nbsp;
                   <a href="javascript:window.close();" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>

        </td></tr>

        <tr><td colspan="2">&nbsp;</td></tr>

        </table>
        </form>

<%
        }
%>
    </td></tr></table></center>
<%
    }
    catch (Exception e)
    {
%>
    <b>Error:</b><%=e.getMessage()%>
<%
    }
%>
</center>
</body>
</html>