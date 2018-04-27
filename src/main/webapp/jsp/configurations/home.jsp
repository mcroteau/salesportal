<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings,
        java.util.LinkedList" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<% try{ %>
<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Configuration", "","", "")%>
    <head>
    <title>Configurations</title>
     <script>
     function openConfigPage(formActionVal) {
     	document.frmAdmin.formAction.value = formActionVal;
     	document.frmAdmin.submit();
     }
     </script>
    <style>
        body, table, form, input, select {font-family: arial,verdana, tahoma; font-size:10pt}
        h1{ font-size: 12pt; color: green}
        hr {height: 1px;}
        hr.section {color: blue}
    </style>

     </head>
     <body>
    <center>
     <form name="frmAdmin" method=post action='<%=AppSettings.getAppCompleteWebPath(request)%>admin/config' target="_blank">
     <input type=hidden name="formAction" value="">
     </form>

     <h1>Customizable Options</h1>
     <a href="javascript:openConfigPage('DISPLAY_UPDATE_APPLICATION')">Application Options</a><br><br>    
     
       <a href="javascript:openConfigPage('DISPLAY_UPDATE_DMP1')">Import into Sales Portal Options (Propects & Commissions)</a><br><br>
       
      
         <a href="javascript:openConfigPage('DISPLAY_UPDATE_EMAIL1')">Automated Email Options </a><br><br>
     </center>
     </body>
<%=AppSettings.getFooter(userProfile)%>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
