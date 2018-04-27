<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings" %>
<%@ page import="java.net.URLEncoder" %>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<%! private String getCheckedOption(String optionName) {
      if (AppSettings.getParm(optionName).equals("YES")) {
        return "checked";
      } else {
        return "";
      }
    } %>

<% try{ %>

<%=AppSettings.getHeader(request, userProfile,"Configuration Site :: Instalation Options - " + AppSettings.getParm("COMPANY_NAME"), "","", "")%>
    <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/options.js">
     </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/installationOptions.js">
     </script>
     <script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/openHTMLEditor.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
     </script>

     </head>

     <body>

     <form name="frmConfig" method=post action="<%=AppSettings.getAppWebPath()%>admin/config">
     <input type=hidden name="formAction" value="UPDATE_INSTALLATION_OPTIONS">

     <table border="0" align="center"><tr><td>


     <h1>Installation Options</h1>
     <input type=submit name="pbSubmit" value="Save Configurations" onclick="return fValidAppConfig()">
     <hr>

     <table border="0" align="center">
<%// Company Info section ..........................................%>
      <tr>
        <td colspan="3">
          <h2>Company Info</h2>
        </td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Company name:</b></td>
        <td ><input name="OPT_COMPANY_NAME" value="<%=AppSettings.getParm("COMPANY_NAME")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Phone:</b></td>
        <td ><input name="OPT_COMPANY_PHONE" value="<%=AppSettings.getParm("COMPANY_PHONE")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Address:</b></td>
        <td ><input name="OPT_COMPANY_ADDRESS" value="<%=AppSettings.getParm("COMPANY_ADDRESS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>State:</b></td>
        <td ><select name="OPT_COMPANY_STATE"><%=AppSettings.getUSStatesCombo()%></select></td>
        <script language="JavaScript">fSetComboSelectionText(document.frmConfig.OPT_COMPANY_STATE,'<%=AppSettings.getParm("COMPANY_STATE")%>','');</script>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>State abreviation:</b></td>
        <td ><input name="OPT_COMPANY_STATE_ABREV" value="<%=AppSettings.getParm("COMPANY_STATE_ABREV")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>City:</b></td>
        <td ><input name="OPT_COMPANY_CITY" value="<%=AppSettings.getParm("COMPANY_CITY")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Zip:</b></td>
        <td ><input name="OPT_COMPANY_ZIP" value="<%=AppSettings.getParm("COMPANY_ZIP")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Fax:</b></td>
        <td ><input name="OPT_COMPANY_FAX" value="<%=AppSettings.getParm("COMPANY_FAX")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Contact email address:</b></td>
        <td ><input name="OPT_CONTACT_EMAIL" value="<%=AppSettings.getParm("CONTACT_EMAIL")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Email address for automated messages:</b></td>
        <td ><input name="OPT_AUTOMATED_EMAIL_FROM_ADRESS" value="<%=AppSettings.getParm("AUTOMATED_EMAIL_FROM_ADRESS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
        <td width="50"></td>
        <td width="200"><b>Send order notification to email address:</b></td>
        <td ><input name="OPT_SEND_ORDER_NOTIFICATION_TO_EMAIL_ADDRESS" value="<%=AppSettings.getParm("SEND_ORDER_NOTIFICATION_TO_EMAIL_ADDRESS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Send lead notification to email address:</b></td>
        <td ><input name="OPT_SEND_LEAD_NOTIFICATION_TO_EMAIL_ADDRESS" value="<%=AppSettings.getParm("SEND_LEAD_NOTIFICATION_TO_EMAIL_ADDRESS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

	<tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>

<%// Basic web configurations section ..............................%>
      <tr>
        <td colspan="3">
          <h2>Basic  web configurations</h2>
        </td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>SMTP relay host:</b></td>
        <td ><input name="OPT_SMTP_RELAY_HOST" value="<%=AppSettings.getParm("SMTP_RELAY_HOST")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>SSL enabled:</b></td>
        <td ><select name="OPT_SSL_ENABLED"><option value="YES">Yes<option value="NO">No</select></td>
        <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_SSL_ENABLED,'<%=AppSettings.getParm("SSL_ENABLED")%>', 'NO');</script>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>HTML Editor type:</b></td>
        <td ><select name="OPT_HTML_EDITOR_TYPE"><option value="EKIT">EKIT<option value="FRONTPAGE">FRONTPAGE</select></td>
        <script language="JavaScript">fSetComboSelectionValue(document.frmConfig.OPT_HTML_EDITOR_TYPE,'<%=AppSettings.getParm("HTML_EDITOR_TYPE")%>', 'NO');</script>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>PayFlow Link login:</b></td>
        <td ><input name="OPT_LINK_LOGIN" value="<%=AppSettings.getParm("LINK_LOGIN")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
          <td width="50"></td>
          <td width="200"><b>PayPal account:</b></td>
          <td ><input name="OPT_PAYPAL_ACCOUNT" value="<%=AppSettings.getParm("PAYPAL_ACCOUNT")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Cookie name for anonymous users:</b></td>
        <td ><input name="OPT_COOKIE_NAME_FOR_ANONYMOUS" value="<%=AppSettings.getParm("COOKIE_NAME_FOR_ANONYMOUS")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>
	<tr>
        <td width="50"></td>
        <td width="200"><b>Default thumbnail ratio (%):</b></td>
        <td ><input name="OPT_DEFAULT_THUMBNAIL_RATIO" value="<%=AppSettings.getParm("DEFAULT_THUMBNAIL_RATIO")%>" size=33 onblur="this.value = fTrunc(this.value,0);">
        </td>
      </tr>
	<tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>

