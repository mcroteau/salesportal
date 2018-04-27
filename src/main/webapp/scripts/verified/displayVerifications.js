function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmVerified.dfVerifiedId.value=id;
	document.frmVerified.formAction.value="DISPLAY_UPDATE";
	document.frmVerified.target="wndUpdate";
	document.frmVerified.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmVerified.formAction.value="DISPLAY_INSERT";
	document.frmVerified.target="wndInsert";
	document.frmVerified.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmVerified.dfVerifiedId.value=id;
	document.frmVerified.formAction.value="DELETE";
	document.frmVerified.target="wndDelete";
	document.frmVerified.submit();
}