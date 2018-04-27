<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings,
        com.randr.webdw.util.Utilities"
 %>

<% try{
    String graphicsCompletePath = AppSettings.getGraphicsCompleteWebPath(request);
    String graphicsPath = AppSettings.getGraphicsPath();
%>


     <SCRIPT language="javascript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/ekitEditor.js">
     </SCRIPT>
     <SCRIPT language="javascript">
     var graphicsPath = '<%=graphicsPath%>';
     var graphicsCompletePath = '<%=graphicsCompletePath%>';
     var isCommonElement = <%=request.getParameter("isCommonElement")%>;
     var fieldType = '<%=request.getParameter("fieldType")%>';
     </SCRIPT>

     <center>
     <b><%=request.getParameter("fieldName")%></b>
     <br><br><br>

     <OBJECT classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
             width="100%" height="80%"
             name="Ekit"
             codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_3_1_02-win.cab#Version=1,3,1,0">
        <PARAM NAME="code" VALUE="com.hexidec.ekit.EkitApplet.class">
        <PARAM NAME="archive" VALUE="<%=AppSettings.getAppWebPath()%>applets/ekitapplet2.jar">
        <PARAM NAME="name" VALUE="Ekit">
        <PARAM NAME="type" VALUE="application/x-java-applet;version=1.4">
        <PARAM NAME="scriptable" VALUE="true">
        <PARAM NAME="MAYSCRIPT" VALUE="true">

        <PARAM NAME="DOCUMENT" VALUE="">
        <PARAM NAME="BASE64" VALUE="false">
<%--        <PARAM NAME="STYLESHEET" VALUE="<%=AppSettings.getAppWebPath()%>css/public.css">--%>
        <PARAM NAME="STYLESHEET" VALUE="">
        <PARAM NAME="LANGCODE" VALUE="en">
        <PARAM NAME="LANGCOUNTRY" VALUE="US">
        <PARAM NAME="TOOLBAR" VALUE="true">
        <PARAM NAME="SOURCEVIEW" VALUE="false">
        <PARAM NAME="EXCLUSIVE" VALUE="true">
        <PARAM NAME="MENUICONS" VALUE="true">
        <PARAM NAME="MENU_EDIT" VALUE="true">
        <PARAM NAME="MENU_VIEW" VALUE="true">
        <PARAM NAME="MENU_FONT" VALUE="true">
        <PARAM NAME="MENU_FORMAT" VALUE="true">
        <PARAM NAME="MENU_INSERT" VALUE="true">
        <PARAM NAME="MENU_TABLE" VALUE="true">
        <PARAM NAME="MENU_FORMS" VALUE="false">
        <PARAM NAME="MENU_SEARCH" VALUE="true">
        <PARAM NAME="MENU_TOOLS" VALUE="true">
        <PARAM NAME="MENU_HELP" VALUE="true">
     </OBJECT>

     <form name="frmConfig">
     <input type=button value="Apply Changes" onclick="fOnClick();">
     <input type=button value="Cancel" onclick="window.close();">
     </form>

     </center>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
