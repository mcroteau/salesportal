function fOnUpdate(id, popup, formAction)
{
    if (popup == true)
    {
	    var wndUpdate = window.open("","wndUpdate","width=900,height=1000,scrollbars=yes,status=yes");
	    wndUpdate.focus();
	    wndUpdate.opener=window;
	    document.frmUser.target="wndUpdate";
	}
	document.frmUser.dfUserId.value=id;
	document.frmUser.formAction.value=formAction;
	document.frmUser.submit();
}

function fOnInsert(popup, formAction)
{
    if (popup == true)
    {
	    var wndInsert = window.open("","wndInsert","width=900,height=600,scrollbars=yes,status=yes");
	    wndInsert.focus();
	    wndInsert.opener=window;
	    document.frmUser.target="wndInsert";
	}
	document.frmUser.formAction.value=formAction;

	document.frmUser.submit();
}

function fOnDelete(id, popup, formAction)
{
    if (popup == true)
    {
    	var wndDelete = window.open("","wndDelete","width=900,height=600");
	    wndDelete.focus();
	    wndDelete.opener=window;
	}
	document.frmUser.dfUserId.value=id;
	document.frmUser.formAction.value=formAction;
	document.frmUser.target="wndDelete";
	document.frmUser.submit();
}