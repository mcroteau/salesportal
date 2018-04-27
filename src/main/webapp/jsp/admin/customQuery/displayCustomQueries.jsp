
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@ page import="com.randr.webdw.AppSettings"%>

<html>
<head>
	<title>Custom Queries</title>
	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
</head>
<body>
<jsp:include page="/jsp/shared/customQuery/displayCustomQueries.jsp" flush="true"/>
</body>
</html>