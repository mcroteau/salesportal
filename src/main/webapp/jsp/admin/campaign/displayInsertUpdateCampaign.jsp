<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.*,
				java.math.BigDecimal,
				java.util.List,
				com.randr.webdw.campaign.CampaignDetails,
				com.randr.webdw.campaignSalesAction.CampaignSalesActionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="campaignData" class="com.randr.webdw.campaign.CampaignData" scope="session"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<% try { 
	String actionDescription = "Update";
	String newFormAction="UPDATE";

	if (formAction.equals("DISPLAY_INSERT")) {
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
%>
     <html>
     <head>
     	<title><%=actionDescription%> Campaign</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>?Timestamp=String.valueOf((new java.util.Date()).getTime())">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/campaign/displayInsertUpdateCampaign.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
		
<script language="JavaScript">
var rows = document.getElementById('dataRollOver').rows;
for (i=0;i<rows.length;i++) {
	rows[i].onmouseover = highlight;
	rows[i].onmouseout = dehighlight;
}


function highlight() { 
	this.className = "highlight"; 
}
function dehighlight() {
 	this.className = ""; 
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}
</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Campaign</h1>
     <hr><br><br>
     <a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>
     <div id="moreInfo" style=display:none>
     	
     		<h2>You are now updating your Campaign</h2>
     		  
     		<p>In this screen you will be able to add new Sales Actions and order
     		them by the sequence in which they should be completed.</p>
     		  
     		<p>To successfully add/order Sales Actions to you campaign
     		follow these steps:</p>
     		
     		<ol class="moreInfo">
     			<li>Make sure you have created all Sales Actions for this Campaign</li>
     			<li>Select the Sales Action 1 by 1 from the "Select Action to Add:" drop down box</li>
     			<li>Click the + sign to the right of the drop down box to add it to the Campaign</li>
     			<li>Click Arrows Up or Down to change the sequence in which the Sales Actions should be Completed</li>
     			<li>Days applies to automated Campaigns.  When the campaign is added to the prospect, the sales action date will<br>
     			automatically be set based on the # of days entered.  So if Sales Action#1 has 2 days and Sales Action #2 has 5 <br>
     			days.  The first Sales Action would be scheduled for today +2 days and the second Sales Action would be <br>
     			scheduled for 5 days after the first Sales Action.  The Default is in the Configuration - Automated Email Options.</li>
     			<li>Display for Salesmen controls whether this campaign will display in the list of campaigns for a salesman to select</li>
     			<li>To Save changes, Click Update</li>
     		
     		</ol>
     		
     		<p>* If you have done this, then all changes will be saved.  If you did not click Update, 
     		all the work you did adding/ordering Sales Actions will be lost.</p>

     		<p>* If you have previously created a Campaign, then assigned the Campaign to a Prospect, you will not be able
     		to update the Campaign until you have deleted it from the corresponding Prospects. </p>
     		
     		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
     	
     </div>
     <br><br>
     <form name="frmCampaign" method="post" action="campaign" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">
     <input type="hidden" name="row" value="" />
<%
	if (request.getAttribute("modelException") != null){
		ModelException modelException = (ModelException) request.getAttribute("modelException");
%>
		<p class="exception"><%=modelException.getMessage()%></p>
<%
	}else{
%>

		<center>                <a class="button" href="javascript: if(fProcessForm()){document.frmCampaign.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
		</center>
		<br/>
		<br/>
		<span id="textRight">
		<a href="javascript:fOpenHelp('<%=AppSettings.getAppCompleteWebPath(request)%>webpages/help_files/campaign/insertUpdateCampaignHelp.html','730','750')" >add'l help</a>
		</span>
		<table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
		<%
		if (formAction.equals("DISPLAY_UPDATE") || formAction.equals("ADD") || formAction.equals("REMOVE")
			|| formAction.equals("MOVE_UP") || formAction.equals("MOVE_DOWN"))
		{
		%>
			<tr>
				<th align="right">Id:</th>
				<td>
 					<%=campaignData.getCampaignDetails().getCampaignId()%>
					<input type="hidden" name="dfCampaignId" value="<%=campaignData.getCampaignDetails().getCampaignId()%>">
				</td>
			</tr>
		<%
		}
		%>

		<tr>
			<th align="right">Campaign Name:</th>
			<td><input type="text" name="dfCampaign" size="40" maxlength="80" value="<%=Utilities.nullToBlank(campaignData.getCampaignDetails().getCampaign())%>"></td>
		</tr>
		<tr>
			<th align="right">Display for Salesmen?:</th>
			<td>
                <select name="dfDisplayForSalesmen">
                 <% if (Utilities.nullToBlank(campaignData.getCampaignDetails().getDisplayForSalesmen()).compareTo("")==0 ||
                 			Utilities.nullToZero(campaignData.getCampaignDetails().getDisplayForSalesmen()).equals(CampaignDetails.YES_DISPLAY_FOR_SALESMEN)) {%>
                    	<option value="1" selected>YES - Include in Sales Action List
                    	<option value="0">NO - Do Not Include in Sales Action List
                     <% }else{ %>
                     	<option value="1">YES - Include in Sales Action List
                    	<option value="0" selected>NO - Do Not Include in Sales Action List
                <% }  %>
                </select></td>
		</tr>
		

        <tr><th  align="right">Description:</th>
		

			<td>

                <textarea name="dfDescription"
                    onkeypress="textCounter(this,this.form.counter,200);"
                    cols="37" rows="4"><%=Utilities.nullToBlank(campaignData.getCampaignDetails().getDescription())%></textarea>
                <br>
                <input id="flat"
	            type="text" name="counter" maxlength="4" size="4" value="<%=new BigDecimal(200).subtract(new BigDecimal(Utilities.nullToBlank(campaignData.getCampaignDetails().getDescription()).length()))%>"
                onfocus = "this.blur();"> characters remaining
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>


		</table>
<%
	}
%>

		<%
		if (formAction.equals("DISPLAY_UPDATE")|| formAction.equals("ADD") 
			|| formAction.equals("REMOVE") || formAction.equals("MOVE_UP")
			|| formAction.equals("MOVE_DOWN"))
		{
		%>
		<br>
		<br>
	<table class="form" id="dataRollOver" width="550px">
       	<tr>
       		<th>Id</th>
			<th align="left">Action Name</th>
            <th>Action Date<br>Is Mandatory</th>
            <th># of Days<br>After Prior Action</th>
            <th>Move<br>Up</th>
            <th>Move<br>Down</th>
            <th>Remove</th>
         </tr>  
          <%CampaignSalesActionDetails campaignSalesActionDetails; %>
      <% for (int i = 0; i < campaignData.getCampaignSalesActionList().size(); i++) {
			 campaignSalesActionDetails = (CampaignSalesActionDetails) campaignData.getCampaignSalesActionList().get(i); %>
			
				<tr class="row">
					<td class="row" id="salesActionId_<%=i%>"><%=campaignSalesActionDetails.getSalesActionId()%></td>
					<td class="row" id="salesActionDescription_<%=i%>"><%=campaignSalesActionDetails.getSalesActionDescription()%></td>
				 <td class="row" align="center" id="salesActionMandatoryDate_<%=i%>"><% if (campaignSalesActionDetails.getSalesActionMandatoryDate().equals(new BigDecimal(1))) {%>yes<% } else { %>no<% } %></td>
					<td class="row" align="center"id="salesActionSendEmailDays_<%=i%>"><%=campaignSalesActionDetails.getSendEmailDays()%></td>
					<td class="rowWithButton" align="center"><% if(i != 0) {%><img onclick="fMoveUp(<%=i%>);" src="<%=AppSettings.getAppWebPath()%>/graphics/icons/arrow-up.gif" /><% } %></td>
						<td class="rowWithButton" align="center"><%if(i != campaignData.getCampaignSalesActionList().size() - 1) { %><img onclick="fMoveDown(<%=i%>);" src="<%=AppSettings.getAppWebPath()%>/graphics/icons/arrow-down.gif" /><% } %></td>
	
						<td class="rowWithButton" align="center"><img onclick="fDelete(<%=i%>);" src="<%=AppSettings.getAppWebPath()%>graphics/icons/remove.gif" /></td>
				</tr>
			
	<% 	}%>
	</table><br><br>
	<table class="form">
		<tr>
		<td>Select Action to Add: Days:</td>
		<td><input type="text" name="dfDays" size="5" maxlength="5" value=""></td>
		<td>Action:</td>
		 <td nowrap align="left">
	            	<select name="cmbSalesAction" >
	            	<option value="">-</option>
	                	<%=CollectionUtilities.getDropDownOptions(campaignData.getSalesActionView().getElements(),
	                		"getActionId", "getAction", "") %>
	                </select></td>
			<td class="rowWithButton"><img onclick="fAdd('');" src="<%=AppSettings.getAppWebPath()%>graphics/icons/add.gif" /></td>
		</tr>
		</table>
		<% 	}%>
		<%if(formAction.equals("DISPLAY_UPDATE")|| formAction.equals("ADD") 
			|| formAction.equals("REMOVE") || formAction.equals("MOVE_UP")
			|| formAction.equals("MOVE_DOWN")){ %>
			<h3> ** Click Update For Your Changes To Take Effect **</h3>
			<p>
			<br> Days only applies to automated Campaigns.  If blank, configuration default value will be used.
			<br> if you want the first Sales Action to be scheduled immediately, set the days to 0. </p>
		<%}else{ %>
			<h3> ** To Add Sales Actions, Go to Update Screen After You Create a Campaign ** </h3>
		<%} %>
		
	 </form>
	 </center>

     <script>
        document.frmCampaign.dfCampaign.focus();
     </script>
     </body>
     </html>

<%
	}
	catch (Exception e)
	{
%>
     Error: <b><%=e.getMessage()%></b>
<%
		e.printStackTrace();
    }
	finally
	{

   	} %>
