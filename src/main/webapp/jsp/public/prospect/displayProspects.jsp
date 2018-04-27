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
<script>

			function displayJasperReport() {
			   var objJasperReportForm = document.jasperReportForm;
			   var width = 980;
			   var height = screen.availHeight - 50;			
			   wTestReportChild = window.open('', objJasperReportForm.target,
			    	'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
			        'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=no');
			   objJasperReportForm.submit();
			   wTestReportChild.opener = self;
			   wTestReportChild.focus();
			}
	
var jsquote = "'";
   function sortReport(sSortBy, sSortDirection){
   document.frmProspect.target='';
   document.frmProspect.action='prospect_search';
   document.frmProspect.dfSortBy.value=sSortBy;
   document.frmProspect.dfSortDirection.value=sSortDirection;
   document.frmProspect.formAction.value='DISPLAY';
   document.frmProspect.submit();
   }

</script>
<%!
    private String displayHeaderWithSort(String sortByField,
                                         String sortByDirection,
                                         String getterMethodName) {
        StringBuffer headerStr = new StringBuffer("");
        String upTrue = "";
        String downTrue = "";

        if (sortByField.equals(getterMethodName)) {
            if ( sortByDirection.equals("")
                    || sortByDirection.equals(String.valueOf(CollectionUtilities.SORT_UP) )) {
                upTrue = "True";
            } else {
                downTrue = "True";
            }
        }
        headerStr.append("<table border=\"0\" cellspcing=\"0\" cellpadding=\"0\"><tr>");

        headerStr.append("<th><a href=\"javascript:sortReport('"+ getterMethodName + "','"+CollectionUtilities.SORT_UP+"')\">");
        headerStr.append("<img src=\""+ AppSettings.getGraphicsPath()
                + "upsort" + upTrue + ".gif\" border=\"0\"></a></th>");
        headerStr.append("<th><a href=\"javascript:sortReport('"+ getterMethodName + "','"+CollectionUtilities.SORT_DOWN+"')\">");
        headerStr.append("<img src=\"" + AppSettings.getGraphicsPath()
                + "downsort" + downTrue + ".gif\" border=\"0\"></a></th>");
        headerStr.append("</tr></table>");
        return headerStr.toString();
    } %>

<% try { %>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospect/displayProspects.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<% String prospectsHeading = "Prospects";
	if(AppSettings.getParm("PROSPECTS").compareTo("")!=0 &&
        		AppSettings.getParm("PROSPECTS")!=null){
        	prospectsHeading = AppSettings.getParm("PROSPECTS");
        }
 %>
<center>
<div id="displayProspects">
<h1><%=prospectsHeading %></h1>
<form name="frmProspectDownload" method="post" action="prospect">
    <input type="hidden" name="formAction" value="DOWNLOAD">
</form>

<form name="frmProspect" method="post" action="">
<input type="hidden" name="formAction" value="">
<input type="hidden" name="oldFormAction" value="DISPLAY">
<input type="hidden" name="oldAction" value="prospect_search">
<input type="hidden" name="dfProspectId" value="">
<input type="hidden" name="dfSortBy" value="">
<input type="hidden" name="dfSortDirection" value="">

<% if (prospectView.getElements().size() > 0) { %>
        <table border="0" width="99%" cellspacing="0" cellpadding="3"><tr>
        
                <td class="navigationbar1"  align="left" nowrap width="10%">
         <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                	<%if(userProfile.getLimitProspectSearchView()== null ||
                		Utilities.nullToZero(userProfile.getLimitProspectSearchView()).compareTo(UserDetails.DO_NOT_LIMIT_VIEW) == 0){ %>
                	<a href="javascript: displayJasperReport();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/print.gif" alt="Print Record"></a>
                    <a href="javascript: displayJasperReport();">Print Prospect List</a>
              
                    &nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:fOnDownload();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/filesave.gif" alt="Download as CSV file"></a>
                    <a href="javascript:fOnDownload();">Download CSV File</a>
                     &nbsp;&nbsp;|&nbsp;&nbsp;
               	 	<%}%>
              
               
                
					<% if (prospectView.getElements().size() > 1) { %>
					
						<% if(AppSettings.getParm("DISPLAY_ALL_CHECKED").equalsIgnoreCase("YES") ||
							userProfile.getUserType().equals(UserDetails.USER_TYPE_ADMIN)){%>
							<a href="javascript:if(fProspectsAreChecked()){fChangeAllChecked()};"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit_collection.gif" alt="Edit collection"></a>
							<a href="javascript:if(fProspectsAreChecked()){fChangeAllChecked()};">Change All Checked</a>   
						<%}%>
						                 
	                    <a href="javascript:fOnEditCollection();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit_collection.gif" alt="Edit collection"></a>
	                    <a href="javascript:fOnEditCollection();">Change all prospects in result set</a>
	                    &nbsp;&nbsp;|&nbsp;&nbsp;
	                    <a href="javascript:fOnRemoveChecked();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/cancel.gif" alt="Remove checked prospects"></a>
	                    <a href="javascript:fOnRemoveChecked();">Remove checked prospects from result set</a>
	                    &nbsp;&nbsp;|&nbsp;&nbsp;
                    <% } %>
                    <a href="javascript:fOnBackToSearch();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/back.gif" alt="Back"></a>
                    <a href="javascript:fOnBackToSearch();">Back to Search Criteria</a>
                    &nbsp;&nbsp;|&nbsp;&nbsp;
                    <% if(AppSettings.getParm("AUTOMATED_EMAIL_EXPORT_ENABLED").equalsIgnoreCase("true") || 
                    AppSettings.getParm("AUTOMATED_EMAIL_EXPORT_ENABLED").equalsIgnoreCase("yes")) { %>
                    	<% if (userProfile.getUserType().compareTo(UserDetails.USER_TYPE_ADMIN)==0){ %>
                    		<a href="javascript:fSendEmailQue();">Send Email</a>
                    	<% } %>
                    <% }  %>	
                    <%  }else{ //else for limit view %>
                    	 <a href="javascript:fOnBackToSearch();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/back.gif" alt="Back"></a>
                    	<a href="javascript:fOnBackToSearch();">Back to Search Criteria</a>
                     <% }//end limit view %> 
                </td>
                
        </tr></table>
    <br>
<% } %>

