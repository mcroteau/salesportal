<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings,
        java.util.Vector,
        com.randr.webdw.util.Utilities,
        com.randr.webdw.user.UserDetails,
        java.math.BigDecimal" %>
<%@ page import="java.net.URLEncoder" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<% try{ %>
<%=AppSettings.getHeader(request, userProfile ,"Configuration Site :: Page Layout Options - " + AppSettings.getParm("COMPANY_NAME"), "","", "")%>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/options.js">
     </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/pageLayoutOptions.js">
     </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/openHTMLEditor.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
     </script>

     <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
     <input type=hidden name="formAction" value="UPDATE_PAGE_LAYOUT_OPTIONS">

     <table border="0" align="center"><tr><td>


     <h1>Page Layout Options</h1>
     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidAppConfig()">
     <hr>

     <table border="0" align="center">
<%// Menu Configuration section ....................................%>
      <tr>
        <th colspan="2"><h2>Menu configuration</h2></th>
      </tr>

      <tr>
        <td width="200"><b>Hierarchical Menu Enabled</b></td>
        <td ><select name="OPT_JAVASCRIPT_MENU_ENABLED"><option value="YES">YES<option value="NO">NO</select></td>
        <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_JAVASCRIPT_MENU_ENABLED,'<%=AppSettings.getParm("JAVASCRIPT_MENU_ENABLED")%>','YES');</script>
      </tr>

      <tr>
        <th  align="right">Menu vertical position (px):</th>
        <td><input name="OPT_MENU_VERTICAL_POSITION" value="<%=AppSettings.getParm("MENU_VERTICAL_POSITION")%>" size=33 onblur="this.value=fTrunc(this.value,0)"></td>
      </tr>
      <tr>

        <th  align="right">Menu horizontal position (px):</th>
        <td><input name="OPT_MENU_HORIZONTAL_POSITION" value="<%=AppSettings.getParm("MENU_HORIZONTAL_POSITION")%>" size=33 onblur="this.value=fTrunc(this.value,0)"></td>
      </tr>
      <tr>

        <th align="right">Menu style:</th>
        <td><select name="OPT_MENU_STYLE"> <option value="0">Vertical <option value="1">Horizontal</select></td>
        <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_MENU_STYLE,'<%=AppSettings.getParm("MENU_STYLE")%>', '');</script>
      </tr>
      <tr>

        <th align="right">Menu option width (px):</th>
        <td><input name="OPT_MENU_OPTION_WIDTH" value="<%=AppSettings.getParm("MENU_OPTION_WIDTH")%>" size=33 onblur="this.value=fTrunc(this.value,0)"></td>
      </tr>
      <tr>

        <th align="right">Submenu option width (px):</th>
        <td><input name="OPT_SUBMENU_OPTION_WIDTH" value="<%=AppSettings.getParm("SUBMENU_OPTION_WIDTH")%>" size=33 onblur="this.value=fTrunc(this.value,0)"></td>
      </tr>
      <tr>

        <th align="right">Menu low background color:</th>
        <td>
        <input name="MENU_LOW_BACKGROUND_COLOR" value="<%=AppSettings.getParm("MENU_LOW_BACKGROUND_COLOR").substring(1)%>" size=33 onblur="fOnFieldBlur(this);">
        <input type=button value="..." onclick="openColorPicker(document.frmConfig.MENU_LOW_BACKGROUND_COLOR.value,'MENU_LOW_BACKGROUND_COLOR','<%=URLEncoder.encode("Menu low background color")%>')">
        <input type=hidden name="OPT_MENU_LOW_BACKGROUND_COLOR" value="">
        </td>
      </tr>
      <tr>

        <th align="right">Menu high background color:</th>
        <td>
        <input name="MENU_HIGH_BACKGROUND_COLOR" value="<%=AppSettings.getParm("MENU_HIGH_BACKGROUND_COLOR").substring(1)%>" size=33 onblur="fOnFieldBlur(this);">
        <input type=button value="..." onclick="openColorPicker(document.frmConfig.MENU_HIGH_BACKGROUND_COLOR.value,'MENU_HIGH_BACKGROUND_COLOR','<%=URLEncoder.encode("Menu high background color")%>')">
        <input type=hidden name="OPT_MENU_HIGH_BACKGROUND_COLOR" value="">
        </td>
      </tr>
      <tr>

        <th align="right">Menu text low color:</th>
        <td>
        <input name="MENU_TEXT_LOW_COLOR" value="<%=AppSettings.getParm("MENU_TEXT_LOW_COLOR").substring(1)%>" size=33 onblur="fOnFieldBlur(this);">
        <input type=button value="..." onclick="openColorPicker(document.frmConfig.MENU_TEXT_LOW_COLOR.value,'MENU_TEXT_LOW_COLOR','<%=URLEncoder.encode("Menu text low color")%>')">
        <input type=hidden name="OPT_MENU_TEXT_LOW_COLOR" value="">
        </td>
      </tr>
      <tr>

        <th align="right">Menu text high color:</th>
        <td>
        <input name="MENU_TEXT_HIGH_COLOR" value="<%=AppSettings.getParm("MENU_TEXT_HIGH_COLOR").substring(1)%>" size=33 onblur="fOnFieldBlur(this);">
        <input type=button value="..." onclick="openColorPicker(document.frmConfig.MENU_TEXT_HIGH_COLOR.value,'MENU_TEXT_HIGH_COLOR','<%=URLEncoder.encode("Menu text high color")%>')">
        <input type=hidden name="OPT_MENU_TEXT_HIGH_COLOR" value="">
        </td>
      </tr>
      <tr>

        <th align="right">Menu border color:</th>
        <td>
        <input name="MENU_BORDER_COLOR" value="<%=AppSettings.getParm("MENU_BORDER_COLOR").substring(1)%>" size=33 onblur="fOnFieldBlur(this);">
        <input type=button value="..." onclick="openColorPicker(document.frmConfig.MENU_BORDER_COLOR.value,'MENU_BORDER_COLOR','<%=URLEncoder.encode("Menu border color")%>')">
        <input type=hidden name="OPT_MENU_BORDER_COLOR" value="">
        </td>
      </tr>
      <tr>

        <th align="right">Menu text font size (pt):</th>
        <td>
           <select name="OPT_MENU_TEXT_FONT_SIZE"><option value="8">8<option value="9">9<option value="10">10<option value="11">11<option value="12">12</select>
           <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_MENU_TEXT_FONT_SIZE,'<%=AppSettings.getParm("MENU_TEXT_FONT_SIZE")%>', '');</script> 
        </td>
      </tr>
      <tr>

        <th align="right">Menu text font family:</th>
        <td><select name="OPT_MENU_TEXT_FONT_FAMILY">
           <option value="Arial,Helvetica"> Arial 
           <option value="Arial Black"> Arial Black 
           <option value="Arial Narrow"> Arial Narrow 
           <option value="Bookman Old Style"> Bookman Old Style 
           <option value="Bookshelf Symbol 1"> Bookshelf Symbol 1 
           <option value="Comic Sans MS"> Comic Sans MS 
           <option value="Courier New,Courier"> Courier New 
           <option value="Garamond"> Garamond 
           <option value="Haettenschweiler"> Haettenschweiler 
           <option value="Impact"> Impact 
           <option value="Lucida Console"> Lucida Console 
           <option value="Lucida Sans Unicode"> Lucida Sans Unicode 
           <option value="Marlett"> Marlett 
           <option value="Monotype Sorts"> Monotype Sorts 
           <option value="MS Outlook"> MS Outlook 
           <option value="Symbol"> Symbol 
           <option value="Tahoma"> Tahoma 
           <option value="Times New Roman,Times"> Times New Roman 
           <option value="Verdana"> Verdana 
           <option value="Webdings"> Webdings 
           <option value="Wingdings"> Wingdings 
       </select>
       <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_MENU_TEXT_FONT_FAMILY,'<%=AppSettings.getParm("MENU_TEXT_FONT_FAMILY")%>', '');</script>
       </td>
      </tr>
        <tr>
            <th valign=top align="right">Menu Sequence:</th>
            <td>
                <table>
                <tr><td>
                <select name="MENU_SEQUENCE" size="12" multiple>
                <%
    Vector menuEntries = Utilities.tokenize(AppSettings.getParm("MENU_OPTIONS_LIST"), ",");
    for (int i = 0; i < menuEntries.size(); i++)
    {
        String menuEntry = (String) menuEntries.elementAt(i);
                %>
                <option value="<%=menuEntry%>"><%--<%=AppSettings.getCustomPageMenuLabel(menuEntry)%> --%>
                
                <%
    }
                %>
                </select>
                </td>
                <td>
                <input type=button value="UP" onclick="fUp();" style="width:70px"><br>
                <input type=button value="DOWN" onclick="fDown();" style="width:70px">
                </td></tr></table>
            </td>
        </tr>
	<tr>

        <th colspan="2"><hr></td>
      </tr>

