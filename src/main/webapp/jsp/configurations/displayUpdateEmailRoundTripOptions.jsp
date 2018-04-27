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
    <title>Automated Email Options - Configurations</title>
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
     <input type=hidden name="formAction" value="UPDATE_DMP_OPTIONS">     

     <table border="0" align="center">
     <tr> 
     <td>
     <h1>Automated Email Round Trip Options</h1>
    <br> <br>
     <br>
     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidAppConfig()">
     <hr> 

     <table border="0" align="center">


<%// Automated Email section ..........................................%>
       <tr>
        <td colspan="3">
          <b><font color="blue">Email Round Trip Options - Automated export of prospects from Sales Portal</font></b>
        </td>
      </tr>
      <tr>
        <td colspan="3">
           Automated Email Sales Actions is on a timer.  Scheduling: For a twice a day time schedule, set the SCHEDULE_START_TIME <br>
         to the start time in military time (just hour no minutes)and set the SCHEDULE_MINUTES to 12 * 60 or 720. For Example:<br>
	
		set EMAIL_SALES_ACTION_SEND_SCHEDULE_MINUTES=720 and EMAIL_SALES_ACTION_SEND_SCHEDULE_START_TIME=22<br>
		This would run at 10:00 pm and 10:00 AM every 12 hours.  Starting at 22:00, runs every 720 minutes.<br>
		<br>
        </td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Data Migration Sales Portal Email CSV Link:</b><br>(Order Portal should be set to this same value)</td>
        <td ><input name="OPT_SALESPORTAL_AUTOMATED_EMAIL_LINK" value="<%=AppSettings.getParm("SALESPORTAL_AUTOMATED_EMAIL_LINK")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      
       <tr>
        <td width="50"></td>
        <td width="200"><b>Is Manual Email Export Enabled (TRUE/FALSE)(From work with prospects screen):</b></td>
        <td ><input name="OPT_AUTOMATED_EMAIL_EXPORT_ENABLED" value="<%=AppSettings.getParm("AUTOMATED_EMAIL_EXPORT_ENABLED")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      

      
       <tr>
        <td width="50"></td>
        <td width="200"><b>Are Email Sales Actions Enabled (TRUE/FALSE):</b></td>
        <td ><input name="OPT_EMAIL_SALES_ACTION_SEND_ENABLED" value="<%=AppSettings.getParm("EMAIL_SALES_ACTION_SEND_ENABLED")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
   	  <tr>
        <td width="50"></td>
        <td width="200"><b>Verfied Id to compare to when Verified = Yes:</b></td>
        <td ><input name="OPT_VERIFIED_VALUE_FOR_EMAIL_SALES_ACTION" value="<%=AppSettings.getParm("VERIFIED_VALUE_FOR_EMAIL_SALES_ACTION")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      
      <tr>
        <td width="50"></td>
        <td width="200"><b>When the Email is sent a completed Sales Action is created for the Prospect, What Sales Action Id to use:</b></td>
        <td ><input name="OPT_SALES_ACTION_ID_FOR_COMPLETED_EMAIL_SALES_ACTION" value="<%=AppSettings.getParm("SALES_ACTION_ID_FOR_COMPLETED_EMAIL_SALES_ACTION")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      
   
       <tr>
        <td width="50"></td>
        <td width="200"><b>Automated Email Sales Action Schedule Minutes (import runs every x minutes):</b></td>
        <td ><input name="OPT_EMAIL_SALES_ACTION_SEND_SCHEDULE_MINUTES" value="<%=AppSettings.getParm("EMAIL_SALES_ACTION_SEND_SCHEDULE_MINUTES")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Automated Email Sales Action Scheduled Start Time (import runs starting at this time, then every x minutes):</b></td>
        <td ><input name="OPT_EMAIL_SALES_ACTION_SEND_SCHEDULE_START_TIME" value="<%=AppSettings.getParm("EMAIL_SALES_ACTION_SEND_SCHEDULE_START_TIME")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      
      <tr>
        <td width="50"></td>
        <td width="200"><b>Test Communication to Datamigration:</b></td>
        <td><input type="submit" value="Test DMP Link" onclick="javascript: fSendTestDatamigration();"></td>
      </tr>
     <script>
     	function fSendTestDatamigration(){
     		document.frmConfig.formAction.value='SEND_TEST_COMMUNICATION_DATAMIGRATION';
     		document.frmConfig.submit();
     	}
     </script>
     <hr>
