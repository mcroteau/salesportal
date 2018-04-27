
function fProcessForm(){
    with (document.frmTerritory){
    	dfTerritory.value=fElimChar(fElimChar(dfTerritory.value,"'"),'"');
		
        if (dfTerritory.value==''){
            alert('Please specify the territory name.');
            return false;
        }

        if (dfReturnTime.value != '' && dfReturnTerritoryId.value == ''){
			alert('Because you specified a Return Time you must \n specify a valid Return Territory');
	        return false;
        }
        
		if (dfReturnTerritoryId.value != '' && dfReturnTime.value == ''){
        		alert('Because you specified a Return Territory you must \n specify a valid Return Time');
            	return false;
        }

		if (dfReturnTerritoryId.value != '' && dfReturnTime.value != ''){
			var returnTime = document.frmTerritory.dfReturnTime.value;
			if(!isValidNumber(returnTime)){
				alert("Please input a number for Time");
            	return false;
            }
        }    
        
		if (dfMaxProspectDisplay.value!=''){
			var maxProspects = document.frmTerritory.dfMaxProspectDisplay.value;
			if(!isValidNumber(maxProspects)){
				alert("Please input a number for Max Prospects Display");
            	return false;
            }
        }
		return true;    
        
    }
}

function isValidNumber(valueEntered){
	var number=/(^\d+$)|(^\d+\.\d+$)/
	if (number.test(valueEntered)){
		return true;
	}else{
		
		return false;
	}
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}

