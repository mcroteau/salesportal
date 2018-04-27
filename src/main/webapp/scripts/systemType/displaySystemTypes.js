function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmSystemType.dfSystemTypeId.value=id;
	document.frmSystemType.formAction.value="DISPLAY_UPDATE";
	document.frmSystemType.target="wndUpdate";
	document.frmSystemType.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmSystemType.formAction.value="DISPLAY_INSERT";
	document.frmSystemType.target="wndInsert";
	document.frmSystemType.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmSystemType.dfSystemTypeId.value=id;
	document.frmSystemType.formAction.value="DELETE";
	document.frmSystemType.target="wndDelete";
	document.frmSystemType.submit();
}