<%// Automated Campaign section ..........................................%>
       <tr>
        <td colspan="3">
          <b><font color="blue">Automated Campaign Options</font></b>
        </td>
      </tr>  
       <tr>
        <td width="50"></td>
        <td width="200"><b>Default number of days between sales action:</b></td>
        <td ><input name="OPT_AUTOMATED_CAMPAIGN_DAYS" value="<%=AppSettings.getParm("AUTOMATED_CAMPAIGN_DAYS")%>" size=5 onblur="fOnFieldBlur(this);"></td>
      </tr>   
       <tr>
        <td width="50"></td>
        <td width="200"><b>Are Automated Campaigns Enabled (TRUE/FALSE):</b></td>
        <td ><input name="OPT_AUTOMATED_CAMPAIGN_CREATE_ENABLED" value="<%=AppSettings.getParm("AUTOMATED_CAMPAIGN_CREATE_ENABLED")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr> 
        <tr>
        <td width="50"></td>
        <td width="200"><b>Automated Campaigns Schedule Minutes (import runs every x minutes):</b></td>
        <td ><input name="OPT_AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_MINUTES" value="<%=AppSettings.getParm("AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_MINUTES")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Automated Campaigns Scheduled Start Time (import runs starting at this time, then every x minutes):</b></td>
        <td ><input name="OPT_AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_START_TIME" value="<%=AppSettings.getParm("AUTOMATED_CAMPAIGN_CREATE_SCHEDULE_START_TIME")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>    
      <tr>
       <td width="50"></td>
		<td width="200"><b>Prospect Import File Prefix:</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_FILE_PREFIX" value="<%=AppSettings.getParm("PROSPECT_IMPORT_FILE_PREFIX")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Unprocessed Storage:</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_UNPROCESSED_STORAGE" value="<%=AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Processed Storage:</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_PROCESSED_STORAGE" value="<%=AppSettings.getParm("PROSPECT_IMPORT_PROCESSED_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Invalid Storage:</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_INVALID_STORAGE" value="<%=AppSettings.getParm("PROSPECT_IMPORT_INVALID_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Log:</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_LOG" value="<%=AppSettings.getParm("PROSPECT_IMPORT_LOG")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
     
	<tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>
<%// Other Importal Options section ..........................................%>
           <tr>
        <td colspan="3">
          <b><font color="blue">Send Email When Prospect is Imported Options</font></b>
        </td>
      </tr>
        <tr>
        <td width="50"></td>
        <td width="200"><b>When the Email is sent a completed Sales Action is created for the Prospect,<br> What Sales Action Id to use (blank, no sales action is created):</b></td>
        <td ><input name="OPT_SALES_ACTION_ID_FOR_COMPLETED_EMAIL_SALES_ACTION" value="<%=AppSettings.getParm("SALES_ACTION_ID_FOR_COMPLETED_EMAIL_SALES_ACTION")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>
<%// Email Option Out Options section ..........................................%>
           <tr>
        <td colspan="3">
          <b><font color="blue">Email Opt Out Options - Coming from Order Portal</font></b>
        </td>
         </tr> 
         <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out import Enabled (TRUE/FALSE):</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_ENABLED" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_ENABLED")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
         <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Schedule Minutes (import runs every x minutes):</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_SCHEDULE_MINUTES" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_SCHEDULE_MINUTES")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Scheduled Start Time (import runs starting at this time, then every x minutes):</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_SCHEDULE_START_TIME" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_SCHEDULE_START_TIME")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>   
       <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Import File Prefix:</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_FILE_PREFIX" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_FILE_PREFIX")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Import Unprocessed Storage:</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_UNPROCESSED_STORAGE" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_UNPROCESSED_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Import Processed Storage:</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_PROCESSED_STORAGE" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_PROCESSED_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Import Invalid Storage:</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_INVALID_STORAGE" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_INVALID_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
          
     <tr>
        <td width="50"></td>
        <td width="200"><b>Email Opt Out Import Log:</b></td>
        <td ><input name="OPT_EMAIL_OPTOUT_IMPORT_LOG" value="<%=AppSettings.getParm("EMAIL_OPTOUT_IMPORT_LOG")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>


     </table>
   
     </td>
     </tr>
    
     </table>
     </form>


<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
