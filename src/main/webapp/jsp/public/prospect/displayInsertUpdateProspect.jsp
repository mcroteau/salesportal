<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.prospect.ProspectDetails,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.note.NoteDetails,
                 com.randr.webdw.user.UserDetails,
                 java.util.Enumeration,
                 java.text.SimpleDateFormat,
                 java.sql.Timestamp,
                 com.randr.webdw.website.WebsiteDetails,
                 com.randr.webdw.commision.CommisionDetails,
                 com.randr.webdw.prospectDocument.ProspectDocumentDetails,
                 com.randr.webdw.contact.ContactDetails,
                 com.randr.webdw.label.LabelView,
                 com.randr.webdw.systemType.SystemTypeDetails,
                 com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails,
                 com.randr.webdw.prospectCampaign.ProspectCampaignDetails"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="noteView" class="com.randr.webdw.note.NoteView" scope="request"/>
<jsp:useBean id="websiteView" class="com.randr.webdw.website.WebsiteView" scope="request"/>
<jsp:useBean id="prospectDocumentView" class="com.randr.webdw.prospectDocument.ProspectDocumentView" scope="request"/>
<jsp:useBean id="contactView" class="com.randr.webdw.contact.ContactView" scope="request"/>
<jsp:useBean id="prospectSalesActionView" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionView" scope="request"/>
<jsp:useBean id="prospectCampaignView" class="com.randr.webdw.prospectCampaign.ProspectCampaignView" scope="request"/>

<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="validTime" class="java.lang.String" scope="request"/>
<jsp:useBean id="updateScreen" type="java.lang.Boolean" scope="request"/>

