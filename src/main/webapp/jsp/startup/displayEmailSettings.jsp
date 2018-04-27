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
  <div class="wizard-container">
  
  <div class="wizard-content">
  	<form name="formInstallWizard" method="post" action="startup">
  	<input type="hidden" name="formAction" value="SAVE_EMAIL_SETTINGS">
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
	    	<li><b>Email</b></li>
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
    
    
    <h2>Email Settings</h2>
    
	<p>Sales Portal makes extensive use of Email to keep users informed of upcoming Sales Actions.
		Emails are sent to notify Sales Portal users of upcoming Sales Actions. Lets Configure your Email
		Settings.</p>
	
	<p class="textright"><a href="javascript:fOpenEmailDetailInfo();" title="Email Definitions">+ email definitions</a></p>
	<table>
	    <tr><td></td><td class="right"><br/></td></tr>
	
	    <tr>
	    	<th>SMTP Relay Host</th>
	    	<td><input type="text" name="dfHost" size="30" value="<%=AppSettings.getParm("SMTP_RELAY_HOST")%>"/></td>
	    </tr>	
	        
	    <tr>
	    	<th>SMTP Relay User</th>
	    	<td><input type="text" name="dfUser" size="30" value="<%=AppSettings.getParm("SMTP_USER")%>"/></td>
	    </tr>	
	        
	    <tr>
	    	<th>SMTP Relay Password</th>
	    	<td><input type="text" name="dfPassword" size="30" value="<%=AppSettings.getParm("SMTP_PASSWORD")%>"/></td>
	    </tr>	
	        
	    <tr>
	    	<th>Automated Email Sender</th>
	    	<td><input type="text" name="dfEmailSender" size="30" value="<%=AppSettings.getParm("AUTOMATED_EMAIL_SENDER")%>"/></td>
	    </tr>
	        
	    <tr>
	    	<th>Email Check Interval</th> 
	    	<td><select name="cmbCheckInterval" >
   				<option value="120">2 hours</option>
   				<option selected value="240">4 hours</option>
   				<option value="360">6 hours</option>
   				<option value="480">8 hours</option>
    		</select>
    		</td>
	    </tr>	
	        
	    <tr>
	    	<th>Email Before Due Date</th>
    		<td><select name="cmbDueDate" >
   				<option value="1">1 day</option>
   				<option value="2">2 days</option>
   				<option selected value="3">3 days</option>
   				<option value="4">4 days</option>
   				<option value="5">5 days</option>
   				<option value="6">6 days</option>
   				<option value="7">7 days</option>
   				<option value="8">8 days</option>
   				<option value="9">9 days</option>
   				<option value="10">10 days</option>
   				<option value="11">11 days</option>
   				<option value="12">12 days</option>
   				<option value="13">13 days</option>
   				<option value="14">14 days</option>
   			</select>
   			</td>
		</tr>

	    <tr>
	    	<th>Sales Action Email Enabled</th> 
	    	<td><select name="cmbEmailEnabled">
   				<option selected value="YES">Yes</option>
   				<option value="NO">No</option>
    		</select></td>
	    </tr>	

	    <tr>
	    	<th>SMTP Authentication</th>
	    	<td><select name="cmbAuthentication" >
   				<option value="true" selected >true</option>
   				<option value="false">false</option>
    		</select>
    		</td>
	    </tr>
	    <tr><td></td><td><br/></td></tr>

	    <tr>
	    	<th>*Valid Email Address:&nbsp;</th>
	    	<td><input type="text" name="dfTestEmail" size="30" value=""/></td>
	    </tr>
	    <tr><td></td><td><br/></td></tr>
	</table>

	<p>*In order to test the email settings, please enter in a valid email address below.  After
	   you click continue, you should receive an email from your Sales Portal Application.   
	</p>

    
    <div id="progressIndicator" style={display:none}">Processing ... please wait&nbsp;</div>
      
  		    
	<div class="buttons">    
		<input type="button" value="Save & Continue" onclick="javascript:fSubmitContinue();" class="continue"/>
		<input type="button" value="Skip this step" onclick="javascript:document.formInstallWizard.skip.value = 'TRUE';fSubmitContinue();" class="skip"/>
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
  
  	<form name="frmEmailInfoDetail" action="startup" method="post">
  	<input type="hidden" name="formAction" value="EMAIL_DETAIL_INFO"/> 
  	</form>  
  	
  	<form name="frmContactInfo" action="startup" method="post">
  	<input type="hidden" name="formAction" value="CONTACT_SUPPORT" /> 
  	</form> 
  	 
  </body>
</html>
