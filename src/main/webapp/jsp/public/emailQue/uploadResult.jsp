<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.prospect.ProspectDetails,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.math.BigDecimal,
                java.text.SimpleDateFormat,
                com.randr.webdw.util.DateUtilities,
                java.util.Enumeration,
                com.randr.webdw.util.CollectionUtilities,
                com.randr.webdw.label.LabelView" %>

<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="sortByField" class="java.lang.String" scope="request"/>
<jsp:useBean id="sortByDirection" class="java.lang.String" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Prospects", "","", "")%>

<% try { %>

<form name="frmProspectUpload" method="post" action="notify_upload">
<input type="hidden" name="formAction" value="NOTIFY_EMAIL_QUE_UPLOAD">

<h1>Send Order Portal Email</h1>


<p>The Prospects have been sent to Order Portal</p>
</form>


<% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
<% e.printStackTrace(); } %>
    
<%=AppSettings.getFooter(userProfile)%>