<jsp:useBean id="companyView" class="com.randr.webdw.company.CompanyView" scope="request"/>
<jsp:useBean id="countryView" class="com.randr.webdw.country.CountryView" scope="request"/>
<jsp:useBean id="stateView" class="com.randr.webdw.state.StateView" scope="request"/>
<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="lobView" class="com.randr.webdw.lob.LobView" scope="request"/>
<jsp:useBean id="systemTypeView" class="com.randr.webdw.systemType.SystemTypeView" scope="request"/>
<jsp:useBean id="softwareTypeView" class="com.randr.webdw.softwareType.SoftwareTypeView" scope="request"/>
<jsp:useBean id="statusView" class="com.randr.webdw.status.StatusView" scope="request"/>
<jsp:useBean id="verifiedView" class="com.randr.webdw.verified.VerifiedView" scope="request"/>
<jsp:useBean id="sizeView" class="com.randr.webdw.size.SizeView" scope="request"/>
<jsp:useBean id="salesActionView" class="com.randr.webdw.salesAction.SalesActionView" scope="request"/>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>
<jsp:useBean id="isInCalendar" class="java.util.GregorianCalendar" scope="session"/>
<jsp:useBean id="fromCurrentActions" class="java.lang.String" scope="request"/>
<jsp:useBean id="commisionView" class="com.randr.webdw.commision.CommisionView" scope="request"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Prospect", "","", "")%>
<%
    try{
        boolean hasPrev = false;
        boolean hasNext = false;
        boolean showActiveSalesActions = false;
    	boolean showCompletedSalesActions = false;
        String nextProspect = "";
        String prevProspect = "";
        String actionDescription = "Insert";
        String newFormAction="INSERT";
        String oldFormAction="DISPLAY_INSERT";

        if (updateScreen.booleanValue()){
            actionDescription = "Update";
            newFormAction="UPDATE";
            oldFormAction="DISPLAY_UPDATE";
            int index = prospectView.getElementIndex(prospectDetails.getProspectId());
            if (index > 0 ){
                hasPrev = true;
                prevProspect = ((ProspectDetails) prospectView.getElements().elementAt(index - 1)).getCustomerCompany();
            }
            if (index < (prospectView.getElements().size() - 1) ){
                hasNext = true;
                nextProspect = ((ProspectDetails) prospectView.getElements().elementAt(index + 1)).getCustomerCompany();
            }
        }
        
		if (prospectSalesActionView.getElements().size() > 0) {       
	        for (int i=0; i<prospectSalesActionView.getElements().size();i++) {
				ProspectSalesActionDetails prospectSalesActionDetails 
	            	= (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
	            if(prospectSalesActionDetails.getActionStatus().compareTo(ProspectSalesActionDetails.STATUS_ACTIVE)==0){
	            	showActiveSalesActions = true;
	        	}else if(prospectSalesActionDetails.getActionStatus().compareTo(ProspectSalesActionDetails.STATUS_COMPLETE)==0){
	        		showCompletedSalesActions = true;        		
	        	}
	        	if(showCompletedSalesActions){
	        		//System.out.println("showCompletedSalesActions = " + showCompletedSalesActions);   		
	        	}
	        }
		}
%>
<% String prospectsHeading = "Prospect";
	if(AppSettings.getParm("PROSPECTS").compareTo("")!=0 &&
        		AppSettings.getParm("PROSPECTS")!=null){
        	prospectsHeading = AppSettings.getParm("PROSPECTS");
        }
 %>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospect/displayInsertUpdateProspect.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/launchInIE.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
 

<script>
var downloadLocation = '<%=AppSettings.getAppCompleteWebPath(request)%>download/WebDialer.exe';
</script>
<% if(validTime.equals("false")) { %>
	<script>
		alert('Time selected is already used.');
		document.frmProspect.dfMinute.focus();
	</script>
	
<% } %>

<script>
    <%=countryView.getCountriesArray()%>
    <%=stateView.getStateArrays()%>
    var dateIsMandatory = 0;
    function fPopulateStates(objParent, objChild)
    {
	    var nSel=objParent.options[objParent.options.selectedIndex].value;
	    objChild.length=0;
	    objChild.length=eval('arrState'+nSel+'.length')/2;
	    var i=0;
	    for (i=0;i<eval('arrState'+nSel+'.length');i=i+2)
	    {
		    objChild.options[i/2].value=eval('arrState'+nSel+'['+i+']');
		    objChild.options[i/2].text=eval('arrState'+nSel+'['+(i+1)+']');
	    };
	    objChild.options.selectedIndex=0;
    };

    function fSelectState(objChild, selectValue)
    {
        var i = 0;
        for (i = 0; i <  objChild.length; i++)
        {
            if (objChild.options[i].value == selectValue)
                    objChild.options.selectedIndex=i;
        }
    };

    <%--<%=salesActionView.getDateMandatoryArray()%>--%>
</script>
<%String systemTypeDescription = "";%>
    <center>

        <h1><%=actionDescription%> <%=prospectsHeading %>
        <% if (updateScreen.booleanValue()) { %>: <%=prospectDetails.getCustomerCompany()%><% } %></h1>
            	<% if (hasPrev) { %> 
	        		<a href="javascript: fOnPrev();" class="navigation">
	        		<img class="navigation" src="<%=AppSettings.getAppWebPath()%>graphics/icons/previous.gif" alt="<%=prevProspect%>">
	        		Previous:&nbsp;<%=prevProspect%>
	        		</a>
	        	<%}%>
	        	<span id="warning">Using the Back Button will produce unpredictable results.</span>
        	    <% if (hasNext) { %> 
	        		<a href="javascript: fOnNext();" class="navigation">
	        		Next:&nbsp;<%=nextProspect%>        		
	        		<img class="navigation" src="<%=AppSettings.getAppWebPath()%>graphics/icons/next.gif" alt="<%=nextProspect%>">
	        		</a>
	        	<%}%> 
	        	<br/>
				<br/>  

<form name="frmProspect" method="post" action="" onsubmit="javascript: return fProcessForm();">
<input type=hidden name="formAction" value="<%=newFormAction%>">
<input type="hidden" name="dfNoteId" value="">
<input type="hidden" name="dfWebsiteId" value="">
<input type="hidden" name="dfCommisionId" value="">
<input type="hidden" name="dfProspectDocumentId" value="">
<input type="hidden" name="dfContactId" value="">
<input type="hidden" name="dfProspectSalesActionId" value="">
<input type="hidden" name="dfProspectCampaignId" value="">
<input type="hidden" name="oldFormAction" value="<%=oldFormAction%>">
<input type="hidden" name="oldAction" value="prospect">
<input type="hidden" name="dfChangeIndex" value="">
<% if (request.getAttribute("modelException") != null){
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><hr><%=modelException.getMessage()%></p>

<% } else { %>

    <table border="0" width="100%">
    <tr><td colspan="2" align="center">
    <% if (updateScreen.booleanValue()) { %>
        <table border="0" cellspacing="0" cellpadding="3" width="100%">
        <tr >
				<%if(!fromCurrentActions.equals("YES")){ %>
                	<a href="javascript:fOnSearchResults();">
                	<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/back.gif" alt="Back">&nbsp;Search Results</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
				    
                	<a href="javascript:fOnRedoSearch();">
                	<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/redo_search.gif" alt="Back">&nbsp;Redo Search(Reflect Changes)</a>
            		&nbsp;&nbsp;&nbsp;&nbsp;     	
            	<%}%>

	        <%if(prospectDetails.getTerritoryId()!= null && request.getSession().getAttribute("calendarSearchCritieria") != null){%>

	                <a href="javascript:fOnCalendarSearch();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/redo_search.gif" alt="Back">&nbsp;Return to Calendar</a>

            <%}%>
           	<br/>
			<br/>  
        </tr>
        <tr >
        <td  class="navigationbar2" colspan="4" align="left">
        <a href="javascript:fOnPrint();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/print.gif" alt="Print Record"> Print Prospect</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddNote();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/postit.gif" alt="Notes"> Add Note</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddWebsite();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/websites.gif" alt="Websites"> Add Website</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddDocument();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/documents.gif" alt="Documents"> Add Document</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddContact();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/addContacts.gif" alt="Contacts"> Add Contact</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddProspectSalesAction();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/addContacts.gif" alt="SalesActions"> Add Sales Action</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddCampaign();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/addContacts.gif" alt="Campaign"> Add Campaign</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="javascript:fOnAddCommission(<%=prospectDetails.getProspectId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/view.gif" alt="Commissions"> Add Revenue/Commission</a>
        </td>
        </tr>
        </table>
        <% } %>
        <br>
    </td></tr>
    <tr><td valign="top" align="center" <% if (updateScreen.booleanValue()) { %>width="50%" <% } %>>
        <table class="form" cellspacing="0" cellpadding="0" border="0" <% if (updateScreen.booleanValue()) { %>width="90%" <% } %>>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td colspan="2" align="center">
                <a class="button" href="javascript: fOnSubmit('<%=actionDescription%>');">
                    &nbsp;&nbsp;
                    <img class="button" class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">
                    &nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                <% if (updateScreen.booleanValue()) { %>
                    <% if (hasNext) { %>
                         <a class="button" href="javascript: fOnUpdateAndNext();">&nbsp;&nbsp;
                         <img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept_and_continue.gif" alt="Accept">
                            Update &amp; Next&nbsp;&nbsp;</a>
                    <% } %>
                <% } %>
                <%  if (((Boolean) request.getAttribute("isAdmin")).booleanValue())  {  %>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
                <%  } %>
            </td>
		</tr>
        <tr><td colspan="2"></td></tr>

    <%  if (updateScreen.booleanValue()) { %>

        <tr>
            <th nowrap align="right">Prospect Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfProspectId" value="<%=Utilities.nullToBlank(prospectDetails.getProspectId())%>">
                <%=Utilities.nullToBlank(prospectDetails.getProspectId())%>
            </td>
        </tr>

    <% }  %>
    
    <%  if (!updateScreen.booleanValue()) { %>

        <tr>
            <th nowrap align="right">Auto Assign Territory:</th>
            <td nowrap align="left">
                <input type="checkbox" name="ckAutoAssignTerritory" checked>
                &nbsp;Assigns territory by zip if no territory is selected.
            </td>
        </tr>
        <tr>
            <td colspan="2"><hr></td>
        </tr>

    <% }  %>

  <tr>
            <th nowrap align="right">Customer Company<font size="+1">*</font>:</th>
            <td nowrap align="left">
                <input type="text" name="dfCustomerCompany" value="<%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%>" maxlength="100" size="55">
            <font color="blue" size="+1">**</font></td>
        </tr>

        <tr>
            <th nowrap align="right">Address:</th>
            <td nowrap align="left">
                <input type="text" name="dfAddress" value="<%=Utilities.nullToBlank(prospectDetails.getAddress())%>" maxlength="100" size="55">
            </td>
        </tr>
        <tr>
            <th nowrap align="right">&nbsp;</th>
            <td nowrap align="left">
                <input type="text" name="dfAddress2" value="<%=Utilities.nullToBlank(prospectDetails.getAddress2())%>" maxlength="50" size="55">
            </td>
        </tr>

        <tr>
            <th nowrap align="right">City:</th>
            <td nowrap align="left">
                <input type="text" name="dfCity" value="<%=Utilities.nullToBlank(prospectDetails.getCity())%>" maxlength="30" size="55">
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right">County:</th>
            <td nowrap align="left">
                <input type="text" name="dfCounty" value="<%=Utilities.nullToBlank(prospectDetails.getCounty())%>" maxlength="30" size="55">
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right">Zip:</th>
            <td nowrap align="left">
                <input type="text" name="dfZip" value="<%=Utilities.nullToBlank(prospectDetails.getZip())%>" maxlength="30" size="55">
            <font color="blue" size="+1">**</font></td>
        </tr>

        <tr>
            <th nowrap align="right">Country:</th>
            <td nowrap align="left">
                <select name="dfCountryId"
                        onchange="javascript:fPopulateStates(this, document.frmProspect.dfStateId)">
                <%=CollectionUtilities.getDropDownOptions(countryView.getElements(),
                                                          "getCountryId", "getCountry",
                                                          prospectDetails.getCountryId())
                %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right">State:</th>
            <td nowrap align="left">
                <select name="dfStateId">
                </select>
             <script>
                fPopulateStates(document.frmProspect.dfCountryId, document.frmProspect.dfStateId);
                fSelectState(document.frmProspect.dfStateId, <%=Utilities.nullToZero(prospectDetails.getStateId()).toString()%>);
             </script>

            <font color="blue" size="+1">**</font></td>
        </tr>

        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <th nowrap align="right">Contact Name:</th>
            <td nowrap align="left">
                <input type="text" name="dfContactName" value="<%=Utilities.nullToBlank(prospectDetails.getContactName())%>" maxlength="50" size="55">
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right">Contact Title:</th>
            <td nowrap align="left">
                <input type="text" name="dfContactTitle" value="<%=Utilities.nullToBlank(prospectDetails.getContactTitle())%>" maxlength="50" size="55">
            </td>
        </tr>

        <tr>
            <th nowrap align="right">Phone:</th>
            <td nowrap align="left">
                <input type="text" name="dfPhone" value="<%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getPhone()))%>"
                    maxlength="30" size="15"  onblur="javascript:this.value=formatPhoneNumber(this.value);">
                    <% if (!Utilities.nullToBlank(prospectDetails.getPhone()).equals("")) { %>
                        <a href="http://www.google.com/search?q=<%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getPhone()))%>" target="_blank"><img class="button" border="1" src="<%=AppSettings.getAppWebPath()%>graphics/icons/google.gif" alt="Search Google"></a>
                        <a href="javascript:launchApp('dial.exe <%=Utilities.replace(prospectDetails.getPhone(),"-","")%>');"><img class="button" border="0" src="<%=AppSettings.getAppWebPath()%>graphics/icons/phone.gif" alt="Dial Number">Click to Dial </a>
                    <% } %>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Phone Extension:</th>
            <td nowrap align="left">
                <input type="text" name="dfPhoneExt" value="<%=Utilities.nullToBlank(prospectDetails.getPhoneExt())%>" maxlength="30" size="15">
                &nbsp;&nbsp;<b>Cell Phone:</b>
                <input type="text" name="dfCellPhone" value="<%=Utilities.nullToBlank(prospectDetails.getCellPhone())%>" maxlength="30" size="15">
            </td>
        </tr>

        <tr>
            <th nowrap align="right">Fax:</th>
            <td nowrap align="left">
                <input type="text" name="dfFax" value="<%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getFax()))%>"
                    maxlength="30" size="15" onblur="javascript:this.value=formatPhoneNumber(this.value);">
            </td>
        </tr>
        <tr>
            <th nowrap align="right">E-mail:</th>
            <td nowrap align="left">
                <input type="text" name="dfEmail" value="<%=Utilities.nullToBlank(prospectDetails.getEmail())%>" maxlength="50" size="55">
                <% if (!Utilities.nullToBlank(prospectDetails.getEmail()).equals("")) { %>
                    <a href="mailto:<%=Utilities.nullToBlank(prospectDetails.getEmail())%><%=Utilities.nullToBlank(prospectDetails.getEmailMailToString())%>">
                    <img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/email.gif" alt="send email"></a>
                <% } %>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Web Site:</th>
            <td nowrap align="left">
                <input type="text" name="dfWebsite" value="<%=Utilities.nullToBlank(prospectDetails.getWebsite())%>" maxlength="100" size="55">
                <% if (!Utilities.nullToBlank(prospectDetails.getWebsite()).equals("")) { %>
                <a href="<%=Utilities.validWebSiteAddress(Utilities.nullToBlank(prospectDetails.getWebsite()))%>"
                    target="_blank"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/view.gif" alt="View Website"></a>
                <% } %>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
		<% if (AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0){ %>
		 <tr>
            <th nowrap align="right">Open Orders #:</th>
            <td nowrap align="left">
                <input type="text" name="dfOpenOrdersNumber" value="<%=Utilities.nullToBlank(prospectDetails.getOpenOrdersNumber())%>" maxlength="7" size="10">
                 &nbsp;&nbsp;<b>Value:</b>
                <input type="text" name="dfOpenOrdersValue" value="<%=Utilities.nullToBlank(prospectDetails.getOpenOrdersValue())%>" maxlength="11" size="15">   
            </td>
        </tr>
		<%  }  %>
		<% if (AppSettings.getParm("DISPLAY_OPEN_QUOTES_FIELDS").compareTo("YES")==0){ %>
		 <tr>
            <th nowrap align="right">Open Quotes #:</th>
            <td nowrap align="left">
                <input type="text" name="dfOpenQuotesNumber" value="<%=Utilities.nullToBlank(prospectDetails.getOpenQuotesNumber())%>" maxlength="7" size="10">
                 &nbsp;&nbsp;<b>Value:</b>
                <input type="text" name="dfOpenQuotesValue" value="<%=Utilities.nullToBlank(prospectDetails.getOpenQuotesValue())%>" maxlength="11" size="15">   
            </td>
        </tr>
		<%  }  %>
		<% if (AppSettings.getParm("DISPLAY_OPEN_AR_FIELDS").compareTo("YES")==0){ %>
		 <tr>
            <th nowrap align="right">Open AR:</th>
            <td nowrap align="left">
                <input type="text" name="dfOpenAccountsReceivable" value="<%=Utilities.nullToBlank(prospectDetails.getOpenAccountsReceivable())%>" maxlength=11" size="15">
          
            </td>
        </tr>
		<%  }  %>
		<% if (AppSettings.getParm("DISPLAY_ORDERS_INVOICES_FIELDS").compareTo("YES")==0){ %>
		 <tr>
            <th nowrap align="right">LTD Orders/Invoices #:</th>
            <td nowrap align="left">
                <input type="text" name="dfInvoicesNumberLtd" value="<%=Utilities.nullToBlank(prospectDetails.getInvoicesNumberLtd())%>" maxlength="7" size="10">
                 &nbsp;&nbsp;<b>Value:</b>
                <input type="text" name="dfInvoicesValueLtd" value="<%=Utilities.nullToBlank(prospectDetails.getInvoicesValueLtd())%>" maxlength="11" size="15">   
            </td>
        </tr>
         <tr>
            <th nowrap align="right">YTD Orders/Invoices #:</th>
            <td nowrap align="left">
                <input type="text" name="dfInvoicesNumberYtd" value="<%=Utilities.nullToBlank(prospectDetails.getInvoicesNumberYtd())%>" maxlength="7" size="10">
                 &nbsp;&nbsp;<b>Value:</b>
                <input type="text" name="dfInvoicesValueYtd" value="<%=Utilities.nullToBlank(prospectDetails.getInvoicesValueYtd())%>" maxlength="11" size="15">   
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Forecast:</th>
            <td nowrap align="left">
                <input type="text" name="dfForecast" value="<%=Utilities.nullToBlank(prospectDetails.getForecast())%>" maxlength="11" size="15">
                   
            </td>
        </tr>
		<%  }  %>
		<% if (AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0 ||
				AppSettings.getParm("DISPLAY_OPEN_QUOTES_FIELDS").compareTo("YES")==0 ||
				AppSettings.getParm("DISPLAY_OPEN_AR_FIELDS").compareTo("YES")==0 ||
				AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0 ){ %>
		<tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <%  }  %>
        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_1)%>:</th>
            <td nowrap align="left"><input type="text" name="dfSsa" value="<%=Utilities.nullToBlank(prospectDetails.getSsa())%>" maxlength="100" size="15">
                <b><%=labelView.getLabel(LabelView.USER_2)%>:</b> <input type="text" name="dfSic" value="<%=Utilities.nullToBlank(prospectDetails.getSic())%>" maxlength="100" size="15">
            </td>
        </tr>

        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_3)%>:</th>
            <td nowrap align="left">
                <input type="text" name="dfSystemNo" value="<%=Utilities.nullToBlank(prospectDetails.getSystemNo())%>" maxlength="100" size="55">
            </td>
        </tr>        
        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_4)%>:</th>
            <td nowrap align="left">
                <input type="text" name="dfHardmnt" value="<%=Utilities.nullToBlank(prospectDetails.getHardwareMaintenance())%>" maxlength="100" size="55">
            </td>
        </tr>
        <% if(prospectDetails.getOptOutDate()!=null){ %>
		<tr>
			<th nowrap align="right">Email Opt Out Date:</th>
			<td><%=DateUtilities.formatDate(prospectDetails.getOptOutDate(), userProfile.getDateFormat())%></td>
		</tr>
		<% } %>

        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <th nowrap align="right">Activity Date:</th>
            <td nowrap align="left">
                <input type="text" name="dfActivityDate" size="12" onchange="javascript:void(0);" 
           		value="<%=DateUtilities.formatDate(prospectDetails.getActivityDate(), userProfile.getDateFormat())%>">
            	<a href="javascript:selectDate.popup();">
            		<img src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date">
            	</a>
			</td>
        </tr>
        <tr>
            <th nowrap align="right">Sales Company<font size="+1">*</font>:</th>
            <td nowrap align="left">
                <select name="dfCompanyId">
                <%=CollectionUtilities.getDropDownOptions(companyView.getElements(),
                                                          "getCompanyId", "getCompany",
                                                          prospectDetails.getCompanyId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>

        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>

        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%>:</th>
            <td nowrap align="left">
                <select name="dfSystemTypeId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(systemTypeView.getElements(),
                                                          "getSystemTypeId", "getSystemType",
                                                          prospectDetails.getSystemTypeId()) %>
                </select>
            </td>
        </tr>
        <tr>
             <% if(labelView.getLabel(LabelView.LOB).compareTo("LOB")==0){// doesnot exist in DB %>
            <th nowrap align="right">Line Of Business:</th>
         <% }else{ %>   
         	<th nowrap align="right"><%=labelView.getLabel(LabelView.LOB) %>:</th>
         <% } %>	
            <td nowrap align="left">
                <select name="dfLobId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          prospectDetails.getLobId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%>:</th>
            <td nowrap align="left">
                <select name="dfSoftwareTypeId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(softwareTypeView.getElements(),
                                                          "getSoftwareTypeId", "getSoftwareType",
                                                          prospectDetails.getSoftwareTypeId()) %>
                </select>
            </td>
        </tr>

        <tr>
            <th nowrap align="right">Territory:</th>
            <td nowrap align="left">
                <select name="dfTerritoryId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          prospectDetails.getTerritoryId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right">ID/Status:</th>
            <td nowrap align="left">
                <select name="dfStatusId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus",
                                                          prospectDetails.getStatusId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>

        <tr>
            <% if(labelView.getLabel(LabelView.VERIFIED).compareTo("Verified")==0){// doesnot exist in DB %>
                   <th nowrap align="right">Verified:</th>
            <% }else{ %>
                  <th nowrap align="right"><%=labelView.getLabel(LabelView.VERIFIED) %>:</th>
            <% } %>
            <td nowrap align="left">
                <select name="dfVerifiedId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(verifiedView.getElements(),
                                                          "getVerifiedId", "getVerified",
                                                          prospectDetails.getVerifiedId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
            <th nowrap align="right">Size:</th>
            <td nowrap align="left">
                <select name="dfSizeId">
                     <option value="">-
                <%=CollectionUtilities.getDropDownOptions(sizeView.getElements(),
                                                          "getSizeId", "getSize",
                                                          prospectDetails.getSizeId()) %>
                </select>
            <font color="blue" size="+1">**</font></td>
        </tr>
        <tr>
        	 <th nowrap align="right">Orderportal User Id:</th>
            <td nowrap align="left">
                <input type="text" name="dfOrderportalUserId" value="<%=Utilities.nullToBlank(prospectDetails.getOrderportalUserId())%>" maxlength="11" size="10">
                 &nbsp;&nbsp;<b>EBS User Id:</b>
                <input type="text" name="dfEbsUserId" value="<%=Utilities.nullToBlank(prospectDetails.getEbsUserId())%>" maxlength="11" size="10">   
            </td>
        </tr>
    <%  if (updateScreen.booleanValue()) { %>
        <tr><td colspan="2"></td></tr>
        <tr>
            <th nowrap align="right">Created On:</th>
            <td><%=DateUtilities.formatDateAndTime(prospectDetails.getCreationDate(),userProfile)%></td>
        </tr>
        <%if(prospectDetails.getChangeDate()!=null) {%>
        <tr>
            <th nowrap align="right">Last Changed On:</th>
            <td><%=DateUtilities.formatDateAndTime(prospectDetails.getChangeDate(),userProfile)%></td>
        </tr>
        <% } %>
    <% } %>

        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td colspan="2" align="center">
                <a class="button" href="javascript: fOnSubmit();">
                &nbsp;&nbsp;
                <img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">
                <%=actionDescription%>&nbsp;&nbsp;</a>
                <% if (updateScreen.booleanValue()) { %>
                    <% if (hasNext) { %><P>
                         <A class="button" href="javascript: fOnUpdateAndNext();">&nbsp;&nbsp;
                         <IMG class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept_and_continue.gif" alt="Accept">
                            Update &amp; Next&nbsp;&nbsp;</A>
                    </P><% } %>
                

                
                &nbsp; &nbsp;
                    <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
                 <%  } %>
            </td>
		</tr>
        <tr><td colspan="2"><font color="blue"><center>** Searchable Field in Work with Prospects</center><CENTER><font color="black">* Required Fields</font></CENTER></font></td></tr>

        </table>
        </form>
 <%--       <script>
			var calSalesActionDate = new calendar2(document.forms['frmProspect'].elements['dfSalesActionDate']);
			calSalesActionDate.year_scroll = true;
			calSalesActionDate.time_comp = false;
        </script> --%>