<table>
<tr><td align="center">
    <% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
                    <p class="exception"><%=modelException.getMessage()%></p>
    <% } %>

    <% if (prospectView.getElements().size() > 0) { 
    //calculate the totals
    	BigDecimal totalOpenOrdersNumber = new BigDecimal(0);
    	BigDecimal totalOpenOrdersValue = new BigDecimal(0);
    	BigDecimal totalOpenQuotesNumber = new BigDecimal(0);
    	BigDecimal totalOpenQuotesValue = new BigDecimal(0);
    	BigDecimal totalOpenAccountsReceivable = new BigDecimal(0);
    	
    	BigDecimal totalInvoicessNumberLtd = new BigDecimal(0);
    	BigDecimal totalInvoicesValueLtd = new BigDecimal(0);
    	BigDecimal totalInvoicessNumberYtd = new BigDecimal(0);
    	BigDecimal totalInvoicesValueYtd = new BigDecimal(0);
    	BigDecimal totalForecast = new BigDecimal(0);
    	%>
                    Records selected: <%=prospectView.getElements().size()%>
                    
                              <!-- put the tabs here -->
                 <table class="report" border="0" cellspacing="0" cellpadding="3">
                 <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
              		<tr id="tabs">
              		
						<td colspan="2" align="center" id="viewOneTab">
							<a href="javascript: displayViewOne();"><img class="button" 
								src="<%=AppSettings.getAppWebPath()%>graphics/ProspectView.png" alt="Prospect View"></a>
	       				</td>
	       			
	       				<td colspan="2" align="center" id="viewTwoTab">
							<a href="javascript: displayViewTwo();"><img class="button" 
								src="<%=AppSettings.getAppWebPath()%>graphics/PerformanceDataView.png" alt="Performance Data View"></a>
	       				</td>
	       				   
	       					      				
					</tr>
					<% }// end limit view %>	
                 </table>
                    
                   <%--  Beginning of the display results   View One ---%>
                    <table class="report" border="1" cellspacing="0" cellpadding="3" id="viewOne">
                    <tr>

                    <th rowspan="2">
                    	Id<br/>
                    	<input type="checkbox" name="checkall" onclick="checkUncheckAll(this);" class="largeCheckbox">
                    </th>
                    <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                    <th rowspan="2">Edit/Print<br>Delete</th>
                    <th rowspan="2">Notes</th>
                    <% } %>
                    <th>Company<br>Name</th>
                    <th>Address</th>
                    	<th>City</th>
                    	<th>Zip</th>
                    	<th>County</th>
                    	<th>Country and State</th>
					<%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                    
                    	
                    	<th>Contact</th>
                    	
                    <%} %>
                    <% if(labelView.getLabelShort(LabelView.LOB).compareTo("LOB")==0){// doesnot exist in DB %>
                    	<th>L.O.B.</th>
                    	<% }else{ %>
                    	<th><%=labelView.getLabelShort(LabelView.LOB) %></th>
                    	<% } %>
                    	  <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                    	<th>Territory</th>
                    	<% } //end limit view %>
                    	<th><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></th>
                    	<th><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></th>
                    	<%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %> 
                    	<th nowrap>ID/Status</th>
                    	<% } %>
                    	 <% if(labelView.getLabelShort(LabelView.VERIFIED).compareTo("Verified")==0){// doesnot exist in DB %>
                    	<th>Verified</th>
                    	<% }else{ %>
                    	<th><%=labelView.getLabelShort(LabelView.VERIFIED) %></th>
                    	<% } %>
                    	
                    	<%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %> 
                    	<th>Size</th>
                    	
						<% } %>
                    </tr>
                    <tr>
                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCustomerCompany")%></th>
                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getAddress")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCity")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getZip")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCounty")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCountryAndStateSortValue")%></th>
 						<%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>               

                    		
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getContactName")%></th>
                    	
                    	<%}%>
                    
                    
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getLobName")%></th>
	                      <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getTerritoryName")%></th>
	                    <% } // end limit view %>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getSystemTypeName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getSoftwareTypeName")%></th>
	                    <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %> 
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getStatusName")%></th>
	                    <% }//end limit view %>
	               
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getVerifiedName")%></th>
	                     <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %> 
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getSizeName")%></th>
	                    <% }//end limit view %>
                    </tr>
                    <% for (int i = 0; i < prospectView.getElements().size(); i++) {
                        ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i); %>
                        <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
                        <th valign="top" nowrap align="center">
                        		<input type="checkbox" name="chkId<%=i%>" value="<%=prospectDetails.getProspectId()%>" onclick="javascript:uncheckCheckallCheckBox();" class="prospectCheckbox">
                                <br>
                                <%=prospectDetails.getProspectId()%>
                        </th>
                        <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                        <th valign="top" nowrap align="center">
                            <table border="0" cellspacing="0" cellpadding="2"><tr>
                            <th valign="top"><a href="javascript:fOnUpdate(<%=prospectDetails.getProspectId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>
                            <th valign="top"><a href="javascript:fOnPrint(<%=prospectDetails.getProspectId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/print.gif" alt="Print Record"></a></th>
                            </tr>
                         <%if(userProfile.getLimitProspectSearchView()== null ||
                			Utilities.nullToZero(userProfile.getLimitProspectSearchView()).compareTo(UserDetails.DO_NOT_LIMIT_VIEW) == 0){ %>
                            <tr>
                            <th valign="top"><a href="javascript:if(confirm('<%=Utilities.escapeProblemCharacters(prospectDetails.getCustomerCompany(), "")%> will be permanently deleted from the database, if there are no notes, documents, contacts etc defined.\n\nPlease confirm.')) {fOnDelete(<%=prospectDetails.getProspectId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Safe Delete Record"></a></th>
                            <th valign="top"><a href="javascript:if(confirm('<%=Utilities.escapeProblemCharacters(prospectDetails.getCustomerCompany(), "")%> will be permanently deleted from the database, including related data such as notes, additional contacts, websites etc.\n\nPlease confirm.')) {fOnDeleteCascade(<%=prospectDetails.getProspectId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete_cascade.gif" alt="Cascade Delete Record"></a></th>
                            </tr>
                            <% }%>
                          </table>
                        </th>

                        <th valign="top" nowrap align="center">
                            <a href="javascript:fOnNotes(<%=prospectDetails.getProspectId()%>,true);">
                                <% String imgName = "postit_bw.gif";
                                   if (prospectDetails.getCountNotes() != null && prospectDetails.getCountNotes().compareTo(new Integer(0)) > 0) {
                                       imgName = "postit.gif";
                                   }
                                %>
                                <img src="<%=AppSettings.getAppWebPath()%>graphics/icons/<%=imgName%>" alt="Notes">
                            </a>
                            <br>
                            <% if (prospectDetails.getCountNotes() != null && prospectDetails.getCountNotes().compareTo(new Integer(0)) > 0) {%>
                                <%=prospectDetails.getCountNotes()%>
                            <% } else { %>
                            none
                            <% } %>
                        </th>
                         <%}%>  
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%></td>
                       
                        
                        		<td valign="top" align="left" nowrap>
                            		<% if (!Utilities.nullToBlank(prospectDetails.getAddress()).trim().equals("")) { %>
                                		<%=Utilities.nullToBlank(prospectDetails.getAddress())%><br>
                            		<% } %>
                            		<% if (!Utilities.nullToBlank(prospectDetails.getAddress2()).trim().equals("")) { %>
                                		<%=Utilities.nullToBlank(prospectDetails.getAddress2())%><br>
                            		<% } %>
                        		</td>
		                        <td valign="top" align="left" nowrap>
		                        <%=Utilities.nullToBlank(prospectDetails.getCity())%>
		                        </td>
		                        <td valign="top" align="left" nowrap>
		                        <%=Utilities.nullToBlank(prospectDetails.getZip())%>
		                        </td>
		                        <td valign="top" align="left" nowrap>
		                                <%=Utilities.nullToBlank(prospectDetails.getCounty())%>
		                        </td>
		                        <td valign="top" align="left" nowrap>
		                        <%=Utilities.nullToBlank(prospectDetails.getCountryName())%>
		                        <br>
		                        <%=Utilities.nullToBlank(prospectDetails.getStateName())%>
		                        </td>
		                     <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>  
		                        <td valign="top" align="left" nowrap>
		                        <% if (prospectDetails.getContactName() != null) { %>
		                            <%=Utilities.nullToBlank(prospectDetails.getContactName())%>
		                        <% } %>
		                        <% if (prospectDetails.getContactTitle() != null) { %>
		                            <br>(<%=Utilities.nullToBlank(prospectDetails.getContactTitle())%>)
		                        <% } %>
		
		                        <% if (prospectDetails.getPhone() != null) { %>
		                            <br>Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getPhone()))%>
		                            <%if (prospectDetails.getPhoneExt() != null){%>
		                                <br>Phone Ext: <%=Utilities.nullToBlank(prospectDetails.getPhoneExt())%>
		                            <% } %>
		                        <% } %>
		                        <% if (prospectDetails.getCellPhone() != null) { %>
		                            <br>Cell Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getCellPhone()))%>
		                        <% } %>
		                        <% if (prospectDetails.getFax() != null) { %>
		                            <br>Fax: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getFax()))%>
		                        <% } %>
		                        <% if (prospectDetails.getEmail() != null) { %>
		                            <br><a href="mailto:<%=Utilities.nullToBlank(prospectDetails.getEmail())%>">
		                                    <%=Utilities.nullToBlank(prospectDetails.getEmail())%>
		                            </a>
		                        <% } %>
		                        <% if (prospectDetails.getWebsite() != null) { %>
		                        <br>
		                        <a href="<%=Utilities.validWebSiteAddress(prospectDetails.getWebsite())%>" target="_wnd<%=prospectDetails.getProspectId()%>">
		                              <%=Utilities.nullToBlank(prospectDetails.getWebsite())%>
		                        </a>
		                        <% } %>
		                        </td>
		                        
		                   <%} %>

                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getLobName())%></td>
                         <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %> 
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getTerritoryName())%></td>
                        <% } //end limit view %>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getSystemTypeName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getSoftwareTypeName())%></td>
                     <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>  
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getStatusName())%></td>
                       <%  }// end limit view %>  
                 
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getVerifiedName())%></td>
                           <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %> 
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getSizeName())%></td>
                      <%  }// end limit view %>  
                        </tr>
                        <% } %>
                  </table>
                    <%--  Beginning of the display results   View Two ---%>
                  
                    <table class="report" border="1" cellspacing="0" cellpadding="3" id="viewTwo" style="display:none">
                   <tr>

                    <th rowspan="2">
                    	Id<br/>
                    	<input type="checkbox" name="checkall" onclick="checkUncheckAll(this);" class="largeCheckbox">
                    </th>
                    <th rowspan="2">Edit/Print<br>Delete</th>
                    <th rowspan="2">Notes</th>
                    <th>Company<br>Name</th>
					<%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                    
                    	
                    	<th>Contact</th>
                    	
                    <%} %>
					<% if (AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0){ %>
                    	<th>Open Orders<br/>Number</th>
                    	<th>Open Orders<br/>Value</th>
                    <%  } %>	
                    <% if (AppSettings.getParm("DISPLAY_OPEN_QUOTES_FIELDS").compareTo("YES")==0){ %>
                    	<th>Open Quotes<br/>Number</th>
                    	<th>Open Quotes<br/>Value</th>
                    <%  } %>
                    <% if (AppSettings.getParm("DISPLAY_OPEN_AR_FIELDS").compareTo("YES")==0){ %>	
                    	<th>Open AR</th>
                    <%  } %>
                    <% if (AppSettings.getParm("DISPLAY_ORDERS_INVOICES_FIELDS").compareTo("YES")==0){ %>	
                    	<th>LTD Ord/Inv<br/>Number</th>
                    	<th>LTD Ord/Inv<br/>Value</th>
                    	<th>YTD Ord/Inv<br/>Number</th>
                    	<th>YTD Ord/Inv<br/>Value</th>
                    	<th>Forecast</th>
					<%  } %>	
                    </tr>
                    <tr>
                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCustomerCompany")%></th>
 						<%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>               

                    		
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getContactName")%></th>
                    	
                    	<%}%>
                    
						<% if (AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0){ %>                    
	                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getOpenOrdersNumber")%></th>
	                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getOpenOrdersValue")%></th>
	                    <% }  %>
	                    <% if (AppSettings.getParm("DISPLAY_OPEN_QUOTES_FIELDS").compareTo("YES")==0){ %>
	                    	 <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getOpenQuotesNumber")%></th>
	                  	  <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getOpenQuotesValue")%></th>
	                   <%  } %>
	                    <% if (AppSettings.getParm("DISPLAY_OPEN_AR_FIELDS").compareTo("YES")==0){ %>
	                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getOpenAccountsReceivable")%></th>
	                    <%  } %>
	                    <% if (AppSettings.getParm("DISPLAY_ORDERS_INVOICES_FIELDS").compareTo("YES")==0){ %>
	                     	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getInvoicesNumberLtd")%></th>
	                     	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getInvoicesValueLtd")%></th>
	                     	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getInvoicesNumberYtd")%></th>
	                     	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getInvoicesValueYtd")%></th>
	                     	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getForecast")%></th>
	                    <%  } %>     
                    </tr>
                    <% for (int i = 0; i < prospectView.getElements().size(); i++) {
                        ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i); 
                        totalOpenOrdersNumber = totalOpenOrdersNumber.add(Utilities.nullToZero(prospectDetails.getOpenOrdersNumber()));
    					totalOpenOrdersValue = totalOpenOrdersValue.add(Utilities.nullToZero(prospectDetails.getOpenOrdersValue()));
    					totalOpenQuotesNumber = totalOpenQuotesNumber.add(Utilities.nullToZero(prospectDetails.getOpenQuotesNumber()));
    					totalOpenQuotesValue = totalOpenQuotesValue.add(Utilities.nullToZero(prospectDetails.getOpenQuotesValue()));
    					totalOpenAccountsReceivable = totalOpenAccountsReceivable.add(Utilities.nullToZero(prospectDetails.getOpenAccountsReceivable()));
    	
    					totalInvoicessNumberLtd = totalInvoicessNumberLtd.add(Utilities.nullToZero(prospectDetails.getInvoicesNumberLtd()));;
    					totalInvoicesValueLtd = totalInvoicesValueLtd.add(Utilities.nullToZero(prospectDetails.getInvoicesValueLtd()));;
    					totalInvoicessNumberYtd = totalInvoicessNumberYtd.add(Utilities.nullToZero(prospectDetails.getInvoicesNumberYtd()));;
    					totalInvoicesValueYtd = totalInvoicesValueYtd.add(Utilities.nullToZero(prospectDetails.getInvoicesValueYtd()));;
    					totalForecast = totalForecast.add(Utilities.nullToZero(prospectDetails.getForecast()));;
                        
                        %>
                        <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
                        <th valign="top" nowrap align="center">
                        		<input type="checkbox" name="chkId<%=i%>" value="<%=prospectDetails.getProspectId()%>" onclick="javascript:uncheckCheckallCheckBox();" class="prospectCheckbox">
                                <br>
                                <%=prospectDetails.getProspectId()%>
                        </th>
                        <th valign="top" nowrap align="center">
                            <table border="0" cellspacing="0" cellpadding="2"><tr>
                            <th valign="top"><a href="javascript:fOnUpdate(<%=prospectDetails.getProspectId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>
                            <th valign="top"><a href="javascript:fOnPrint(<%=prospectDetails.getProspectId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/print.gif" alt="Print Record"></a></th>
                            </tr>
                        <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
                            <tr>
                            <th valign="top"><a href="javascript:if(confirm('<%=Utilities.escapeProblemCharacters(prospectDetails.getCustomerCompany(), "")%> will be permanently deleted from the database, if there are no notes, documents, contacts etc defined.\n\nPlease confirm.')) {fOnDelete(<%=prospectDetails.getProspectId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Safe Delete Record"></a></th>
                            <th valign="top"><a href="javascript:if(confirm('<%=Utilities.escapeProblemCharacters(prospectDetails.getCustomerCompany(), "")%> will be permanently deleted from the database, including related data such as notes, additional contacts, websites etc.\n\nPlease confirm.')) {fOnDeleteCascade(<%=prospectDetails.getProspectId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete_cascade.gif" alt="Cascade Delete Record"></a></th>
                            </tr>
                         <%}%>  
                          </table>
                        </th>

                        <th valign="top" nowrap align="center">
                            <a href="javascript:fOnNotes(<%=prospectDetails.getProspectId()%>,true);">
                                <% String imgName = "postit_bw.gif";
                                   if (prospectDetails.getCountNotes() != null && prospectDetails.getCountNotes().compareTo(new Integer(0)) > 0) {
                                       imgName = "postit.gif";
                                   }
                                %>
                                <img src="<%=AppSettings.getAppWebPath()%>graphics/icons/<%=imgName%>" alt="Notes">
                            </a>
                            <br>
                            <% if (prospectDetails.getCountNotes() != null && prospectDetails.getCountNotes().compareTo(new Integer(0)) > 0) {%>
                                <%=prospectDetails.getCountNotes()%>
                            <% } else { %>
                            none
                            <% } %>
                        </th>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%></td>
                        <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>  
                        
                        		
		                        <td valign="top" align="left" nowrap>
		                        <% if (prospectDetails.getContactName() != null) { %>
		                            <%=Utilities.nullToBlank(prospectDetails.getContactName())%>
		                        <% } %>
		                        <% if (prospectDetails.getContactTitle() != null) { %>
		                            <br>(<%=Utilities.nullToBlank(prospectDetails.getContactTitle())%>)
		                        <% } %>
		
		                        <% if (prospectDetails.getPhone() != null) { %>
		                            <br>Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getPhone()))%>
		                            <%if (prospectDetails.getPhoneExt() != null){%>
		                                <br>Phone Ext: <%=Utilities.nullToBlank(prospectDetails.getPhoneExt())%>
		                            <% } %>
		                        <% } %>
		                        <% if (prospectDetails.getCellPhone() != null) { %>
		                            <br>Cell Phone: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getCellPhone()))%>
		                        <% } %>
		                        <% if (prospectDetails.getFax() != null) { %>
		                            <br>Fax: <%=Utilities.nullToBlank(Utilities.formatPhoneNumber(prospectDetails.getFax()))%>
		                        <% } %>
		                        <% if (prospectDetails.getEmail() != null) { %>
		                            <br><a href="mailto:<%=Utilities.nullToBlank(prospectDetails.getEmail())%>">
		                                    <%=Utilities.nullToBlank(prospectDetails.getEmail())%>
		                            </a>
		                        <% } %>
		                        <% if (prospectDetails.getWebsite() != null) { %>
		                        <br>
		                        <a href="<%=Utilities.validWebSiteAddress(prospectDetails.getWebsite())%>" target="_wnd<%=prospectDetails.getProspectId()%>">
		                              <%=Utilities.nullToBlank(prospectDetails.getWebsite())%>
		                        </a>
		                        <% } %>
		                        </td>
		                        
		                   <%} %>
						<% if (AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0){ %>
                      	  	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getOpenOrdersNumber()),0)%></td>
                     	   	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getOpenOrdersValue()),2)%></td>
                       <%  } %>
                       <% if (AppSettings.getParm("DISPLAY_OPEN_QUOTES_FIELDS").compareTo("YES")==0){ %>
                       		 <td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getOpenQuotesNumber()),0)%></td>
                        	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getOpenQuotesValue()),2)%></td>
                       <%  } %>
                        <% if (AppSettings.getParm("DISPLAY_OPEN_AR_FIELDS").compareTo("YES")==0){ %>
                       		 <td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getOpenAccountsReceivable()),2)%></td>
                        <%  } %>
                        <% if (AppSettings.getParm("DISPLAY_ORDERS_INVOICES_FIELDS").compareTo("YES")==0){ %>
                        	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getInvoicesNumberLtd()),0)%></td>
                        	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getInvoicesValueLtd()),2)%></td>
                        	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getInvoicesNumberYtd()),0)%></td>
                        	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getInvoicesValueYtd()),2)%></td>
                        	<td valign="top" align="left"><%=Utilities.formatNumber(Utilities.nullToZero(prospectDetails.getForecast()),2)%></td>
                        <%  } %>
                    
                        </tr>
                        <% if(prospectView.getElements().size()==i+1) { %>
                        	<tr>
                        		<th colspan="4">&nbsp;</th>
		                 	 	<th colspan="1">Totals:</th>
		                 	 <% if (AppSettings.getParm("DISPLAY_OPEN_ORDERS_FIELDS").compareTo("YES")==0){ %>
		                      	  	<th valign="top" align="left"><%=Utilities.formatNumber(totalOpenOrdersNumber,0)%></th>
		                     	   	<th valign="top" align="left"><%=Utilities.formatNumber(totalOpenOrdersValue,2)%></th>
		                       <%  } %>
		                       <% if (AppSettings.getParm("DISPLAY_OPEN_QUOTES_FIELDS").compareTo("YES")==0){ %>
		                       		 <th valign="top" align="left"><%=Utilities.formatNumber(totalOpenQuotesNumber,0)%></td>
		                        	<th valign="top" align="left"><%=Utilities.formatNumber(totalOpenQuotesValue,2)%></td>
		                       <%  } %>
		                        <% if (AppSettings.getParm("DISPLAY_OPEN_AR_FIELDS").compareTo("YES")==0){ %>
		                       		 <th valign="top" align="left"><%=Utilities.formatNumber(totalOpenAccountsReceivable,2)%></th>
		                        <%  } %>
		                        <% if (AppSettings.getParm("DISPLAY_ORDERS_INVOICES_FIELDS").compareTo("YES")==0){ %>
		                        	<th valign="top" align="left"><%=Utilities.formatNumber(totalInvoicessNumberLtd,0)%></th>
		                        	<th valign="top" align="left"><%=Utilities.formatNumber(totalInvoicesValueLtd,2)%></th>
		                        	<th valign="top" align="left"><%=Utilities.formatNumber(totalInvoicessNumberYtd,0)%></th>
		                        	<th valign="top" align="left"><%=Utilities.formatNumber(totalInvoicesValueYtd,2)%></th>
		                        	<th valign="top" align="left"><%=Utilities.formatNumber(totalForecast,2)%></th>
		                        <%  } %>
                        	</tr>
                        <%  } %>
                        <% } %>
                        
                  </table> 
                  <%-- End of View 2 --%>
                  
                  Records selected: <%=prospectView.getElements().size()%>
                  <% } %>
              </td></tr></table>
		<script>
			<%-- Next Scripting is to clear all checkboxes on refresh or reload --%>
			uncheckCheckallCheckBox();
			clear();
		</script>
</form>
</div>
        </center>
    <% } catch (Exception e) { %>
        Error: <b><%=e.getMessage()%></b>
    <% e.printStackTrace(); } %>
    	
         <form name="jasperReportForm" method="post" action="prospect_search_report" target="prospect_search_jasper_report">
	     	<input type=hidden name="reportURL" value="prospect_search_report">
	     	<input type=hidden name="reportLayout" value="JASPER">
	     	<input type=hidden name="formAction" value="DISPLAY">
	     </form>

<%=AppSettings.getFooter(userProfile)%>