<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.randr.webdw.AppSettings,
				com.randr.webdw.util.Utilities,
				com.randr.webdw.campaign.CampaignDetails,
				com.randr.webdw.mvcAbstract.exception.ModelException" %>

<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>

<html>
  <head>
    <%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Update Prospects Collection", "","", "")%>
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
 	</script>
	<script>
	function displayHideMoreInfo(){
		if(document.getElementById('moreInfoSearchDups').style.display == 'block'){
			document.getElementById('moreInfoSearchDups').style.display = 'none';
		}else{
			document.getElementById('moreInfoSearchDups').style.display = 'block';
		}	 
	}
	
	function fSearchForProspectDuplicates(){
		document.frmSearchDuplicates.submit();
	}

	</script>
  </head>
  
<body>

<form name="frmSearchDuplicates" method="post" action="prospect_search">
<input type=hidden name="formAction" value="SEARCH_FOR_PROSPECT_DUPLICATES"> 

<table class="duplicateSearch" align="center">
	<tr>
		<td colspan="2" class="headSearchDups">
			<center><h1>Search For Prospect Duplicates</h1></center>
		</td>
	</tr>
	<tr>
		<td>
			<table class="innerDuplicateSearch">
   				<tr><td><input type="radio" name="rdSearchDuplicates" value="companyName">Company Name</td></tr>
   				<tr><td><input type="radio" name="rdSearchDuplicates" value="contactName">Contact Name</td></tr>
   				<tr><td><input type="radio" name="rdSearchDuplicates" value="phoneNumber">Phone Number</td></tr>
   				<tr><td><input type="radio" name="rdSearchDuplicates" value="email">Email Address</td></tr>
   			</table>
		</td>
		<td>
			<table class="innerDuplicateSearch">
   				<tr><td><input type="submit" value="Search For Duplicates" name="dfSearchDuplicates"></td></tr>
   				<tr><td></td></tr>
   				<tr><td></td></tr>
   				<tr><td></td></tr>
   			</table>
   		</td>
   		
	</tr>
	<tr><td></td></tr>
</table>
<br/>
	<center><a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Show Help</a>
	<div id="moreInfoSearchDups" style=display:none>
	  	
  		<h3>Searching for Prospect Duplicates...</h3>  		  
  		<p>In this screen you will be able to search your database for prospect duplicates.  You will
  			will be able to search for prospects with the same Company Name, Contact Name,
  			Phone Number or Email.  
  		<br/><br/>
  		To successfully search for duplicates:
  		
  		
  		<ol class="moreInfoDups">
  			<li>Click which option you want to search by</li>
  			<li>Click "Search for Duplicates" ... simple as that.</li>     		
  		</ol>
  		<br/>
  		  * After you click search, you will be taken to a results screen where you will
  			be able to delete or update the prospects at will.
  		</p>
  		
  		<a class="moreInfo" href="javascript:displayHideMoreInfo();">+ Hide Help</a>
	  	
	</div></center>
</body>
</html>
<%=AppSettings.getFooter(userProfile)%>