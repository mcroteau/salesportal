<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.util.*,
				 com.randr.webdw.AppSettings"%>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="successMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="stackTrace" class="java.lang.String" scope="request"/>

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
	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>

	</head>
  
  <body>
  <div class="wizard-container">
  
  <div class="wizard-content">
  	<form name="formInstallWizard" method="post" action="startup">
  	<input type="hidden" name="formAction" value="SAVE_APPLICATION_CONTEXT">
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
	    	<li><b>Application</b></li>
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
    
    <% if(!Utilities.nullToBlank(errorMessage).equals("")) { %>
    	<p class="errorMessage"><%=errorMessage%>&nbsp;&nbsp;ACTUAL ERROR BELOW</p>
    <% } %>
    
    <% if(!Utilities.nullToBlank(successMessage).equals("")) { %>
    	<p class="successMessage"><%=successMessage%></p>
    <% } %>
    
    <h2>Application Context</h2>

     <p>In this section you will need to specify the <b>Port #</b> and the <b>.war</b> file name. </p>
    
    <p>Default settings are set for an Ubuntu Install (Tomcat6) which requires the Port to be set to <b>8080</b> and the war file
    not changed so the Application Context will be <b>salesportal</b>.  </p>
    
    <p>If you have not changed the name of the salesportal.war file and are installing on Ubuntu, just press Continue.
    However, if you are installing on a Red Hat machine or you changed the name of the salesportal.war, 
    then please make the necessary changes below.</p>    
    
    <p><b>Example&nbsp;:&nbsp;</b>Lets assume you changed the name of the <b>salesportal.war</b> to <b>marketing.war</b> .Enter
    <b>marketing</b> below</p>


    <p>* Default value is <b>salesportal</b></p>
    
	<table>
	    <tr><td></td><td><br/></td></tr>
	    <tr>
			<th>Port #</th>
			<td><input value="8080" name="dfPortNumber" size="5"></td>
		</tr>
		<tr>
			<th>Application Context</th>
			<td><input value="salesportal" name="dfApplicationContext" size="15"></td>
		</tr>
	    <tr><td></td><td><br/></td></tr>
	</table>

   <!-- <p>* If the name of the war file is different than what's on the screen, change the name
    to the name of the war file in order to resolve any future conflicts.</p>
      -->
    
    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  	
	<div class="buttons">    
		<input type="button" value="Save & Continue" onclick="javascript:fSubmitContinue();" class="continue"/>
		<!-- <input type="button" value="Skip this step" onclick="javascript:document.formInstallWizard.skip.value = 'TRUE';fSubmitContinue();" class="skip"/> -->
	</div>

    </form>
     	

    <% if(!Utilities.nullToBlank(stackTrace).equals("")) { %>
	    <div class="stacktrace">
	    	<h2>Actual Error</h2>
	    	<p>We apologize for the error.</p>
	    	<p>If you feel this is an application error, please copy the error below and email us at 
	    	<a href="mailto:john4@randrinc.com">john4@randrinc.com</a></p>
	    	
	    	<textarea rows="7" cols="60" readonly="true"><%=stackTrace%></textarea>
	    </div>
    <%}%>
    
    </div>
    
    
  </div> 	    

  <script type="text/javascript">
  	function fSkip(){
  		
  		document.frmInstallWizard.submit();
  	}
  	
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

