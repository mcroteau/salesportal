<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Sales Commisions", "","", "")%>
<jsp:include page="/jsp/shared/commision/displayCommisions.jsp" flush="true"/>
<%=AppSettings.getFooter(userProfile)%>
