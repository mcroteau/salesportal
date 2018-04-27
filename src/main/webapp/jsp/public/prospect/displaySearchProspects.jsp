<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.prospect.ProspectDetails,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.user.UserDetails,
                 com.randr.webdw.label.LabelView" %>
                 
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>

<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>

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

<jsp:useBean id="salesmanTerritoriesView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>
<jsp:useBean id="salesmanStatusesView" class="com.randr.webdw.status.StatusView" scope="request"/>
<jsp:useBean id="salesmanLobsView" class="com.randr.webdw.status.StatusView" scope="request"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Search Prospects", "","", "")%>
<% try { %>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospect/displayInsertUpdateProspect.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>



<script>
    <%=countryView.getCountriesArray()%>
    <%=stateView.getStateArrays()%>
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
</script>

<% String prospectsHeading = "Prospects";
	if(AppSettings.getParm("PROSPECTS").compareTo("")!=0 &&
        		AppSettings.getParm("PROSPECTS")!=null){
        	prospectsHeading = AppSettings.getParm("PROSPECTS");
        }
 %>
    <center><table><tr><td align="center">

        <h1>Search <%=prospectsHeading %></h1>
        <hr><br>
         <form name="frmProspect" method="post" action="prospect_search" onsubmit="javascript: return fProcessForm();">
         <input type=hidden name="formAction" value="DISPLAY">

<% if (request.getAttribute("modelException") != null) {
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>

        <table class="form">

        <tr><td colspan="2">&nbsp;</td></tr>
		        <tr>
        			<td colspan="2" align="center">
        <%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                        <a class="button" href="javascript: document.frmProspect.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/search.gif" alt="Search">Search&nbsp;&nbsp;</a>
                        <br/>
                      
                        <% if (((Boolean) request.getAttribute("isAdmin")).booleanValue()) { %>
                        &nbsp; &nbsp;
                        <a class="button" href="javascript: window.close();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">Cancel&nbsp;&nbsp;</a>
                        <% } %>

                    </td>
        		</tr>
                <tr><td colspan="2">&nbsp;</td></tr>  
                 <% if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>      
        <tr>
            <th nowrap align="right">Sales Company:</th>
            <td nowrap align="left">
                <select name="dfSearchCompanyId">
                <%=CollectionUtilities.getDropDownOptions(companyView.getElements(),
                                                          "getCompanyId", "getCompany",
                                                          userProfile.getCompanyId())
                %>
                </select>
            </td>
        </tr>
        <tr>
           
               <tr>
            <th nowrap align="right">Generic Text Search:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchGenericText" value="<%=Utilities.nullToBlank(prospectDetails.getGenericSearchText())%>" maxlength="100" size="20">
            </td>
            
        </tr>
        <tr>
            <th nowrap align="right">Generic Number Search:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchGenericNumeric" value="<%=Utilities.nullToBlank(prospectDetails.getGenericSearchNumeric())%>" 
                maxlength="100" size="20" onblur="this.value = fTrunc(this.value,0);">
            </td>
            
        </tr>
        
         <tr>
            <th nowrap align="right">Generic Date Search:</th>
            <td nowrap align="left">
                <input name="dfSearchGenericDate" value="" onchange="javascript:void(0);">
                 <% if(Utilities.nullToBlank(userProfile.getDateFormat()).equals("MM/dd/yyyy")) {%>  
                <a href="javascript:calGenericSearchDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            	<%} %>
            </td>
        </tr>
        <% } // end limited view %>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <th nowrap align="right">Contact Name:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchContactName" value="<%=Utilities.nullToBlank(prospectDetails.getContactName())%>" maxlength="100" size="30">
            </td>
            <% if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
           	 <td><input type="checkbox" name="dfSearchProspectContactName"> Also search contacts</td>
            <% }else{ %>
           	<td>&nbsp;</td>
            <% }//end limit view %>
        </tr>
         <% if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
        <tr>
            <th nowrap align="right">Customer Company:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchCustomerCompany" value="<%=Utilities.nullToBlank(prospectDetails.getCustomerCompany())%>" maxlength="100" size="30">
            </td>
        </tr>

        <tr>
            <th nowrap align="right">Country:</th>
            <td nowrap align="left">
                <select name="dfSearchCountryId"
                        onchange="javascript:fPopulateStates(this, document.frmProspect.dfSearchStateId)">
<%--                <% if (countryView.getElements().size() > 1){ %><option value=""><% } %>--%>
                <option value="">
                <%=CollectionUtilities.getDropDownOptions(countryView.getElements(),
                                                          "getCountryId", "getCountry",
                                                          prospectDetails.getCountryId())
                %>
                </select>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">State:</th>
            <td nowrap align="left">
                <select name="dfSearchStateId">
                </select>
             <script>
                fPopulateStates(document.frmProspect.dfSearchCountryId, document.frmProspect.dfSearchStateId);
                fSelectState(document.frmProspect.dfSearchStateId, <%=Utilities.nullToZero(prospectDetails.getStateId()).toString()%>);
             </script>

            </td>
        </tr>

        <tr>
            <th nowrap align="right">City:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchCity" value="<%=Utilities.nullToBlank(prospectDetails.getCity())%>" maxlength="30" size="15">
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Zip:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchZip" value="<%=Utilities.nullToBlank(prospectDetails.getZip())%>" maxlength="20" size="8">
            </td>
        </tr>
         <% }//end limit view %>
	 <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
		<tr>
            <th nowrap align="right">Email:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchEmail" value="<%=Utilities.nullToBlank(prospectDetails.getEmail())%>" maxlength="50" size="20">
            </td>
        </tr>
        
        <tr>
            <th nowrap align="right">Phone:</th>
            <td nowrap align="left">
                <input type="text" name="dfSearchPhone" value="<%=Utilities.nullToBlank(prospectDetails.getEmail())%>" maxlength="50" size="20">
            </td>
            <td><input type="checkbox" name="dfSearchProspectContactPhone"> Also search contacts</td>
        </tr>
        <tr>
            <th nowrap align="right">Prospect Change Date<br>(greater than or equal to):</th>
            <td nowrap align="left">
                <input name="dfSearchChangeDate" value="" onchange="javascript:void(0);">
                 <% if(Utilities.nullToBlank(userProfile.getDateFormat()).equals("MM/dd/yyyy")) {%>  
                <a href="javascript:calChangeDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            	<%} %>
            </td>
             <td><input type="checkbox" name="dfSearchExactChangeDate"> Exact match on Change Date</td>
        </tr>
		 <tr><td>&nbsp;</td></tr>
       
        <tr>
            <th nowrap align="right">Next Sales Action:</th>
            <td nowrap align="left">
                <select name="dfSearchSalesActionId">
                <% if (salesActionView.getElements().size() > 1){ %><option value=""><% } %>
                <%=CollectionUtilities.getDropDownOptions(salesActionView.getElements(),
                                                          "getActionId", "getAction")
                %>
                </select>
            </td>
        </tr>
        <%  } // end of limited view %>
         <tr><td>&nbsp;</td></tr>
        <tr>

           <th colspan="2"> <font color="blue">Click to select an option Cltr+click to de-select.</font></th>
        </tr>
         <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
        <tr>
            <th nowrap align="right">Next Sales Action<br>Start Date:</th>
            <td nowrap align="left">
                <input name="dfSearchSalesActionDate" value="" onchange="javascript:void(0);">
                 <% if(Utilities.nullToBlank(userProfile.getDateFormat()).equals("MM/dd/yyyy")) {%>  
                <a href="javascript:calSalesActionDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            	<%} %>&nbsp;&nbsp;&nbsp;End Date:
            </td>
            
            <td nowrap align="left">
                <input name="dfSearchSalesActionDateEnd" value="" onchange="javascript:void(0);">
                 <% if(Utilities.nullToBlank(userProfile.getDateFormat()).equals("MM/dd/yyyy")) {%>  
                <a href="javascript:calSalesActionDateEnd.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a>
            	<%} %>
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
     <%  } //end of limited view %>
        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%>:</th>
            <td nowrap align="left">
                <select name="dfSearchSystemTypeId">
