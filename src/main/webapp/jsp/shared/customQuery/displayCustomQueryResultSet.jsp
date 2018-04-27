<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.util.Utilities,
                 java.util.Vector,
                 java.math.BigDecimal"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>

<jsp:useBean id="resultVector" class="java.util.Vector" scope="session"/>
<jsp:useBean id="customQueryDetails" class="com.randr.webdw.customQuery.CustomQueryDetails" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<script>
function fOnDownload(filename){
    document.frmCSVDownload.action='query.csv';
    document.frmCSVDownload.formAction.value='DOWNLOAD';
    document.frmCSVDownload.submit();
}
</script>
<% try{ %>
<center>


<form name="frmCSVDownload" method="post" action="scmportal/query">
    <input type="hidden" name="formAction" value="DOWNLOAD">
    <input type="hidden" name="resultVector" value="<%=resultVector%>">
</form>
<center>
<h1 style="font-family: arial; font-size: 13px"><%=customQueryDetails.getCustomQueryName()%></h1>
<p align="center" style="font-family: arial; font-size: 12px"><%=customQueryDetails.getCustomQueryDescription()%></p>
<br><a href="javascript:fOnDownload();">Download CSV File</a><br><br>

<table border="1" class="report" border="1" cellspacing="0" cellpadding="3">
     <%
    if (resultVector.size() > 0)
    {
        Vector header = (Vector) resultVector.elementAt(0);
        int nColumns = header.size();
     %>
     <tr>
	 <%
        for (int i = 0; i< nColumns; i++)
        {
     %>
     <th class="dark"><%=header.elementAt(i)%></th>
	<%
        }
    %>
    </tr>
	<%
        for (int rowNo = 1; rowNo < resultVector.size(); rowNo++)
        {
    %>
    <tr>
	 	<%
            Vector row = (Vector) resultVector.elementAt(rowNo);
            nColumns = row.size();
            for (int i = 0; i < nColumns; i++)
            {
         %>
         <td><%=Utilities.nullToString((String) row.elementAt(i), " &nbsp;")%></td>
		<%
            }
        %>
        </tr>
	<%
        }
    }
    %>
    </table>
    </center>

    <% }
catch (Exception e)
{ %>
Error: <b><%=e.getMessage()%></b>
    <%   	e.printStackTrace();
}
finally
{
}
%>