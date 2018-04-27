function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=950,height=700,scrollbars=yes,resizable=yes");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmTerritory.action="territory";
	document.frmTerritory.dfTerritoryId.value=id;
	document.frmTerritory.formAction.value="DISPLAY_UPDATE";
	document.frmTerritory.target="wndUpdate";
	document.frmTerritory.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=950,height=700,scrollbars=yes,resizable=yes");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmTerritory.action="territory";
	document.frmTerritory.formAction.value="DISPLAY_INSERT";
	document.frmTerritory.target="wndInsert";
	document.frmTerritory.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=400");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmTerritory.action="territory";
	document.frmTerritory.dfTerritoryId.value=id;
	document.frmTerritory.formAction.value="DELETE";
	document.frmTerritory.target="wndDelete";
	document.frmTerritory.submit();
}

function fOnAddNewZip(id){
	var wndUpdate = window.open("","wndUpdate","width=400,height=400,toolbar=no,menubar=yes, scrollbars=yes, resizable=yes");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmTerritory.action="territory_zip";
	document.frmTerritory.dfTerritoryId.value=id;
	document.frmTerritory.formAction.value="DISPLAY";
	document.frmTerritory.target="wndUpdate";
	document.frmTerritory.submit();
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}