<%--                <% if (systemTypeView.getElements().size() > 1){ %><option value=""><% } %>--%>
                <option value="">
                <%=CollectionUtilities.getDropDownOptions(systemTypeView.getElements(),
                                                          "getSystemTypeId", "getSystemType",
                                                          prospectDetails.getSystemTypeId())
                %>
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
                <select name="dfSearchLobId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          prospectDetails.getLobId())
                %>
                </select>
            </td>
           <% if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
             <% if(labelView.getLabelShort(LabelView.LOB).compareTo("LOB")==0){// doesnot exist in DB %>
             <td><input type="checkbox" name="dfSearchLobIsNull"> Contacts with No LOB</td>
             <% }else{ %>
             <td><input type="checkbox" name="dfSearchLobIsNull"> Contacts with No <%=labelView.getLabelShort(LabelView.LOB)%></td>
             <% } %>
           <%  } //end of limit view %>
        </tr>
        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%>:</th>
            <td nowrap align="left">
                <select name="dfSearchSoftwareTypeId">
<%--                <% if (softwareTypeView.getElements().size() > 1){ %><option value=""><% } %>--%>
                <option value="">
                <%=CollectionUtilities.getDropDownOptions(softwareTypeView.getElements(),
                                                          "getSoftwareTypeId", "getSoftwareType",
                                                          prospectDetails.getSoftwareTypeId())
                %>
                </select>
            </td>
        </tr>
  <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
       <tr>
            <th nowrap align="right">Territory:</th>
            <td nowrap align="left">
                <select name="dfSearchTerritoryId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          prospectDetails.getTerritoryId())
                %>
                </select>
            </td>
            <td><input type="checkbox" name="dfSearchTerritoryIsNull"> Contacts with No Territory</td>
        </tr>
        <tr>
            <th nowrap align="right">Status Code:</th>
            <td nowrap align="left">
                <select name="dfSearchStatusId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus",
                                                          prospectDetails.getStatusId())
                %>
                </select>
            </td>
            <td><input type="checkbox" name="dfSearchStatusIsNull"> Contacts with No Status Id</td>
        </tr>

        <tr>
         	<% if(labelView.getLabelShort(LabelView.VERIFIED).compareTo("Verified")==0){// doesnot exist in DB %>
                   <th nowrap align="right">Verified:</th>
            <% }else{ %>
                  <th nowrap align="right"><%=labelView.getLabelShort(LabelView.VERIFIED) %>:</th>
            <% } %>
            <td nowrap align="left">
                <select name="dfSearchVerifiedId">