<%
        }
%>
    </td>
    <td valign="top" align="center" <% if (updateScreen.booleanValue()) { %>width="50%" <% } %>>
<%--=======================================================--%>

    <%  if (updateScreen.booleanValue()) { %>
    
        <% if (prospectCampaignView.getElements().size() > 0) { %>
    	    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%" border="0">
    			<tr><th colspan="5"><h2>Campaigns</h2></th></tr>
    			<tr>
        		<th align=center>Campaign Name</th>
        		<th align=center>Campaign Description</th>
        		<th align=center>Status</th>
        		<th align=center>Edit</th>
        		<th align=center>Del</th>
    			</tr>
    			
    		<% for (int i=0; i<prospectCampaignView.getElements().size();i++) {
            	ProspectCampaignDetails prospectCampaignDetails 
            		= (ProspectCampaignDetails) prospectCampaignView.getElements().elementAt(i);
            	
            	String startStr = "";
            	String endStr = "";
        	%>
    			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        		<td valign="top" align="center" nowrap>
            	<b>
            	<%if (prospectCampaignDetails.getCampaign() != null) { %>
                 	<%=startStr %><%=prospectCampaignDetails.getCampaign()%><%=endStr %>
            	<%}%>
            	</b>
            	</td>
            	<td style="vertical-align: top;">
					<%=Utilities.nullToBlank(prospectCampaignDetails.getDescription())%>	
        		</td>
        		<td style="width:100px; vertical-align: top;">
        			<%if(prospectCampaignDetails.getStatus() == ProspectCampaignDetails.CAMPAIGN_COMPLETED){%>
        				<span id="completedCampaign" style="color:#39ca39; font-size:9pt; font-weight: bold;">Completed</span>
        			<%}else{%>
        				<span id="notCompletedCampaign" style="color:#dd1010; font-size:9pt; font-weight: bold;">Not Completed</span>
        			<%}%>
        			<br/>
        			<b><%=prospectCampaignDetails.getActionsRemaining()%></b>&nbsp;Actions Remaining
        			<br/>
        			<b><%=prospectCampaignDetails.getTotalActions()%></b>&nbsp;Total Actions        			

        		</td>
        		<td valign="top"  width="1"><a href="javascript:fOnUpdateProspectCampaign(<%=prospectCampaignDetails.getProspectCampaignId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Campaign"></a><br><br></td>
        		<td valign="top"  width="1"><a href="javascript:fOnDeleteProspectCampaign(<%=prospectCampaignDetails.getProspectCampaignId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Campaign"></a><br><br></td>
        	
    <% } %>
    </table><br>
    <% } %>
    
    <%if(showActiveSalesActions){%>	
    	    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%" border="0">
    			<tr><th colspan="5"><h2>Sales Actions</h2></th></tr>
    			<tr>
        		<th align=center>Priority</th>
        		<th align=center>Action</th>
        		<th align=center>Date/Time</th>
        		<th align=center>Edit</th>
        		<th align=center>Del</th>
    			</tr>
    			
    		<% for (int i=0; i<prospectSalesActionView.getElements().size();i++) {
            	ProspectSalesActionDetails prospectSalesActionDetails 
            	= (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
            	SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
            	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            	String startStr = "";
            	String endStr = "";
            	if(prospectSalesActionDetails.getActionDate() != null 
            		&& prospectSalesActionDetails.getActionDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
            		startStr = "<font color=\"red\">";
            		endStr = "</font>";
            	}
        	%>
        	<%if(prospectSalesActionDetails.getActionStatus().compareTo(ProspectSalesActionDetails.STATUS_ACTIVE)==0){%>
    			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        		<td valign="top" align="center" nowrap>
            	<b>
            	<% if (prospectSalesActionDetails.getActionPriority() != null) { %>
                 	<%=startStr %><%=prospectSalesActionDetails.getActionPriority()%><%=endStr %>
            	<% } %>
            	</b>
        		</td>
        		<td valign="top" align="left" >
            	<% if (prospectSalesActionDetails.getAction() != null) { %>
					<%=startStr %><%=prospectSalesActionDetails.getAction()%><%=endStr %>
            	<% } %>
        		</td>
        		<td valign="top" align="left">
        			<% if(prospectSalesActionDetails.getActionDate() != null) {%>
            			<%=startStr %><%= dateFormat.format(prospectSalesActionDetails.getActionDate())%><br>
            			<%= timeFormat.format(prospectSalesActionDetails.getActionDate())%><%=endStr %>
            		<% } else {%>
            			<%=startStr %>No date or time associated with Sales Action.<%=endStr %>
            		<% } %>
        		</td>
        		<td valign="top"  width="1"><a href="javascript:fOnUpdateProspectSalesAction(<%=prospectSalesActionDetails.getProspectSalesActionId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Sales Action"></a><br><br></td>
        		<td valign="top"  width="1"><a href="javascript:fOnDeleteProspectSalesAction(<%=prospectSalesActionDetails.getProspectSalesActionId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Sales Action"></a><br><br></td>
		   		</tr>
		   		<% if (prospectSalesActionDetails.getActionNote()!=null) {%>
		   		<tr>
		   		<td valign="top" align="left" >Note:</td>
		   		<td valign="top"  align="left" colspan="4"><%=prospectSalesActionDetails.getActionNote()%></td>
		   		</tr>
		   		<%} %>
			<%}%>
		<% } %>
    </table><br>
    <% } %>

	<%if(showCompletedSalesActions) {%>
		<a href="javascript:fToggleCompleted();" class="completed" title="Show completed sales actions">+&nbsp;Show Completed Sales Actions</a>
		<table width="95%" class="completed" id="completedSalesActions" style="display:none">
			<tr>
				<th colspan="2"><h2>Completed Sales Actions</h2></th>
			</tr>
			<tr>
				<th>Last Changed</th>
				<th>Action</th>
			</tr>
		    <% for (int i=0; i<prospectSalesActionView.getElements().size();i++) {
            	ProspectSalesActionDetails prospectSalesActionDetails 
            		= (ProspectSalesActionDetails) prospectSalesActionView.getElements().elementAt(i);
            	SimpleDateFormat dateFormat = new SimpleDateFormat(userProfile.getDateFormat());
            	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        	%>
        	<%if(prospectSalesActionDetails.getActionStatus().compareTo(ProspectSalesActionDetails.STATUS_COMPLETE)==0){%>	
        		<tr>
        		<td class="comLeft">
       			<% if(prospectSalesActionDetails.getChangeDate() != null) {%>
           			<%= dateFormat.format(prospectSalesActionDetails.getChangeDate())%><br/>
           			<%= timeFormat.format(prospectSalesActionDetails.getChangeDate())%>
           		<% } else {%>
           			No date or time associated with Sales Action.
           		<% } %>
           		<br>By: <%=Utilities.nullToBlank(prospectSalesActionDetails.getChangedByUserName()) %>
           		</td>
           		<td class="comRight">
            	<% if (prospectSalesActionDetails.getAction() != null) { %>
					<%=prospectSalesActionDetails.getAction()%>
            	<% } %>
            	<% if(prospectSalesActionDetails.getActionDate() != null) {%>
           			&nbsp;- Action Date:<%= dateFormat.format(prospectSalesActionDetails.getActionDate())%>&nbsp;
           			<%= timeFormat.format(prospectSalesActionDetails.getActionDate())%>
           		
           		<% } %>
            	<br/>
            	<% if (prospectSalesActionDetails.getActionNote()!=null) {%>
					<b>Note&nbsp;:&nbsp;</b>
					<%=prospectSalesActionDetails.getActionNote()%>
		   		<%}%>
				</td>
				</tr>
		   	<%}%>
		<%}%>
		</table>
    <% } %>
	<script>
	function fToggleCompleted(){
	 	var rowDisplayElement = '';
	 	if(navigator.appName == "Microsoft Internet Explorer") {
			rowDisplayElement = "block";
		} else {
			rowDisplayElement = "table";	
		}
		if(document.getElementById('completedSalesActions').style.display == 'none'){
			document.getElementById('completedSalesActions').style.display = rowDisplayElement;
		}else{
			document.getElementById('completedSalesActions').style.display = 'none';
		}	 
	}
	</script>
    <br/>

 	 <% 
 	 		boolean isAdminUser = false;
 	 	 if (userProfile.getUserType().compareTo(UserDetails.USER_TYPE_ADMIN)==0) {
           isAdminUser = true;
        }
 	 	if (commisionView.getElements().size() > 0) { 
 	 	
 	 
 	 %>
 	 
 	 		<table class="report" border="1" cellspacing="0" cellpadding="3" width="80%">
 	 		
 	 		<tr>
 	 		<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("NO")==0){ %>
				 <th colspan="12">
			<% }else { %>	
					<%  if (isAdminUser) {  %>
						<th colspan="15">
					<% }else { %>	
						<th colspan="14">
					<%  } %>
			<%  } %> 

 	 		<h3>Revenue/Commission Transactions</h3></th></tr>
		<tr>
			<th>Id</th>
		<%  if (isAdminUser) {  %>
			<th>Salesman</th>
		<% } %>
			 <th>Prospect</th>
            <th>Revenue Amt.</th>
            <th>Com. Amount</th>
            <th>Exp. Close Date</th>
             <th>Date Sold</th>
            <th>Cancel Date</th>
            <th>Date Billed</th>
            <th>Date Paid</th>
		<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0){ %>
            <th>Check Number</th>
            <th>Check Amount</th>
			<% if (isAdminUser) {  %>
            	<th>Creation Date</th>
			<% } %>
		<% } %>
			<th>Update</th>
			<th>Delete</th>

		</tr>
<%		
		String saveCurrencyCode = ""; 
		BigDecimal amount = new BigDecimal(0);
		BigDecimal revenue = new BigDecimal(0);
		CommisionDetails commisionDetails =(CommisionDetails) commisionView.getElements().elementAt(0);
		saveCurrencyCode = commisionDetails.getCurrencyCode();
		for (int i = 0; i < commisionView.getElements().size(); i++){
			commisionDetails = (CommisionDetails) commisionView.getElements().elementAt(i);
%>			
		<%if(commisionDetails.getCurrencyCode()!=null && saveCurrencyCode.compareTo(commisionDetails.getCurrencyCode())!=0){ %>

			<%  saveCurrencyCode = commisionDetails.getCurrencyCode();
			revenue = new BigDecimal(0); 
			amount = new BigDecimal(0);}%>  

					<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=commisionDetails.getCommisionId()%></th>
			<%  if (isAdminUser) {  %>
				<td nowrap align="left"><%=commisionDetails.getUserFirstName()%> <%=commisionDetails.getUserLastName()%></td>
			<% } %>
				<td align="left"><%=Utilities.nullToBlank(commisionDetails.getProspectName())%></td>
               
               <td nowrap align="right"><%=Utilities.formatNumber(commisionDetails.getRevenue(), Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%>
                <%revenue = revenue.add(commisionDetails.getRevenue());%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(commisionDetails.getAmount(), Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%>
                <%amount = amount.add(commisionDetails.getAmount());%></td>
            
               <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getExpectedCloseDate(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDateSold(), userProfile.getDateFormat())%></td>
                 <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getCancelDate(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDateBilled(), userProfile.getDateFormat())%></td>
                <td nowrap align="center"><%=DateUtilities.formatDate(commisionDetails.getDatePaid(), userProfile.getDateFormat())%></td>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("YES")==0){ %>
                <td nowrap align="left"><%=Utilities.nullToBlank(commisionDetails.getCheckNumber())%></td>
                <td nowrap align="right"><%=Utilities.formatNumber(commisionDetails.getCheckAmount(), 2)%></td>

				<%  if (isAdminUser) {  %>
                	<td nowrap align="left"><%=DateUtilities.formatDate(commisionDetails.getCreationDate(),userProfile.getDateFormat()+ " " + "hh:mm a")%></td>
                	
				<% } %>
			<% } %>
				<td nowrap align="center"><a href="javascript:fOnUpdateCommission(<%=commisionDetails.getCommisionId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Commission <%=commisionDetails.getCommisionId()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDeleteCommission(<%=commisionDetails.getCommisionId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>

			</tr>
			<tr>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("NO")==0){ %>
				 <td align="left" colspan="13"><%=Utilities.nullToBlank(commisionDetails.getDescription())%></td>
			<% }else { %>	
					<%  if (isAdminUser) {  %>
						<td align="left" colspan="17"><%=Utilities.nullToBlank(commisionDetails.getDescription())%></td>
					<% }else { %>	
						<td align="left" colspan="15"><%=Utilities.nullToBlank(commisionDetails.getDescription())%></td>
					<%  } %>
			<%  } %> 
			</tr>
			
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("NO")==0){ %>
			<% }else { %>	
					<%  if (isAdminUser && commisionDetails.getInternalDescription()!=null && commisionDetails.getInternalDescription().compareTo("")!=0) {  %>
						<tr><td align="left" colspan="17"><%=Utilities.nullToBlank(commisionDetails.getInternalDescription())%> got here</td></tr>
					<%  } %>
			<%  } %> 
			
			
<%} // end of for loop%>
		<tr class="roweven">
			<td >&nbsp;</td>
			<%  if (isAdminUser) {  %>
			<td>&nbsp;</td>
			<%}%>
			<td colspan="1"><b>Total <%=saveCurrencyCode%>: </b></td>
			<td align="right"><b> <%=Utilities.formatNumber(revenue, Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%> </b></td>
			<td align="right"><b> <%=Utilities.formatNumber(amount, Utilities.nullToBlank(commisionDetails.getCurrencyCode()) + "###,###.00")%> </b></td>
			<% if (AppSettings.getParm("DISPLAY_AP_FIELDS").compareTo("NO")==0){ %>
				 <td colspan="7">
			<% }else { %>	
					<%  if (isAdminUser) {  %>
						<td colspan="10">
					<% }else { %>	
						<td colspan="9">
					<%  } %>
			<%  } %> 
			</tr>
		

				
	</table>
	 
 	 <% } %>
 	<br>
 	<br>
    <% if (contactView.getElements().size() > 0) { %>

    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%" border="0">
    <tr><th colspan="5"><h2>Additional Contacts</h2></th></tr>
    <tr>
        <th align=center>Contact Name & Title</th>
        <th align=center colspan="2">Contact Details</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
    </tr>

        <% for (int i=0; i<contactView.getElements().size();i++) {
            ContactDetails contactDetails = (ContactDetails) contactView.getElements().elementAt(i);
        %>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td valign="top" align="center" nowrap>
            <b>
            <% if (contactDetails.getContactName() != null) { %>
                 <%=Utilities.nullToBlank(contactDetails.getContactName())%>
            <% } %>
            <% if (contactDetails.getContactTitle() != null) { %>
                 <br>(<%=Utilities.nullToBlank(contactDetails.getContactTitle())%>)
            <% } %>
            </b>
        </td>
        <td valign="top" align="left" colspan="2">
            <% if (contactDetails.getPhone() != null) { %>
                  Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(contactDetails.getPhone()))%>
            <%    if (contactDetails.getPhoneExt() != null){%>
                      &nbsp&nbsp;Ext: <%=Utilities.nullToBlank(contactDetails.getPhoneExt())%>
            <%    } %>
            <% } %>
            <% if (contactDetails.getCellPhone() != null) { %>
                  <br>Cell Phone: <%=Utilities.nullToBlank(contactDetails.getCellPhone())%>
            <% } %>
            <% if (contactDetails.getFax() != null) { %>
                  <br>Fax: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(contactDetails.getFax()))%>
            <% } %>
            <% if (contactDetails.getEmail() != null) { %>
                  <br><a href="mailto:<%=Utilities.nullToBlank(contactDetails.getEmail())%>">
                  <%=Utilities.nullToBlank(contactDetails.getEmail())%>
                  </a>
            <% } %>

        </td>
        <td valign="top"  width="1"><a href="javascript:fOnUpdateContact(<%=contactDetails.getContactId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Contact"></a><br><br></td>
        <td valign="top"  width="1"><a href="javascript:fOnDeleteContact(<%=contactDetails.getContactId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Contact"></a><br><br></td>
    </tr>
    <% } %>
    </table>

    <br>
    <% } %>

    <% if (noteView.getElements().size() > 0)  { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%" border="0">
    <tr><th colspan="4"><h2>Notes</h2></th></tr>
    <tr>
        <th align=center>User & Date</th>
        <th align=center>Note</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
<%--        <th align=center>Edit</th>--%>
    </tr>
        <% for (int i=0; i<noteView.getElements().size();i++) {
                    NoteDetails noteDetails = (NoteDetails) noteView.getElements().elementAt(i);
        %>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td valign="top" align="center" nowrap><b><%=noteDetails.getUserName()%></b>
        		<br><%=DateUtilities.formatDate(noteDetails.getNoteDate(),userProfile)%>
                <br><%=DateUtilities.formatDate(noteDetails.getNoteDate(), userProfile, "hh:mm a")%>
        </td>
        <td valign="top" width="75%"  align="left"><textarea readonly cols="90" rows="2"><%=noteDetails.getNote()%></textarea></td>
        <td valign="top"  width="1"><a href="javascript:fOnUpdateNote(<%=noteDetails.getNoteId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Note"></a><br><br></td>
        <td valign="top"  width="1"><a href="javascript:fOnDeleteNote(<%=noteDetails.getNoteId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Note"></a><br><br></td>
<%--        <th valign="top" nowrap align="center"><a href="javascript:fOnUpdateNote(<%=noteDetails.getNoteId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>--%>
    </tr>
    <% } %>
    </table><br>
    <% } %>

    <% if (websiteView.getElements().size() > 0) { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%" border="0">
    <tr><th colspan="3"><h2>Additional Websites</h2></th></tr>
    <tr>
        <th align=center>URL & Description</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
<%--        <th align=center>Edit</th>--%>
    </tr>

        <% for (int i=0; i<websiteView.getElements().size();i++) {
                    WebsiteDetails websiteDetails = (WebsiteDetails) websiteView.getElements().elementAt(i);
        %>

    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td valign="top" align="left"  width="95%"  >
            <% String link=websiteDetails.getUrl();
                if (!websiteDetails.getUrl().startsWith("http://")
                    && !websiteDetails.getUrl().startsWith("https://")){
                    link="http://"+ websiteDetails.getUrl();
                }
            %>
            <a href="<%=link%>" target="_blank">
                <%=websiteDetails.getUrl()%>
            <a>
            <br>
            <%=websiteDetails.getDescription()%>
        </td>
        <td valign="top" width="1" ><a href="javascript:fOnUpdateWebsite(<%=websiteDetails.getWebsiteId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Website"></a><br><br></td>
        <td valign="top" width="1" ><a href="javascript:fOnDeleteWebsite(<%=websiteDetails.getWebsiteId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Website"></a><br><br></td>
<%--        <th valign="top" nowrap align="center"><a href="javascript:fOnUpdateWebsite(<%=websiteDetails.getWebsiteId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>--%>
    </tr>

    <% } %>
    </table><br>
    <% } %>

    <% if (prospectDocumentView.getElements().size() > 0) { %>
    <table class="report" border="1" cellspacing="0" cellpadding="3" width="95%" border="0">
    <tr><th colspan="5"><h2>Additional Documents</h2></th></tr>
    <tr>
        <th align=center colspan="2">File Name & Description</th>
        <th align=center>Document Type</th>
        <th align=center>Edit</th>
        <th align=center>Del</th>
    </tr>

<% for (int i=0; i<prospectDocumentView.getElements().size();i++) {
    ProspectDocumentDetails prospectDocumentDetails
        = (ProspectDocumentDetails) prospectDocumentView.getElements().elementAt(i);
    String imgFileName = "unknown.gif";
    if (prospectDocumentDetails.getFileType() != null){
        imgFileName = prospectDocumentDetails.getFileType()+".gif";
    }

%>
    <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
        <td width="10" valign="top">
            <img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/filetypes/<%=imgFileName%>">
        </td>
        <td valign="top" align="left"  width="75%"  >
            <% String link=AppSettings.getAppWebPath()+"doc/"+prospectDocumentDetails.getFileName();%>
            <a href="<%=link%>" target="_blank">
                <%=prospectDocumentDetails.getFileName()%>
            <a>
            <br>
            <%=prospectDocumentDetails.getDescription()%>
        </td>
        <td valign="top">
            <%=ProspectDocumentDetails.getDocumentTypeDescription(prospectDocumentDetails.getDocumentType())%>
            <br> Created:&nbsp;<%=DateUtilities.formatDate(prospectDocumentDetails.getCreationDate(), userProfile)%><br>
            By:&nbsp;<%=prospectDocumentDetails.getCreationUserName() %>
        </td>

        <td valign="top" ><a href="javascript:fOnUpdateDocument(<%=prospectDocumentDetails.getProspectDocumentId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Document"></a><br><br></td>
        <td valign="top" ><a href="javascript:fOnDeleteDocument(<%=prospectDocumentDetails.getProspectDocumentId()%>);"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete Document"></a><br><br></td>


    </tr>
    <% } %>
    </table><br>
    <% } %>

<!--     <script>fSelectSalesAction();</script> -->
    <% }// end updateScreen.booleanValue() line 600 %>
    </td>
    </td>
    </tr></table>
    </center>
 
  
	<script>
    	var selectDate = new calendar2(document.forms['frmProspect'].elements['dfActivityDate']);
		selectDate.year_scroll = true;
		selectDate.time_comp = false;
	</script>

<%
    }catch (Exception e){
    	e.printStackTrace();
%>
	
    <b>Error:</b><%=e.getMessage()%>
<%
    }
%>
<%=AppSettings.getFooter(userProfile)%>