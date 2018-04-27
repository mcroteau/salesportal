<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.size.SizeDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="sizeDetails" class="com.randr.webdw.size.SizeDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

<% try{ %>

	<%

	String actionDescription = "Update";
	String newFormAction="UPDATE";

	if (formAction.equals("DISPLAY_INSERT"))
	{
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
	%>
     <html>
     <head>
     	<title><%=actionDescription%> Size</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/size/displayInsertUpdateSize.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Size</h1>
     <hr><br>
     <form name="frmSize" method="post" action="size" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">



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
		<table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
		<%
		if (formAction.equals("DISPLAY_UPDATE"))
		{
		%>
			<tr>
				<th align="right">Id:</th>
				<td>
 					<%=sizeDetails.getSizeId()%>
					<input type="hidden" name="dfSizeId" value="<%=sizeDetails.getSizeId()%>">
				</td>
			</tr>
		<%
		}
		%>

		<tr>
			<th align="right">Size Name:</th>
			<td><input type="text" name="dfSize" size="40" maxlength="50" value="<%=Utilities.nullToBlank(sizeDetails.getSize())%>"></td>
		</tr>

        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if (fProcessForm()) {document.frmSize.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>


		</table>
<%
	}
%>
	 </form>
	 </center>

     <script>
        document.frmSize.dfSize.focus();
     </script>
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
