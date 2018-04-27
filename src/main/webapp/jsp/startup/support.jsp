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
    
    <title>Contact Us</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
  </head>
  
  <body>
  <div class="support">

    <h2>Contact Us</h2>
    <hr>
    <ul>
    	<li class="contact">Phone: (951) 369-3427</li>
    	<li class="contact">Email: <A HREF="mailto:john4@randrinc.com">john4@randrinc.com</A></li>
    	<li class="contact">Vist our <a href="http://www.randrinc.com/jforum" target="_blank">Support Forum.</a></li>
    	<li class="contact">Vist our <a href="http://www.2d-salesportal.com" target="_blank">Website.</a></li>
    </ul>
    
	    
	<div class="buttons">    
		<input type="button" value="Close" onclick="javascript:window.close()" class="skip"/>
	</div>
  </div>
  </body>
</html>
