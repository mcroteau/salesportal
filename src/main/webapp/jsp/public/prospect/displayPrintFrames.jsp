<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<html>
<head>
<title>
    Print Prospect
</title>
</head>

<frameset rows="15%,85%" border="0">
<frame src="<%=AppSettings.getAppWebPath()%>prospect_print?formAction=DISPLAY_PRINT_TOP_FRAME">
<frame src="<%=AppSettings.getAppWebPath()%>prospect_print?formAction=DISPLAY_PRINT_PROSPECT_DETAILS&dfProspectId=<%=request.getParameter("dfProspectId")%>">
</frameset>
</html>

