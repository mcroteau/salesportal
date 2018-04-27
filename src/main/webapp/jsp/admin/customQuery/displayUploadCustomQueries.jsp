<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings, com.randr.webdw.util.Utilities" %>
<%@page import="java.math.BigDecimal" %>

<% try{ %>

     <html>
     <head>
     <meta http-equiv="expires" content="0">
     <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
     <script language="JavaScript">
     function fProcessForm() {
       if (document.frmUpload.dfFile.value=='') {alert('Please specify the SQL file!');return false;};
	   if (document.frmUpload.dfQueryName.value=='') {alert('Please enter a name for the query!');return false;};
	   if (document.frmUpload.dfFileDescription.value=='') {alert('Please enter a short description for the SQL file!');return false;};
       if (document.frmUpload.dfFile.value.indexOf("'")>-1) {alert('The specified file contains an apostrophe which is an invalid character!');return false;};
       return true;
     };
     </script>
     </head>

     <body>
     <form name="frmUpload" method=post action="<%=AppSettings.getAppWebPath()%>admin/custom_queries" ENCTYPE="multipart/form-data">
     <input type="hidden" name="formAction" value="PROCESS_UPLOAD">

     <table border=0 align=center>
     <tr><td>
     <center><h1>Create Custom SQL Query</h1></center><br>
<br><br><br>

     </td></tr>
     <tr><td>
     <input type=submit class="button" name="pbSubmit" value="Upload File" onclick="return fProcessForm()">
     </td></tr>
     <tr><td>
     <hr><br><Br>
     </td></tr>
     <tr><td>

     <table border=0 align=center>

     <tr>
      <td align="right" width="33%"><b>SQL File:</b></td>
      <td align="left"><input type="file" name="dfFile" size="30"></td>
    </tr>
     <tr>
      <td align="right" width="33%"><b>Query Name:</b></td>
      <td align="left"><input type="text" name="dfQueryName" size="30" maxlength="30"></td>
    </tr>
     <tr>
      <td align="right" width="33%"><b>Short Description:</b></td>
      <td align="left"><input type="text" name="dfFileDescription" size="30" maxlength="255"></td>
    </tr>
     </table>
     </td></tr>
     </table>
     </form>
     </body>
     </html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {
   } %>