<%// Common page elements section ..................................%>
      <tr>
        <th colspan="2">
          <h2>Common page elements</h2>
        </td>
      </tr>
      <tr>

        <th>META description: </th>
        <td><input name="OPT_META_DESCRIPTION" value="<%=AppSettings.getParm("META_DESCRIPTION")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>

        <th>META keywords:</th>
        <td><input name="OPT_META_KEYWORDS" value="<%=AppSettings.getParm("META_KEYWORDS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td colspan="2">
        <table border="0">
            <tr>
                <th align="left" colspan="2">Other &lt;HEAD> content:</th>
            </tr>
            <tr>
                <td colspan="2">
                <textarea NAME="OPT_COMMON_PAGE_HEAD" wrap="off" rows="5" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("COMMON_PAGE_HEAD")%></textarea>
                </td>
            </tr>
        </table>
        </td>
      </tr>

<% for (int i =1; i <= UserDetails.userTypeDescription.length; i++) { %>
    <% if (i == 1 ||
            (AppSettings.getParm("USER_AUTHENTICATION_ENABLED").equals("YES") && i > 1)) { %>
      <tr>

        <td colspan="2">
           <table border="0">
           <tr>
             <th align="left">General Page Layout for <%=UserDetails.getUserTypeDescription(new BigDecimal(i), false)%> Users:</th>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('COMMON_BODY_<%=i%>','<%=URLEncoder.encode("General Page Layout for "+UserDetails.getUserTypeDescription(new BigDecimal(i), false)+" Users")%>','1')"></td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('COMMON_BODY_<%=i%>','<%=URLEncoder.encode("General Page Layout for "+UserDetails.getUserTypeDescription(new BigDecimal(i), false)+" Users")%>','1')"></td>
<% } %>

           </tr>
           <tr>
             <td colspan="2"><textarea NAME="COMMON_BODY_<%=i%>" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("COMMON_PAGE_HEADER_"+String.valueOf(i))%>[PAGE_CONTENT]<%=AppSettings.getParm("COMMON_PAGE_FOOTER_"+String.valueOf(i))%></textarea>
             <input type=hidden name="OPT_COMMON_PAGE_HEADER_<%=i%>" value="">
             <input type=hidden name="OPT_COMMON_PAGE_FOOTER_<%=i%>" value="">
             </td>
           </tr>
           </table>
        </td>
      </tr>
      <% } %>
