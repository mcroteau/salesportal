<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings,
        com.randr.webdw.util.Utilities"
 %>
<jsp:useBean id="appSettings" class="com.randr.webdw.AppSettings" scope="application"/>
<jsp:useBean id="webProfileDetails" class="com.randr.orderportal.webprofile.WebProfileDetails" scope="request"/>
<jsp:useBean id="webProfileFile" class="java.lang.String" scope="session"/>

<% try{
	String cssPath = appOrderportalSettings.getAppCompleteWebPath(request) + "css/orderportal/public.css?Timestamp=" + String.valueOf((new java.util.Date()).getTime());
    System.out.println("cssPath = " + cssPath);
    String graphicsCompletePath = appOrderportalSettings.getGraphicsCompleteWebPath(request);
    String graphicsPath = appOrderportalSettings.getGraphicsPath();
%>

<%=appOrderportalSettings.getHeader(request, response, null, 1 , "TINY MCE EDITOR - " + appOrderportalSettings.getParm("COMPANY_NAME", webProfileFile), "", "", "", false, webProfileFile)%>

     <SCRIPT language="javascript" src="<%=appOrderportalSettings.getAppWebPath()%>scripts/orderportal/configurations/tinyMceEditor.js"></script>
     <script language="JavaScript" src="<%=appOrderportalSettings.getAppWebPath()%>scripts/common/thirdparty/tiny_mce/tiny_mce.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>

     <script language="javascript" type="text/javascript">
     var cssPath = '<%=cssPath%>';
     var graphicsPath = '<%=graphicsPath%>';
     var graphicsCompletePath = '<%=graphicsCompletePath%>';
     var isCommonElement = <%=request.getParameter("isCommonElement")%>;
     var fieldType = '<%=request.getParameter("fieldType")%>';
	 tinyMCE.init({		
	 	content_css : cssPath,
	 	theme : "advanced",
	 	convert_urls : false,
		mode : "textareas", apply_source_formatting : true});
		
	</script>

     <center>
     <b><%=request.getParameter("fieldName")%></b>
     <br><br><br>

     <form name="frmConfig">
     <textarea id="content" name="htmlContent" rows="30" cols="120"></textarea>
     <input type=button value="Apply Changes" onclick="fOnClick();">
     <input type=button value="Cancel" onclick="window.close();">
     </form>
     
     <script>establishBrowserContent();
     </script>

     </center>

<%=appOrderportalSettings.getFooter(request, response, null, webProfileFile)%>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
