function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmLob.dfLobId.value=id;
	document.frmLob.formAction.value="DISPLAY_UPDATE";
	document.frmLob.target="wndUpdate";
	document.frmLob.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmLob.formAction.value="DISPLAY_INSERT";
	document.frmLob.target="wndInsert";
	document.frmLob.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmLob.dfLobId.value=id;
	document.frmLob.formAction.value="DELETE";
	document.frmLob.target="wndDelete";
	document.frmLob.submit();
}