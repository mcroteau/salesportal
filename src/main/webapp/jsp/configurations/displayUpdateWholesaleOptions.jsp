<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings" %>
<%@ page import="java.net.URLEncoder" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<% try{ %>

<%=AppSettings.getHeader(request, userProfile ,"Configuration Site :: Web User Options - " + AppSettings.getParm("COMPANY_NAME"), "","", "")%>
    <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/options.js">
     </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/webuserOptions.js">
     </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/openHTMLEditor.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
     </script>

     <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
     <input type=hidden name="formAction" value="UPDATE_WHOLESALE_OPTIONS">     
     
     <table border="0" align="center">
     <tr> 
     <td>
     <center><h1>Web User Options</h1>
     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidWholesaleConfig()">
     <hr> 

     <table border="0" align="center">


<%// Create Account section ..........................................%>
      <tr>
        <td colspan="3">
          <h2>Create Account</h2>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan="2">
           <table border="0">
           <tr>
             <td align="left"><b>Confirmation page:</b> </td>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_ACCOUNT_CREATED_CONFIRMATION_TEXT','<%=URLEncoder.encode("Create Account - Confirmation page")%>','0')"> <br> </td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_ACCOUNT_CREATED_CONFIRMATION_TEXT','<%=URLEncoder.encode("Create Account - Confirmation page")%>','0')"> <br> </td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_ACCOUNT_CREATED_CONFIRMATION_TEXT" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ACCOUNT_CREATED_CONFIRMATION_TEXT")%></TEXTAREA>
           </tr>
           </table>
        </td>       
      </tr>

      <tr>
        <td width="50"></td>
        <td width="200"><b>Notification email subject:</b></td>
        <td><input name="OPT_ACCOUNT_CREATED_NOTIFICATION_EMAIL_SUBJECT" value="<%=AppSettings.getParm("ACCOUNT_CREATED_NOTIFICATION_EMAIL_SUBJECT")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

	<tr>
        <td width="50"></td><td colspan=2><b>Notification email header:</b></td><td ></td> 
      </tr>
	<tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_ACCOUNT_CREATED_NOTIFICATION_EMAIL_HEADER" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ACCOUNT_CREATED_NOTIFICATION_EMAIL_HEADER")%></TEXTAREA>
        </td>
      </tr>
	<tr>
        <td width="50"></td><td colspan=2><b>Notification email footer:</b></td><td ></td>
      </tr>
	<tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_ACCOUNT_CREATED_NOTIFICATION_EMAIL_FOOTER" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ACCOUNT_CREATED_NOTIFICATION_EMAIL_FOOTER")%></TEXTAREA>
        </td>
      </tr>

	<tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>

<%// Forgor user id/password section .................................%>
	<tr>
        <td colspan="3">
          <h2>Forgot user/password</h2>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan="2">
           <table border="0">
           <tr>
             <td align="left"><b>Confirmation page:</b> </td>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_ACCOUNT_FORGOT_PASSWORD_CONFIRMATION_TEXT','<%=URLEncoder.encode("Forgot user/password - Confirmation page")%>','0')"> <br> </td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_ACCOUNT_FORGOT_PASSWORD_CONFIRMATION_TEXT','<%=URLEncoder.encode("Forgot user/password - Confirmation page")%>','0')"> <br> </td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_ACCOUNT_FORGOT_PASSWORD_CONFIRMATION_TEXT" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ACCOUNT_FORGOT_PASSWORD_CONFIRMATION_TEXT")%></TEXTAREA>
           </tr>
           </table>
        </td>       
      </tr>

	<tr>
        <td width="50"></td><td colspan=2><b>Email header:</b></td><td ></td> 
      </tr>
	<tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_ACCOUNT_FORGOT_PASSWORD_EMAIL_HEADER" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ACCOUNT_FORGOT_PASSWORD_EMAIL_HEADER")%></TEXTAREA>
        </td>
      </tr>

	<tr>
        <td width="50"></td><td colspan=2><b>Email Footer:</b></td><td ></td>
      </tr>
	<tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_ACCOUNT_FORGOT_PASSWORD_EMAIL_FOOTER" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("ACCOUNT_FORGOT_PASSWORD_EMAIL_FOOTER")%></TEXTAREA>
        </td>
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
<%=AppSettings.getFooter(userProfile)%>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
