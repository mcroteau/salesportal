function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=500,height=600,scrollbars=yes");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmCommision.dfCommisionId.value=id;
	document.frmCommision.formAction.value="DISPLAY_UPDATE";
	document.frmCommision.target="wndUpdate";
	document.frmCommision.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=500,height=600,scrollbars=yes");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmCommision.formAction.value="DISPLAY_INSERT";
	document.frmCommision.target="wndInsert";
	document.frmCommision.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmCommision.dfCommisionId.value=id;
	document.frmCommision.formAction.value="DELETE";
	document.frmCommision.target="wndDelete";
	document.frmCommision.submit();
}