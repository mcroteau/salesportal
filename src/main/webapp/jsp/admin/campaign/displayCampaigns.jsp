<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.campaign.CampaignDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="campaignView" class="com.randr.webdw.campaign.CampaignView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Campaigns</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/campaign/displayCampaigns.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
     </head>

     <body>
	 <center>
<h1>Display/Update Campaigns</h1>

     <form name="frmCampaign" method="post" action="campaign">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfCampaignId" value="">

	 </form>

	 <table>
	 <tr><td align="center">

     <p align="right">
	    <a class="button" href="javascript:fOnInsert();">&nbsp; &nbsp;Add new Campaign&nbsp; &nbsp;</a><hr><br>
     </p>


<%
	if (request.getAttribute("modelException") != null)
	{
		ModelException modelException = (ModelException) request.getAttribute("modelException");
%>
		<p class="exception"><%=modelException.getMessage()%></p>
<%
	}
	else
	{
%>
		<table class="report" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th>Id</th>
			<th>Campaign Name</th>
			<th>Display for<br>Salesmen?</th>
			<th>Description</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
<%
		for (int i = 0; i < campaignView.getElements().size(); i++)
	  	{
			CampaignDetails campaignDetails = (CampaignDetails) campaignView.getElements().elementAt(i);
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=campaignDetails.getCampaignId()%></th>
				<td nowrap align="left"><%=campaignDetails.getCampaign()%></td>
				<td nowrap align="left">
				<%if(campaignDetails.getDisplayForSalesmen().equals(CampaignDetails.YES_DISPLAY_FOR_SALESMEN)){ %>
					Yes
				<%}else{ %>
					No
				<% } %>
				</td>
				<td><textarea readonly cols="30" rows="3"><%=Utilities.nullToBlank(campaignDetails.getDescription())%></textarea>
				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=campaignDetails.getCampaignId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Campaign <%=campaignDetails.getCampaign()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=campaignDetails.getCampaignId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
			</tr>
<%
		}
%>
		</table>
<%
	}
%>
	 </td></tr></table>
	 
	 <h3> ** Click Update to Add Sales Actions to Campaigns **</h3>
	 </center>
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
