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
 
  <body>
  <div class="wizard-container">
  
  <div class="wizard-content">
  	<form name="formInstallWizard" method="post" action="startup">
  	<input type="hidden" name="formAction" value="SAVE_COMPANY_INFO">
  
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
	    	<li><b>Company</b></li>
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
    
    
    <h2>Company Information</h2>
    
    <p>To customize your Sales Portal to fit your business, please enter your Company Information below.</p>


	<table>
	    <tr><td></td><td><br/></td></tr>

	    <tr>
    		<th>Company Name</th>
    		<td><input type="text" name="dfCompanyName" size="30" value="<%=AppSettings.getParm("COMPANY_NAME")%>"/></td>
    	</tr>
    	
	    <tr>    	
    		<th>Address&nbsp;</th>
    		<td><input type="text" name="dfAddress" size="30" value="<%=AppSettings.getParm("COMPANY_ADDRESS")%>"/></td>
    	</tr>
    	
	    <tr>
    		<th>City</th>
    		<td><input type="text" name="dfCity" size="30" value="<%=AppSettings.getParm("COMPANY_CITY")%>"/></td>
    	</tr>
    	
	    <tr>    	
    		<th>State</th>
    		<td><input type="text" name="dfState" size="30" value="<%=AppSettings.getParm("COMPANY_STATE")%>"/></td>
    	</tr>
    	
	    <tr>
    		<th>State Abbreviation</th>
    		<td><input type="text" name="dfStateAbb" size="30" value="<%=AppSettings.getParm("COMPANY_STATE_ABREV")%>"/></td>
    	</tr>
    	
	    <tr>
    		<th>Zip</th>
    		<td><input type="text" name="dfZip" size="30" value="<%=AppSettings.getParm("COMPANY_ZIP")%>"/></td>
    	</tr>
    	
	    <tr>
    		<th>Phone</th>
    		<td><input type="text" name="dfPhone" size="30" value="<%=AppSettings.getParm("COMPANY_PHONE")%>"/></td>
    	</tr>
    	
	    <tr>
    		<th>Fax</th>
    		<td><input type="text" name="dfFax" size="30" value="<%=AppSettings.getParm("COMPANY_FAX")%>"/></td>
    	</tr>
    	
	    <tr>
    		<th>Contact Email</th>
    		<td><input type="text" name="dfEmail" size="30" value="<%=AppSettings.getParm("CONTACT_EMAIL")%>"/></td>
    	</tr>
	    <tr><td></td><td><br/></td></tr>
  	
	</table>

    
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

