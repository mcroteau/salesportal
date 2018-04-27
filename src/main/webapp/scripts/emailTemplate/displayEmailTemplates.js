function fOnDisplayEmailTemplates()
{
    var wndEmailTemplates = window.open('', 'wndEmailTemplates', 'status=yes,scrollbars=yes,width=550,height=550,top=0,left=0');
    document.frmEmailTemplates.target='wndEmailTemplates';
    document.frmEmailTemplates.formAction.value='DISPLAY';
    document.frmEmailTemplates.submit();
}

function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=500,height=500");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmEmailTemplates.dfEmailTemplateId.value=id;
	document.frmEmailTemplates.formAction.value="DISPLAY_UPDATE";
	document.frmEmailTemplates.target="wndUpdate";
	document.frmEmailTemplates.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=500,height=500");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmEmailTemplates.formAction.value="DISPLAY_INSERT";
	document.frmEmailTemplates.target="wndInsert";
	document.frmEmailTemplates.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=500,height=500");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmEmailTemplates.dfEmailTemplateId.value=id;
	document.frmEmailTemplates.formAction.value="DELETE";
	document.frmEmailTemplates.target="wndDelete";
	document.frmEmailTemplates.submit();
	}
