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
    
    <title>Salesportal Setup Wizard - Registration</title>
    
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
  	<form name="formInstallWizard" method="post" action="startup">
  	<input type="hidden" name="formAction" value="REGISTER">
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
	    	<li><b>Registration</b></li>
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
    
    
    <h2>Registration</h2>
	
	<p>Register with us for Randr Sales Portal news, updates etc. We will never share or sell your information.</p>
	
	<p>The Randr portals are up on <a href="https://launchpad.net/rentalportal" target="_blank">Canonical's Launchpad (opens in new window)</a> 
	so you can add bugs and any new requests can be added as blueprints.
	 Also you can add yourself to the mailing list to get updates. </p>
	
	<p><b>May we send you email notifications for Sales Portals latest News and updates?</b> : &nbsp;
			    	<select name="cmbEmailUpdate" >
		   				<option value="0">No</option>
		   				<option selected value="1">Yes</option>
		    		</select>
	    		</p>
	<table>
	    <tr><td></td><td><br/></td></tr>
	    <tr>
	    	<th>Name</th>
	    	<td><input type="text" name="dfName" size="40" value=""/></td>
	    </tr>
	    <tr>
	    	<th>Country</th>
	    	<td><input type="text" name="dfCountry" size="40" value=""/></td>
	    </tr>
	    <tr>
	    	<th>Email</th>
	    	<td><input type="text" name="dfEmail" size="40" value=""/></td>
	    </tr>
	    <tr>
	    	<td colspan="2">
		    	
    		</td>
    	</tr>
	    <tr><td></td><td><br/></td></tr>
   	</table>

    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  		    
	<div class="buttons">    
		<input type="button" value="Save & Continue" onclick="javascript:fSubmitContinue();" class="continue"/>
		<input type="button" value="Skip this step" onclick="javascript:document.formInstallWizard.skip.value = 'TRUE';fSubmitContinue();" class="skip"/>
	</div>

    
        
    <% if(!Utilities.nullToBlank(stackTrace).equals("")) { %>
	    <div class="stacktrace">
	    	<h2>Actual Error</h2>
	    	<p>We apologize for the error.</p>
	    	<p>If you feel this is an application error, please copy the error below and email us at 
	    	<a href="mailto:john4@randrinc.com">john4@randrinc.com</a></p>
	    	
	    	<textarea rows="7" cols="60" readonly="true"><%=stackTrace%></textarea>
	    </div>
    <%}%>
    
    
    </form>
    
    
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

