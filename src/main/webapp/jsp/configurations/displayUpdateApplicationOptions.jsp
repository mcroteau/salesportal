<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings" %>
<%@ page import="java.net.URLEncoder" %>

<%! private String getCheckedOption(String optionName) { 
      if (AppSettings.getParm(optionName).equals("YES")) { 
        return "checked";
      } else {
        return "";
      }
    } %>

<% try{ %>
    <head>
    <title>Application Options - Configurations</title>
    <style>
        body, table, form, input, select {font-family: arial,verdana, tahoma; font-size:10pt}
        h1{ font-size: 12pt; color: green}
        hr {height: 1px;}
        hr.section {color: blue}
    </style>
    <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js"> </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/options.js"> </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/applicationOptions.js"> </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/openHTMLEditor.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>
    </head>
    <body>
     <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
     <input type=hidden name="formAction" value="UPDATE_APPLICATION_OPTIONS">

     <table border="0" align="center"> <tr><td>
     <h1 align="center">Application Options</h1>

     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidAppConfig()">


     <table border="0" align="center">
      <tr>
      <th colspan="2"><hr class="section"><br><br><font color=blue>Company Info</font><br><br></th>
      </tr>
      <tr>
        <th align="right" valign="top" width="50%">Company name:</th>
        <td><input name="OPT_COMPANY_NAME" value="<%=AppSettings.getParm("COMPANY_NAME")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th align="right" valign="top">Phone:</th>
        <td><input name="OPT_COMPANY_PHONE" value="<%=AppSettings.getParm("COMPANY_PHONE")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th align="right" valign="top">Address:</th>
        <td><input name="OPT_COMPANY_ADDRESS" value="<%=AppSettings.getParm("COMPANY_ADDRESS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th align="right" valign="top">State:</th>
        <td><input name="OPT_COMPANY_STATE" value="<%=AppSettings.getParm("COMPANY_STATE")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th align="right" valign="top">State abreviation:</th>
        <td><input name="OPT_COMPANY_STATE_ABREV" value="<%=AppSettings.getParm("COMPANY_STATE_ABREV")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th align="right" valign="top">City:</th>
        <td><input name="OPT_COMPANY_CITY" value="<%=AppSettings.getParm("COMPANY_CITY")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th align="right" valign="top">Zip:</th>
        <td><input name="OPT_COMPANY_ZIP" value="<%=AppSettings.getParm("COMPANY_ZIP")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <th align="right" valign="top">Fax:</th>
        <td><input name="OPT_COMPANY_FAX" value="<%=AppSettings.getParm("COMPANY_FAX")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
  	 <tr>
      <th colspan="2"><hr class="section"><br><br><font color=blue>Email Response Settings</font><br><br></th>
      </tr>
      <tr>
       
        <td colspan="2" align="center">Don't make this too long, there is a 256 character limit including all email addresses.</td>
      </tr>
  	 <tr>
        <th align="right" valign="top">Email CC:</th>
        <td><input name="OPT_CC_EMAILS" value="<%=AppSettings.getParm("CC_EMAILS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <th align="right" valign="top">Email Blind CC:</th>
        <td><input name="OPT_BCC_EMAILS" value="<%=AppSettings.getParm("BCC_EMAILS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <th align="right" valign="top">Include Customer Company in Subject</th>
         <td>
                   <input type="checkbox" name="CB_INCLUDE_CONTACT_NAME_IN_SUBJECT" value="YES" <%=getCheckedOption("INCLUDE_CONTACT_NAME_IN_SUBJECT")%>>
                   <input type=hidden name="CB_INCLUDE_CONTACT_NAME_IN_SUBJECT" value="">
        </td>
        
      </tr>
      <tr>
        <th align="right" valign="top">Subject Text(after customer company):</th>
        <td><input name="OPT_SUBJECT_TEXT" value="<%=AppSettings.getParm("SUBJECT_TEXT")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
      
        <th align="right" valign="top">Body Text:</th>
        <td><input name="OPT_BODY_TEXT" value="<%=AppSettings.getParm("BODY_TEXT")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>	
	
	<tr>
      <th colspan="2"><hr class="section"><br><br><font color=blue>Application Settings</font><br><br></th>
      </tr>

	<tr>
        <th align="right" valign="top">Google Analytics Account No:</th>
        <td ><input name="OPT_GOOGLE_ANALYTICS_ACCT_NO" value="<%=AppSettings.getParm("GOOGLE_ANALYTICS_ACCT_NO")%>" size=33 onblur="fOnFieldBlur(this);"></td>
	</tr>
	<tr>
        <th align="right" valign="top">Display AP fields in Commission</th>
         <td>
                   <input type="checkbox" name="CB_DISPLAY_AP_FIELDS" value="YES" <%=getCheckedOption("DISPLAY_AP_FIELDS")%>>
                   <input type=hidden name="CB_DISPLAY_AP_FIELDS" value="">
        </td>
        
      </tr>
	<tr>
        <th align="right" valign="top">Show "Change All Checked" on search results screen.</th>
         <td>
                   <input type="checkbox" name="CB_DISPLAY_ALL_CHECKED" value="YES" <%=getCheckedOption("DISPLAY_ALL_CHECKED")%>>
                   <input type=hidden name="CB_DISPLAY_ALL_CHECKED" value="">
        </td>
        
      </tr>
      <tr>
        <th align="right" valign="top">Display Open Orders Fields</th>
         <td>
                   <input type="checkbox" name="CB_DISPLAY_OPEN_ORDERS_FIELDS" value="YES" <%=getCheckedOption("DISPLAY_OPEN_ORDERS_FIELDS")%>>
                   <input type=hidden name="CB_DISPLAY_OPEN_ORDERS_FIELDS" value="">
        </td>
        
      </tr>
       <tr>
        <th align="right" valign="top">Display Open Quotes Fields</th>
         <td>
                   <input type="checkbox" name="CB_DISPLAY_OPEN_QUOTES_FIELDS" value="YES" <%=getCheckedOption("DISPLAY_OPEN_QUOTES_FIELDS")%>>
                   <input type=hidden name="CB_DISPLAY_OPEN_QUOTES_FIELDS" value="">
        </td>
        
      </tr>
       <tr>
        <th align="right" valign="top">Display Open Accounts Receivable Fields</th>
         <td>
                   <input type="checkbox" name="CB_DISPLAY_OPEN_AR_FIELDS" value="YES" <%=getCheckedOption("DISPLAY_OPEN_AR_FIELDS")%>>
                   <input type=hidden name="CB_DISPLAY_OPEN_AR_FIELDS" value="">
        </td>
        
      </tr>
       <tr>
        <th align="right" valign="top">Display Orders/Invoices Fields</th>
         <td>
                   <input type="checkbox" name="CB_DISPLAY_ORDERS_INVOICES_FIELDS" value="YES" <%=getCheckedOption("DISPLAY_ORDERS_INVOICES_FIELDS")%>>
                   <input type=hidden name="CB_DISPLAY_ORDERS_INVOICES_FIELDS" value="">
        </td>
        
      </tr>
      <tr>
      <th colspan="2"><hr class="section"><br><br><font color=blue>E-Mail Settings</font><br><br></th>
      </tr>

      <tr>
        <th align="right" valign="top">SMTP Relay Host:</th>
        <td><input name="OPT_SMTP_RELAY_HOST" value="<%=AppSettings.getParm("SMTP_RELAY_HOST")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
        <th align="right" valign="top">SMTP Relay User:</th>
        <td><input name="OPT_SMTP_USER" value="<%=AppSettings.getParm("SMTP_USER")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
        <th align="right" valign="top">SMTP Relay Password:</th>
        <td><input name="OPT_SMTP_PASSWORD" value="<%=AppSettings.getParm("SMTP_PASSWORD")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
        <th align="right" valign="top">Automated Email Sender Address:</th>
        <td><input name="OPT_AUTOMATED_EMAIL_SENDER" value="<%=AppSettings.getParm("AUTOMATED_EMAIL_SENDER")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
      <th colspan="2"><hr width="250">Next Sales Action E-mail Notifications<br></th>
      </tr>
      <tr>
        <th align="right" valign="top">Enabled:</th>
        <td>
                   <input type="checkbox" name="CB_SALES_ACTION_EMAIL_ENABLED" value="YES" <%=getCheckedOption("SALES_ACTION_EMAIL_ENABLED")%>>
                   <input type=hidden name="CB_SALES_ACTION_EMAIL_ENABLED" value="">
                   <font color="red">Requires server restart</font>
        </td>
      </tr>
       <tr>
        <th align="right" valign="top">Sales Portal URL (link in email):</th>
        <td><input name="OPT_SALESPORTAL_URL" value="<%=AppSettings.getParm("SALESPORTAL_URL")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
        <th align="right" valign="top">Check every:</th>
        <td><input name="OPT_SALES_ACTION_EMAIL_SCHEDULE_MINUTES"
                value="<%=AppSettings.getParm("SALES_ACTION_EMAIL_SCHEDULE_MINUTES")%>" size=3
                onblur="fOnFieldBlur(this); if (!isValidInteger(this.value)) {alert('Invalid value for the selected field, please specify a valid positive integer number'); this.focus();} else { if (this.value < 5) {alert('Please specify at least 5 minutes for the check interval to avoid server overloading.'); this.focus();}}"> minutes
        <font color="red">Requires server restart</font>
      </td>
      </tr>
      <tr>
        <th align="right" valign="top">Check/send notifications for actions scheduled :</th>
        <td><input name="OPT_SALES_ACTION_EMAIL_BEFORE_DAYS"
                value="<%=AppSettings.getParm("SALES_ACTION_EMAIL_BEFORE_DAYS")%>" size=3
                onblur="fOnFieldBlur(this); if (!isValidInteger(this.value)) {alert('Invalid value for the selected field, please specify a valid positive integer number'); this.focus();}"> days from now (0 means TODAY, 1 means TOMORROW etc.)</td>
      </tr>

      <tr>
             <th align="right" valign="top">Email body additional text:</th>
             <td colspan="2">
                    <textarea NAME="OPT_SALES_ACTION_BODY_TEXT" wrap="off" rows="5" cols="30" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("SALES_ACTION_BODY_TEXT")%></textarea>
                    <% if (AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
                                 <input type=button value="Preview" onclick="openHTMLEditor('OPT_SALES_ACTION_BODY_TEXT','<%=URLEncoder.encode("Email content")%>','0')">
                    <% } else { %>
                                 <input type=button value="Preview" onclick="openEkitEditor('OPT_SALES_ACTION_BODY_TEXT','<%=URLEncoder.encode("Email content")%>','0')">
                    <% } %>

            </td>
           </tr>
      <tr>
<!-- TRYING TO IMPLETENT A TEST EMAI... FOR FUTURE RELEASE DEBUG      <tr>
        <th align="right" valign="top">Send Test Email:</th>
        <td>
        	<input name="dfEmailAddress" value="" size=20>
        	<input type="button" onclick="javascript:testEmail();" value="Send Email">
        </td>
        <script>
        	function testEmail(){
        		document.frmConfig.formAction.value = 'SEND_TEST_EMAIL';
        		document.frmConfig.submit();
        	}
        </script>
      </tr>--> 
	<th colspan="2"><hr class="section"><br><br><font color=blue>Alert Settings</font><br><br></th>
      </tr>
      <tr>
        <th align="right" valign="top">Current Sales Action Alert:</th>
        <td><input name="OPT_SALES_ACTION_ALERT_USER_MINUTES" value="<%=AppSettings.getParm("SALES_ACTION_ALERT_USER_MINUTES")%>" size=10 onblur="fOnFieldBlur(this);">
        	when not blank, default settings is 15 minutes.  This is the number of minutes before a Sales Action alert is performed.  Please refer to the 
        	documentation for details.
        </td>
      </tr>
      
      <th colspan="2"><hr class="section"><br><br><font color=blue>Debug Settings</font><br><br></th>
      </tr>

      <tr>
        <th align="right" valign="top">Output SQL Queries to log files:</th>
        <td>
                   <input type="checkbox" name="CB_SQL_TRACE" value="YES" <%=getCheckedOption("SQL_TRACE")%>>
                   <input type=hidden name="CB_SQL_TRACE" value="">
        </td>
      </tr>
      <tr>
        <th align="right" valign="top">Sales Actions Test Email Address:</th>
        <td><input name="OPT_SALES_ACTION_TEST_EMAIL_ADDRESS" value="<%=AppSettings.getParm("SALES_ACTION_TEST_EMAIL_ADDRESS")%>" size=33 onblur="fOnFieldBlur(this);">
        when not blank, the notifications are sent to this address in addition to the salesmen; the prospects are not flagged unless at least one salesman is notified
        </td>
      </tr>

       
       <tr>
      <th colspan="2"><hr class="section"><br><br><font color=blue>Infoportal Settings</font><br><br></th>
      </tr>
      
	<tr>
        <th align="right" valign="top">Linked Information Portal Installed:</th>
         <td>
                   <input type="checkbox" name="CB_INFOPORTAL_LINK_ENABLED" value="YES" <%=getCheckedOption("INFOPORTAL_LINK_ENABLED")%>>
                   <input type=hidden name="CB_INFOPORTAL_LINK_ENABLED" value="">
        </td>
        
      </tr>
       <tr>
        <th align="right" valign="top">Linked Information Portal Address:</th>
        <td><input name="OPT_INFOPORTAL_LINK" value="<%=AppSettings.getParm("INFOPORTAL_LINK")%>" size=50	 onblur="fOnFieldBlur(this);">
        <font color="red">Requires server restart</font>
        </td>
      </tr>
   
	<tr>
      <th colspan="2"><hr class="section"><br><br><font color=blue>Import and USHUD Round Robin Import Settings</font><br><br></th>
      </tr>
       </tr>
       <tr>
        <th align="right" valign="top">Eliminate Dups on Automated Imports (set to TRUE for USHUD):</th>
        <td><input name="OPT_ELIMINATE_DUPS_ON_AUTOMATED_IMPORTS" value="<%=AppSettings.getParm("ELIMINATE_DUPS_ON_AUTOMATED_IMPORTS")%>" size=10 onblur="fOnFieldBlur(this);">
        </td>
      </tr>
		<tr>
        <th align="right" valign="top">Round Robin Enabled:</th>
         <td>
                   <input type="checkbox" name="CB_ROUND_ROBIN_ENABLED" value="YES" <%=getCheckedOption("ROUND_ROBIN_ENABLED")%>>
                   <input type=hidden name="CB_ROUND_ROBIN_ENABLED" value="">
        </td>
        
      </tr>
       <tr>
        <th align="right" valign="top">Round Robin Sales Action:</th>
        <td><input name="OPT_ROUND_ROBIN_SALES_ACTION_ID" value="<%=AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_ID")%>" size=10 onblur="fOnFieldBlur(this);">
        </td>
      </tr>
  
       <tr>
        <th align="right" valign="top">Round Robin Sales Action Note:</th>
        <td> <textarea NAME="OPT_ROUND_ROBIN_SALES_ACTION_NOTE" wrap="off" rows="5" cols="30" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_NOTE")%></textarea>
        </td>
      </tr>
        <tr>
        <th colspan="2"><hr class="section"></th>
        </tr>
          <tr>
             <th align="right" valign="top">Optional home page content text:</th>
             <td colspan="2">
                    <textarea NAME="OPT_HOME_PAGE_CONTENT" wrap="off" rows="20" cols="50" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("HOME_PAGE_CONTENT")%></textarea>
                    

            </td>
           </tr>
         <th colspan="2"><hr class="section"></th>
     </table>

     </td> </tr></table>
     </form>
     <br><br><br>
     <form name="frmEmailTest" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
     <input type=hidden name="formAction" value="SEND_TEST_EMAIL">
     </form>
</body>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
