<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings,
        com.randr.webdw.user.UserProfile,
        java.math.BigDecimal" %>
<%@ page import="java.net.URLEncoder" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<% try{ %>
<%=AppSettings.getHeader(request, userProfile ,"Configuration Site - " + AppSettings.getParm("COMPANY_NAME"), "","", "")%>

    <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/options.js"></script>
    <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/createCustomPage.js"></script>
    <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/openHTMLEditor.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>"></script>

    <h1>Create Custom Page</h1>
    <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
        <input type=hidden name="formAction" value="CREATE_CUSTOM_PAGE">
        <input type=submit name="pbSubmit" value="Create Page" onclick="return fValidateForm()">
        <input type="button" value="Close Window" onclick="javascript:window.close();">
        <br><br><br>
           <table border="0" class="form">
           <tr>
            <th align="left" width="25%">Page Title:</th>
            <td align="left"><input type="text" name="OPT_TITLE_NEW" value="" size="40" maxlength="50"></tr>
           </tr>

           <tr>
            <th align="left" width="25%">User type:</th>
            <td align="left">
                <select name="OPT_USER_TYPE_NEW">
                    <option value="ALL">ALL
                    <% for (int i = 0; i < UserProfile.userTypeDescription.length; i++) { %>
                        <option value="<%=(i + 1)%>"><%=UserProfile.userTypeDescription[i]%>
                    <% } %>
                </select>
            </td>
            </tr>
           <tr>
            <th align="left" width="25%">Menu Entry:</th>
            <td align="left">
                <select name="OPT_AVAILABLE_FOR_MENU_NEW">
                    <option value="YES">Enabled
                    <option value="NO">Disabled
                </select>
            </td>
            </tr>
           <tr>
            <th align="left" width="25%">Menu label:</th>
            <td align="left"><input type="text" name="OPT_MENU_TEXT_NEW" value="" size="20" ></tr>
           </tr>

           <tr>
             <th align="left">Page content:</th>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_CONTENT_NEW','Page Content','0')"> <br> </td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_CONTENT_NEW','Page Content','0')"> <br> </td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_CONTENT_NEW" wrap="off" ROWS=10 COLS=80 onblur="fOnTextareaBlur(this);"></TEXTAREA></td>
           </tr>
           </table>

    </form>
<%=AppSettings.getFooter(userProfile)%>
    <% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {
}
%>
