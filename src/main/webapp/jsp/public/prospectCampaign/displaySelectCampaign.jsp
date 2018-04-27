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
<jsp:useBean id="campaignView" class="com.randr.webdw.campaign.CampaignView" scope="request" />
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session" />
<html>

<head>
        <title>Select Campaign</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">

</head>

<body>
<center>

<% try { %>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospectCampaign/displayInsertUpdateProspectCampaign.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

    <center><table><tr><td align="center">

        <h1>Select Campaign</h1>
        <hr><br>
		<form name="frmCampaign" method="post" action="prospect">
			<input type="hidden" name="formAction" value="DISPLAY_INSERT_PROSPECT_CAMPAIGN" />
			<input type="hidden" name="oldFormAction" value="<%=request.getParameter("oldFormAction")%>">
			<input type="hidden" name="oldAction" value="<%=request.getParameter("oldAction")%>">

		<input type=hidden name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
			<select name="cmbCampaign" >
	            	<option value="">Please Select ...</option>
	                	<%=CollectionUtilities.getDropDownOptions(campaignView.getElements(),
	                		"getCampaignId", "getCampaign", "") %>
	        </select>
	        <input type="submit" name="btnSubmit" value="Submit" />	        
        </form>

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