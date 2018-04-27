<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<html>
    <head>
        <title>Insert/Update User </title>
        <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
    </head>
<body>

<jsp:include page="/jsp/shared/user/displayInsertUpdateUser.jsp" flush="true"/>

</body>
</html>