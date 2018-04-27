<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.user.UserDetails,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.company.CompanyDetails,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.label.LabelView"%>
                 
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="userDetails" class="com.randr.webdw.user.UserDetails" scope="request"/>
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

<jsp:useBean id="isAdmin" type="java.lang.Boolean" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

<jsp:useBean id="companyDetails" class="com.randr.webdw.company.CompanyDetails" scope="request"/>
<jsp:useBean id="dateFormatView" class="com.randr.webdw.international.dateFormat.DateFormatView" scope="request"/>
<jsp:useBean id="timeOffsetView" class="com.randr.webdw.international.timeOffset.TimeOffsetView" scope="request"/>

<jsp:useBean id="userStatusesView" class="com.randr.webdw.userStatuses.UserStatusesView" scope="request"/>
<jsp:useBean id="userTerritoriesView" class="com.randr.webdw.userTerritories.UserTerritoriesView" scope="request"/>
<jsp:useBean id="userLobsView" class="com.randr.webdw.userLobs.UserLobsView" scope="request"/>

<%
    try
    {
        String actionDescription = "Update";
        String newFormAction="UPDATE";

        if (formAction.equals("DISPLAY_INSERT"))
        {
            actionDescription = "Insert";
            newFormAction="INSERT";
        }
%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/user/displayInsertUpdateUser.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
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

    <center><table><tr><td align="center">

        <h1><%=actionDescription%>  <%=UserDetails.getUserTypeDescription(UserDetails.USER_TYPE_SALESMAN, false)%></h1>
        <hr><br>
         <form name="frmUser" method="post" action="user" onsubmit="javascript: return fProcessForm();">
         <input type=hidden name="formAction" value="<%=newFormAction%>">
         <input type=hidden name="adminUpdate" value="<%=isAdmin.booleanValue()%>">


<%
        if (request.getAttribute("modelException") != null)
        {
            ModelException modelException = (ModelException) request.getAttribute("modelException");
%>
		<p class="exception"><%=modelException.getMessage()%></p>
<%
        }
        else
        {
%>
        <table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
    <%
            if (formAction.startsWith("DISPLAY_UPDATE"))
            {
    %>
        <tr>
            <th nowrap align="right">User Id:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfUserId" value="<%=Utilities.nullToBlank(userDetails.getUserId())%>">
                <%=Utilities.nullToBlank(userDetails.getUserId())%>
            </td>
        </tr>

    <%
            }
    %>
    <%

    %>

        <tr>
            <th nowrap align="right">User Name*:</th>
            <td nowrap align="left">
                <input type="hidden" name="dfOldUserName" value="<%=Utilities.nullToBlank(userDetails.getUserName())%>">
                <input type="text" name="dfUserName" value="<%=Utilities.nullToBlank(userDetails.getUserName())%>" maxlength="50" size="30">
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Password*:</th>
            <td nowrap align="left">
                <input type="password" name="dfPassword" value="" maxlength="50" size="30">
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Confirm Password*:</th>
            <td nowrap align="left">
                <input type="password" name="dfConfirmPassword" value="" maxlength="50" size="30">
            </td>
        </tr>

        <%

            if (formAction.startsWith("DISPLAY_UPDATE"))
            {
        %>
            <tr>
                <td colspan="2" align="right"><p class="note">Leave password blank if you do not want to change it</p></td>
            </tr>
        <%
            }

        %>
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr>
            <th nowrap align="right">First Name*:</th>
            <td nowrap align="left">
                <input type="text" name="dfFirstName" value="<%=Utilities.nullToBlank(userDetails.getFirstName())%>" maxlength="100" size="30">
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Last Name*:</th>
            <td nowrap align="left">
                <input type="text" name="dfLastName" value="<%=Utilities.nullToBlank(userDetails.getLastName())%>" maxlength="100" size="30">
            </td>
        </tr>

        <tr>
            <th nowrap align="right">E-mail:</th>
            <td nowrap align="left">
                <input type="text" name="dfEmail" value="<%=Utilities.nullToBlank(userDetails.getEmail())%>" maxlength="50" size="30">
            </td>
        </tr>
        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>

<%
            if (isAdmin.booleanValue()){
            
%>
        <tr>
            <th nowrap align="right">Sales Company*:</th>
            <td nowrap align="left">
                <select name="dfCompanyId" class="salesmen">
                <option value="">Please select...
                <%=CollectionUtilities.getDropDownOptions(companyView.getElements(),
                                                          "getCompanyId", "getCompany",
                                                          userDetails.getCompanyId())
                %>
                </select>
            </td>
        </tr>
         <%String limitCompanyCheck = "";
          if(userDetails.getLimitCompanyView()!= null){ 
	      	if(userDetails.getLimitCompanyView().compareTo(UserDetails.LIMIT_COMPANY)==0){
	      		limitCompanyCheck = "checked";
	     	}
          }%>
        <tr><th colspan="2">Limit Company View<input type="checkbox" <%=limitCompanyCheck%> name="ckLimitCompanyView" onclick="javascript:fCompanyLimitInitiated();">
		<tr><td><br/></td></tr>

        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>

        <tr>
            <th nowrap align="right">Country:</th>
            <td nowrap align="left">
                <select name="dfCountryId" class="salesmen"
                        onchange="javascript:fPopulateStates(this, document.frmUser.dfStateId)">
                <option value="">All
                <%=CollectionUtilities.getDropDownOptions(countryView.getElements(),
                                                          "getCountryId", "getCountry",
                                                          userDetails.getCountryId())
                %>
                </select>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">State:</th>
            <td nowrap align="left">
                <select name="dfStateId" class="salesmen">
                </select>
             <script>
                fPopulateStates(document.frmUser.dfCountryId, document.frmUser.dfStateId);
                fSelectState(document.frmUser.dfStateId, <%=Utilities.nullToZero(userDetails.getStateId()).toString()%>);
             </script>

            </td>
        </tr>


        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%>:</th>
            <td nowrap align="left">
                <select name="dfSystemTypeId" class="salesmen">
                     <option value="">All
                <%=CollectionUtilities.getDropDownOptions(systemTypeView.getElements(),
                                                          "getSystemTypeId", "getSystemType",
                                                          userDetails.getSystemTypeId())
                %>
                </select>
            </td>
        </tr>

        <tr>
            <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%>:</th>
            <td nowrap align="left">
                <select name="dfSoftwareTypeId" class="salesmen" >
                     <option value="">All
                <%=CollectionUtilities.getDropDownOptions(softwareTypeView.getElements(),
                                                          "getSoftwareTypeId", "getSoftwareType",
                                                          userDetails.getSoftwareTypeId())
                %>
                </select>
            </td>
        </tr>
        
        <%String limitCheck = "";
          if(userDetails.getLimitProspectSearchView()!= null){ 
	      	if(userDetails.getLimitProspectSearchView().compareTo(UserDetails.LIMIT_VIEW)==0){
	      		limitCheck = "checked";
	     	}
          }%>
        <tr><th colspan="2">Limit Prospect Search View<input type="checkbox" <%=limitCheck%> name="ckLimitProspectSearchView" onclick="javascript:fCheckLimitInitiated();">
		<tr><td><br/></td></tr>
		
		<%	String lobCheck = "";
			if(userDetails.getSpecificLobs() != null && 
				userDetails.getSpecificLobs().compareTo(UserDetails.SPECIFIC_LOBS)== 0){
				
				lobCheck = "checked";
			}
		%>
		<tr>
            <th nowrap align="center" colspan="2">Assign Specific Lead Sources&nbsp;:&nbsp;
            	<input type="checkbox" name="ckSpecificLobs" <%=lobCheck%> onclick="javascript:fCheckToDisableLobs()"/></th>
        </tr>  
    <%if (formAction.startsWith("DISPLAY_UPDATE")){ %> 
		<tr>
            <td nowrap align="center" colspan="2">
                <select name="dfLobId" class="salesmen" multiple  size="5">
                <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          userLobsView.getUserLobsIdsVector())
                %>
                </select>
            </td>
        </tr>
	<%}else{%>    
        <tr>
            <td nowrap align="center" colspan="2">
                <select name="dfLobId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                          "getLobId", "getLob",
                                                          userLobsView.getElements())
                %>
                </select>
            </td>
        </tr>
      <%} %>
          
		<tr><td><br/></td></tr>
		<%	String territoryCheck = "";
			if(userDetails.getSpecificTerritories() != null && 
				userDetails.getSpecificTerritories().compareTo(UserDetails.SPECIFIC_TERRITORIES)== 0){
				
				territoryCheck = "checked";
			}%>
         <tr>
            <th nowrap align="center" colspan="2">Assign Specific Territories&nbsp;:&nbsp;
            	<input type="checkbox" name="ckSpecificTerritories" <%=territoryCheck%> onclick="javascript:fCheckToDisableTerritories()"/></th>
        </tr>       		


    <%if (formAction.startsWith("DISPLAY_UPDATE")){ %>

        <tr>
            <!-- <th nowrap align="right">Territory:</th> -->
            <td nowrap align="center" colspan="2">
                <select name="dfTerritoryId" class="salesmen" multiple  size="5">
                <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          userTerritoriesView.getUserTerritoriesIdsVector())
                %>
                </select>
            </td>
        </tr>
        
	<%}else{%>
	 
        <tr>
            <!-- <th nowrap align="right">Territory:</th> -->
            <td nowrap align="center" colspan="2">
                <select name="dfTerritoryId" class="salesmen" multiple size="5">
                <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          userTerritoriesView.getElements())
                %>
                </select>
            </td>
        </tr>
      <%} %>
		<%	String statusCheck = "";
			if(userDetails.getSpecificStatuses() != null && 
				userDetails.getSpecificStatuses().compareTo(UserDetails.SPECIFIC_STATUSES)== 0){
				
				statusCheck = "checked";
			}%>  
      	<tr>
            <th nowrap align="center" colspan="2">Assign Specific Statuses&nbsp;:&nbsp;
            	<input type="checkbox" name="ckSpecificStatuses" <%=statusCheck%> onclick="javascript:fCheckToDisableStatuses()"/></th>
        </tr>
      
    <%if (formAction.startsWith("DISPLAY_UPDATE")){%>

        <tr>
            <!-- <th nowrap align="right">ID / Status:</th> -->
            <td nowrap align="center"  colspan="2">
                <select name="dfStatusId" class="salesmen" multiple  size="5">
                <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus",
                                                          userStatusesView.getUserStatusesIdsVector())
                %>
                </select>
            </td>
        </tr>
	<%}else{%>

        <tr>
            <!-- <th nowrap align="right">ID / Status:</th> -->
            <td nowrap align="center" colspan="2">
                <select name="dfStatusId"  class="salesmen" multiple  size="4">
                <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                          "getStatusId", "getStatus",
                                                          userStatusesView.getElements())
                %>
                </select>
            </td>
        </tr>

	<%} %>
	
	<tr><td><br/></td></tr>
        <tr>
            <th nowrap align="right">Verified:</th>
            <td nowrap align="left">
                <select name="dfVerifiedId" class="salesmen">
                     <option value="">All
                <%=CollectionUtilities.getDropDownOptions(verifiedView.getElements(),
                                                          "getVerifiedId", "getVerified",
                                                          userDetails.getVerifiedId())
                %>
                </select>
            </td>
        </tr>
        <tr>
            <th nowrap align="right">Size:</th>
            <td nowrap align="left">
                <select name="dfSizeId" class="salesmen">
                     <option value="">All
                <%=CollectionUtilities.getDropDownOptions(sizeView.getElements(),
                                                          "getSizeId", "getSize",
                                                          userDetails.getSizeId())
                %>
                </select>
            </td>
        </tr>
        
        <% 	String checkboxSelection = "";
        	if(userDetails.getAllowCalendarViewOfAllTerritories() != null && 
        		userDetails.getAllowCalendarViewOfAllTerritories().compareTo(new BigDecimal(1)) == 0) {
        		checkboxSelection = "checked";
        	} %>
        		