<%// Payment types section .........................................%>
      <tr>
        <td colspan="3">
          <h2>Payment Types</h2>
        </td>
      </tr>

      <tr>
         <td width="50"><br></td>
         <td width="200"><b>Payment types for Retail:</b></td>
         <td><b>Payment types for Wholesale:</b></td>
      </tr>
      <tr>
         <td width="50"><br></td>
         <td width="200">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_RETAIL_CC_ENABLED" value="YES" <%=getCheckedOption("RETAIL_CC_ENABLED")%>> Credit Card<br>
           <input type=hidden name="OPT_RETAIL_CC_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_RETAIL_PAYPAL_ENABLED" value="YES" <%=getCheckedOption("RETAIL_PAYPAL_ENABLED")%>> PayPal<br>
           <input type=hidden name="OPT_RETAIL_PAYPAL_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_RETAIL_CHECK_ENABLED" value="YES" <%=getCheckedOption("RETAIL_CHECK_ENABLED")%>> Check<br>
           <input type=hidden name="OPT_RETAIL_CHECK_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_RETAIL_CASH_ENABLED" value="YES" <%=getCheckedOption("RETAIL_CASH_ENABLED")%>> Cash<br>
           <input type=hidden name="OPT_RETAIL_CASH_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_RETAIL_MONEY_ENABLED" value="YES" <%=getCheckedOption("RETAIL_MONEY_ENABLED")%>> Money Order<br>
           <input type=hidden name="OPT_RETAIL_MONEY_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_RETAIL_COD_ENABLED" value="YES" <%=getCheckedOption("RETAIL_COD_ENABLED")%>> COD<br>
           <input type=hidden name="OPT_RETAIL_COD_ENABLED" value="">
         </td>
         <td>
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_WHOLESALE_CC_ENABLED" value="YES" <%=getCheckedOption("WHOLESALE_CC_ENABLED")%>> Credit Card<br>
           <input type=hidden name="OPT_WHOLESALE_CC_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_WHOLESALE_PAYPAL_ENABLED" value="YES" <%=getCheckedOption("WHOLESALE_PAYPAL_ENABLED")%>> PayPal<br>
           <input type=hidden name="OPT_WHOLESALE_PAYPAL_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_WHOLESALE_CHECK_ENABLED" value="YES" <%=getCheckedOption("WHOLESALE_CHECK_ENABLED")%>> Check<br>
           <input type=hidden name="OPT_WHOLESALE_CHECK_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_WHOLESALE_CASH_ENABLED" value="YES" <%=getCheckedOption("WHOLESALE_CASH_ENABLED")%>> Cash<br>
           <input type=hidden name="OPT_WHOLESALE_CASH_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_WHOLESALE_MONEY_ENABLED" value="YES" <%=getCheckedOption("WHOLESALE_MONEY_ENABLED")%>> Money Order<br>
           <input type=hidden name="OPT_WHOLESALE_MONEY_ENABLED" value="">
           &nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" name="CB_WHOLESALE_COD_ENABLED" value="YES" <%=getCheckedOption("WHOLESALE_COD_ENABLED")%>> COD<br>
           <input type=hidden name="OPT_WHOLESALE_COD_ENABLED" value="">
         <td>

         </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>

<%// Checkout section ..............................................%>
      <tr>
        <td colspan="3">
          <h2>Checkout</h2>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td width="200"><b>Merchant name:</b></td>
        <td ><input name="OPT_MERCHANT_NAME" value="<%=AppSettings.getParm("MERCHANT_NAME")%>" size=33 onblur="fOnFieldBlur(this);"></td>
      </tr>

      <tr>
        <td width="50"></td><td colspan=2><b>Email header</b>(for payments other than CC-VeriSign)<b>:</b></td><td ></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_CHECKOUT_CONFIRMATION_HEADER" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);">
<%=AppSettings.getParm("CHECKOUT_CONFIRMATION_HEADER")%></TEXTAREA>
        </td>
      </tr>

      <tr>
        <td width="50"></td><td colspan=2><b>Email footer</b><font size=-1> (for payments other than CC-VeriSign)</font><b>:</b></td><td ></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td colspan="2">
          <TEXTAREA NAME="OPT_CHECKOUT_CONFIRMATION_FOOTER" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);">
