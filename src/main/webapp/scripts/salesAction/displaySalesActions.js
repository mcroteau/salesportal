function fOnUpdate(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndUpdate = window.open("","wndUpdate","width=700,height=350");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmSalesAction.dfSalesActionId.value=id;
	document.frmSalesAction.formAction.value="DISPLAY_UPDATE";
	document.frmSalesAction.target="wndUpdate";
	document.frmSalesAction.submit();
}

function fOnInsert(){

//	var wndInsert = window.open("","wndInsert","width=400,height=300");
	var wndInsert = window.open("","wndInsert","width=700,height=350");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmSalesAction.formAction.value="DISPLAY_INSERT";
	document.frmSalesAction.target="wndInsert";
	document.frmSalesAction.submit();
}

function fOnDelete(id){

	//var wndDelete = window.open("","wndDelete","width=400,height=300");
	var wndDelete = window.open("","wndDelete","width=700,height=350");
	
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmSalesAction.dfSalesActionId.value=id;
	document.frmSalesAction.formAction.value="DELETE";
	document.frmSalesAction.target="wndDelete";
	document.frmSalesAction.submit();
}