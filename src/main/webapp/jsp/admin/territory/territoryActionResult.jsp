<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.territory.TerritoryDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<% try{ %>

     <%
    String actionDescription = "Update";

    if (formAction.equals("INSERT"))
    {
        actionDescription = "Insert";
    }
    else if (formAction.equals("UPDATE"))
    {
        actionDescription = "Update";
    }
    else if (formAction.equals("DELETE"))
    {
        actionDescription = "Delete";
    }


     %>
     <html>
     <head>
     	<title><%=actionDescription%> Territory</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
     </head>

     <body>
	 <center>

	 <h1><%=actionDescription%> Territory</h1>
     <br><hr>
<%
    if (request.getAttribute("modelException") != null)
    {
        ModelException modelException = (ModelException) request.getAttribute("modelException");
%>
		<p class="exception"><%=modelException.getMessage()%></p>
<%
    }
    else
    {
%>
    <p class="success">Operation successful.</p>

    <script>
        if (window.opener.name != "wndMainAdmin")
        {
            window.opener.location.reload();
            window.close();
        }
    </script>
<%
    }
%>

    <hr><br>
    <a href="javascript:window.close()" class="button">&nbsp; &nbsp;Close&nbsp;&nbsp;</a>

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
