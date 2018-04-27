<%@ page import="com.randr.webdw.AppSettings"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<html>
<head>
<title>
    Print Prospect
</title>
    <link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
</head>

<body>
<br>
<center>
    <form>
    <table border="0">
    <tr>
        <td align="left">
            <input type="button"  name="btnPrint" value="Print" width="100"
            onclick="top.frames[1].focus();top.frames[1].print()">
        </td>
        <td>&nbsp;</td>
        <td align="right">
            <input type="button"  name="btnPrint" value="Close" width="100"
            onclick="top.close();">
        </td>

    </tr>

    </table>
    </form>
   </center>
</body>