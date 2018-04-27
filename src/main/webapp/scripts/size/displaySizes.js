function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmSize.dfSizeId.value=id;
	document.frmSize.formAction.value="DISPLAY_UPDATE";
	document.frmSize.target="wndUpdate";
	document.frmSize.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmSize.formAction.value="DISPLAY_INSERT";
	document.frmSize.target="wndInsert";
	document.frmSize.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmSize.dfSizeId.value=id;
	document.frmSize.formAction.value="DELETE";
	document.frmSize.target="wndDelete";
	document.frmSize.submit();
}