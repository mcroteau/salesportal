function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmCurrency.dfCurrencyId.value=id;
	document.frmCurrency.formAction.value="DISPLAY_UPDATE";
	document.frmCurrency.target="wndUpdate";
	document.frmCurrency.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmCurrency.formAction.value="DISPLAY_INSERT";
	document.frmCurrency.target="wndInsert";
	document.frmCurrency.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmCurrency.dfCurrencyId.value=id;
	document.frmCurrency.formAction.value="DELETE";
	document.frmCurrency.target="wndDelete";
	document.frmCurrency.submit();
}