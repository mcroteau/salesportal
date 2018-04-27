<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings, com.randr.webdw.util.*"%>

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
  <div class="wizard-container">
  
  <div class="wizard-content">
  	<form name="formInstallWizard" method="post" action="startup">
  	<input type="hidden" name="formAction" value="SAVE_DATABASE_SETTINGS">
  
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
	    	<li><b>Database</b></li>
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
    	<p class="errorMessage">ERROR&nbsp;:&nbsp;<%=errorMessage%>&nbsp;&nbsp;Please try again
    	<br/><b>Below you will see the actual error</b></p>
    <% } %>
    
    <% if(!Utilities.nullToBlank(successMessage).equals("")) { %>
    	<p class="successMessage">SUCCESS&nbsp;:&nbsp;<%=successMessage%></p>
    <% } %>
    
    <h2>Database Settings</h2>


    <p>Sales Portal requires that postgresql is setup and running.  
    If it is not, please return to setup when postgresql is properly installed. <a href="http://www.postgresql.org/download/" target="_blank" title="Postgres Website">Download PostgreSQL</a>
    </p>
    

    <table>
	    <tr><td></td><td><br/></td></tr>
	    <tr>
	    	<th>Database Host Address</th>
	    	<td><input type="text" size="30" name="dfIPAddress" value="<%=AppSettings.getParm("DB_SYSTEM_IP")%>"/></td>
	    	<td>E.g. localhost or 10.10.10.10</td>
	    </tr>
	    <tr>
	    	<th>Database Name</th>
	    	<td><input type="text" size=30 name="dfDatabaseName" value="<%=AppSettings.getParm("DB_NAME")%>"/></td>
	    </tr>
	    <tr>
	    	<th>*Database Type</th>
	    	<td><select align="left" name="cmbDatabaseType" >
    				<option selected value="POSTGRES">PostgreSQL</option>
    				</select></td>
	    </tr>
	    <tr>
	    	<th>Database Driver</th>
	    	<td><select name="cmbDatabaseDriver">
    				<option selected value="org.postgresql.Driver">org.postgresql.Driver</option>
    				</select></td>
	    </tr>
	    <tr>
	    	<th>Database Username</th>
	    	<td><input type="text" size="30" name="dfUserName" value="<%=AppSettings.getParm("DB_USER")%>"/></td>
	    </tr>
	    <tr>
	    	<th>Database Password</th>
	    	<td><input type="text" size="30" name="dfPassword" value="<%=AppSettings.getParm("DB_PASSWORD")%>"/></td>
	    </tr>
	    <tr><td></td><td><br/></td></tr>
	</table>

  	<p>* Currently Sales Portal is configured to operate with PostgreSQL Server only.  </p>
    
    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  	
      
	<div class="buttons">    
		<input type="button" value="Save & Continue" onclick="javascript:fSubmitContinue();" class="continue"/>
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
