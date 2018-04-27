<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings" %>
<%@ page import="java.net.URLEncoder" %>

<jsp:useBean id="testResponseCode" class="java.lang.String" scope="request"/>

<%try{%>

<h1>Response Code : <%=testResponseCode%></h1>




<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>