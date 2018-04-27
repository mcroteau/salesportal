<%@page contentType="text/html; charset=iso-8859-1" language="java"
import="com.randr.webdw.AppSettings, com.randr.webdw.util.Utilities,
        com.randr.webdw.util.CollectionUtilities, com.randr.webdw.label.LabelView" %>
<%@page import="java.math.BigDecimal" %>

<jsp:useBean id="companyView" class="com.randr.webdw.company.CompanyView" scope="request"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

<% try{ %>
<html>
<head>
	<title>Upload File</title>
	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
  	<script language="JavaScript">
  	function fProcessForm() {
  		fDisplayLoading();
      	if (document.frmUpload.dfFile.value=='') {		
      		alert('Please specify the CSV file!');	
      		fDisplayLoading();
      		return false;
      	}
      	if (document.frmUpload.dfFile.value.indexOf("'")>-1) {
      		alert('The specified file contains an apostrophe which is an invalid character!');
      		fDisplayLoading();
      		return false;
      	}
      	return true; 
  	}
  	
  	function fDisplayLoading(){
	if( document.getElementById("loading").style.display=='none' ){
  		document.getElementById("loading").style.display = '';
	}else{
  		document.getElementById("loading").style.display = 'none';
	}
}
function displayHideMoreInfo(){
	if(document.getElementById('moreInfoImport').style.display == 'block'){
		document.getElementById('moreInfoImport').style.display = 'none';
	}else{
		document.getElementById('moreInfoImport').style.display = 'block';
	}	 
}
</script>
</head>

   
<body class="import">
<form name="frmUpload" method=post action="import_data" ENCTYPE="multipart/form-data">
<input type="hidden" name="formAction" value="PROCESS_UPLOAD">

<center>  
<table class="import">

<tr>
<td class="importLeft">
	 <center><h1>Import Data</h1></center>
	 <center>
     <table class="form">
     <tr><td colspan="2">&nbsp;</td></tr>
     <tr>
      <th align="right">Company:</th>
      <td align="left"><select name="dfCompanyId"><%=CollectionUtilities.getDropDownOptions(companyView.getElements(), "getCompanyId", "getCompany")%></select></td>
    </tr>

     <tr>
      <th align="right">CSV File:</th>
      <td align="left"><input type="file" name="dfFile" size="40"></td>
    </tr>
        <tr>
          <td align="left" colspan="2">
                <input type="checkbox" name="dfIgnoreFirstLine"> Ignore first line (headings)<br>
        </td>
        </tr>
          <tr>
          <td align="left" colspan="2">
                <input type="checkbox" name="dfEliminateDups"> Eliminate potential dups from uploaded file <br>
                (based on Contact Name & Company, Email & Company, Phone# & Company or External ID)<br>
        </td>
        </tr>

    <tr><td colspan="2">&nbsp;</td></tr>
    <tr>
      <td align="left" colspan="2">If category from file does not exist in database, create it: <br>
        <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><input type="checkbox" name="dfCreateStatus"> Status Id</td>
            <td><input type="checkbox" name="dfCreateCountry"> Country</td>
            <td><input type="checkbox" name="dfCreateState"> State</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="dfCreateSystemType" > <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></td>
            <td><input type="checkbox" name="dfCreateSoftwareType"> <%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></td>
            <td><input type="checkbox" name="dfCreateLob"> L.O.B.</td>
        </tr>
        <tr>
            <td><input type="checkbox" name="dfCreateTerritory"> Teritory</td>
            <td><input type="checkbox" name="dfCreateVerified"> Verified Status</td>
            <td></td>
        </tr>
        </table>
    </td>
    </tr>
    <tr><td colspan="2">&nbsp;</td></tr>

    <tr>
  		<td colspan="2" align="center">
            <input type=submit class="importButton" name="pbSubmit" value="Import Prospects" onclick="return fProcessForm()">
        </td>  		
    </tr>
    
    <tr><td colspan="2">&nbsp;</td></tr>
    </table>
    </center>
    <center>
		<p id="loading" style="display: none;">
				Processing Import...<br/><br/>
				Depending on the size of the .csv file, the import may take several minutes.<br/><br/>
				Please Wait....
		</p>
		
    <p>Upload size limit is about 10,000 records per upload.</p>
    <P>Dates should be in mm/DD/yyyy format (ex. 10/15/2009).</P>
    <a class="import" href="<%=AppSettings.getAppWebPath()%>doc/salesportal-import-template.xls">Download Import Template</a></center>
<center>	
<p class="import">
	* This import will also Update your prospects if the prospect already exists.</p>
<p class="import">
	External Id is the Prospect ID from your external system.
	It can be used to link uploaded prospects to documents and legacy applications.</p></center>
	
	<center><a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a></center>
     <div id="moreInfoImport" style=display:none>	  
     		<p>In this screen you will be able to import up to 10,000 prospects at a time.	</p>
     		  
     		<p>To ensure that the import works smoothly, here are some things to follow:</p>
     		
     		<ol class="moreInfoImport">
     			<li class="moreInfoImport">Make sure the data in the csv file is clean.  No extra " , or ' </li>
     			<li class="moreInfoImport">To make creating the csv file correcly, use the Import Template above.</li>
     			<li class="moreInfoImport">Make sure the names of Territories, Statuses, States, Countries, LOB, Verified Status, and User Drop Down 2 
     				match your Sales Portal definitions exactly.  If some are different, make sure you click the 
     				"Create Category" check boxes for each of the values as necessary.</li>
     			<li class="moreInfoImport">If you have headings in your .csv file, make sure you click the "Ignore first line (headings)" checkbox.</li>
     			<li class="moreInfoImport">If you check Eliminate Dups, then potential duplicates will not be imported</li>
     			<li class="moreInfoImport">State - use 2 character state code</li>
     			<li class="moreInfoImport">Country - use full country name</li>
     			<li class="moreInfoImport">Territory - you can use either the full Territory Name or the territory number</li>
     		</ol>
			<p>If for some reason you make a mistake, you will be able to delete the imported prospects</p>
    <a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
	</div>

</td>
<td class="importRight">
	<center><h1>CSV File Layout</h1></center>
	<ol class="importAttributes">
		<li class="import">(A) - Company</li>
		<li class="import">(B) - Contact</li>
		<li class="import">(C) - Address 1</li>
		<li class="import">(D) - Address 2</li>
		<li class="import">(E) - City</li>
		<li class="import">(F) - State</li>
	
		<li class="import">(G) - Zip</li>
		<li class="import">(H) - Status Id</li>
		<li class="import">(I) - Phone</li>
		<li class="import">(J) - Fax</li>
		<li class="import">(K) - Title</li>
		<li class="import">(L) - <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></li>
		
		<li class="import">(M) - <%=labelView.getLabel(LabelView.USER_2)%></li>
		<li class="import">(N) - Size</li>
		<li class="import">(O) - Verified</li>
		<li class="import">(P) - <%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></li>
		<li class="import">(Q) - LOB</li>
		<li class="import">(R) - Website</li>
			
		<li class="import">(S) - Email</li>
		<li class="import">(T) - County</li>
		<li class="import">(U) - Territory</li>
		<li class="import">(V) - Country</li>
		<li class="import">(W) - Default SP Company</li>
		<li class="import">(X) - Campaign ID</li>
		
		<li class="import">(Y) - Sales Action ID</li>
		<li class="import">(Z) - Sales Action Note (200 Max)</li>
		<li class="import">(AA) - Note (500 Max)</li>
		<li class="import">(AB) - External Id</li>
		<li class="import">(AC) - Sales Action Date (yyyy-mm-dd)</li>
		<li class="import">(AD) - <%=labelView.getLabel(LabelView.USER_1)%></li>
		
		<li class="import">(AE) - <%=labelView.getLabel(LabelView.USER_3)%></li>
		<li class="import">(AF) - <%=labelView.getLabel(LabelView.USER_4)%></li>
		<li class="import">(AG) - Ext./Work Phone</li>
		<li class="import">(AH) - Cell Phone</li>
	</ol>

</td>
</tr>
</table>
</center>
</form>
</body>
</html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {
   } %>