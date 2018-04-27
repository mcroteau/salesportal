<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.salesAction.SalesActionDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>
<jsp:useBean id="salesActionDetails" class="com.randr.webdw.salesAction.SalesActionDetails" scope="request"/>
<jsp:useBean id="formAction" class="java.lang.String" scope="request"/>
<script language="JavaScript">
var rows = document.getElementById('dataRollOver').rows;
for (i=0;i<rows.length;i++) {
	rows[i].onmouseover = highlight;
	rows[i].onmouseout = dehighlight;
}


function highlight() { 
	this.className = "highlight"; 
}
function dehighlight() {
 	this.className = ""; 
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}
</script>
<% try{ %>

	<%

	String actionDescription = "Update";
	String newFormAction="UPDATE";

	String dateMandatoryYesSelected = "";
	String dateMandatoryNoSelected = "";
	
	if(salesActionDetails.getMandatoryDate() != null){
		if(salesActionDetails.getMandatoryDate().compareTo(SalesActionDetails.REQUIRES_MANDATORY_DATE)==0){
			dateMandatoryYesSelected = "selected";	
		}else{
			dateMandatoryNoSelected = "selected";
		}
	}
		
	if (formAction.equals("DISPLAY_INSERT")){
		actionDescription = "Insert";
		newFormAction="INSERT";
	}
	%>
     <html>
     <head>
     	<title><%=actionDescription%> Sales Action</title>
     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">
		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>

		<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/salesAction/displayInsertUpdateSalesAction.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
		</script>
		
		<script src="<%=AppSettings.getAppWebPath()%>scripts/salesAction/201a.js" type="text/javascript"></script>
		
     </head>

     <body>
	 <center>
	 <h1><%=actionDescription%> Sales Action</h1>
     <hr><br>
      <a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>
     <div id="moreInfo" style=display:none>
     	
     		<h2>Insert and Update Sales Actions</h2>
     		  
     		<p></p>
     		  
     		<p>Here is an explanation for each field:</p>
     		
     		<ol class="moreInfo">
     			<li>The sales action id will be assigned automatically by the system</li>
     			<li>sales action name should be descriptive as that is what displays in the selection boxes</li>
     			<li>If you select a color, on the Calendar view the sales action description will surrounded by that color</li>
     			<li>If you set date mandatory = yes, then when a sales action is selected the salesman has to enter a date and time.</li>
     			<li>Display for Salesmen controls whether this sales action will display in the list of sales actions for a salesman to select</li>
     			<li>Email Draft to use.  Enter the email draft number from the Order Portal.  If there is an email draft number entered <br>
     			an email will be automatically sent to the prospect on the date.  This is done through the Order Portal, so make sure<br>
     			the settings in your Order Portal and Data Migration Portal are correct.</li>
     			
     			<li>To Save changes, Click Update</li>
     		
     		</ol>
     		
     		<p></p>
     		
     		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
     	
     </div>
     <form name="frmSalesAction" method="post" action="sales_action" onsubmit="javascript: return fProcessForm();">
     <input type=hidden name="formAction" value="<%=newFormAction%>">



<% if (request.getAttribute("modelException") != null) {
		ModelException modelException = (ModelException) request.getAttribute("modelException"); %>
		<p class="exception"><%=modelException.getMessage()%></p>
<% } else { %>
		<table class="form">
        <tr><td colspan="2">&nbsp;</td></tr>
		<% if (formAction.equals("DISPLAY_UPDATE")) { %>
			<tr>
				<th align="right">Id:</th>
				<td>
 					<%=salesActionDetails.getActionId()%>
					<input type="hidden" name="dfSalesActionId" value="<%=salesActionDetails.getActionId()%>">
				</td>
			</tr>
		<% } %>
		<tr>
			<th align="right">Action Name:</th>
			<td><input type="text" name="dfSalesAction" size="40" maxlength="80" value="<%=Utilities.nullToBlank(salesActionDetails.getAction())%>"></td>
		</tr>

 		<tr>
			<th align="right">Action Color:</th>
			<td>
				<div id="colorpicker201" class="colorpicker201"></div>
				
				<img src="<%=AppSettings.getAppWebPath()%>graphics/sel.gif" onclick="showColorGrid2('dfColor','sample_1');" border="0" style="cursor:pointer" alt="select color" title="select color">
				<input type="hidden" ID="dfColor" name="dfColor" value="<%=Utilities.nullToBlank(salesActionDetails.getColor())%>" size="9" value="">
				&nbsp;
				<input type="text" ID="sample_1" size="1" value="" onclick="showColorGrid2('dfColor','sample_1');" style="background-color:<%=Utilities.nullToBlank(salesActionDetails.getColor())%>" >
			</td>
		</tr>

		<tr>
			<th align="right">Date Is Mandatory:</th>
			<td>
                <select name="dfMandatoryDate">
                    <option value="1" <%=dateMandatoryYesSelected%>>YES</option>
                    <option value="0" <%=dateMandatoryNoSelected%>>NO</option>
                </select></td>
		</tr>
			<tr>
			<th align="right">Display for Salesmen?:</th>
			<td>
                <select name="dfDisplayForSalesmen">
                 <% if (Utilities.nullToBlank(salesActionDetails.getDisplayForSalesmen()).compareTo("")==0 ||
                 			Utilities.nullToZero(salesActionDetails.getDisplayForSalesmen()).equals(SalesActionDetails.YES_DISPLAY_FOR_SALESMEN)) {%>
                    	<option value="1" selected>YES - Include in Sales Action List
                    	<option value="0">NO - Do Not Include in Sales Action List
                     <% }else{ %>
                     	<option value="1">YES - Include in Sales Action List
                    	<option value="0" selected>NO - Do Not Include in Sales Action List
                <% }  %>
                </select></td>
		</tr>
		
		<tr>
			<th align="right">Email Draft # to use <br>(get this from Order Portal <br>leave blank if not an email Action:</th>
			<td><input type="text" name="dfEmailDraftToUse" size="5" maxlength="9" value="<%=Utilities.nullToBlank(salesActionDetails.getEmailDraftToUse())%>"></td>
		</tr>

        <tr><td colspan="2">&nbsp;</td></tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		<tr>

			<td colspan="2" align="center">
                <a class="button" href="javascript: if(fProcessForm()){document.frmSalesAction.submit();}">&nbsp;&nbsp;<%=actionDescription%>&nbsp;&nbsp;</a>
                &nbsp; &nbsp;
                <a class="button" href="javascript: window.close();">&nbsp;&nbsp;Cancel&nbsp;&nbsp;</a>
            </td>
		</tr>
        <tr><td colspan="2">&nbsp;</td></tr>
		</table>
<% } %>
	 </form>
	 </center>
     <script>
        document.frmSalesAction.dfSalesAction.focus();
     </script>
     </body>
</html>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<% 	e.printStackTrace(); } %>