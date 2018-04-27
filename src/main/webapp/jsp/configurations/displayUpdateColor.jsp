<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings" %>

<% try{ %>

     <html>
     <head>
     <meta http-equiv="expires" content="0">
     <title>Color picker for html</title>
     <script language="JavaScript">
     function applyColor() {
<%   if (request.getParameter("fieldType").equals("fg_color")) { %>     
       window.opener.fg_color_posa(document.frmColor.COLOR.value);
<%   } else if (request.getParameter("fieldType").equals("bg_color")) { %>
       window.opener.bg_color_posa(document.frmColor.COLOR.value);
<%   } else { %>
       window.opener.document.frmConfig.<%=request.getParameter("fieldType")%>.value=document.frmColor.COLOR.value;
<%   } %>
     window.close();
     }
     </script>
     </head>

     <body>
     <center>
     Please specify the <b><%=request.getParameter("fieldName")%><b><br><br> 
     <APPLET code="colorpicker.class" codebase="<%=AppSettings.getAppWebPath()%>applets/" width=306 height=200 MAYSCRIPT>
     <PARAM NAME="initialColor" VALUE="<%=request.getParameter("initialColor")%>">
     <H2>Java applet is here. <BR>
     Please use Java enabled browser.</H2>
     </APPLET>

     <FORM name="frmColor">
     <input type=button value="Apply Color" onclick="applyColor()">
     <input type=button value="Cancel" onclick="window.close()">
     <input type=hidden name="COLOR" value="<%=request.getParameter("initialColor")%>">
     </FORM>

     </center>
     </body>
     </html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
