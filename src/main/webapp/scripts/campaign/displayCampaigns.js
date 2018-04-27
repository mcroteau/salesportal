function fOnUpdate(id)
{
	var wndUpdate = window.open("","wndUpdate","width=600,height=800,scrollbars=yes,resizable=yes");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmCampaign.dfCampaignId.value=id;
	document.frmCampaign.formAction.value="DISPLAY_UPDATE";
	document.frmCampaign.target="wndUpdate";
	document.frmCampaign.submit();
}

function fOnInsert()
{
	var wndInsert = window.open("","wndInsert","width=400,height=300");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmCampaign.formAction.value="DISPLAY_INSERT";
	document.frmCampaign.target="wndInsert";
	document.frmCampaign.submit();
}

function fOnDelete(id)
{
	var wndDelete = window.open("","wndDelete","width=400,height=300");
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmCampaign.dfCampaignId.value=id;
	document.frmCampaign.formAction.value="DELETE";
	document.frmCampaign.target="wndDelete";
	document.frmCampaign.submit();
}