<%--                <% if (verifiedView.getElements().size() > 1){ %><option value=""><% } %>--%>
                <option value="">
                <%=CollectionUtilities.getDropDownOptions(verifiedView.getElements(),
                                                          "getVerifiedId", "getVerified",
                                                          prospectDetails.getVerifiedId())
                %>
                </select>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Size:</th>
            <td nowrap align="left">
                <select name="dfSearchSizeId">
<%--                <% if (sizeView.getElements().size() > 1){ %><option value=""><% } %>--%>
                <option value="">
                <%=CollectionUtilities.getDropDownOptions(sizeView.getElements(),
                                                          "getSizeId", "getSize",
                                                          prospectDetails.getSizeId())
                %>
                </select>
            </td>
        </tr>
  		
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr>
            <th nowrap align="right">Sort By:</th>
            <td nowrap align="left">
                <select name="dfSortBy">
                <option value="getCustomerCompany">Company
                <option value="getContactName">Contact Name
                <option value="getAddress">Address
                <option value="getZip">Zip
                <option value="getCountryAndStateSortValue">State
                </select>
                <select name="dfSortDirection">
                <option value="<%=CollectionUtilities.SORT_UP%>">ASC
                <option value="<%=CollectionUtilities.SORT_DOWN%>">DESC
                </select>
            </td>
        </tr>
<%  } //end of limited view %>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: document.frmProspect.submit();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/search.gif" alt="Search">Search&nbsp;&nbsp;</a>
                <% if (((Boolean) request.getAttribute("isAdmin")).booleanValue()) { %>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">Cancel&nbsp;&nbsp;</a>
                <% } %>

            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>
        </table>
        <br/>
        <%if(userProfile.getUserType().compareTo(UserDetails.USER_TYPE_ADMIN) == 0){%>
			<center><input type="button" class="searchForDuplicates" onclick="javascript:fSearchForDuplicates();" value="Search For Prospect Duplicates"></a></center>
		<%}%>
        <script>
	        function fSearchForDuplicates(){
	        	document.frmDuplicates.action = "prospect_search";
	        	document.frmDuplicates.formAction.value = "DISPLAY_SEARCH_DUPLICATES";
	        	document.frmDuplicates.submit();
	        }
        </script>
        <p><font color="red"><b>Warning:</b> Depending on your computer capabilities (speed and memory),<br>
        a search returning too many results may stall or hang the browser.<br>
        If this occurs simply close the brower down and restart another.</font></p>
        </form>
         <%if(userProfile.getUserType().compareTo(new BigDecimal(3))!=0){ %>
        <script>
			var calSalesActionDate = new calendar2(document.forms['frmProspect'].elements['dfSearchSalesActionDate']);
			calSalesActionDate.year_scroll = true;
			calSalesActionDate.time_comp = false;
			
			var calChangeDate = new calendar2(document.forms['frmProspect'].elements['dfSearchChangeDate']);
			calChangeDate.year_scroll = true;
			calChangeDate.time_comp = false;

			var calSalesActionDateEnd = new calendar2(document.forms['frmProspect'].elements['dfSearchSalesActionDateEnd']);
			calSalesActionDate.year_scroll = true;
			calSalesActionDate.time_comp = false;
			
			var calGenericSearchDate = new calendar2(document.forms['frmProspect'].elements['dfSearchGenericDate']);
			calSalesActionDate.year_scroll = true;
			calSalesActionDate.time_comp = false;
		
        </script>
        <% } //end limited view %>

<%
        }
%>
    </td></tr></table>
    <form name="frmDuplicates" method="post" action="admin/duplicates">
     <input type=hidden name="formAction" value="DISPLAY_SEARCH">
     </form>    
    </center>

<%
    }
    catch (Exception e)
    {
%>
    <b>Error:</b><%=e.getMessage()%>
<%
    }
%>
<%=AppSettings.getFooter(userProfile)%>