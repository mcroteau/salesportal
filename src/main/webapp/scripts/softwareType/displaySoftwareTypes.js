function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmSoftwareType.dfSoftwareTypeId.value=id;
	document.frmSoftwareType.formAction.value="DISPLAY_UPDATE";
	document.frmSoftwareType.target="wndUpdate";
	document.frmSoftwareType.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmSoftwareType.formAction.value="DISPLAY_INSERT";
	document.frmSoftwareType.target="wndInsert";
	document.frmSoftwareType.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmSoftwareType.dfSoftwareTypeId.value=id;
	document.frmSoftwareType.formAction.value="DELETE";
	document.frmSoftwareType.target="wndDelete";
	document.frmSoftwareType.submit();
}