<%@page contentType="text/plain; charset=iso-8859-1" language="java" import="com.randr.webdw.AppSettings" %>
<%@page import="com.randr.webdw.staticPages.MenuView" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<%=MenuView.getMenu(userProfile.getUserType(),request)%>
<%=MenuView.getMenuVars()%>