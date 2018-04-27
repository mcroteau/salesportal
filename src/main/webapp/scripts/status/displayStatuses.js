function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmStatus.dfStatusId.value=id;
	document.frmStatus.formAction.value="DISPLAY_UPDATE";
	document.frmStatus.target="wndUpdate";
	document.frmStatus.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmStatus.formAction.value="DISPLAY_INSERT";
	document.frmStatus.target="wndInsert";
	document.frmStatus.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmStatus.dfStatusId.value=id;
	document.frmStatus.formAction.value="DELETE";
	document.frmStatus.target="wndDelete";
	document.frmStatus.submit();
}