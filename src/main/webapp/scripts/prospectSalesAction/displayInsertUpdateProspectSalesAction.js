function fOnAddProspectSalesAction()
{
    	document.frmProspectSalesAction.formAction.value="DISPLAY_INSERT";
    	document.frmProspectSalesAction.submit();
    	
}
function fOnUpdateProspectSalesAction(prospectSalesActionId)
{
    document.frmProspectSalesAction.formAction.value="DISPLAY_UPDATE";
    document.frmProspectSalesAction.dfProspectSalesActionId.value=prospectSalesActionId;
    document.frmProspectSalesAction.submit();
}

function fOnDeleteProspectSalesAction(prospectSalesActionId){
    document.frmProspectSalesAction.formAction.value="DISPLAY_DELETE";
    document.frmProspectSalesAction.dfProspectSalesActionId.value=prospectSalesActionId;
    document.frmProspectSalesAction.submit();
}

function fProcessForm(){
    with (document.frmProspectSalesAction)
{
 /*       dfProspectSalesActionName.value=fElimChar(fElimChar(dfProspectSalesActionName.value,"'"),'"');
        dfProspectSalesActionTitle.value=fElimChar(fElimChar(dfProspectSalesActionTitle.value,"'"),'"');
        dfPhone.value=fElimChar(fElimChar(dfPhone.value,"'"),'"');
        dfPhoneExt.value=fElimChar(fElimChar(dfPhoneExt.value,"'"),'"');
        dfCellPhone.value=fElimChar(fElimChar(dfCellPhone.value,"'"),'"');
        dfEmail.value=fElimChar(fElimChar(dfEmail.value,"'"),'"');
        dfFax.value=fElimChar(fElimChar(dfFax.value,"'"),'"');
        if ((dfProspectSalesActionName.value == "") &&
            (dfProspectSalesActionTitle.value == "") &&
            (dfPhone.value == "") &&
            (dfPhoneExt.value == "") &&
            (dfCellPhone.value == "") &&
            (dfFax.value == "") &&
            (dfEmail.value == ""))
       {
            alert('Please enter the contact content.');
            return false;
        }*/
    }
    return true;
}

function insertSalesAction(){
	if(isValidForm()){
		document.frmProspectSalesAction.submit();	
	}
};

function isValidForm(){
	var selected = document.frmProspectSalesAction.cmbSalesAction.options.selectedIndex - 1;
	var dateField = document.frmProspectSalesAction.dfSalesActionDate.value;
	var hourField = document.frmProspectSalesAction.dfHour.value;
	
	if (arrMandatoryDateFlags[selected]==1){
		if(dateField ==''){
			alert('Date is Mandatory for the Sales Action you have Selected!');
			return false;
		}
		if(hourField ==''){
			alert('Hour is Mandatory for the Sales Action you have Selected!');
			return false;
    	}
    	return true;
    } else {
    	return true;		
    }
}

function fSelectSalesAction(){
	var i = 0;
	var selected = document.frmProspectSalesAction.cmbSalesAction.options.selectedIndex - 1;

	var emailDraft = arrEmailDraft[selected];
	if (selected >= 0) {
		if (arrMandatoryDateFlags[selected]==0){
 			document.frmProspectSalesAction.dfSalesActionDate.style.background='#c0c0c0';
			document.frmProspectSalesAction.dfSalesActionDate.disabled = true;
			document.frmProspectSalesAction.dfSalesActionDate.value = '';
			dateIsMandatory = 0;
		} else {
			document.frmProspectSalesAction.dfSalesActionDate.style.background='#ffffff';
			document.frmProspectSalesAction.dfSalesActionDate.disabled = false;
			dateIsMandatory = 1;
		}
		document.frmProspectSalesAction.dfEmailDraftToUse.value = arrEmailDraft[selected];
	} else {
		document.frmProspectSalesAction.dfSalesActionDate.style.background='#c0c0c0';
		document.frmProspectSalesAction.dfSalesActionDate.disabled = true;
		document.frmProspectSalesAction.dfSalesActionDate.value = '';
		dateIsMandatory = 0;
	}
};


selectTime();
var selectAmPm =  getRadioButtonSelectedValue(time);
  
       
function selectTime(){
        var selectedViewType =  getRadioButtonSelectedValue();
        var amEnabled = (selectAmPm == 'AM') ? true : false;
        var pmEnabled = (selectAmPm == 'PM') ? true : false;
}

	
function getRadioButtonSelectedValue(objRadio) {
    for (var i=0; i < objRadio.length; i++) {
        if (objRadio[i].checked) {
            return objRadio[i].value;
        }
    }
    return '';
}