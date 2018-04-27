<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.user.UserDetails,
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
     <form name="frmActionResult" method="get" action="">
        <input type=hidden name="formAction" value="">
        <input type=hidden name="userType" value="<%=UserDetails.USER_TYPE_SALESMAN%>">
	 <center>

	 <h1><%=actionDescription%>User</h1>
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
    <%
        if (request.getServletPath().startsWith( "/admin")) {
        	if (!actionDescription.equals("Insert")){
    %>
    <script>
        if (window.opener.name != "wndMainAdmin"){
        	
            window.opener.location.reload();
            window.close();
        }
        
    </script>
<%
        }
        }
    }
%>

    <hr><br>
    <%
    if (((Boolean) request.getAttribute("isAdmin")).booleanValue()){%>
		<a href="javascript:if(refreshSalesmanPage('user','width=1400, height=600,resizable=yes,toolbar=yes,location=no,status=yes,scrollbars=yes','DISPLAY')) window.close();" class="button">&nbsp; &nbsp;Close&nbsp;&nbsp;</a>	
    <%}%>
	 </center>
	 <script>
     	function refreshSalesmanPage(page, attributes, formActionVal) {
             window.name='wndMainAdmin';
             if (attributes==''){
                 attributes='resizable=yes,toolbar=yes,location=no,status=yes,scrollbars=yes';
             }

             if (navigator.appName=='Netscape'){
                 attributes += ',scrollbars=yes';
             }

             var wAdmin = window.open('',page,attributes);
             wAdmin.opener=window;
             document.frmActionResult.action='<%=AppSettings.getAppCompleteWebPath(request)%>admin/' + page;
             document.frmActionResult.formAction.value=formActionVal;

             document.frmActionResult.target = page;
             document.frmActionResult.submit();
             wAdmin.focus();
             
             return true;
         }
        
    </script>
<%}catch (Exception e){%>
     Error: <b><%=e.getMessage()%></b>
	<%e.printStackTrace();

}finally{

} %>
