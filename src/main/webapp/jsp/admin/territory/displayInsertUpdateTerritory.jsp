<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.territory.TerritoryDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException,
                com.randr.webdw.util.CollectionUtilities" %>
<jsp:useBean id="territoryDetails" class="com.randr.webdw.territory.TerritoryDetails" scope="request"/>
<jsp:useBean id="userView" class="com.randr.webdw.user.UserView" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<jsp:useBean id="returnTerritoryView" class="com.randr.webdw.territory.TerritoryView" scope="request"/>

<% try{ %>

	<%

	String actionDescription = "Update";
	String newFormAction="UPDATE";

	if (formAction.equals("DISPLAY_INSERT"))
	{
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
	%>
     <html>
     <head>
     	<title><%=actionDescription%> Territory</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/territory/displayInsertUpdateTerritory.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Territory</h1>
     <hr><br>
     <a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>

     <form name="frmTerritory" method="post" action="territory" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">



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
		if (formAction.equals("DISPLAY_UPDATE"))
		{
		%>
			<tr>
				<th align="right">Id&nbsp;:&nbsp;</th>
				<td>
 					<%=territoryDetails.getTerritoryId()%>
					<input type="hidden" name="dfTerritoryId" value="<%=territoryDetails.getTerritoryId()%>">
				</td>
			</tr>
		<%
		}
		%>

		<tr>
			<th align="right">Territory Name&nbsp;:&nbsp;</th>
			<td><input type="text" name="dfTerritory" size="40" maxlength="80" value="<%=Utilities.nullToBlank(territoryDetails.getTerritory())%>"></td>
		</tr>

		<tr>
			<th align="right">Territory Owner&nbsp;:&nbsp;</th>
			<td><select name="dfOwnerUserId">
                     <option value=""> -
                <%=CollectionUtilities.getDropDownOptions(userView.getElements(),
                                                          "getUserId", "getDropDownText",
                                                          territoryDetails.getOwnerUserId()) %>

                </select>
            </td>
		</tr>
		<tr>
			<th align="right">Territory Sales manager&nbsp;:&nbsp;</th>
			<td><select name="dfSalesManagerUserId">
                     <option value=""> -
                <%=CollectionUtilities.getDropDownOptions(userView.getElements(),
                                                          "getUserId", "getDropDownText",
                                                          territoryDetails.getSalesManagerUserId()) %>

                </select>
            </td>
		</tr>
		<tr>
			<th align="right">Territory Service manager&nbsp;:&nbsp;</th>
			<td><select name="dfServiceManagerUserId">
                     <option value=""> -
                <%=CollectionUtilities.getDropDownOptions(userView.getElements(),
                                                          "getUserId", "getDropDownText",
                                                          territoryDetails.getServiceManagerUserId()) %>

                </select>
            </td>
		</tr>
		<tr>
			<th align="right">Return Territory&nbsp;:&nbsp;</th>
            <td>
                <select name="dfReturnTerritoryId" class="salesmen">
                	<option value=""> -
                <%=CollectionUtilities.getDropDownOptions(returnTerritoryView.getElements(),
                                                          "getTerritoryId", "getTerritory",
                                                          territoryDetails.getReturnTerritoryId())

                %>
                </select>
        	</td>
		</tr>
		<tr>
			<th align="right">Return Time&nbsp;:&nbsp;</th>
			<td><input type="text" name="dfReturnTime" value="<%=Utilities.nullToBlank(territoryDetails.getReturnTime())%>"></td>
		</tr>
		<%
			String random = "";
			String notRandom = "";
			if(territoryDetails.getRandomSequence() != null){
				if(territoryDetails.getRandomSequence().compareTo(TerritoryDetails.RANDOM) == 0){ 
					random = "checked";
					notRandom = "";
				}else{
					random = "";
					notRandom = "checked";
				}
			}else{
				notRandom = "checked";
			}
		%>

		<tr>
			<th align="right">Display Random Sequence&nbsp;:&nbsp;</th>
			<td><input type="radio" <%=random%> name="dfRandomSequence" value="<%=TerritoryDetails.RANDOM%>">Yes&nbsp;
				<input type="radio" <%=notRandom%> name="dfRandomSequence" value="<%=TerritoryDetails.NOT_RANDOM%>">NO</td>
		</tr>
			

		<tr>
			<th align="right">Max Prospects Displayed&nbsp;:&nbsp;</th>
			<td><input type="text" name="dfMaxProspectDisplay" value="<%=Utilities.nullToBlank(territoryDetails.getMaxProspectDisplay())%>"></td>
		</tr>
		<tr>
			<th colspan="2">(Select random display if you are limiting the # of prospects displayed)</th>
		</tr>
		<%
			String include = "";
			String notInclude = "";
			if(territoryDetails.getIncludeInConversionReport() != null){
				if(territoryDetails.getIncludeInConversionReport().compareTo(TerritoryDetails.INCLUDED_IN_CONVERSION_REPORT) == 0){ 
					include = "checked";
					notInclude = "";
				}else{
					include = "";
					notInclude = "checked";
				}
			}else{
				include = "checked";
			}
			
		%>
		<tr>
			<th align="right">Include in Reports&nbsp;:&nbsp;</th>
			<td><input type="radio" <%=include%> name="dfIncludeInReport" value="<%=TerritoryDetails.INCLUDED_IN_CONVERSION_REPORT%>">Yes&nbsp;
				<input type="radio" <%=notInclude%> name="dfIncludeInReport" value="<%=TerritoryDetails.NOT_INCLUDED_IN_CONVERSION_REPORT%>">NO</td>
		</tr>
		<%
			String includeRoundRobin = "";
			String notIncludeRoundRobin = "";
			if(territoryDetails.getIncludeInRoundRobin() != null){
				if(territoryDetails.getIncludeInRoundRobin().compareTo(TerritoryDetails.INCLUDED_IN_ROUND_ROBIN) == 0){ 
					includeRoundRobin = "checked";
					notIncludeRoundRobin = "";
				}else{
					includeRoundRobin = "";
					notIncludeRoundRobin = "checked";
				}
			}else{
				notIncludeRoundRobin = "checked";
			}
			
		%>
		<tr>
			<th align="right">Include in Round Robin&nbsp;:&nbsp;</th>
			<td><input type="radio" <%=includeRoundRobin%> name="dfIncludeInRoundRobin" value="<%=TerritoryDetails.INCLUDED_IN_ROUND_ROBIN%>">Yes&nbsp;
				<input type="radio" <%=notIncludeRoundRobin%> name="dfIncludeInRoundRobin" value="<%=TerritoryDetails.NOT_INCLUDED_IN_ROUND_ROBIN%>">NO</td>
		</tr>


        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
                <a class="button" href="javascript: if (fProcessForm()) {document.frmTerritory.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>


		</table>
 		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>		
		<div id="moreInfo" style=display:none>
     	
     		<h2>You are now updating a Territory</h2>
     		<ul class="moreInfo">
     			<li class="moreInfo">Territory Name<span id="description"> - Cannot be Blank.  This is the name that will be displayed throughout Sales Portal</span></li>
     			<li class="moreInfo">Territory Owner<span id="description"> - Can be blank. Will receive notification emails if specified.</span></li>
     			<li class="moreInfo">Territory Sales Manager<span id="description"> - Can be blank. Will receive notification emails if specified.</span></li>
     			<li class="moreInfo">Territory Service Manager<span id="description"> - Can be blank. Will receive notification emails if specified.</span></li>
				<li class="moreInfo">Return Territory<span id="description"> - Can be blank.  This is the territory that prospect will return to
								after a set amount of time (you specify below). This is for Sales Teams that operate based on a time interval. ie. 
								The Salesman has 20 days ("Return Time") to close on a given prospect, if the salesman is unsuccessful in doing so, then
								the prospect with this territory will be returned to the "Return Territory".  </span></li>
				<li class="moreInfo">Return Time<span id="description"> - If you specified a Return Territory, you must specify a Return Time. 
								Number of days that the prospect will stay in this Territory before switching
								to the territory that is specified above.  The timer starts when a given prospect is assigned this territory. </span></li>
 				<li class="moreInfo">Display Random Sequence<span id="description"> - Can be blank. Selecting "Yes" will display the prospects that 
 								belong to this territory in random sequence.</span></li>
 				<li class="moreInfo">Max Prospects Displayed<span id="description"> - Can be blank. Must be a number, when specified, the number 
 								you enter will be the number of prospects displayed during a search.  If left blank, there will be no limit
 								on the number of prospects displayed.</span></li>
 			</ul>
     		
     		
     		
     		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
     	
     </div>
<%
	}
%>
	 </form>
	 </center>

     <script>
        document.frmTerritory.dfTerritory.focus();
     </script>
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