<%=AppSettings.getParm("CHECKOUT_CONFIRMATION_FOOTER")%></TEXTAREA>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan="2">
           <table border="0">
           <tr>
             <td align="left"><b>Confirmation page footer:</b>(for CC-VeriSign payments) </td>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_CHECKOUT_CONFIRMATION_PAGE_FOOTER_CC','<%=URLEncoder.encode("Checkout - Confirmation page footer (for CC-VeriSign payments)")%>','0')"></td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_CHECKOUT_CONFIRMATION_PAGE_FOOTER_CC','<%=URLEncoder.encode("Checkout - Confirmation page footer (for CC-VeriSign payments)")%>','0')"></td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_CHECKOUT_CONFIRMATION_PAGE_FOOTER_CC" wrap="off" rows="10" cols="100" onblur="fOnTextareaBlur(this);">
<%=AppSettings.getParm("CHECKOUT_CONFIRMATION_PAGE_FOOTER_CC")%></TEXTAREA></td>
           </tr>
           </table>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan="2">
           <table border="0">
           <tr>
             <td align="left"><b>Confirmation page footer:</b>(for payments other than CC-VeriSign)</td>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_CHECKOUT_CONFIRMATION_PAGE_FOOTER_NON_CC','<%=URLEncoder.encode("Checkout - Confirmation page footer (for payments other than CC-VeriSign)")%>','0')"></td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_CHECKOUT_CONFIRMATION_PAGE_FOOTER_NON_CC','<%=URLEncoder.encode("Checkout - Confirmation page footer (for payments other than CC-VeriSign)")%>','0')"></td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_CHECKOUT_CONFIRMATION_PAGE_FOOTER_NON_CC" wrap="off" rows="10" cols="100">
<%=AppSettings.getParm("CHECKOUT_CONFIRMATION_PAGE_FOOTER_NON_CC")%></TEXTAREA></td>
           </tr>
           </table>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
      </tr>

<%// Order form section ............................................%>
      <tr>
        <td colspan="3">
          <h2>Order form</h2>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td width="200"><b>Email subject for retail:</b></td>
        <td ><input name="OPT_ORDER_FORM_EMAIL_SUBJECT_1" value="<%=AppSettings.getParm("ORDER_FORM_EMAIL_SUBJECT_1")%>" size=33></td>
      </tr>
      <tr>
        <td width="50"></td>
        <td width="200"><b>Email subject for wholesale:</b></td>
        <td ><input name="OPT_ORDER_FORM_EMAIL_SUBJECT_2" value="<%=AppSettings.getParm("ORDER_FORM_EMAIL_SUBJECT_2")%>" size=33></td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan="2">
           <table border="0">
           <tr>
             <td align="left"><b>Company info:</b> </td>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_ORDER_FORM_COMPANY_INFO','<%=URLEncoder.encode("Order Form - Company info")%>','0')"> <br> </td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_ORDER_FORM_COMPANY_INFO','<%=URLEncoder.encode("Order Form - Company info")%>','0')"> <br> </td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_ORDER_FORM_COMPANY_INFO" wrap="off" rows="10" cols="100">
<%=AppSettings.getParm("ORDER_FORM_COMPANY_INFO")%></TEXTAREA></td>
           </tr>
           </table>
        </td>
      </tr>

      <tr>
        <td width="50"></td>
        <td colspan="2">
           <table border="0">
           <tr>
             <td align="left"><b>Confirmation page footer:</b> </td>

<% if (AppSettings.getParm("HTML_EDITOR_TYPE")!=null && AppSettings.getParm("HTML_EDITOR_TYPE").equals("FRONTPAGE")) { %>
             <td align="right"><input type=button value="Preview" onclick="openHTMLEditor('OPT_ORDER_FORM_CONFIRMATION_PAGE_FOOTER','<%=URLEncoder.encode("Order Form - Confirmation page footer")%>','0')"> <br> </td>
<% } else { %>
             <td align="right"><input type=button value="Preview" onclick="openEkitEditor('OPT_ORDER_FORM_CONFIRMATION_PAGE_FOOTER','<%=URLEncoder.encode("Order Form - Confirmation page footer")%>','0')"> <br> </td>
<% } %>

           </tr><tr>
             <td colspan="2"><TEXTAREA NAME="OPT_ORDER_FORM_CONFIRMATION_PAGE_FOOTER" wrap="off" rows="10" cols="100">
<%=AppSettings.getParm("ORDER_FORM_CONFIRMATION_PAGE_FOOTER")%></TEXTAREA>
           </tr>
           </table>
        </td>
      </tr>
<%// end of config sections.........................................%>

     <tr>
        <td width="50"></td>
        <td colspan=2><hr></td>
     </tr>

     </table>

     </td>
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
