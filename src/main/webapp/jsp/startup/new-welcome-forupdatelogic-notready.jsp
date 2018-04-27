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
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>

  </head>
  
  <body>
  	<div id="contentPane">
 	<a href="http://www.2d-salesportal.com/" target="_blank"  title="Sales Portal Website"><img src="<%= AppSettings.getAppWebPath()%>/graphics/salesportal-banner.jpg" alt="Visit our Sales Portal Website"/></a>
 
    <h1>Salesportal Setup Wizard</h1>
    <hr>    
    <div id="infomessage">This wizard will guide you through the setup steps of salesportal.
    Technical Help is available on the Randr, Inc. <a href="http://www.randrinc.com/jforum"
    title="Sales Portal Support Forum" target="_blank">Forum</a>. And
    documentation is available at our <a href="http://www.2d-salesportal.com"
    target="_blank" title="Sales Portal Website">Sales Portal Site</a>.
    This wizard is for a new installation not an upgrade to an existing sales portal installation.
    See the forum for an upgrade guide. An update wizard is in progress, but has no firm
    release date at this time.</div>
    
    <div id="infoMessage"><a href="javascript:fOpenContact();" title="Contact Us">Have Questions concerning Sales Portal? Contact us</a><br>
    </div>
    
    <div id="left">
    	<h3>This is a New Sales Portal Install...</h3>
    	<input type="button" value="GO!" onclick="javascript:fNewInstall();">
    </div>
    <div id="right">
    	<h3>I am Updating my Sales Portal...</h3>
    	<input type="button" value="GO!" onclick="javascript:fUpdateSalesPortal();">
    </div>

        
    <form name="frmWizardWelcome" action="startup" method="post">
    	<input type="hidden" name="formAction" value="DISPLAY_WIZARD_1_DATABASE_SETTINGS" />
    </form>

  	<form name="frmContactInfo" action="startup" method="post">
  	<input type="hidden" name="formAction" value="CONTACT_SUPPORT" /> 
  	</form>  
 
   	<form name="frmUpdateApplication" action="startup_update">
  	<input type="hidden" name="formAction" value="DISPLAY_WIZARD_1_UPDATE_DATABASE" /> 
  	</form>  
  	
    <div id="progressIndicator" style={display:none}">Processing ... please wait.</div>
  </div> 	    

<script>
 function fUpdateSalesPortal(){
 	document.getElementById('progressIndicator').style.display = 'block';
 	document.frmUpdateApplication.submit();
 }
</script>
    
  </body>
</html>
