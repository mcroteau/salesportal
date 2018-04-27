<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.lob.LobDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<% try{ %>

    
     <html>
     <head>
     	<title> Lob</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
     </head>

     <body>
	 <center>

	 <h1>Campaign was added to each prospect in the collection.</h1>
     <br>
    <hr><br>
    <a href="javascript:window.close()" class="button">&nbsp; &nbsp;Close to Continue&nbsp;&nbsp;</a>

	 </center>
     </body>
     </html>
<%
    }
    catch (Exception e)
    {
%>
     Error: <b><%=e.getMessage()%></b>
<%
        e.printStackTrace();
    }
    finally
    {

       } %>
