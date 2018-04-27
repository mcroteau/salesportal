<%@page contentType="text/html; charset=iso-8859-1" language="java" import="com.randr.webdw.AppSettings,
                                                                            com.randr.webdw.user.UserProfile,
                                                                            com.randr.webdw.util.Utilities" %>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<%--<jsp:setProperty name="userProfile" property="*"/>--%>

<%
try
{
%>

<html>

    <head>
        <title>User Log In</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
        <script  language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

        <script  language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/user/login.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>
        <SCRIPT language=JavaScript>
         var okToLogin = false;

         document.onkeydown=keydown;

         if (document.layers){
            window.captureEvents(Event.KEYDOWN);
        	}
         window.onkeydown=keydown;


		</script>
     </head>

     <body>
     <%=AppSettings.getGoogleAnalyticsCode()%>
     <center>
	 

	<form name="frmLogin" method="post"
                        action="<%=AppSettings.getAppCompleteWebPath(request)%>home"
                        onsubmit="return fValidLoginForm()">
            <% if(request.getParameter("initialProspectId") != null){%>
                <input type="hidden" name="initialProspectId" value="<%=request.getParameter("initialProspectId")%>">
            <% } %>
     <br/><br/>
     <br/><br/>
<table width=400 class="nocolor">
     	<tr>
        	<td align=center colspan="2" class="companyLoginName">
        	<%=AppSettings.getParm("COMPANY_NAME")%> Login
			</td>
 	         
        <tr>
			<td colspan="2"><p>If you have an active account you can sign on by providing your User Name and Password.</p></td>
		</tr>
        <tr>
			<td><br/></td>
		</tr>
        <tr>
            <td align=right>User Name&nbsp;:&nbsp;</td>
            <td><input name="userName" value="" size="30" maxlength="30"></td>
        </tr>
        <tr>
            <td align=right>Password&nbsp;:&nbsp;</td>
            <td><input type=password name="password" size="30" maxlength=20 onfocus="okToLogin = true;" onblur="okToLogin = false;"></td>
        </tr>
        <tr>
			<td><br/></td>
		</tr>
		<tr>
			<td colspan="2"><center><input type="image" src="<%=AppSettings.getGraphicsPath()%>login.gif" border="0" onclick="javascript:fDisplayLoading()" onfocus="okToLogin=true;" onblur="okToLogin=false;"></center></td>
		</tr>
		<tr>
			<td><br/></td>
		</tr>
		<tr id="loading" style="display: none;">
			<td class="loggingIn" colspan="2">
				Logging In...
			</td>
		</tr>
		
		<tr>
			<td><br/></td>
		</tr>
		<tr>
			<td class="salesportallogo">
				
			</td>
			<td class="randrlogo">
<a href="http://www.randrinc.com/" target="_blank" title="Visit www.randrinc.com">
				<img class="salesportal" src="<%=AppSettings.getGraphicsPath()%>randr-logo-small.jpg" class="randrLogo" border="0" alt="Powered by Randr"></a><br/>					

			</td>
		</tr>
		
    </table>

    <br><br>
    
    
   </form>
   <script>
       document.frmLogin.userName.focus();
   </script>
   <br>
<div id="loginError" style="display: none;"> 
  <br><br>

<%  if (userProfile.getErrorCode()!=0)
{
      int errorType = userProfile.getErrorCode(); %>


      <p class="exception">
<%  if (errorType==UserProfile.ERROR_INVALID_USER_NAME)
    {
%>
	<script>fShowError();</script>
  	    Invalid User Name. Please try again.
<%
    }
    else if (errorType==UserProfile.ERROR_INVALID_PASSWORD)
    {
%>
	<script>fShowError();</script>
	    Invalid password. <br><br><br>Please make sure that Caps Lock is off before you try again. <br>Password is case sensitive.
<%
    }
    else if (errorType==UserProfile.ERROR_ACCOUNT_INACTIVE)
    {
%>
	<script>fShowError();</script>
	    Account not active.
<%        %>
<%
    }
    else if (errorType==UserProfile.ERROR_USER_NOT_SPECIFIED)
    {
        // this situation happens when a page is tried to be accesed directly and when the application is restarted (and profile lost...)%>

<%
    }
%>
</p>
<%  } %>
  <br><br>
</div>

</center>

    <center>
   </body>
   </HTML>

<%
    } catch (Exception e) {
      e.printStackTrace();  %>
      Error: <%=e.getMessage()%>
<%  } %>