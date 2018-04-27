<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.user.UserProfile,
				com.randr.webdw.territory.TerritoryDetails,
				com.randr.webdw.territoryZip.TerritoryZipView,
				com.randr.webdw.territoryZip.TerritoryZipDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.CollectionUtilities" %>
                
<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
                
<jsp:useBean id="territoryZipView" class="com.randr.webdw.territoryZip.TerritoryZipView" scope="request"/>
<jsp:useBean id="territoryDetails" class="com.randr.webdw.territory.TerritoryDetails" scope="request"/>
<jsp:useBean id="message" class="java.lang.String" scope="request"/>
	 	
<% try{ %>
    <html>
    <head>
    	<title>Display Territory Zip</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
	
	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
	 	
	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/territoryZip/territoryZip.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
		 	
    </head>
    <body>
	<center>
	
	<form name="frmTerritoryZip" method="post" action="territory_zip">

	    <input type="hidden" name="formAction" value="">
		<input type="hidden" name="dfTerritoryZipId" value="">
		<input type="hidden" name="dfTerritoryId" value="<%=territoryDetails.getTerritoryId()%>"/>


	<h1><font color="red"><%=message%></font></h1>
	<table>
	<tr><td align="center">

    <p align="center">
	    <h2><%=territoryDetails.getTerritory()%></h2><hr><br>
    </p>

	<%if (request.getAttribute("modelException") != null){
		
		ModelException modelException = (ModelException) request.getAttribute("modelException");%>
		
		<p class="exception"><%=modelException.getMessage()%></p>
		
	<%}else if(territoryZipView.getElements().isEmpty()){%>
	
		No Zips have been associated with this territory.
	
	<% } else { %>

	<table class="report" border="1" cellspacing="0" cellpadding="3">
	
    	<tr>
    		<th>Delete</th>
			<th>Id</th>
			<th>Zip</th>
			
    	</tr>
    	
<%	for (int i = 0; i < territoryZipView.getElements().size(); i++){
		
			TerritoryZipDetails territoryZipDetails = (TerritoryZipDetails) territoryZipView.getElements().elementAt(i);%>

    	<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
    		<td nowrap align="center"><a href="javascript:if(confirm('TerritoryZip <%=territoryZipDetails.getTerritoryZipId()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=territoryZipDetails.getTerritoryZipId()%>);}"><img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
    		<td nowrap align="left" width=30><center><%=Utilities.nullToBlank(territoryZipDetails.getTerritoryZipId())%></td>
    		<td nowrap align="center" width=100><center><%=Utilities.nullToBlank(territoryZipDetails.getZip())%></td>
		</tr>
		
	<%}%>
		
	</table>

	<%}%>

	</td></tr></table>
	 
	

	<table>
		<hr>
			<tr>
				<tr align="center">
					<td><h2>Enter Zip</h2></td>
				</tr>
				<tr>
					<td><input type="text" name="dfZip" value=""></td>
				</tr>
    			<tr colspan="2" align="center">
            	    <td><a class="button" href="javascript: fOnAddZip();">&nbsp;&nbsp;<img class="button" src="<%=AppSettings.getAppWebPath()%>graphics/icons/search.gif" alt="Add">Add&nbsp;&nbsp;</a></td>
            	</tr>
			</tr>
	</table>
	</form>
	</center>
    </body>
    </html>

<%}catch (Exception e){%>

     Error: <b><%=e.getMessage()%></b>
     
	<%e.printStackTrace();
	
	}finally {

}%>
 