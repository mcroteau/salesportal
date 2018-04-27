<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.importData.ImportDataException,
                 com.randr.webdw.util.Utilities,
                 java.util.GregorianCalendar,
                 java.util.Calendar" %>

<% try{
    GregorianCalendar date1GregorianCalendar = new GregorianCalendar();
    date1GregorianCalendar.add(GregorianCalendar.DATE,
                                  Integer.parseInt(Utilities.blankToString(AppSettings.getParm("DEFAULT_DATE1"), "0") ));
    GregorianCalendar date2GregorianCalendar = new GregorianCalendar();
    date2GregorianCalendar.add(GregorianCalendar.DATE,
                                  Integer.parseInt(Utilities.blankToString(AppSettings.getParm("DEFAULT_DATE2"), "0") ));
    String strDate1 = date1GregorianCalendar.get(Calendar.MONTH)+1
                             + "/" + date1GregorianCalendar.get(Calendar.DAY_OF_MONTH)
                             + "/" + date1GregorianCalendar.get(Calendar.YEAR);
%>

<html>
<head>
<title>Import Date</title>
<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js"></script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/util.js"></script>
</head>


<body>
<center>
<h1>Remove Data</h1>
<form name="frmDeleteProspects" method=post action="import_data">
    <input type="hidden" name="formAction" value="DELETE">
    <INPUT type="hidden" name="dfTime1" size="6" value="00:00">
    Remove all prospects imported this date and after:<br><br>
    <TABLE border="0" cellpadding="0" cellspacing="0">
				<TBODY>
					<TR>
                        <TH align="right">&nbsp;<FONT color="red">*</FONT>Date:&nbsp;</TH>
						<TD>
                            <INPUT type="text" name="dfDate1" size="12"  onchange="void(0);"
							value="<%=Utilities.blankToString(request.getParameter("dfDate1"), strDate1)%>"><a href="javascript:calDate1.popup();"><img
                            src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
                        </TD>
					</TR>
				</TBODY>
			</TABLE>
    <br><hr>
    <input type="submit" value="Delete">
</form>
<script>
			var calDate1 = new calendar2(document.forms['frmDeleteProspects'].elements['dfDate1']);
			calDate1.year_scroll = true;
			calDate1.time_comp = false;
</script>
</center>
</body>
</html>

    <% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {} %>