<%@page contentType="text/html; charset=iso-8859-1" language="java" import="com.randr.webdw.AppSettings, com.randr.webdw.util.Utilities" %>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>


<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Home", "","", "")%>
<%=AppSettings.parseCompanyInfo(AppSettings.getParm("HOME_PAGE_CONTENT"))%>
<%=AppSettings.getFooter(userProfile)%>