<% } %>

      <tr>

        <th colspan="2"><hr></td>
      </tr>

<%// Home page section .............................................%>
      <tr>
        <th colspan="2">
          <h2>Home Page</h2>
        </td>
      </tr>

<% for (int i =1; i <= UserDetails.userTypeDescription.length; i++) { %>
    <% if (i == 1 ||
            (AppSettings.getParm("USER_AUTHENTICATION_ENABLED").equals("YES") && i > 1)) { %>

      <tr>

        <td colspan="2">
           <table border="0">
           <tr>
             <th align="left">Home Page content for <%=UserDetails.getUserTypeDescription(new BigDecimal(i), false)%> Users:</th>
<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_HOME_PAGE_CONTENT_<%=i%>','<%=URLEncoder.encode("Home Page content for "+UserDetails.getUserTypeDescription(new BigDecimal(i), false))%>','0')"></td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_HOME_PAGE_CONTENT_<%=i%>','<%=URLEncoder.encode("Home Page content for "+UserDetails.getUserTypeDescription(new BigDecimal(i), false))%>','0')"></td>
<% } %>

           </tr>
           <tr>
             <td colspan="2"><textarea NAME="OPT_HOME_PAGE_CONTENT_<%=i%>" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("HOME_PAGE_CONTENT_"+String.valueOf(i))%></textarea></td>
           </tr>
           </table>
        </td>
      </tr>
      <% } %>
