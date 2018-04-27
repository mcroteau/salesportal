function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmState.dfStateId.value=id;
	document.frmState.formAction.value="DISPLAY_UPDATE";
	document.frmState.target="wndUpdate";
	document.frmState.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmState.formAction.value="DISPLAY_INSERT";
	document.frmState.target="wndInsert";
	document.frmState.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmState.dfStateId.value=id;
	document.frmState.formAction.value="DELETE";
	document.frmState.target="wndDelete";
	document.frmState.submit();
}