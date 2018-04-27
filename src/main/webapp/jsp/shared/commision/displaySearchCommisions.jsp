<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.commision.CommisionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.DateUtilities" %>
<jsp:useBean id="isAdmin" type="java.lang.Boolean" scope="request"/>

<% try{ %>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/commision/displayCommisions.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

        <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

	 <center>

	 <h1>Search Revenue/Commissions</h1>

     <form name="frmCommision" method="post" action="commision">

	     <input type="hidden" name="formAction" value="DISPLAY">

         <table class="form">
         <tr><td colspan="2">&nbsp;</td></tr>
         <tr>
            <th align="right">Status:</th>
            <td><select name="dfStatus">
                <option value="">All
                <% for (int i = 1; i <= CommisionDetails.getStatusDescriptionLength(); i++) { %>
                    <option value="<%=i%>"><%=CommisionDetails.getStatusDescription(i)%>
                <% } %>
                </select>
            </td>
         </tr>
         <tr>
            <th align="right">Sold between:</th>
            <td>
            <input type="text" name="dfDateSold1" size="12" onchange="javascript:void(0);"><a href="javascript:calDateSold1.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>&nbsp;-&nbsp;<input type="text" name="dfDateSold2" size="12" onchange="javascript:void(0);"><a href="javascript:calDateSold2.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
         </tr>
         <tr>
            <th align="right">Paid between:</th>
            <td>
            <input type="text" name="dfDatePaid1" size="12" onchange="javascript:void(0);"><a href="javascript:calDatePaid1.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>&nbsp;-&nbsp;<input type="text" name="dfDatePaid2" size="12" onchange="javascript:void(0);"><a href="javascript:calDatePaid2.popup();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            </td>
         </tr>
         <tr><td colspan="2">&nbsp;</td></tr>
         <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Search">
            </td>
         </tr>
         <tr><td colspan="2">&nbsp;</td></tr>
         </table>

	 </form>

    <script>
			var calDateSold1 = new calendar2(document.forms['frmCommision'].elements['dfDateSold1']);
			calDateSold1.year_scroll = true;
			calDateSold1.time_comp = false;

			var calDateSold2 = new calendar2(document.forms['frmCommision'].elements['dfDateSold2']);
			calDateSold2.year_scroll = true;
			calDateSold2.time_comp = false;

			var calDatePaid1 = new calendar2(document.forms['frmCommision'].elements['dfDatePaid1']);
			calDatePaid1.year_scroll = true;
			calDatePaid1.time_comp = false;

			var calDatePaid2 = new calendar2(document.forms['frmCommision'].elements['dfDatePaid2']);
			calDatePaid2.year_scroll = true;
			calDatePaid2.time_comp = false;

        </script>
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
