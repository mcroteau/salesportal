<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings, com.randr.webdw.util.*"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Email Info</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
  </head>
  
  <body>  
  
  <div class="wizard-container">
  
  <div class="wizard-content">
  	<h2>Email Definitions</h2>
	<p><b>** S.A. = Sales Action **</b>
	<p><b>SMTP Relay Host :&nbsp;</b>&nbsp;Email URL Address 
		(e.g. smtp.mail.yahoo.com)&nbsp;
    </p>

    <tr>
    	<p><b>SMTP Relay User :&nbsp;</b>&nbsp;
    	A valid email account username
    	 on the email server.</b>&nbsp;
	</p>
        
    <tr>
    	<p><b>SMTP Relay Password :&nbsp;</b>&nbsp;
    	A valid email account password
    	 for specified username on the email server.</b>&nbsp;
	</p>	
        
    <tr>
    	<p><b>Automated Email Sender :&nbsp;</b>&nbsp;
    	The name that you would like to appear
    	 in the from address on all Sales 
    	 Portal sent emails.</b>&nbsp;
    </p>	
        
    <tr>
    	<p><b>S.A. Email Check Interval :&nbsp;</b>&nbsp;
		The interval that Sales Portal
		 checks to see Sales Action mail is ready to be sent</b>&nbsp;
    </p>
    	    
    <tr>
    	<p><b>Send S.A. Email Before Due Date :&nbsp;</b>&nbsp;
    	Send Email reminder this number of days 
    	before the Sales Action is due eg. You select 3 Days, If you
    	have a Sales Action for the 4rd of Dec, a Sales Action 
    	notification will be sent on the 1st of Dec.</b>&nbsp;
	</p>

    <tr>
    	<p><b>S.A. Email Enabled :&nbsp;</b>&nbsp;
    	If set to NO, you will not 
    	receive Sales Action Email Notifications</b>&nbsp;
    </p>
    
    <p><b>SMTP Authentication :&nbsp;</b>&nbsp;
    If your required to enter a password when 
    		loging into your SMPT server, choose option TRUE 
 	</p>

	    
	<div class="buttons">    
		<input type="button" value="Close" onclick="javascript:window.close()" class="skip"/>
	</div>

	</div>			
			
  </div>
  </body>
</html>
