<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<html>
    <head>
        <title>Display Revenue/Commissions </title>
        <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
    </head>
<body>

<jsp:include page="/jsp/shared/commision/displaySearchCommisions.jsp" flush="true"/>

</body>
</html>