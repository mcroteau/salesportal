<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.prospect.ProspectDetails,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.util.CollectionUtilities,
                 java.math.BigDecimal,
                 com.randr.webdw.mvcAbstract.exception.ModelException,
                 com.randr.webdw.util.DateUtilities,
                 com.randr.webdw.note.NoteDetails,
                 java.util.Enumeration,
                 com.randr.webdw.website.WebsiteDetails,
                 com.randr.webdw.prospectDocument.ProspectDocumentDetails,
                 com.randr.webdw.contact.ContactDetails,
                 com.randr.webdw.label.LabelView,
                 java.text.SimpleDateFormat,
                 com.randr.webdw.prospectSalesAction.ProspectSalesActionView"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java"%>
<jsp:useBean id="prospectView" class="com.randr.webdw.prospect.ProspectView" scope="request"/>
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
<jsp:useBean id="prospectSalesActionDetails" class="com.randr.webdw.prospectSalesAction.ProspectSalesActionDetails" scope="request"/>

<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Update Prospects Collection", "","", "")%>
<%try{%>

<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/util/calendar2.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>
<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/prospect/displayProspectCollection.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
</script>

<script>
    <%=countryView.getCountriesArray()%>
    <%=stateView.getStateArrays()%>


    <%=salesActionView.getDateMandatoryArray()%>
</script>
<form name="frmProspect" method="post" action="prospect">
<input type=hidden name="formAction" value="UPDATE_COLLECTION">

	<% if (request.getAttribute("modelException") != null){
            ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><hr><%=modelException.getMessage()%></p>
	<% } else { %>
	<br />
<table border="0" width="80%">
<center>

	<tr>
    		<td nowrap valign="top" align="left" width="20%" class="collectionList">
    			<br />
    			<h2>Collection size: <%=prospectView.getElements().size()%></h2>
    			<hr />
    			<% for (int i = 0; i < prospectView.getElements().size(); i++){ %>
        		<% ProspectDetails prospectDetails = (ProspectDetails) prospectView.getElements().elementAt(i); %>
        		<%=prospectDetails.getCustomerCompany()%><br>
    			<% } %>
    			<br />
    			<hr />
    		</td>
    		<td align="center" valign="top" align="left" width="40%">
        		<h1>Update Collection</h1>

        		<table class="form" cellspacing="0" cellpadding="0" border="0">
        		<tr><td colspan="2">&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center">
                		<a class="button" href="javascript: updateCollection();">&nbsp;&nbsp;
                    	<img class="button"class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">
                    	&nbsp;&nbsp;Update All&nbsp;&nbsp;</a>
            		&nbsp;&nbsp;
            		<a href="javascript:history.go(-1);" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>

				<br><br>Leave blank the fields you do not want to change
            		</td>
			</tr>
        		<tr><td colspan="2">&nbsp;</td></tr>
        		<tr>
            <th nowrap align="right">Sales Company:</th>
            <td nowrap align="left">
                <select name="dfCompanyId">
                <option value="">-</option>
                <%=CollectionUtilities.getDropDownOptions(companyView.getElements(),
                                                          "getCompanyId", "getCompany")%>
                
                </select>
            </td>
        </tr>
        		<tr>
            		<th nowrap align="right">City:</th>
            		<td nowrap align="left">
                			<input type="text" name="dfCity" value="" maxlength="30" size="40">
            		</td>
       		</tr>
       	 	<tr>
            		<th nowrap align="right">County:</th>
            		<td nowrap align="left">
                			<input type="text" name="dfCounty" value="" maxlength="30" size="40">
            		</td>
        		</tr>
                  <tr>
                      	<th nowrap align="right">Zip:</th>
                      	<td nowrap align="left">
                          	<input type="text" name="dfZip" value="" maxlength="30" size="40">
                      	</td>
                  </tr>

        		<tr>
            		<th nowrap align="right">Country:</th>
            		<td nowrap align="left">
                			<select name="dfCountryId"
                        	onchange="javascript:fPopulateStates(this, document.frmProspect.dfStateId)">
                			<option value="">-</option>
                			<%=CollectionUtilities.getDropDownOptions(countryView.getElements(),
                                                          "getCountryId", "getCountry")%>
                			</select>
            		</td>
        		</tr>
        		<tr>
            		<th nowrap align="right">State:</th>
            		<td nowrap align="left">
					<select name="dfStateId"></select>
                              <script>
                                 fPopulateStates(document.frmProspect.dfCountryId, document.frmProspect.dfStateId);
                              </script>
				</td>
        		</tr>
                  <tr>
                      	<td colspan="2">&nbsp;</td>
                  </tr>
                  <tr>
                      <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_1)%>:</th>
                      <td nowrap align="left"><input type="text" name="dfSsa" value="" maxlength="100" size="15">
                          <b><%=labelView.getLabel(LabelView.USER_2)%>:</b> <input type="text" name="dfSic" value="" maxlength="100" size="15">
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_3)%>:</th>
                      <td nowrap align="left">
                          <input type="text" name="dfSystemNo" value="" maxlength="100" size="40">
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_4)%>:</th>
                      <td nowrap align="left">
                          <input type="text" name="dfHardmnt" value="" maxlength="100" size="40">
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%>:</th>
                      <td nowrap align="left">
                          <select name="dfSystemTypeId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(systemTypeView.getElements(),
                                                                    "getSystemTypeId", "getSystemType") %>
                          </select>
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right">Line Of Business:</th>
                      <td nowrap align="left">
                          <select name="dfLobId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(lobView.getElements(),
                                                                    "getLobId", "getLob") %>
                          </select>
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right"><%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%>:</th>
                      <td nowrap align="left">
                          <select name="dfSoftwareTypeId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(softwareTypeView.getElements(),
                                                                    "getSoftwareTypeId", "getSoftwareType") %>
                          </select>
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right">Territory:</th>
                      <td nowrap align="left">
                          <select name="dfTerritoryId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(territoryView.getElements(),
                                                                    "getTerritoryId", "getTerritory") %>
                          </select>
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right">ID / Status:</th>
                      <td nowrap align="left">
                          <select name="dfStatusId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(statusView.getElements(),
                                                                    "getStatusId", "getStatus") %>
                          </select>
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right">Verified:</th>
                      <td nowrap align="left">
                          <select name="dfVerifiedId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(verifiedView.getElements(),
                                                                    "getVerifiedId", "getVerified") %>
                          </select>
                      </td>
                  </tr>
                  <tr>
                      <th nowrap align="right">Size:</th>
                      <td nowrap align="left">
                          <select name="dfSizeId">
                               <option value="">-
                          <%=CollectionUtilities.getDropDownOptions(sizeView.getElements(),
                                                                    "getSizeId", "getSize") %>
                          </select>
                      </td>
                  </tr>
                  <tr><td colspan="2">&nbsp;</td></tr>
            	<tr>
            		<td colspan="2" align="center">
                            <a class="button" href="javascript: updateCollection();">&nbsp;&nbsp;
                                <img class="button" class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_accept.gif" alt="Accept">
                                &nbsp;&nbsp;Update All&nbsp;&nbsp;</a>&nbsp;&nbsp;
                       		  <a href="javascript:history.go(-1);" class="button">&nbsp; &nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/button_cancel.gif" alt="Cancel">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
                        </td>
            	</tr>
        		<tr><td colspan="2">&nbsp;</td></tr>
	        </table>

    	</td>
	<td nowrap valign="top" align="center" width="40%">
	<br />
		<h2>Add Options</h2>
		<hr />
		<div id="removeSalesActionDiv" style={display:none}>	
		 	<a href="javascript:removeSalesActionInfo();"><h3>Remove Sales Action Info</h3></a>
		</div>
		<div id="insertSalesActionDiv">
			<h3><a href="javascript:fDisplayHidden()">Add Sale Action</a></h3>
		</div>
	<a href="javascript:fOnAddCampaign();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/addContacts.gif" alt="Campaign"> Add Campaign To Collection</a>
	<br><br><a href="javascript:if(confirm('Prospects will be inserted back into the Round Robin.\n\nPlease confirm.')){fOnInsertIntoRoundRobin();}"> Add Prospects in Collection back into Round Robin</a>
		<div id="hiddenDiv" style={display:none}">
		<center>
 		
		<table>
			<tr>
				<td align="center">
			        <table class="form">
                  	      <tr>
                  	          	<th nowrap align="right">Priority:</th>
                  	          	<td nowrap align="left">
                  	            	<select name="cmbPriority" >
                  	       	    	<%=ProspectSalesActionView.getActionPriorityDropDown(prospectSalesActionDetails) %>
                  	              </select>
                  	          	</td>
                  	      </tr>       
	        			<tr>
						<th nowrap align="right">Sales Action:</th>
	            			<td nowrap align="left">
	            				<select name="cmbSalesAction" onchange="javascript:fSelectSalesAction()">
	            				<option value="">-</option>
	                				<%=CollectionUtilities.getDropDownOptions(salesActionView.getElements(),
	                				"getActionId", "getAction", prospectSalesActionDetails.getSalesActionId()) %>
	                				</select>
					&nbsp;<font color="blue" size="+1">**</font>&nbsp;<b>Date:</b><input type="text" name="dfSalesActionDate" size="12" value="<%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>" onchange="javascript:void(0);">
	                <input type="hidden" name="dfOldSalesActionDate" value="<%=DateUtilities.formatDate(prospectSalesActionDetails.getActionDate(), "MM/dd/yyyy")%>">
	                <a href="javascript:calSalesActionDate.popup();"><img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/calendar.gif" border="0" alt="Click Here to Pick up the date"></a><font color="blue" size="+1">**</font>
	            			</td> 
	        			</tr>
        				<tr >  
           					<th nowrap align="right">Time:</div></th>
            				<td nowrap align="left">
         						<input size="2" name="dfHour" value="">
                					&nbsp;<BIG><b>:</b></BIG>&nbsp;
         						<input size="2" name="dfMinute" value="">

							<input type="radio" name="time" value="AM" onclick="selectTime();"  checked>
            					&nbsp;&nbsp;AM&nbsp;&nbsp;
            					<input type="radio" name="time" value="PM" onclick="selectTime();">
                					&nbsp;&nbsp;PM		 		
						</td>
					</tr>
					<tr>
            				<th nowrap align="right" valign="top">Action Note:</th>
            				<td nowrap align="left">
                <textarea name="dfActionNote"
                    onkeypress="textCounter(this,this.form.counter,200);"
                    cols="50" rows="4"><%=Utilities.nullToBlank(prospectSalesActionDetails.getActionNote())%></textarea>
                <br><input id="flat"
	            type="text" name="counter" maxlength="3" size="3" value="<%=new BigDecimal(200).subtract(new BigDecimal(Utilities.nullToBlank(prospectSalesActionDetails.getActionNote()).length()))%>"
                onfocus = "this.blur();"> characters remaining
            				</td>
		<script>
			var calSalesActionDate = new calendar2(document.forms['frmProspect'].elements['dfSalesActionDate']);
			calSalesActionDate.year_scroll = true;
			calSalesActionDate.time_comp = false;
		</script>
					</tr>
        				<tr><td colspan="2">&nbsp;</td></tr>
        				<tr>
						<td colspan="2">
							<div id="hideSales" style={display:none}>
                                    		<a href="javascript:fHideSalesAction(arrMandatoryDateFlags)" title="Hide Sales Actions">Hide</a>	
                             			</div>
        				    	</td>
        				</tr>
        			</table>
			</td>
		</tr>
	</table>
	</td>
</tr>
</table>
<%}%>
    <script>fSelectSalesAction();</script>
    
<%}catch (Exception e){%>
    <b>Error:</b><%=e.getMessage()%>
<%}%>
<%=AppSettings.getFooter(userProfile)%>