function fOnUpdate(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndUpdate = window.open("","wndUpdate","width=1000,height=800");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmEmailSalesAction.dfEmailSalesActionId.value=id;
	document.frmEmailSalesAction.formAction.value="DISPLAY_UPDATE";
	document.frmEmailSalesAction.target="wndUpdate";
	document.frmEmailSalesAction.submit();
}

function fOnInsert(){

//	var wndInsert = window.open("","wndInsert","width=400,height=300");
	var wndInsert = window.open("","wndInsert","width=1000,height=800");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmEmailSalesAction.formAction.value="DISPLAY_INSERT";
	document.frmEmailSalesAction.target="wndInsert";
	document.frmEmailSalesAction.submit();
}

function fOnDelete(id){

	//var wndDelete = window.open("","wndDelete","width=400,height=300");
	var wndDelete = window.open("","wndDelete","width=1000,height=800");
	
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmEmailSalesAction.dfEmailSalesActionId.value=id;
	document.frmEmailSalesAction.formAction.value="DELETE";
	document.frmEmailSalesAction.target="wndDelete";
	document.frmEmailSalesAction.submit();
}

function fOnEstimateProspects(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndEstimateProspects = window.open("","wndEstimateProspects","width=600,height=300, scrollbar=yes, resizable=yes");
	wndEstimateProspects.focus();
	wndEstimateProspects.opener=window;
	document.frmEmailSalesAction.dfEmailSalesActionId.value=id;
	document.frmEmailSalesAction.formAction.value="ESTIMATE_PROSPECTS";
	document.frmEmailSalesAction.target="wndEstimateProspects";
	document.frmEmailSalesAction.submit();
}

function fOnDisplayProspects(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndDisplayProspects = window.open("","wndDisplayProspects","width=600,height=800");
	wndDisplayProspects.focus();
	wndDisplayProspects.opener=window;
	document.frmEmailSalesAction.dfEmailSalesActionId.value=id;
	document.frmEmailSalesAction.formAction.value="DISPLAY_PROSPECTS";
	document.frmEmailSalesAction.target="wndDisplayProspects";
	document.frmEmailSalesAction.submit();
}
