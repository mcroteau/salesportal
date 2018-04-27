function fOnUpdate(prospectId){
    document.frmDuplicates.target="";
	document.frmDuplicates.action="prospect";
	document.frmDuplicates.formAction.value='DISPLAY_UPDATE';
	document.frmDuplicates.dfProspectId.value=prospectId;
	document.frmDuplicates.submit();
}

function fOnInsert(popup, formAction){
    document.frmDuplicates.target="";
    if (popup == true){
	    var wndInsert = window.open("","wndInsert","width=500,height=600,scrollbars=yes,status=yes");
	    wndInsert.focus();
	    wndInsert.opener=window;
	    document.frmDuplicates.target="wndInsert";
	}
	document.frmDuplicates.action="prospect";
	document.frmDuplicates.formAction.value=formAction;
	document.frmDuplicates.submit();
}

function fOnDelete(prospectId){
    document.frmDuplicates.target="";
	document.frmDuplicates.action="prospect";
	document.frmDuplicates.dfProspectId.value=prospectId;
	document.frmDuplicates.formAction.value='DELETE';
	document.frmDuplicates.submit();
}
function fOnDeleteCascade(prospectId){
    document.frmDuplicates.target="";
	document.frmDuplicates.action="prospect";
	document.frmDuplicates.dfProspectId.value=prospectId;
	document.frmDuplicates.formAction.value='DELETE_CASCADE';
	document.frmDuplicates.submit();
}

function fOnNotes(prospectId, popup){
    document.frmDuplicates.target="";
    if (popup == true){
	    var wndUpdate = window.open("","wndUpdate","width=500,height=600,scrollbars=yes,status=yes");
	    window.name = "mainWnd";
	    wndUpdate.focus();
	    wndUpdate.opener=window;
	    document.frmDuplicates.target="wndUpdate";
	}
	document.frmDuplicates.dfProspectId.value=prospectId;
	document.frmDuplicates.action="note";
	document.frmDuplicates.formAction.value='DISPLAY';
	document.frmDuplicates.submit();
}



function fOnPrint(prospectId){
    var wndPrint = window.open('', 'wndPrint', 'status=yes,scrollbars=yes,width=780,height=500,top=0,left=0');
    window.name = "mainWnd";
    document.frmDuplicates.target='wndPrint';
    document.frmDuplicates.action="prospect_print";
    document.frmDuplicates.dfProspectId.value = prospectId;
    document.frmDuplicates.formAction.value="DISPLAY_PRINT_FRAMESET";

    document.frmDuplicates.submit();
}


function fOnDeleteChecked(){

	document.frmDuplicates.formAction.value='DELETE_SELECTED';
    document.frmDuplicates.target="";
    document.frmDuplicates.action="prospect_search";
    document.frmDuplicates.submit();
       
}

function fOnRemoveChecked(){

	document.frmDuplicates.formAction.value='REMOVE_SELECTED';
    document.frmDuplicates.target="";
    document.frmDuplicates.action="prospect_search";
    document.frmDuplicates.submit();
       
}

function fOnEditCollection() {
    document.frmDuplicates.formAction.value='DISPLAY_UPDATE_COLLECTION';
    document.frmDuplicates.target="";
    document.frmDuplicates.action="prospect";
    document.frmDuplicates.submit();
}