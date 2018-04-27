<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
                com.randr.webdw.util.Utilities,
                com.randr.webdw.prospect.ProspectDetails,
                com.randr.webdw.user.UserDetails,
                com.randr.webdw.mvcAbstract.exception.ModelException,
                java.math.BigDecimal,
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


<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Prospect Duplicate Results", "","", "")%>

<script>
	
var jsquote = "'";
   function sortReport(sSortBy, sSortDirection){
   document.frmDuplicates.target='';
   document.frmDuplicates.action='prospect_search';
   document.frmDuplicates.dfSortBy.value=sSortBy;
   document.frmDuplicates.dfSortDirection.value=sSortDirection;
   document.frmDuplicates.formAction.value='DISPLAY';
   document.frmDuplicates.submit();
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

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/duplicateSearch/displayProspects.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/public.css?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">

<body id="duplicateSearch">

<center>
<h1>Possible Prospect Duplicates</h1>
<form name="frmProspectDownload" method="post" action="prospect">
    <input type="hidden" name="formAction" value="DOWNLOAD">
</form>

<form name="frmDuplicates" method="post" action="">
<input type="hidden" name="formAction" value="">
<input type="hidden" name="oldFormAction" value="SEARCH_FOR_PROSPECT_DUPLICATES">
<input type="hidden" name="oldAction" value="prospect_search">
<input type="hidden" name="dfProspectId" value="">
<input type="hidden" name="dfSortBy" value="">
<input type="hidden" name="dfSortDirection" value="">
<input type="hidden" name="isDuplicateSearch" value="YES">

<% if (prospectView.getElements().size() > 0) { %>
        <table border="0" width="99%" cellspacing="0" cellpadding="3" class="">
        	<tr>
        
                <td class="headerBar"  align="left" nowrap width="10%">
                
					<% if (prospectView.getElements().size() > 1) { %>     				               
	                    
	                    <center>    
	                    <a href="javascript:fOnEditCollection();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit_collection.gif" alt="Edit collection"></a>
	                    <a href="javascript:fOnEditCollection();" class="header">Change all prospects</a>
	                    &nbsp;&nbsp;|&nbsp;&nbsp;
	                    <a href="javascript:fOnDeleteChecked();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/cancel.gif" alt="Delete checked prospects"></a>
	                    <a href="javascript:fOnDeleteChecked();" class="header">Delete all checked prospects</a>
	                    &nbsp;&nbsp;|&nbsp;&nbsp;
	                    <a href="javascript:fOnRemoveChecked();"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/cancel.gif" alt="Remove checked prospects"></a>
	                    <a href="javascript:fOnRemoveChecked();" class="header">Remove all checked prospects</a>
	                    
	                    </center>
	                    
                    <% } %>
                </td>
        	</tr>
        </table>
    <br>
<% } %>

<table>
<tr><td align="center">
    <% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
                    <p class="exception"><%=modelException.getMessage()%></p>
    <% } %>

    <% if (prospectView.getElements().size() > 0) { %>
                    Records selected: <%=prospectView.getElements().size()%>
                    <table class="report" border="1" cellspacing="0" cellpadding="3">
                    <tr>

                    <th rowspan="2">Id</th>
                    <th rowspan="2">Edit/Print<br>Delete</th>
                    <th rowspan="2">Notes</th>
                    <th>Company<br>Name</th>
					<%if(userProfile.getLimitProspectSearchView() == null ||
                			Utilities.nullToZero(userProfile.getLimitProspectSearchView()).compareTo(UserDetails.DO_NOT_LIMIT_VIEW) == 0){ %>
                    
                    	<th>Address</th>
                    	<th>City</th>
                    	<th>Zip</th>
                    	<th>County</th>
                    	<th>Country and State</th>
                    	<th>Contact</th>
                    	
                    <%} %>

                    	<th>Lead Source</th>
                    	<th>Territory</th>
                    	<th><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></th>
                    	<th><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></th>
                    	<th nowrap>ID/Status</th>
                    	<th>Verified</th>
                    	<th>Size</th>
						
                    </tr>
                    <tr>
                    	<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCustomerCompany")%></th>
 						<%if(userProfile.getLimitProspectSearchView() == null ||
                			Utilities.nullToZero(userProfile.getLimitProspectSearchView()).compareTo(UserDetails.DO_NOT_LIMIT_VIEW) == 0){ %>               

                    		<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getAddress")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCity")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getZip")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCounty")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getCountryAndStateSortValue")%></th>
                   			<th><%=displayHeaderWithSort(sortByField, sortByDirection, "getContactName")%></th>
                    	
                    	<%}%>
                    
                    
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getLobName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getTerritoryName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getSystemTypeName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getSoftwareTypeName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getStatusName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getVerifiedName")%></th>
	                    <th><%=displayHeaderWithSort(sortByField, sortByDirection, "getSizeName")%></th>
                    </tr>
                    <% for (int i = 0; i < prospectView.getElements().size(); i++) {
                        ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i); %>
                        <tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
                        <th valign="top" nowrap align="center">
                                <input type="checkbox" name="chkId<%=i%>" value="<%=prospectDetails.getProspectId()%>">
                                <br>
                                <%=prospectDetails.getProspectId()%>
                        </th>
                        <th valign="top" nowrap align="center">
                            <table border="0" cellspacing="0" cellpadding="2"><tr>
                            <th valign="top"><a href="javascript:fOnUpdate(<%=prospectDetails.getProspectId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update Record"></a></th>
                            <th valign="top"><a href="javascript:fOnPrint(<%=prospectDetails.getProspectId()%>);"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/print.gif" alt="Print Record"></a></th>
                            </tr>
                            <tr>
                            <th valign="top"><a href="javascript:if(confirm('<%=Utilities.escapeProblemCharacters(prospectDetails.getCustomerCompany(), "")%> will be permanently deleted from the database, if there are no notes, documents, contacts etc defined.\n\nPlease confirm.')) {fOnDelete(<%=prospectDetails.getProspectId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Safe Delete Record"></a></th>
                            <th valign="top"><a href="javascript:if(confirm('<%=Utilities.escapeProblemCharacters(prospectDetails.getCustomerCompany(), "")%> will be permanently deleted from the database, including related data such as notes, additional contacts, websites etc.\n\nPlease confirm.')) {fOnDeleteCascade(<%=prospectDetails.getProspectId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete_cascade.gif" alt="Cascade Delete Record"></a></th>
                            </tr></table>
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
                        <%if(userProfile.getLimitProspectSearchView() == null ||
                			Utilities.nullToZero(userProfile.getLimitProspectSearchView()).compareTo(UserDetails.DO_NOT_LIMIT_VIEW) == 0){ %>  
                        
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
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getTerritoryName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getSystemTypeName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getSoftwareTypeName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getStatusName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getVerifiedName())%></td>
                        <td valign="top" align="left"><%=Utilities.nullToBlank(prospectDetails.getSizeName())%></td>
                        </tr>
                        <% } %>
                  </table>
                  Records selected: <%=prospectView.getElements().size()%>
                  <% } %>
              </td>
              </tr>
            </table>
		</form>
	</center>
</body>

 <% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
 <% e.printStackTrace(); } %>
    	
<form name="jasperReportForm" method="post" action="prospect_search_report" target="prospect_search_jasper_report">
	<input type=hidden name="reportURL" value="prospect_search_report">
	<input type=hidden name="reportLayout" value="JASPER">
	<input type=hidden name="formAction" value="DISPLAY">
</form>

<%=AppSettings.getFooter(userProfile)%>