<!--         <tr>
            <th nowrap align="right">Allow All Territories<br>in Calendar:</th>
            <td nowrap align="left"><input type="checkbox" name="ckAllowAllTerritoriesCalendar" <%=checkboxSelection%>/></td>
        </tr> -->
        <tr>
        	<th nowrap align="right">Date Format:
        	<td nowrap align="left">
                <select name="cmbDateFormat" class="salesmen">
                     <option value="">Please select ...</option>
                <%=CollectionUtilities.getDropDownOptions(dateFormatView.getElements(),
                                                          "getDateFormatId", "getDateFormat",
                                                          userDetails.getDateFormatId()) %>
                </select>
        </tr>
        <tr>
        	<th nowrap align="right">Time Offset:
			<td nowrap align="left">
				<select name="cmbTimeOffset" class="salesmen">
					<option value="">Please select ...</option>				
				 <%=CollectionUtilities.getDropDownOptions(timeOffsetView.getElements(),
                                                          "getTimeOffsetId", "getTimeOffset",
                                                          userDetails.getTimeOffsetId()) %>
                </select>
       	</tr>
    <% if (request.getParameter("formAction").startsWith("DISPLAY_UPDATE")) { %>
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr>
            <th nowrap align="right">Created On:</th>
            <td><%=DateUtilities.formatDate(userDetails.getCreationDate(),userProfile.getDateFormat() + "hh:mm a")%></td>
        </tr>
           <%if(userDetails.getChangeDate()!=null) {%>
        <tr>
            <th nowrap align="right">Last Changed On:</th>
            <td><%=DateUtilities.formatDate(userDetails.getChangeDate(),userProfile.getDateFormat() + "hh:mm a")%></td>
        </tr>
          <% } %>
    <% } %>
<%
            }
            else
            {
%>
            <tr><td colspan="2">&nbsp;
                <input type="hidden" name="dfCompanyId" value="<%=userDetails.getCompanyId()%>">
            </td></tr>
<%
            }

%>
        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
<%--                <input type="submit" value="<%=actionDescription%>" onclick="javascript: return fProcessForm();">--%>
                <a class="button" href="javascript: if(fProcessForm(1)){document.frmUser.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
    <%
            if (((Boolean) request.getAttribute("isAdmin")).booleanValue())
            {
    %>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
    <%
            }
    %>

            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>

        </table>
        <script>

    		
    		fCheckLimitInitiated();
    		fCompanyLimitInitiated();
    		fCheckToDisableStatuses();
   			fCheckToDisableTerritories();
    		fCheckToDisableLobs();
        </script>

        </form>
<%
        }
%>
    </td></tr></table></center>
<%
    }
    catch (Exception e)
    {
%>
    <b>Error:</b><%=e.getMessage()%>
<%
    }
%>
