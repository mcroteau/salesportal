function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmCountry.dfCountryId.value=id;
	document.frmCountry.formAction.value="DISPLAY_UPDATE";
	document.frmCountry.target="wndUpdate";
	document.frmCountry.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmCountry.formAction.value="DISPLAY_INSERT";
	document.frmCountry.target="wndInsert";
	document.frmCountry.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmCountry.dfCountryId.value=id;
	document.frmCountry.formAction.value="DELETE";
	document.frmCountry.target="wndDelete";
	document.frmCountry.submit();
}