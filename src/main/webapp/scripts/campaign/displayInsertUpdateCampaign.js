//
// displayInsertUpdateCampaign.js
//

function fProcessForm() {
    with (document.frmCampaign) {
    	dfCampaign.value=fElimChar(fElimChar(dfCampaign.value,"'"),'"');
        if (dfCampaign.value==''){
            alert('Please specify the Campaign name.');
            return false;
        }
    }
    return true;
}

function fMoveUp(rowIdentifier) {
	var frm = document.frmCampaign;
	frm.formAction.value = "MOVE_UP";
	frm.row.value = rowIdentifier;
	frm.submit();
}

function fMoveDown(rowIdentifier) {
	var frm = document.frmCampaign;
	frm.formAction.value = 'MOVE_DOWN';
	frm.row.value = rowIdentifier;
	frm.submit();
}

function fAdd() {
	var frm = document.frmCampaign;
	if(frm.cmbSalesAction.value == '') {
		alert('You must select a Sales Action to add.');
	} else {
		frm.formAction.value = 'ADD';
		frm.submit();
	}
}

function fDelete(rowIdentifier) {
	alert('Confirm to delete');
	var frm = document.frmCampaign;
	frm.formAction.value = 'REMOVE';
	frm.row.value = rowIdentifier;		
	frm.submit();
}

