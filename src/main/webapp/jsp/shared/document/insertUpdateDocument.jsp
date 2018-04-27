<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings,
        java.util.Enumeration" %>

<jsp:useBean id="dbErrorMsg" class="java.lang.String" scope="request"/>
<% try{ %>

     <html>
     <body>
<%   if (dbErrorMsg.equals("")) {  %>
        Operation successful.
<%   } else {  %>
       <font color="red">Could not complete the operation.</font>
       <br><br>
       <%=dbErrorMsg%>
<%   }   %>
     </body>
     </html>     

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
