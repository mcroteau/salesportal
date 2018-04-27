function fOnUpdate(prospectId){
	document.frmCurrentActions.dfProspectId.value = prospectId;
	document.frmCurrentActions.action="prospect";
	document.frmCurrentActions.formAction.value='DISPLAY_UPDATE_CURRENT_ACTIONS';
	document.frmCurrentActions.submit();
}

function fPopupView(){
	var wndCurrentActions = window.open("Actions","wndCurrentActions","width=450,height=350,scrollbars=yes,resizable=yes");
	wndCurrentActions.focus();
	wndCurrentActions.opener=window;
	document.frmPopupActions.target="wndCurrentActions";
	document.frmPopupActions.submit();
}

function fIncludePassedDue(searchBy){
	document.frmCurrentActions.action="current_actions";
	document.frmCurrentActions.formAction.value='DISPLAY_CURRENT_ACTIONS';
	document.frmCurrentActions.includePassedDue.value='YES';
	document.frmCurrentActions.currentSearchBy.value=searchBy;
	document.frmCurrentActions.submit();
}

function fSalesManagerSA(){
	document.frmCurrentActions.action="current_actions";
	document.frmCurrentActions.formAction.value='DISPLAY_CURRENT_ACTIONS';
	document.frmCurrentActions.currentSearchBy.value='salesManager';
	document.frmCurrentActions.submit();
}

function fServiceManagerSA(){
	document.frmCurrentActions.action="current_actions";
	document.frmCurrentActions.formAction.value='DISPLAY_CURRENT_ACTIONS';
	document.frmCurrentActions.currentSearchBy.value='serviceManager';
	document.frmCurrentActions.submit();
}


function fTerritoryOwnerSA(){
	document.frmCurrentActions.action="current_actions";
	document.frmCurrentActions.formAction.value='DISPLAY_CURRENT_ACTIONS';
	document.frmCurrentActions.currentSearchBy.value='territoryOwner';
	document.frmCurrentActions.submit();
}


function fOpenHelp(helpFile, width, height){	
	help = window.open(''+helpFile+'', '',
		'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
	    'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=yes, scrollbar=yes, resizable=yes');
	help.opener = self;
	help.focus();
}