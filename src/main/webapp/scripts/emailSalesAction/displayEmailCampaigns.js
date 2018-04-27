function fOnUpdate(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndUpdate = window.open("","wndUpdate","width=1000,height=800");
	wndUpdate.focus();
	wndUpdate.opener=window;
	document.frmEmailCampaign.dfEmailCampaignId.value=id;
	document.frmEmailCampaign.formAction.value="DISPLAY_UPDATE";
	document.frmEmailCampaign.target="wndUpdate";
	document.frmEmailCampaign.submit();
}

function fOnInsert(){

//	var wndInsert = window.open("","wndInsert","width=400,height=300");
	var wndInsert = window.open("","wndInsert","width=1000,height=800");
	wndInsert.focus();
	wndInsert.opener=window;
	document.frmEmailCampaign.formAction.value="DISPLAY_INSERT";
	document.frmEmailCampaign.target="wndInsert";
	document.frmEmailCampaign.submit();
}

function fOnDelete(id){

	//var wndDelete = window.open("","wndDelete","width=400,height=300");
	var wndDelete = window.open("","wndDelete","width=1000,height=800");
	
	wndDelete.focus();
	wndDelete.opener=window;
	document.frmEmailCampaign.dfEmailCampaignId.value=id;
	document.frmEmailCampaign.formAction.value="DELETE";
	document.frmEmailCampaign.target="wndDelete";
	document.frmEmailCampaign.submit();
}

function fOnEstimateProspects(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndEstimateProspects = window.open("","wndEstimateProspects","width=600,height=300, scrollbar=yes, resizable=yes");
	wndEstimateProspects.focus();
	wndEstimateProspects.opener=window;
	document.frmEmailCampaign.dfEmailCampaignId.value=id;
	document.frmEmailCampaign.formAction.value="ESTIMATE_PROSPECTS";
	document.frmEmailCampaign.target="wndEstimateProspects";
	document.frmEmailCampaign.submit();
}

function fOnDisplayProspects(id){
	//var wndUpdate = window.open("","wndUpdate","width=400,height=300");
	var wndDisplayProspects = window.open("","wndDisplayProspects","width=600,height=800");
	wndDisplayProspects.focus();
	wndDisplayProspects.opener=window;
	document.frmEmailCampaign.dfEmailCampaignId.value=id;
	document.frmEmailCampaign.formAction.value="DISPLAY_PROSPECTS";
	document.frmEmailCampaign.target="wndDisplayProspects";
	document.frmEmailCampaign.submit();
}
