<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings" %>

<% try{ %>

     <html>
     <head>
     <title>Wholesale Configurations</title>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/updateWholesaleMessage.js">
     </script>
     </head>

     <body>
     <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
     <input type="hidden" name="formAction" value="UPDATE_WHOLESALE_MESSAGE">
     
     <table border="0" align="center">
     <tr> 
     <td>
     <center><font style="font-size:18px;color:#008000;font-family:Arial">
     <b>Wholesale Configurations</b>
     </font></center><br>
     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidF()">
     <hr> 
         
     <table border="0" align="center" width="500">
      <tr>
        <td colspan="3">
          <b><font color="blue">Wholesale Messages</font></b>
        </td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200">
          <b>Active:</b> 
        </td>
        <td width="250">
          <select name="OPT_MESSAGE_W_ACTIVE"><option value="1">Yes<option value="0">No</select>
          <script language="JavaScript">fSetActive(document.frmConfig.OPT_MESSAGE_W_ACTIVE,'<%=AppSettings.getParm("MESSAGE_W_ACTIVE")%>');</script>
        </td>
      </tr>
      <tr>
       <td width="50"></td>
       <td width="200">
          <b>Message Window Width :</b>
        </td>
        <td width="250">
          <input name="OPT_MESSAGE_W_WIDTH" size=10 maxlength=10 value="<%=AppSettings.getParm("MESSAGE_W_WIDTH")%>">
        </td>
      </tr>  
      <tr>
        <td width="50"></td>
        <td width="200">
          <b>Message Window Height:</b>
        </td>
        <td width="250">
          <input name="OPT_MESSAGE_W_HEIGHT" size=10 maxlength=10 value="<%=AppSettings.getParm("MESSAGE_W_HEIGHT")%>">
        </td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200">
          <b>Message Window Heading:</b>
        </td>
        <td width="250">
          <input name="OPT_MESSAGE_W_HEADING" size=30 maxlength=50 value="<%=AppSettings.getParm("MESSAGE_W_HEADING")%>">
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td width="200">
          <b>Message content:</b>
        </td>
        <td width="250"></td> 
      </tr>
      <tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_MESSAGE_W_CONTENT" ROWS=10 COLS=50>
<%=AppSettings.getParm("MESSAGE_W_CONTENT")%></TEXTAREA>
        </td>
      </tr>
     </table>
   
     </td>
     </tr>
    
     </table>
     </body>
     </html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
