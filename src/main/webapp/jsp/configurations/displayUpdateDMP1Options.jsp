<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings,
		com.randr.webdw.util.Utilities" %>
<%@ page import="java.net.URLEncoder" %>
<jsp:useBean id="confirmationStatus" class="java.lang.String" scope="request"/>
<style type="text/css">
p#success{padding:4px; color:#038d03; font-weight:bold;border:solid 2px #00ff00;}
p#failed{padding:4px; color:#ff0000; font-weight:bold; border:solid 2px #ff0000;}

</style>
<%! private String getCheckedOption(String optionName) { 
      if (AppSettings.getParm(optionName).equals("YES")) { 
        return "checked";
      } else {
        return "";
      }
    } %>

<% try{ %>
    <head>
    <title>Data Migration Options - Configurations</title>
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
     <h1>Data Migration Options</h1>
    <br> <br>
     <br>
     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidAppConfig()">
     <input type="button" value="Send Test to Datamigration" onclick="javascript:fSendTestDatamigration()"><%=Utilities.nullToBlank(confirmationStatus)%>
     <hr> 

     <table border="0" align="center">


<%// DMP Prospect Importal section ..........................................%>
       <tr>
        <td colspan="3">
          <b><font color="blue">Data Migration Portal - Prospect Import</font></b>
        </td>
      </tr>
      <tr>
        <td colspan="3">
         Prospect Import is on a timer.  Scheduling: For a once a day time scheduled import. Set the SCHEDULE_START_TIME <br>
         to the start time in military time (just hour no minutes)and set the SCHEDULE_MINUTES to 24 * 60 or 1440. For Example:<br>
	
		set PROSPECT_IMPORT_SCHEDULE_MINUTES=1440 and PROSPECT_IMPORT_SCHEDULE_START_TIME=22<br>
		This would run at 10:00 pm every 24 hours (daily). Starting at 22:00, runs every 1440 minutes.<br>
		To run every hour, set PROSPECT_IMPORT_SCHEDULE_MINUTES=60 and leave PROSPECT_IMPORT_SCHEDULE_START_TIME blank.
		<br>
        </td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Data Migration Prospect CSV Link:</b></td>
        <td ><input name="OPT_DATA_MIGRATION_PROSPECT_CSV_LINK" value="<%=AppSettings.getParm("DATA_MIGRATION_PROSPECT_CSV_LINK")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Is Prospect Import Enabled (TRUE/FALSE):</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_ENABLED" value="<%=AppSettings.getParm("PROSPECT_IMPORT_ENABLED")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Schedule Minutes (import runs every x minutes):</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_SCHEDULE_MINUTES" value="<%=AppSettings.getParm("PROSPECT_IMPORT_SCHEDULE_MINUTES")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Scheduled Start Time (import runs starting at this time, then every x minutes):</b></td>
        <td ><input name="OPT_PROSPECT_IMPORT_SCHEDULE_START_TIME" value="<%=AppSettings.getParm("PROSPECT_IMPORT_SCHEDULE_START_TIME")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import File Prefix:</b></td>
        <td><%=AppSettings.getParm("PROSPECT_IMPORT_FILE_PREFIX")%></td>
        <!-- <td ><input name="OPT_PROSPECT_IMPORT_FILE_PREFIX" value="<%=AppSettings.getParm("PROSPECT_IMPORT_FILE_PREFIX")%>" size=50 onblur="fOnFieldBlur(this);"></td> -->
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Unprocessed Storage:</b></td>
        <td><%=AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE")%></td>
        <!-- <td ><input name="OPT_PROSPECT_IMPORT_UNPROCESSED_STORAGE" value="<%=AppSettings.getParm("PROSPECT_IMPORT_UNPROCESSED_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td> -->
      </tr>
       <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Processed Storage:</b></td>
        <td><%=AppSettings.getParm("PROSPECT_IMPORT_PROCESSED_STORAGE")%></td>
        <!-- <td ><input name="OPT_PROSPECT_IMPORT_PROCESSED_STORAGE" value="<%=AppSettings.getParm("PROSPECT_IMPORT_PROCESSED_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td> -->
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Invalid Storage:</b></td>
        <td><%=AppSettings.getParm("PROSPECT_IMPORT_INVALID_STORAGE")%></td>
        <!-- <td ><input name="OPT_PROSPECT_IMPORT_INVALID_STORAGE" value="<%=AppSettings.getParm("PROSPECT_IMPORT_INVALID_STORAGE")%>" size=50 onblur="fOnFieldBlur(this);"></td> -->
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Prospect Import Log:</b></td>
        <td><%=AppSettings.getParm("PROSPECT_IMPORT_LOG")%></td>
        <!-- <td ><input name="OPT_PROSPECT_IMPORT_LOG" value="<%=AppSettings.getParm("PROSPECT_IMPORT_LOG")%>" size=50 onblur="fOnFieldBlur(this);"></td> -->
      </tr>
     


     <script>
     	function fSendTestDatamigration(){
     		document.frmConfig.formAction.value='SEND_TEST_COMMUNICATION_DATAMIGRATION';
     		document.frmConfig.submit();
     	}
     </script>
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
        <td width="200"><b>Is Send Email Enabled (TRUE/FALSE):</b></td>
        <td ><input name="OPT_IMPORT_EMAIL_ENABLED" value="<%=AppSettings.getParm("IMPORT_EMAIL_ENABLED")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Email Subject:</b></td>
        <td ><input name="OPT_IMPORT_EMAIL_SUBJECT" value="<%=AppSettings.getParm("IMPORT_EMAIL_SUBJECT")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Address to Receive Email (separate by ,):</b></td>
        <td ><input name="OPT_IMPORT_AUTOMATED_EMAIL_RECEIVER" value="<%=AppSettings.getParm("IMPORT_AUTOMATED_EMAIL_RECEIVER")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Email From Address:</b></td>
        <td ><input name="OPT_IMPORT_AUTOMATED_EMAIL_FROM_ADDRESS" value="<%=AppSettings.getParm("IMPORT_AUTOMATED_EMAIL_FROM_ADDRESS")%>" size=50 onblur="fOnFieldBlur(this);"></td>
      </tr>

<tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>

    	<tr>
      <th colspan="3"><hr class="section"><br><br><font color=blue>General Import Round Robin Settings</font><br><br></th>
      </tr>
      
		<tr>
	 	<td width="50"></td>
        <th align="right" valign="top" width="200">Order Portal Round Robin Enabled:</th>
         <td width="200">
                   <input type="checkbox" name="CB_ROUND_ROBIN_ENABLED" value="YES" <%=getCheckedOption("ROUND_ROBIN_ENABLED")%>>
                   <input type=hidden name="CB_ROUND_ROBIN_ENABLED" value="">
        </td>
        
      </tr>
       <tr>
	 	<td width="50"></td>
        <th align="right" valign="top" width="200">Order Portal Round Robin Sales Action:</th>
        <td><input name="OPT_ROUND_ROBIN_SALES_ACTION_ID" value="<%=AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_ID")%>" size=10 onblur="fOnFieldBlur(this);">
        </td>
      </tr>
      </tr>
       <tr>
	 	<td width="50"></td>
        <th align="right" valign="top" width="200">Order Portal Round Robin Sales Action Note:</th>
        <td> <textarea NAME="OPT_ROUND_ROBIN_SALES_ACTION_NOTE" wrap="off" rows="5" cols="30" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ROUND_ROBIN_SALES_ACTION_NOTE")%></textarea>
        </td>
      </tr>
        <tr>
        <th colspan="3"><hr class="section"></th>
        </tr>
        	<tr>
      <th colspan="3"><hr class="section"><br><br><font color=blue>USHUD Round Robin Import Settings</font><br><br></th>
      </tr>
      
		<tr>
	 	<td width="50"></td>
        <th align="right" valign="top" width="200">USHUD Round Robin Enabled:</th>
         <td>
                   <input type="checkbox" name="CB_USHUD_ROUND_ROBIN_ENABLED" value="YES" <%=getCheckedOption("USHUD_ROUND_ROBIN_ENABLED")%>>
                   <input type=hidden name="CB_USHUD_ROUND_ROBIN_ENABLED" value="">
        </td>
        
      </tr>
       <tr>
	 	<td width="50"></td>
        <th align="right" valign="top" width="200">USHUD Round Robin Sales Action:</th>
        <td><input name="OPT_USHUD_ROUND_ROBIN_SALES_ACTION_ID" value="<%=AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_ID")%>" size=10 onblur="fOnFieldBlur(this);">
        </td>
      </tr>
      </tr>
       <tr>
	 	<td width="50"></td>
        <th align="right" valign="top" width="200">USHUD Round Robin Sales Action Note:</th>
        <td> <textarea NAME="OPT_USHUD_ROUND_ROBIN_SALES_ACTION_NOTE" wrap="off" rows="5" cols="30" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("USHUD_ROUND_ROBIN_SALES_ACTION_NOTE")%></textarea>
        </td>
      </tr>
        <tr>
        <th colspan="3"><hr class="section"></th>
        </tr>


      
<%// end of config sections.........................................%>	      	

     <tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
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
