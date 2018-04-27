<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Salesportal Setup Wizard</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Randr Salesportal Install, Installation Wizard">
	<meta http-equiv="description" content="Randr Salesportal Portal Installation Wizard">
	<link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>

  </head>
  
  <body>
  
  <div class="wizard-container">
  
  <div class="wizard-content">
  
  	<div class="portal">
 		<a href="http://www.2d-salesportal.com/" target="_blank"  title="Sales Portal Website">
 		<img src="<%= AppSettings.getAppWebPath()%>/graphics/salesportal-banner-small.png" alt="Visit the Randr Website"/>
 		</a>
 	</div>
 	
    <h1>Installation Wizard</h1>
    
    <div class="help"><a href="javascript:fOpenContact();" title="Contact Us">+ help</a></div>
    <div class="header-break"></div>
    
    <div class="ulcontainer">    
		<ul>
	    	<li><b>Welcome</b></li>
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
	    	<li>Finish</li>
	    </ul>
    </div>
    
    <h2>Welcome</h2>
    
    <p>This wizard will guide you through the setup steps of <a href="http://www.2d-salesportal.com"
    target="_blank" title="Sales Portal Website">Sales Portal</a>.
    Technical Help is available on the Randr, Inc. <a href="http://www.randrinc.com/jforum"
    title="Sales Portal Support Forum" target="_blank">Forum</a>. And
    documentation is available at our <a href="http://www.2d-salesportal.com"
    target="_blank" title="Sales Portal Website">Sales Portal Site</a>.
    This wizard is for a new installation not an upgrade to an existing sales portal installation.
    See the forum for an upgrade guide. An update wizard is in progress, but has no firm
    release date at this time.</p>
    
    
    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  	
     
    <div class="buttons">    
    	<form name="formInstallWizard" action="startup" method="post">
    		<input type="hidden" name="formAction" value="DISPLAY_APPLICATION_CONTEXT" />
    		<input type="button" value="Continue with setup ..." onclick="javascript:fSubmitContinue()" class="continue"/>
    	</form>
	</div>

 
    </div>
    
    
  </div> 	    

  <script type="text/javascript">
  	hideShowProgress();
  	function hideShowProgress(){
  		document.getElementById("progressIndicator").style.display = 'none';
  	}
  </script>
  
  
  	
  	<form name="frmContactInfo" action="startup" method="post">
  	<input type="hidden" name="formAction" value="CONTACT_SUPPORT" /> 
  	</form>  
  	
  	
  </body>
</html>
