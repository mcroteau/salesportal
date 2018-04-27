<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings,
        com.randr.webdw.user.UserProfile,
        java.math.BigDecimal,
        java.util.Vector,
        com.randr.webdw.util.Utilities" %>
<%@page import = "org.jdom.Element" %>
<%@page import = "java.util.LinkedList" %>
<%@ page import="java.net.URLEncoder" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<center>
<% try{ %>

    <%=AppSettings.getHeader(request, userProfile, "Configuration Site - " + AppSettings.getParm("COMPANY_NAME"), "","", "")%>
    <h1>Custom Pages</h1>
<% if (AppSettings.elementExists("CUSTOM_PAGES")) {
    LinkedList customPages = (LinkedList) AppSettings.getElement("CUSTOM_PAGES").getChildren();
    if (customPages.size() > 0) { %>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/options.js"></script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/openHTMLEditor.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
     </script>

     <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
        <input type=hidden name="formAction" value="UPDATE_CUSTOM_PAGES_OPTIONS">
        <input type=submit name="pbSubmit" value="Save Configurations">
        <input type="button" value="Close Window" onclick="javascript:window.close();">


    <%
    Element customPageElement = null;
    String oldSection = "";
    for (int i=0; i<customPages.size();i++) {
        customPageElement = (org.jdom.Element) customPages.get(i);
        String menuId = customPageElement.getChild("MENU_ID").getText();
        String menuOptionsEnabledName = "";
        String menuOptionsListName = "";
        if (customPageElement.getChild("USER_TYPE").getText().equals("ALL"))
        {
            menuOptionsEnabledName = "MENU_OPTIONS_ENABLED_1";
        }
        else
        {
            menuOptionsEnabledName = "MENU_OPTIONS_ENABLED_"+customPageElement.getChild("USER_TYPE").getText();
        }
        Vector menuOptionsEnabledList = Utilities.tokenize(AppSettings.getParm(menuOptionsEnabledName), ",");

        menuOptionsListName = "MENU_OPTIONS_LIST";

        Vector menuOptionsList = Utilities.tokenize(AppSettings.getParm(menuOptionsListName),",");
        int pos = menuOptionsList.indexOf(menuId);

        String menuFlag = (String) menuOptionsEnabledList.elementAt(pos);
        if (menuFlag.equals("1"))
        {
            menuFlag = "YES";
        }
        else
        {
            menuFlag = "NO";
        }

        if (!oldSection.equals(customPageElement.getChild("SECTION").getText())) {
            if (!oldSection.equals("")) { %>
                <%       } %>
                <hr width="500">
                <h2><%=customPageElement.getChild("SECTION").getText()%></h2>
            <%       oldSection = customPageElement.getChild("SECTION").getText();
        } %>

        <table border="0" cellspacing="0" cellpadding="2" width="60%">
        <tr>
        <th align="left">User type:</th>
        <td align="left">
            <% if (customPageElement.getChild("USER_TYPE").getText().equals("ALL")) { %>
            All
            <% } else { %>
            <%=UserProfile.userTypeDescription[new BigDecimal(customPageElement.getChild("USER_TYPE").getText()).intValue() - 1]%>
            <% } %>
        </td>
        </tr>
        </tr>
        <tr>
        <th align="left">Menu Entry:</th>
        <td ><select name="OPT_AVAILABLE_FOR_MENU_<%=customPageElement.getName()%>"><option value="YES">Enabled<option value="NO">Disabled</select></td>
            <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_AVAILABLE_FOR_MENU_<%=customPageElement.getName()%>,'<%=menuFlag%>','YES');</script>
        </tr>

        <tr>
            <th align="left" width="25%">Menu label:</th>
            <td align="left"><input type="text" name="OPT_MENU_TEXT_<%=customPageElement.getName()%>" value="<%=customPageElement.getChild("MENU_TEXT").getText()%>" size="20"></tr>
        </tr>

        <tr>
            <th align="left"><%=customPageElement.getChild("LABEL").getText()%>:</th>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
            <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_CONTENT_<%=customPageElement.getName()%>','<%=URLEncoder.encode(customPageElement.getChild("LABEL").getText())%>','0')"> </td>
<% } else { %>
            <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_CONTENT_<%=customPageElement.getName()%>','<%=URLEncoder.encode(customPageElement.getChild("LABEL").getText())%>','0')"> </td>
<% } %>

        </tr>
        <tr>
            <td colspan="2"><TEXTAREA NAME="OPT_CONTENT_<%=customPageElement.getName()%>" wrap="off" rows="10" cols="130" onblur="fOnTextareaBlur(this);"><%=customPageElement.getChild("CONTENT").getText()%></TEXTAREA></td>
        </tr>
        </table>
        <%   } %>

    </form>
<% }
} %>
<%=AppSettings.getFooter(userProfile)%>
<% } catch (Exception e) { %>
    Error: <b><%=e.getMessage()%></b>
    <%   e.printStackTrace();
} finally {
}
%>
</center>
