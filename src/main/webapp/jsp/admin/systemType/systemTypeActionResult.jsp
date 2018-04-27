<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.systemType.SystemTypeDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.label.LabelView" %>

<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

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
     	<title><%=actionDescription%> <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
     </head>

     <body>
	 <center>

	 <h1><%=actionDescription%> <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></h1>
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
