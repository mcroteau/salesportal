//
// startup.js
//


function fSubmitContinue(){
	document.getElementById('progressIndicator').style.display = 'block';
	document.formInstallWizard.submit();
}


// wizard step 4 - Registration 

function fSubmitRegistration() {
	document.frmWizardRegistration.register.value = 'true';
	document.getElementById('progressIndicator').style.display = 'block';
	document.frmWizardRegistration.submit();
}

function fSubmitSkipRegistration() {
	document.frmWizardRegistration.formAction.value = 'SKIP_REGISTRATION';
	document.frmWizardRegistration.register.value = 'false';
	document.getElementById('progressIndicator').style.display = 'block';
	document.frmWizardRegistration.submit();
}

//Contact Support
function fOpenContact(){
	var objForm = document.frmContactInfo;
	var wndUpdate = window.open("","wndSupport","width=450,height=310,scrollbars=yes,resizable=yes"); 	
    objForm.target="wndSupport";
    objForm.submit();
   	wndUpdate.opener = self;
   	wndUpdate.focus();
}

//Email Detail Information
function fOpenEmailDetailInfo(){
	var objForm = document.frmEmailInfoDetail;
	var wndUpdate = window.open("","wndSupport","width=900,height=670,scrollbars=yes,resizable=yes"); 	
    objForm.target="wndSupport";
    objForm.submit();
   	wndUpdate.opener = self;
   	wndUpdate.focus();
}


function fSaveLocalSettings(){
	document.getElementById('progressIndicator').style.display = 'block';
	document.frmWizardLocalSettings.submit();
}