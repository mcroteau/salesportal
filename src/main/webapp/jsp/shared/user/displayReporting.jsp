<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
    <head>

        <title>Information Portal for Sales Portal</title>
        <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
    </head>
<body>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Calendar Select", "","", "")%>
<% try { %>

<img src="<%=AppSettings.getAppWebPath()%>graphics/infoportal-banner.jpg">

<h2>Information Portal (Info Portal) is Randr Inc's real time reporting tool. </h2>


<div>
	<%if(AppSettings.getParm("INFOPORTAL_LINK_ENABLED").equals("YES")){ %>
	<span id="installstatusyes">* You have Info Portal set as installed</span>
		<h2><a href="<%=AppSettings.getParm("INFOPORTAL_LINK")%>" target="_blank">Info Portal Login</a></h2>
		
		<div id="infoportal">
			<center><p><b>For information on Info Portal and documentation visit 
			<a href="http://www.randrinc.com" title="Visit Information Portal" target="_blank">www.randrinc.com</a></b></p>	</center>
		</div>
			
	<%}else{ %>
	
	
	<div id="infoportal">
		<center><span id="installstatusno">* You currently do not have Info portal installed/configured. </span></center> 
		<ul class="infoportal">
			<li><a href="http://www.2d-infoportal.com" target="_blank" title="Information Portal Website">Visit Website</a></li>
			<li>Need to Download? Click <a href="http://sourceforge.net/projects/infoportal" target="_blank">here.</a>&nbsp;
			You will be taken to the Informaion Portal project page on Sourceforge.net where you can download documents and application packages.
			The Application packages include documentation and war files.</li>
		</ul>
	</div>
	
	<div id="aboutInfoportal">

		<h1>About Info Portal:</h1>

		<p>Information Portal is a web application used for real time key business 
		indicators presented in a centralized web browser with drill down capabilities.</p>

		<p>We currently have <b>Information Portal for Sales Portal available for download. </b> 
		Once you have it downloaded and installed, make sure your Sales Portal is 
		configured correctly by going to <b>"Configuration"</b> on the menu.</p>

		<a href="<%=AppSettings.getAppWebPath()%>admin/config?&formAction=DISPLAY_UPDATE_APPLICATION" target="_blank">Click Here or side menu for Configuration Settings</a>

		<p>At the bottom of the Application Settings page, you will see two Information Portal options:	</p>	
		<ul>
			<li>Linked Information Portal Installed: (check box) </li>
			<li>Linked Information Portal Address: (http://yourhost:8080/ipsp_yourname)</li>
		</ul>
		<p>Save settings.  Click on <b>"Reporting"</b> again and you should see a direct link to your Info Portal. </p>

	</div>
	

	
<%} %>
	<span id="havingTrouble" ><a href="javascript: showTroubleShoot();" title="Shows Troubleshooting"> ++ Having trouble?</a></span>
	<div id="havingProblems" style={display:none}>
		<span id="problem">Q. Not seeing the Info Portal link?</span><br/>
		<span id="solution">A. Make sure application settings are correct <b><a href="<%=AppSettings.getAppWebPath()%>admin/config?&formAction=DISPLAY_UPDATE_APPLICATION" target="_blank">direct link</a></b>
			at the bottom of page change these settings:</span><br/><br/>
		<ul>
			<li>Linked Information Portal Installed: (check box) </li>
			<li>Linked Information Portal Address: (http://yourhost:8080/ipsp_yourname)</li>
		</ul>

		<p>Go to Configuration to check these settings.  Click <a href="<%=AppSettings.getAppWebPath()%>admin/config?&formAction=DISPLAY_UPDATE_APPLICATION" target="_blank">here</a>
			
		<br/>
		<br/>
		<span id="problem">Q.  Still having problems with link to Info Portal?</span><br/>
		<span id="solution">A.  Make sure you have the right port number.  :8180 for Ubuntu(tomcat 5.5 and lower), :8080 for Tomcat6 and redhat
			eg.  http://localhost:8080/salesportal</span><br/>
		
		<br/>
		<br/>
		<span id="problem">Q.  Still having problems seeing link to Info Portal?</span><br/>
		<span id="solution">A.  Try restarting application, if this does not work, please contact us</span><br/>

		<ul>
			<li>email: john4@randrinc.com</li>
			<li>phone: 1-951-369-3427</li>
		</ul>


	</div>
	
	<form name="frmCheckConfigurations" action="/admin/config" method="post">
      <input type="hidden" name="formAction" value="DISPLAY_UPDATE_APPLICATION" />
      
	</form>
	<script>
    	function showTroubleShoot(){
    		if(document.getElementById('havingProblems').style.display == 'block'){
    			document.getElementById('havingProblems').style.display = 'none';
    		}else{
    			document.getElementById('havingProblems').style.display = 'block';
    		}
    	}
    </script>
<%=AppSettings.getFooter(userProfile)%>        
<% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>


</body>
</html>