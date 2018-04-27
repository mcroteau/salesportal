//
// displayInsertCampaign.js
//

function fSubmit() {
	var frm = document.frmCampaignSalesAction;
	var dateElements = getElementsByTypeAndPartialId('input', 'dfDateReq_');
	var hourElements = getElementsByTypeAndPartialId('input', 'dfHourReq_');
	var empty = false;
	for(i=0;i<dateElements.length;i++) {
		if(dateElements[i].value == '') {
			empty = true;
		}
	}
	for(i=0;i<hourElements.length;i++) {
		if(hourElements[i].value == '') {
			empty = true;
		}
	}
	if(empty == true) {
		alert('You must fill in the mandatory date and time fields!');
	} else {
		frm.submit();
	}
	
}

function fSubmitDelete() {
	var frm = document.frmCampaignSalesAction;
	if(confirm('Are you sure you want to delete this prospect campaign and all sale actions associated with it?')) {
		frm.submit();
	}
}

function getElementsByTypeAndPartialId(type, id) {
	var matches = new Array();
	var elementsOfType = document.getElementsByTagName(type);
	for(i = 0; i < elementsOfType.length; i++) {
		if(elementsOfType[i].id.indexOf(id) != -1) {
			matches.push(elementsOfType[i]);			
		}
	}
	return matches;
}