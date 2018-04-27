<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.territory.TerritoryDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="territoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>


<% try{ %>

     <html>
     <head>
     	<title>Display Territories</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/territory/displayTerritories.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>
	 	
     </head>

     <body>
	 <center>

	 <h1>Display Territories</h1>
	 
	 <form name="frmTerritory" method="post" action="territory">

	     <input type="hidden" name="formAction" value="">
		 <input type="hidden" name="dfTerritoryId" value="">

	 </form>

	 <table>
	 <tr><td align="center">

     <p align="right">
	    <a class="button" href="javascript:fOnInsert();" title="Add/Create New Territory">&nbsp; &nbsp;Add New Territory&nbsp; &nbsp;</a><hr><br>
     </p>
     


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
		<table class="report" border="1" cellspacing="0" cellpadding="3">
		<tr>
			<th>Id</th>
			<th>Territory Name</th>
            <th>Owner<br>User Name</th>
            <th>Sales Manager<br>User Name</th>
            <th>Service Manager<br>User Name</th>
            <th>Return <br>Territory</th>
            <th>Return Time <br>(in days)</th>
            <th>Random <br> Sequence</th>
            <th>Round <br> Robin</th>
            <th>Max Prospects<br>Displayed</th>
			<th>Update</th>
			<th>Delete</th>
			<Th>Display Add<br>Zip Codes</Th>
		</tr>
<%
		for (int i = 0; i < territoryView.getElements().size(); i++)
	  	{
			TerritoryDetails territoryDetails = (TerritoryDetails) territoryView.getElements().elementAt(i);
			String random = "";
			if(territoryDetails.getRandomSequence()!= null &&
				territoryDetails.getRandomSequence().compareTo(TerritoryDetails.RANDOM) == 0){
				random = "Yes";
			}else{
				random = "No";
			}
%>
			<tr <% if (i%2==0) {%> class="roweven" <%} else { %> class="rowodd"<%}%>>
				<th nowrap align="right"><%=territoryDetails.getTerritoryId()%></th>
				<td nowrap align="left"><%=territoryDetails.getTerritory()%></td>

                <td nowrap align="left"><%=Utilities.nullToBlank(territoryDetails.getOwnerUserName())%></td>
                <td nowrap align="left"><%=Utilities.nullToBlank(territoryDetails.getSalesManagerUserName())%></td>
                <td nowrap align="left"><%=Utilities.nullToBlank(territoryDetails.getServiceManagerUserName())%></td>

				<td nowrap align="center"><%=territoryView.getTerritoryName(territoryDetails.getReturnTerritoryId())%></td>
				<td nowrap align="center"><%=Utilities.nullToBlank(territoryDetails.getReturnTime())%></td>
				
				<td nowrap align="center"><%=random%></td>
				<td nowrap align="center">
				<%if(territoryDetails.getIncludeInRoundRobin() != null){ %>
					<%if(territoryDetails.getIncludeInRoundRobin().compareTo(TerritoryDetails.INCLUDED_IN_ROUND_ROBIN)==0){%>
						Yes
					<%}else{%>
						No
					<%}%>
				<%} %>
				</td>
				
				<td nowrap align="center"><%=Utilities.nullToBlank(territoryDetails.getMaxProspectDisplay())%></td>
				
				<td nowrap align="center"><a href="javascript:fOnUpdate(<%=territoryDetails.getTerritoryId()%>);" title="Update Territory">
					<img src="<%=AppSettings.getAppWebPath()%>graphics/icons/edit.gif" alt="Update"></a></td>
				<td nowrap align="center"><a href="javascript:if(confirm('Territory <%=territoryDetails.getTerritory()%> will be permanently deleted from the database.\n\nPlease confirm.')) {fOnDelete(<%=territoryDetails.getTerritoryId()%>);}"
					title="Delete Territory">
					<img src="<%=AppSettings.getAppWebPath()%>graphics/icons/delete.gif" alt="Delete"></a></td>
				<td nowrap align="center"><a href="javascript:fOnAddNewZip(<%=territoryDetails.getTerritoryId()%>);" title="Assign Territory Zip Codes">
					<img src="<%=AppSettings.getAppWebPath()%>graphics/icons/new.gif" alt="Display Insert Update Zip"></a></td>
			</tr>
<%
		}
%>
		</table>
 		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>		
		<div id="moreInfo" style=display:none>
     	
     		<h2>Display Territories</h2>
     		<p>From here you can <b>Update, Delete</b> or assign <b>Territory Zips.</b></p>
     		<ul class="moreInfo">
     			<li class="moreInfo">Territory Id<span id="description"> - Internal Id to be used for reference</span></li>
     			<li class="moreInfo">Territory Name<span id="description"> - This is the name that will be displayed throughout Sales Portal</span></li>
     			<li class="moreInfo">Territory Owner<span id="description"> - Will receive notification emails if specified.</span></li>
     			<li class="moreInfo">Territory Sales Manager<span id="description"> - Will receive notification emails if specified.</span></li>
     			<li class="moreInfo">Territory Service Manager<span id="description"> - Can be blank. Will receive notification emails if specified.</span></li>
				<li class="moreInfo">Return Territory<span id="description"> - This is the territory that prospect will return to
								after a set amount of time (you specified in Return Time).</span></li>
				<li class="moreInfo">Return Time<span id="description"> - If you specified a Return Territory, you must specify a Return Time. 
								Number of days that the prospect will stay in this Territory before switching
								to the territory that you specified (Return Territory).</span></li>
 				<li class="moreInfo">Display Random Sequence<span id="description"> - "Yes" will display the prospects that 
 								belong to this territory in random sequence.</span></li>
 				<li class="moreInfo">Max Prospects Displayed<span id="description"> - The number 
 								of prospects displayed during a prospect search. </span></li>
 			</ul>
     		
     		
     		
     		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
     	
     </div>
<%
	}
%>
	 </td></tr></table>
	 </center>
     </body>
     </html>

<%
	}
	catch (Exception e)
	{
%>
     Error: <b><%=e.getMessage()%></b>
<%
		e.printStackTrace();
    }
	finally
	{

   	} %>
