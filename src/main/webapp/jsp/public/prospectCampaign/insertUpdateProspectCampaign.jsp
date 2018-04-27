<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings,
        java.util.Enumeration" %>

<jsp:useBean id="dbErrorMsg" class="java.lang.String" scope="request"/>

<% try{ %>

     <html>
     <body>
<form name="frmCampaign" method="post" action="<%=request.getParameter("oldAction")%>" target="mainWnd">
<input type="hidden" name="formAction" value="<%=request.getParameter("oldFormAction")%>">

</form>

<%   if (dbErrorMsg.equals("")) {  %>
       <script>
       document.frmCampaign.submit();
       window.close();
       </script>

<%   } else {  %>
       <font color = red >An error occured.</font>
       <br><br>The most probable reason for the error is that the prospect has been deleted.<br><br>

<%   }   %>

     </body>
     </html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {
   }
%>
