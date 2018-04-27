<%@page contentType="text/html; charset=iso-8859-1" language="java" import="com.randr.webdw.AppSettings"%>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:setProperty name="userProfile" property="*"/>
<%=AppSettings.getHeader(request,userProfile,"Custom Queries","","","")%>
<jsp:include page="/jsp/shared/customQuery/displayCustomQueries.jsp" flush="true"/>
<%=AppSettings.getFooter(userProfile)%>
