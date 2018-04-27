<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings" %>
<% try{ %>
<%   String title = "Configurations";
    String formAction = request.getParameter("formAction");

    if (formAction.equals("UPDATE_APPLICATION_OPTIONS")) {
      title = "Application Options";
    } %>
    <style>
        body, table, form, input, select {font-family: arial,verdana, tahoma; font-size:10pt}
        h1{ font-size: 12pt; color: green}
        hr {height: 1px;}
        hr.section {color: blue}
    </style>
    <center>
    <h1><%=title%></h1>
    <br><br><br>

    <hr width="50%">
    The <%=title%> have been saved.
    <hr width="50%">
    <br><br><br>
    <input type="button" value="Back" onclick="javascript:history.go(-1);">
    <input type="button" value="Close" onclick="javascript:window.close();">

    </center>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
  } finally {
  }
%>