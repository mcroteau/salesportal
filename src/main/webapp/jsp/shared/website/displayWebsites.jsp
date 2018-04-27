<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.website.WebsiteDetails,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.util.DateUtilities,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.util.Enumeration" %>

<jsp:useBean id="websiteView" class="com.randr.webdw.website.WebsiteView" scope="request"/>

<% try { %>
<html>

<head>
        <title>Prospect Websites</title>
        <meta http-equiv="expires" content="0">
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("PUBLIC_SITE_CSS")%>">
        <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/website/displayInsertUpdateWebsite.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
        </script>

</head>

<body>
<center>
<h1>Prospect Websites</h1>

<br><br>
<form name="frmWebsites" action="website">
    <input type="hidden" name="formAction" value="">
    <input type="hidden" name="dfProspectId" value="<%=request.getParameter("dfProspectId")%>">
    <input type="hidden" name="dfWebsiteId" value="">

<input type="hidden" name="oldFormAction" value="<%=request.getParameter("formAction")%>">
<input type="hidden" name="oldAction" value="prospect">
</form>

<a href="javascript:fOnAddWebsite();">Add Website</a><br><br>
<%  if (request.getAttribute("modelException") != null) {
        ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
        <p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%">
    <tr>
        <th align=center>URL</th>
        <th align=center>Description</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
    </tr>
        <% for (int i=0; i<websiteView.getElements().size();i++) {
                WebsiteDetails websiteDetails = (WebsiteDetails) websiteView.getElements().elementAt(i); %>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td valign="top" nowrap align="left">
            <% String link=websiteDetails.getUrl();
                if (!websiteDetails.getUrl().startsWith("http://")
                    && !websiteDetails.getUrl().startsWith("https://")){
                    link="http://"+ websiteDetails.getUrl();
                }
            %>
            <a href="<%=link%>" target="_blank">
                <%=websiteDetails.getUrl()%>
            <a>
        </td>
        <td valign="top" align="left"><%=websiteDetails.getDescription()%></td>
        <td valign="top"><a href="javascript:fOnUpdateWebsite(<%=websiteDetails.getWebsiteId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Website"></a></td>
        <td valign="top"><a href="javascript:fOnDeleteWebsite(<%=websiteDetails.getWebsiteId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Website"></a></td>
    </tr>
        <% } %>
    </table>
<% } %>
    <br><br>
    <a href="javascript:window.close()" class="button">&nbsp; &nbsp;Close&nbsp;&nbsp;</a>
    </center>
    </body>
    </html>
    <% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {} %>