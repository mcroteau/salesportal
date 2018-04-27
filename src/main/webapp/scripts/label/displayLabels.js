function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmLabel.dfLabelId.value=id;
	document.frmLabel.formAction.value="DISPLAY_UPDATE";
	document.frmLabel.target="wndUpdate";
	document.frmLabel.submit();
}


