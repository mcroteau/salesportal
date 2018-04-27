<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings, 
		com.randr.webdw.util.*,
		com.randr.webdw.util.CollectionUtilities" %>
		
<jsp:useBean id="prospectDetails" class="com.randr.webdw.prospect.ProspectDetails" scope="request"/>

<jsp:useBean id="countryView" class="com.randr.webdw.country.CountryView" scope="request"/>
<jsp:useBean id="stateView" class="com.randr.webdw.state.StateView" scope="request"/>
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
<head>
    <title>Sales Portal Mobile- Search Prospects</title>
    <link rel="stylesheet" type="text/css" href="<%= AppSettings.getAppWebPath()%>css/mobility.css">
</head>
<body>
<form name="frmSearchProspects" method="POST" action="mobility">
<input type="hidden" name="formAction" value="SEARCH"/> 

<div id="allContent">

	<table class="report" border="1" cellspacing="0" cellpadding="3" width="100%" border="0">
    	<tr id="heading"><th colspan="5"><h2>Search Prospects</h2></th></tr>
		

		<tr>
			<td id="label">Company Name:</td>
			<td id="content"><input name="dfSearchCustomerCompany" id="content" value="" size="20" maxlength="20"></td>
		</tr>
		<tr>
			<td id="label">Contact Name:</td>
			<td id="content"><input name="dfSearchContactName" id="content" value="" size="20" maxlength="20"></td>
		</tr>
		<tr>
			<td id="label">Country:</td>
	        <td id="content">
	            <select name="dfSearchCountryId" id="content">
	            	<option value="">Select Country....</option>
	            	<option><%=CollectionUtilities.getDropDownOptions(countryView.getElements(),
	                                                      "getCountryId", "getCountry",
	                                                      prospectDetails.getCountryId())%>
	                </option>                             
	            </select>
	       </td>
		</tr>
		<tr>
			<td id="label">State:</td>
	        <td id="content">
	            <select name="dfSearchStateId" id="content">
	             	<option value="">Select State....</option>
	            	<option><%=CollectionUtilities.getDropDownOptions(stateView.getElements(),
	                                                          "getStateId", "getState",
	                                                          prospectDetails.getStateId())%>
	                </option>
	            </select>
	        </td>
		</tr>
		<tr>
			<td id="label" padding="5px">City:</td>
			<td id="content"><input name="dfSearchCity" id="content" value="" size="20" maxlength="20"></td>
		</tr>
	
		<tr>
			<td id="label">Zip:</td>
			<td id="content"><input name="dfSearchZip" id="content" value="" size="20" maxlength="20"></td>
		</tr>
	</table>

	<div id="buttonGroup">
			<p><input type="submit" value="Search" id="customButton"/>&nbsp;&nbsp;
			<a href="<%=AppSettings.getAppWebPath()+ "mobility?formAction=LOGOUT"%>"><input type="button" value="Logout" id="customButton"/></a></p>
	</div>	
	<div id="userProfile">
		<%=userProfile.getUserName() %> is logged in...
	</div>
</div>
</form>
</body>
</html>
