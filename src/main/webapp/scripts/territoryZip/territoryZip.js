function fOnAddZip(){
	document.frmTerritoryZip.formAction.value="INSERT";
	//document.frmTerritoryZip.target="wndInsert";
	document.frmTerritoryZip.submit();
}
function fOnDelete(id){
	//var wndDelete = window.open("","wndDelete","width=400,height=400");
	//wndDelete.focus();
	//wndDelete.opener=window;
	document.frmTerritoryZip.formAction.value="DELETE";
	document.frmTerritoryZip.dfTerritoryZipId.value=id;
	//document.frmTerritoryZip.target="wndDelete";
	document.frmTerritoryZip.submit();
}
