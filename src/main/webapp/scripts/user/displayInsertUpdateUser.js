
function fProcessForm(login)
{
    with (document.frmUser)
    {

    	dfFirstName.value=fElimChar(fElimChar(dfFirstName.value,"'"),'"');
    	dfLastName.value=fElimChar(fElimChar(dfLastName.value,"'"),'"');

        if (login == "1")
        {
    	    dfUserName.value=fElimChar(fElimChar(dfUserName.value,"'"),'"');
    	    dfPassword.value=fElimChar(fElimChar(dfPassword.value,"'"),'"');
    	}
    	else
    	{
    	    dfAddress.value=fElimChar(fElimChar(dfAddress.value,"'"),'"');
    	    dfAddress2.value=fElimChar(fElimChar(dfAddress2.value,"'"),'"');
    	    dfCity.value=fElimChar(fElimChar(dfCity.value,"'"),'"');
    	    dfCounty.value=fElimChar(fElimChar(dfCounty.value,"'"),'"');
    	    dfZip.value=fElimChar(fElimChar(dfZip.value,"'"),'"');
        	dfPhone.value=fElimChar(fElimChar(dfPhone.value,"'"),'"');
        	dfPhoneExt.value=fElimChar(fElimChar(dfPhoneExt.value,"'"),'"');
        	dfEmail.value=fElimChar(fElimChar(dfEmail.value,"'"),'"');
        	dfFax.value=fElimChar(fElimChar(dfFax.value,"'"),'"');
            dfWebsite.value=fElimChar(fElimChar(dfWebsite.value,"'"),'"');
    	}

        if (dfCompanyId.value=='')
        {
            alert('Please specify the Sales Company.');
            return false;
        }


        if (dfFirstName.value=='')
        {
            alert('Please specify the First Name.');
            return false;
        }

        if (dfLastName.value=='')
        {
            alert('Please specify the Last Name.');
            return false;
        }

        if (login == 1)
        {
            if (dfUserName.value=='')
            {
                alert('Please specify the Login User Name.');
                return false;
            }
        }

    }

    return true;
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}

function fCheckLimitInitiated(){
	box = eval(document.frmUser.ckLimitProspectSearchView);
	if(box.checked == true){
		//1 = Limit
		//2 = Do Not Limit
		document.frmUser.ckLimitProspectSearchView.value = '1';
	}else{
		document.frmUser.ckLimitProspectSearchView.value = '0';
	}
}
    
function fCompanyLimitInitiated(){
	box = eval(document.frmUser.ckLimitCompanyView);
	if(box.checked == true){
		//1 = Limit
		//2 = Do Not Limit
		document.frmUser.ckLimitCompanyView.value = '1';
	}else{
		document.frmUser.ckLimitCompanyView.value = '0';
	}
}
function fCheckToDisableTerritories(){
   	box = eval(document.frmUser.ckSpecificTerritories);
   	territoryOptions = document.frmUser.dfTerritoryId.options.length
	//1 = Specific Territories
	//2 = Not Specific Territories
	if(box.checked == true){
		document.frmUser.ckSpecificTerritories.value = '1';
		document.frmUser.dfTerritoryId.disabled=false;

	}else{
		document.frmUser.ckSpecificTerritories.value = '2';
		document.frmUser.dfTerritoryId.disabled=true;
		for(var i =0; i < territoryOptions; i++){
      			document.frmUser.dfTerritoryId.options[i].selected = false;
      		}
	}
}
		
function fCheckToDisableStatuses(){
	box = eval(document.frmUser.ckSpecificStatuses);
    statusOptions = document.frmUser.dfStatusId.options.length
	//1 = Specific Statuses
	//2 = Not Specific Statuses
	if(box.checked == true){
		document.frmUser.ckSpecificStatuses.value = '1';
		document.frmUser.dfStatusId.disabled=false;
	}else{
		document.frmUser.ckSpecificStatuses.value = '2';
		document.frmUser.dfStatusId.disabled=true;
		for(var j =0; j < statusOptions; j++){
      			document.frmUser.dfStatusId.options[j].selected = false;
      		}
      		
	}
}

function fCheckToDisableLobs(){
	box = eval(document.frmUser.ckSpecificLobs);
    lobOptions = document.frmUser.dfLobId.options.length
	//1 = Specific Lobs
	//2 = Not Specific Lobs
	if(box.checked == true){
		document.frmUser.ckSpecificLobs.value = '1';
		document.frmUser.dfLobId.disabled=false;
	}else{
		document.frmUser.ckSpecificLobs.value = '2';
		document.frmUser.dfLobId.disabled=true;
		for(var j =0; j < lobOptions; j++){
      			document.frmUser.dfLobId.options[j].selected = false;
      		}
      		
	}
}

function fHasSpecificLobs(){
	eval(document.frmUser.ckSpecificLobs).checked == true;
}
		
function fHasSpecificTerritories(){
	eval(document.frmUser.ckSpecificTerritories).checked == true;
}

function fHasSpecificStatuses(){
	eval(document.frmUser.ckSpecificStatuses).checked == true;
}