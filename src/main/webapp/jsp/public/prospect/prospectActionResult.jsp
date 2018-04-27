<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="autoAssignMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Prospect", "","", "")%>


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
     
     
	 <center>

	 <h1><%=actionDescription%> Prospect</h1>
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
    <p class="success">Prospect ID = <%= prospectDetails.getProspectId()%>
   	Click <a href="<%=AppSettings.getAppWebPath()%>prospect?formAction=DISPLAY_ONE_PROSPECT&ProspectID=<%=prospectDetails.getProspectId()%>">here</a> to review and update Prospect.</p>
    <br>
    <p class="success"><%=autoAssignMessage%></p>
    <%
        if (!actionDescription.equals("Insert"))
        {
    %>
<%--    <script>--%>
<%--        if (window.opener.name != "wndMainAdmin")--%>
<%--        {--%>
<%--            window.opener.location.reload();--%>
<%--            window.close();--%>
<%--        }--%>
<%--    </script>--%>
<%
        }
    }
%>

    <hr><br>
    <%
    if (((Boolean) request.getAttribute("isAdmin")).booleanValue())
    {
    %>
    <a href="javascript:window.close()" class="button">&nbsp; &nbsp;Close&nbsp;&nbsp;</a>
    <%
    }
    %>
	 </center>
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
<%=AppSettings.getFooter(userProfile)%>