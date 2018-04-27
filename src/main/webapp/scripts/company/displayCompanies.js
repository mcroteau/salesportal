function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmCompany.dfCompanyId.value=id;
	document.frmCompany.formAction.value="DISPLAY_UPDATE";
	document.frmCompany.target="wndUpdate";
	document.frmCompany.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmCompany.formAction.value="DISPLAY_INSERT";
	document.frmCompany.target="wndInsert";
	document.frmCompany.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmCompany.dfCompanyId.value=id;
	document.frmCompany.formAction.value="DELETE";
	document.frmCompany.target="wndDelete";
	document.frmCompany.submit();
}