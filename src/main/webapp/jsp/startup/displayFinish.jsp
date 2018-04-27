<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.*"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Salesportal Setup Wizard - Finish</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
  </head>
  
  <body>
  <% 
   	int hours = 0;
    hours = Integer.parseInt(AppSettings.getParm("SALES_ACTION_EMAIL_SCHEDULE_MINUTES")) / 60;
  %>
  <div class="wizard-container">
  
  <div class="wizard-content">
  	<form name="formInstallWizard" method="post" action="startup">
  	<input type="hidden" name="formAction" value="LOAD_APP_LOGIN">
  	<input type="hidden" name="skip" value="">
  
  	<div class="portal">
 		<a href="http://www.2d-salesportal.com/" target="_blank"  title="Sales Portal Website">
 		<img src="<%= AppSettings.getAppWebPath()%>/graphics/salesportal-banner-small.png" alt="Visit our Sales Portal Website"/>
 		</a>
 	</div>
 	
    <h1>Installation Wizard</h1>
    
    <div class="help"><a href="javascript:fOpenContact();" title="Contact Us">+ help</a></div>
    <div class="header-break"></div>
    
    <div class="ulcontainer">    
		<ul>
	    	<li>Welcome</li>
	    	<li>&nbsp;-&nbsp;<li>
	    	<li>Application</li>
	    	<li>&nbsp;-&nbsp;<li>
	    	<li>Database</li>
	    	<li>&nbsp;-&nbsp;<li>
	    	<li>Company</li>
	    	<li>&nbsp;-&nbsp;<li>
	    	<li>Email</li>
	    	<li>&nbsp;-&nbsp;<li>
	    	<li>Registration</li>
	    	<li>&nbsp;-&nbsp;<li>
	    	<li><b>Finish</b></li>
	    </ul>
    </div>
    
    
    
    
    <h2>Finish</h2>
    
    <p class="successMessage">You have successfully completed the Installation Wizard.</p>
    <p>You can check your settings below or click "Continue" to proceed to the Log In screen.  Default 
    Login information for first time install is:</p>
    <p><b>username:</b>&nbsp;&nbsp;&nbsp;admin<br/>
    <b>password:</b>&nbsp;&nbsp;&nbsp;admin</p>
    
    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  	    
    <div class="buttons">    
		<input type="button" value="Continue to Log In" onclick="javascript:fSubmitContinue();" class="continue"/>
	</div>
    
    <h5>Application Settings</h5>
	<table class="finish">
	    <tr><td></td><td><br/></td></tr>
   		<tr>
   			<th>Application Context :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("APPLICATION_CONTEXT"))%></td>	
   		</tr>
	    <tr><td></td><td><br/></td></tr>
   	</table>
   		
    <h5>Database Settings</h5>
	<table class="finish">
	    <tr><td></td><td><br/></td></tr>
   		<tr>
   			<th>Database Type :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("DB_TYPE"))%></td>	
   		</tr>
   		<tr>
   			<th>Database Driver :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("DB_DRIVER"))%></td>
		</tr>
   		<tr>
   			<th>Database Name :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("DB_NAME"))%></td>
		</tr>
   		<tr>
   			<th>Database IP Address :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("DB_SYSTEM_IP"))%></td>
		</tr>
   		<tr>
   			<th>Database User Name :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("DB_USER"))%></td>
		</tr>
   		<tr>
   			<th>Database Password :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("DB_PASSWORD"))%></td>
		</tr>
	    <tr><td></td><td><br/></td></tr>
	</table>
	
	<h5>Company Information</h5>
	<table class="finish">
	    <tr><td></td><td><br/></td></tr>
   		<tr>
   			<th>Company Name :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_NAME"))%></td>
		</tr>
   		<tr>
   			<th>Company Address :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_ADDRESS"))%></td>
		</tr>
   		<tr>
   			<th>Company City :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_CITY"))%></td>
		</tr>
   		<tr>
   			<th>Company State :&nbsp;</th>
			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_STATE"))%></td>
		</tr>
   		<tr>
   			<th>Company State Abb :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_STATE_ABREV"))%></td>
		</tr>
   		<tr>
   			<th>Company Zip :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_ZIP"))%></td>
		</tr>
   		<tr>
   			<th>Company Phone :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_PHONE"))%></td>
		</tr>
   		<tr>
   			<th>Company Fax :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("COMPANY_FAX"))%></td>
		</tr>
   		<tr>
   			<th>Contact Email :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("CONTACT_EMAIL"))%></td>			    			
		</tr>
	    <tr><td></td><td><br/></td></tr>
	</table>    
	
	<h5>Email Settings</h5>
	<table class="finish">
	    <tr><td></td><td><br/></td></tr>
   		<tr>
   			<th>SMTP Relay Host :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("SMTP_RELAY_HOST"))%></td>
   		</tr>			
   		<tr>
   			<th>SMTP Relay User :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("SMTP_USER"))%></td>
   		</tr>			
   		<tr>
   			<th>SMTP Relay Password :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("SMTP_PASSWORD"))%></td>
   		</tr>						
   		<tr>
   			<th>S.A. Email Check Interval :&nbsp;</th>
   			<td><%=hours %>&nbsp;Hours</td>										 
   		</tr>
   					
   		<tr>
   			<th>Email Before Due Date :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("SALES_ACTION_EMAIL_BEFORE_DAYS"))%>&nbsp;Days</td>										 
   		</tr>			
   		<tr>
   			<th>Automated Email Sender :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("AUTOMATED_EMAIL_SENDER"))%></td>						 
   		</tr>
   		<tr>
   			<th>S.A. Email Enabled :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("SALES_ACTION_EMAIL_ENABLED"))%></td>
   		</tr>
   		<tr>
   			<th>SMTP Authentication :&nbsp;</th>
   			<td><%=Utilities.nullToDash(AppSettings.getParm("SMTP_AUTHENTICATION"))%></td>
		</tr>
	    <tr><td></td><td><br/></td></tr>
	</table>

    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  	

	<div class="buttons">    
		<input type="button" value="Continue to Log In" onclick="javascript:fSubmitContinue();" class="continue"/>
	</div>

    </form>

    
    </div>
    

    
  </div> 	    

  <script type="text/javascript">
  	hideShowProgress();
  	function hideShowProgress(){
  		document.getElementById("progressIndicator").style.display = 'none';
  	}
  </script>
  
  	<form name="frmEmailInfoDetail" action="startup" method="post">
  	<input type="hidden" name="formAction" value="EMAIL_DETAIL_INFO"/> 
  	</form>  
  	
  	<form name="frmContactInfo" action="startup" method="post">
  	<input type="hidden" name="formAction" value="CONTACT_SUPPORT" /> 
  	</form> 
  	 
</html>

