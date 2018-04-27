<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings, com.randr.webdw.util.*"%>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="successMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="databaseConnectionSuccess" class="java.lang.String" scope="request"/>
<jsp:useBean id="databaseConnectionError" class="java.lang.String" scope="request"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Salesportal Setup Wizard </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">
	<link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/startup.css">
	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/startup/startup.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
  </head>

  <body>
  <div id="contentPane">
  <form name="frmWizardDatabaseSettings" action="startup_update">
  <input type="hidden" name="formAction" value="" />

	<a href="http://www.2d-salesportal.com/" target="_blank"  title="Sales Portal Website"><img src="<%=AppSettings.getAppWebPath()%>/graphics/salesportal-banner.jpg" alt="Visit our Sales Portal Website"/></a>

    <h1>Sales Portal Setup Wizard - Database</h1>
    <hr>
    <ol>
    	<li class="selectedStep">Step 1 - Database Setup</li>
    	<li>Step 2 - Company Setup</li>
    	<li>Step 3 - Email Setup</li>
    	<li>Step 4 - Registration (optional)</li>
    </ol>
    
    <% if(!Utilities.nullToBlank(errorMessage).equals("")) { %>
    <div id="errorMessage"><%=errorMessage%></div>
    <% } %>
    
    <div id="infoMessage"><a href="javascript:fOpenContact();" title="Contact Us">Have Questions concerning Sales Portal? Contact us</a><br>
    </div>
	<p>Enter in your current Sales Portal database Settings below</p>
	
	<%if(!databaseConnectionSuccess.equals("")){%>
    	<div id="databaseConnectionSuccess"><img src="<%=AppSettings.getAppWebPath()%>/graphics/successCheck.gif" alt=""/>
    	Your settings are correct.<br/>Proceed below and select the Sales Portal you currently have.</div>
    <%}%>

    <%if(!databaseConnectionError.equals("")){%>
		<div id="databaseConnectionError">These settings are not correct.  Check information and try again.<br/>
		<%= databaseConnectionError%>	
		</div>
	<%}%>
   	<div id="top">
    <fieldset>
    	<legend>1. Database Settings</legend>

	    <div class="form_row">
	    	<label>Database Host Address:&nbsp;</label>
	    	<input type="text" size="15" name="dfIPAddress" value="<%=AppSettings.getParm("DB_SYSTEM_IP")%>"/> 
	    </div>	    
	    <div class="form_row">
	    	<label>Database Name:&nbsp;</label>
	    	<input type="text" size=30 name="dfDatabaseName" value="<%=AppSettings.getParm("DB_NAME")%>"/>
	    </div> 
    	<div class="form_row">
    		<label>Database Type:&nbsp;</label>
    		<select align="left" name="cmbDatabaseType" >
    				<option selected value="POSTGRES">PostgreSQL</option>
    				</select>
		</div>	
    	<div class="form_row">
    		<label>Database Driver:&nbsp;</label>
    		<select name="cmbDatabaseDriver">
    				<option selected value="org.postgresql.Driver">org.postgresql.Driver</option>
    				</select>
    	</div> 	
    	<div class="form_row">
    		<label>User Name:&nbsp;</label>
    		<input type="text" size="30" name="dfUserName" value="<%=AppSettings.getParm("DB_USER")%>"/>
		</div>
    	<div class="form_row">
    		<label>Password:&nbsp;</label>
    		<input type="text" size="30" name="dfPassword" value="<%=AppSettings.getParm("DB_PASSWORD")%>"/>
		</div>
		<br>
		<center>
			<%if(databaseConnectionSuccess.equals("")){%>
		    	<input type="button" value="Check Database Settings" onclick="fCheckDatabaseInfo()"/>
		    <%}%>
		    <script>
		    function fCheckDatabaseInfo(){
		    	document.frmWizardDatabaseSettings.formAction.value = 'CHECK_DATABASE_SETTINGS';
		    	document.frmWizardDatabaseSettings.submit();
		    } 
		    </script>
		    <div id="progressIndicator" style={display:none}">Processing ... please wait.</div>
		</center>
	</fieldset>	
	</div>
<%if(!databaseConnectionSuccess.equals("")){%>
    <div id="bottom">
    <fieldset>
    	<legend>2. Select Sales Portal you currently have</legend>

	    <div class="form_row">
	    	<label>Sales Portal 1.0&nbsp;:&nbsp;</label>
	    	<input type="radio" name="release" value="1"/> 
	    </div>	    
	    <div class="form_row">
	    	<label>Sales Portal 1.1&nbsp;:&nbsp;</label>
	    	<input type="radio" name="release" value="2"/>
	    </div> 
    	<div class="form_row">
    		<label>Sales Portal 1.1.1&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="3" >				
		</div>	
    	<div class="form_row">
    		<label>Sales Portal 1.1.2&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="4" >	
    	</div> 	
    	<div class="form_row">
    		<label>Sales Portal 1.1.3&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="5" >	
		</div>
    	<div class="form_row">
    		<label>Sales Portal 1.1.4&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="6" >	
		</div>
		<div class="form_row">
    		<label>Sales Portal 1.2.0&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="7" >	
		</div>
		<div class="form_row">
    		<label>Sales Portal 1.2.3&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="8" >	
		</div>
		<div class="form_row">
    		<label>Sales Portal 1.2.5&nbsp;:&nbsp;</label>
    		<input type="radio" name="release" value="9">	
		</div>
		</fieldset>
	</div>
<%}%>
		<br>
		<center>
			<input type="button" value="Check Database Settings" onclick="fUpdateDatabase()"/>
		    <div id="progressIndicator" style={display:none}">Processing ... please wait.</div>
		    <script>
		    function fUpdateDatabase(){
		    	document.frmWizardDatabaseSettings.formAction.value = 'UPDATE_DATABASE';
		    	document.frmWizardDatabaseSettings.submit();
		    } 
		    </script>
		</center>

	</form>
  	
  	<form name="frmContactInfo" action="startup" method="post">
  	<input type="hidden" name="formAction" value="CONTACT_SUPPORT" /> 
  	</form> 
  	 
  	</div>
  </body>
</html>
