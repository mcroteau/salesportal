<%@ page import="com.randr.webdw.AppSettings"%>
<jsp:useBean id="exception" class="java.lang.Exception" scope="request"/>
<html>
<head>
    <title>Error Screen</title>
</head>

<body>
<%exception.printStackTrace();%>
<center>
<font face="arial" size="2">
<% try
{
%>

    <h1><%=AppSettings.getParm("COMPANY_NAME")%></h1>
<%
    }catch(Exception e) {}
%>

<h2> Your request could not be processed </h2>
<p>If you feel this is a permanent error please report to <a href="mailto:john4@mail.randrinc.com">john4@mail.randrinc.com</a></p>
<hr>
<p>
[<%=exception.getMessage()%>]
</p>
</center>

</body>
</html>