<% } %>
      <tr>

        <th colspan="2"><hr></td>
      </tr>

<%// Catalog Main Page section .....................................%>
<%  if ((AppSettings.getParm("CATALOG_STYLE").equals("WEB_PAGE"))) { %>
      <tr>
        <th colspan="2">
          <h2>Catalog Main Page</h2>
        </td>
      </tr>

<% for (int i =1; i <= UserDetails.userTypeDescription.length; i++) { %>
    <% if (i == 1 ||
            (AppSettings.getParm("USER_AUTHENTICATION_ENABLED").equals("YES") && i > 1)) { %>
      <tr>

        <td colspan="2">
           <table border="0">
           <tr>
             <th align="left">Catalog Main Page content for <%=UserDetails.getUserTypeDescription(new BigDecimal(i), false)%> Users:</th>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_CATALOG_MAIN_PAGE_CONTENT_<%=i%>','<%=URLEncoder.encode("Catalog Main Page content for " + UserDetails.getUserTypeDescription(new BigDecimal(i), false))%>','0')"> <br> </td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_CATALOG_MAIN_PAGE_CONTENT_<%=i%>','<%=URLEncoder.encode("Catalog Main Page content for " + UserDetails.getUserTypeDescription(new BigDecimal(i), false))%>','0')"> <br> </td>
<% } %>

           </tr><tr>
             <td colspan="2"><textarea NAME="OPT_CATALOG_MAIN_PAGE_CONTENT_<%=i%>" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("CATALOG_MAIN_PAGE_CONTENT_"+String.valueOf(i))%></textarea></td>
           </tr>
           </table>
        </td>
      </tr>
   <% } %>
<% } %>

      <tr>
        <th colspan="2"><hr></td>
      </tr>
<% } %>

<%// Virtual Showroom Main Page section ............................%>
<%  if (AppSettings.getParm("VIRTUAL_SHOWROOM_ENABLED").equals("YES")
        && (AppSettings.getParm("VIRTUAL_SHOWROOM_STYLE").equals("WEB_PAGE"))) { %>
	  <tr>
        <th colspan="2"><h2>Virtual Showroom Main Page</h2></th>
      </tr>

<% for (int i =1; i <= UserDetails.userTypeDescription.length; i++) { %>
    <% if (i == 1 ||
            (AppSettings.getParm("USER_AUTHENTICATION_ENABLED").equals("YES") && i > 1)) { %>

      <tr>
        <td colspan="2">
           <table border="0">
           <tr>
             <th align="left">Virtual Showroom Main Page content for <%=UserDetails.getUserTypeDescription(new BigDecimal(i), false)%> Users:</th>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_VIRTUAL_SHOWROOM_MAIN_PAGE_CONTENT_<%=i%>','<%=URLEncoder.encode("Virtual Showroom Main Page content for " + UserDetails.getUserTypeDescription(new BigDecimal(i), false))%>','0')"></td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_VIRTUAL_SHOWROOM_MAIN_PAGE_CONTENT_<%=i%>','<%=URLEncoder.encode("Virtual Showroom Main Page content for " + UserDetails.getUserTypeDescription(new BigDecimal(i), false))%>','0')"></td>
<% } %>

           </tr>
           <tr>
             <td colspan="2"><textarea NAME="OPT_VIRTUAL_SHOWROOM_MAIN_PAGE_CONTENT_<%=i%>" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);"><%=AppSettings.getParm("VIRTUAL_SHOWROOM_MAIN_PAGE_CONTENT_"+String.valueOf(i))%></textarea></td>
           </tr>
           </table>
        </td>
      </tr>       
   <% } %>
<% } %>

      <tr>
        <th colspan="2"><hr></td>
     </tr>
<% } %>    
<%// end of config sections.........................................%>	      	     

     </table>
     </td>
     </tr>
    
     </table>
     </form>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
<%=AppSettings.getFooter(userProfile)%>