<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.campaign.CampaignDetails,
                com.randr.webdw.prospect.ProspectDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>

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
     	<title><%=actionDescription%> Campaign</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>?Timestamp=String.valueOf((new java.util.Date()).getTime())">
     </head>

     <body>
	 <center>

	 <h1><%=actionDescription%> Campaign</h1>
     <br><hr>

<%
    if (request.getAttribute("modelException") != null){
        ModelException modelException = (ModelException) request.getAttribute("modelException");%>

		<p class="exception"><%=modelException.getMessage()%></p>
		<div id="list">
		<%if(prospectView.getElements().size() > 0){%>
        	<ol>
        	<%for(int i = 0; i < prospectView.getElements().size(); i++){
        		 ProspectDetails details = (ProspectDetails)prospectView.getElement(i);%>
        		<li><%=details.getCustomerCompany()%></li>
        	
        	<%} %>
        	</ol>
        <%} %>
        </div>
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
