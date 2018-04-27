
function fProcessForm(sFormAction)
{
    document.frmProspect.action='prospect';
    with (document.frmProspect)
    {
    	dfContactName.value=fElimChar(fElimChar(dfContactName.value,"'"),'"');

    	dfAddress.value=fElimChar(fElimChar(dfAddress.value,"'"),'"');
    	dfAddress2.value=fElimChar(fElimChar(dfAddress2.value,"'"),'"');
    	dfCity.value=fElimChar(fElimChar(dfCity.value,"'"),'"');
    	dfCounty.value=fElimChar(fElimChar(dfCounty.value,"'"),'"');
    	dfZip.value=fElimChar(fElimChar(dfZip.value,"'"),'"');
        dfPhone.value=fElimChar(fElimChar(dfPhone.value,"'"),'"');
        dfPhoneExt.value=fElimChar(fElimChar(dfPhoneExt.value,"'"),'"');
        dfCellPhone.value=fElimChar(fElimChar(dfCellPhone.value,"'"),'"');
        dfEmail.value=fElimChar(fElimChar(dfEmail.value,"'"),'"');
        dfFax.value=fElimChar(fElimChar(dfFax.value,"'"),'"');
        dfWebsite.value=fElimChar(fElimChar(dfWebsite.value,"'"),'"');

        if (dfCompanyId.value=='')
        {
            alert('Please specify the Sales Company.');
            return false;
        }
        if (dfCustomerCompany.value=='')
        {
            alert('Please specify the Customer Company.');
            return false;
        }

        if (dateIsMandatory == 1 && dfSalesActionDate.value=='' )
        {
            alert('Please specify the next action date.');
            return false;
        }
        dfOpenOrdersNumber.value=fElimChar(fElimChar(dfOpenOrdersNumber.value,","),'');
        dfOpenOrdersNumber.value=fElimChar(fElimChar(dfOpenOrdersNumber.value,"$"),'');
        dfOpenOrdersValue.value=fElimChar(fElimChar(dfOpenOrdersValue.value,","),'');
        dfOpenOrdersValue.value=fElimChar(fElimChar(dfOpenOrdersValue.value,"$"),'');
        dfOpenQuotesNumber.value=fElimChar(fElimChar(dfOpenQuotesNumber.value,","),'');
        dfOpenQuotesNumber.value=fElimChar(fElimChar(dfOpenQuotesNumber.value,"$"),'');
        dfOpenQuotesValue.value=fElimChar(fElimChar(dfOpenQuotesValue.value,","),'');
        dfOpenQuotesValue.value=fElimChar(fElimChar(dfOpenQuotesValue.value,"$"),'');
        dfOpenAccountsReceivable.value=fElimChar(fElimChar(dfOpenAccountsReceivable.value,","),'');
        dfOpenAccountsReceivable.value=fElimChar(fElimChar(dfOpenAccountsReceivable.value,"$"),'');
        dfForecast.value=fElimChar(fElimChar(dfForecast.value,","),'');
        dfForecast.value=fElimChar(fElimChar(dfForecast.value,"$"),'');
        dfInvoicesNumberLtd.value=fElimChar(fElimChar(dfInvoicesNumberLtd.value,","),'');
        dfInvoicesNumberLtd.value=fElimChar(fElimChar(dfInvoicesNumberLtd.value,"$"),'');
        dfInvoicesValueLtd.value=fElimChar(fElimChar(dfInvoicesValueLtd.value,","),'');
        dfInvoicesValueLtd.value=fElimChar(fElimChar(dfInvoicesValueLtd.value,"$"),'');
        dfInvoicesNumberYtd.value=fElimChar(fElimChar(dfInvoicesNumberYtd.value,","),'');
        dfInvoicesNumberYtd.value=fElimChar(fElimChar(dfInvoicesNumberYtd.value,"$"),'');
        dfInvoicesValueYtd.value=fElimChar(fElimChar(dfInvoicesValueYtd.value,","),'');
        dfInvoicesValueYtd.value=fElimChar(fElimChar(dfInvoicesValueYtd.value,"$"),'');
    }

    return true;
}

function fOnSubmit(actionDescription){
    if (fProcessForm(1)){
    		if(actionDescription == 'Insert'){
    			document.frmProspect.formAction.value='INSERT';
    		}
			if(actionDescription == 'Update'){
    			document.frmProspect.formAction.value='UPDATE';
    		}
    		document.frmProspect.target=window.name;
            document.frmProspect.action='prospect';
        	document.frmProspect.submit();
    }
}

function fOnUpdateAndNext(){
    if (fProcessForm(1)){

    		document.frmProspect.formAction.value='UPDATE';
    		document.frmProspect.target=window.name;
            document.frmProspect.action='prospect';
        	document.frmProspect.dfChangeIndex.value = '1';
        	document.frmProspect.submit();
    }
}

function fOnAddNote()
{
    window.name = "mainWnd";
    var wndNotes = window.open('', 'wndNotes', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndNotes';
    document.frmProspect.action='prospect';

    document.frmProspect.formAction.value="DISPLAY_INSERT_NOTE";
    document.frmProspect.submit();
}

function fOnUpdateNote(id)
{
    window.name = "mainWnd";
    var wndNotes = window.open('', 'wndNotes', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndNotes';
    document.frmProspect.action='prospect';
    document.frmProspect.dfNoteId.value=id;
    document.frmProspect.formAction.value="DISPLAY_UPDATE_NOTE";
    document.frmProspect.submit();
}

function fOnDeleteNote(id)
{
    window.name = "mainWnd";
    var wndNotes = window.open('', 'wndNotes', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndNotes';
    document.frmProspect.action='prospect';
    document.frmProspect.dfNoteId.value=id;
    document.frmProspect.formAction.value="DISPLAY_DELETE_NOTE";
    document.frmProspect.submit();
}

function fOnPrint()
{
    window.name = "mainWnd";
    var wndPrint = window.open('', 'wndPrint', 'status=yes,scrollbars=yes,width=780,height=500,top=0,left=0');
    document.frmProspect.target='wndPrint';
    document.frmProspect.action='prospect_print';

    document.frmProspect.formAction.value="DISPLAY_PRINT_FRAMESET";
    document.frmProspect.submit();
}

function fOnNext()
{
    document.frmProspect.target="";
	document.frmProspect.action="prospect";
	document.frmProspect.formAction.value='DISPLAY_UPDATE';
	document.frmProspect.dfChangeIndex.value = '1';
	document.frmProspect.submit();
}

function fOnPrev()
{
    document.frmProspect.target="";
	document.frmProspect.action="prospect";
	document.frmProspect.formAction.value='DISPLAY_UPDATE';
	document.frmProspect.dfChangeIndex.value = '-1';
	document.frmProspect.submit();
}

function fOnSearchResults()
{
    document.frmProspect.target="";
	document.frmProspect.action="prospect_search";
	document.frmProspect.formAction.value='DISPLAY';
	document.frmProspect.submit();
}

function fOnRedoSearch()
{
    document.frmProspect.target="";
	document.frmProspect.action="prospect_search";
	document.frmProspect.formAction.value='REDO_DISPLAY';
	document.frmProspect.submit();
}

function fOnCalendarSearch()
{
    document.frmProspect.target="";
	document.frmProspect.action="territory_calendar";
	document.frmProspect.formAction.value='DISPLAY';
	document.frmProspect.submit();
}

function fOnAddCommission(prospectId)
{

	window.name = "mainWnd";
    var wndWebsites = window.open('', 'wndCommision', 'status=yes,scrollbars=yes,width=700,height=800,top=0,left=0');
    document.frmProspect.target="wndCommision";
	document.frmProspect.action="commision";
	document.frmProspect.dfProspectId.value=prospectId;
	document.frmProspect.formAction.value='DISPLAY_INSERT';
	document.frmProspect.submit();
}
function fOnUpdateCommission(id)
{
	
	window.name = "mainWnd";
    var wndWebsites = window.open('', 'wndCommision', 'status=yes,scrollbars=yes,width=600,height=700,top=0,left=0');
    document.frmProspect.target="wndCommision";
	document.frmProspect.action="commision";
	document.frmProspect.dfCommisionId.value=id;
	document.frmProspect.formAction.value='DISPLAY_UPDATE';
	document.frmProspect.submit();
}
function fOnDeleteCommission(id)
{

	window.name = "mainWnd";
    var wndWebsites = window.open('', 'wndCommision', 'status=yes,scrollbars=yes,width=600,height=700,top=0,left=0');
    document.frmProspect.target="wndCommision";
	document.frmProspect.action="commision";
	document.frmProspect.dfCommisionId.value=id;
	document.frmProspect.formAction.value='DELETE';
	document.frmProspect.submit();
}
function fOnAddWebsite()
{
    window.name = "mainWnd";
    var wndWebsites = window.open('', 'wndWebsites', 'status=yes,scrollbars=yes,width=600,height=700,top=0,left=0');
    document.frmProspect.target='wndWebsites';
    document.frmProspect.action='prospect';
    document.frmProspect.formAction.value="DISPLAY_INSERT_WEBSITE";
    document.frmProspect.submit();
}

function fOnUpdateWebsite(id)
{
    window.name = "mainWnd";
    var wndWebsites = window.open('', 'wndWebsites', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndWebsites';
    document.frmProspect.action='prospect';
    document.frmProspect.dfWebsiteId.value=id;
    document.frmProspect.formAction.value="DISPLAY_UPDATE_WEBSITE";
    document.frmProspect.submit();
}

function fOnDeleteWebsite(id)
{
    window.name = "mainWnd";
    var wndWebsites = window.open('', 'wndWebsites', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndWebsites';
    document.frmProspect.action='prospect';
    document.frmProspect.dfWebsiteId.value=id;
    document.frmProspect.formAction.value="DISPLAY_DELETE_WEBSITE";
    document.frmProspect.submit();
}

function fOnAddDocument()
{
    window.name = "mainWnd";
    var wndDocuments = window.open('', 'wndDocuments', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndDocuments';
    document.frmProspect.action='prospect';
    document.frmProspect.formAction.value="DISPLAY_INSERT_PROSPECT_DOCUMENT";
    document.frmProspect.submit();
}

function fOnUpdateDocument(id)
{
    window.name = "mainWnd";
    var wndDocuments = window.open('', 'wndDocuments', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndDocuments';
    document.frmProspect.action='prospect';
    document.frmProspect.dfProspectDocumentId.value=id;
    document.frmProspect.formAction.value="DISPLAY_UPDATE_PROSPECT_DOCUMENT";
    document.frmProspect.submit();
}

function fOnDeleteDocument(id)
{
    window.name = "mainWnd";
    var wndDocuments = window.open('', 'wndDocuments', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndDocuments';
    document.frmProspect.action='prospect';
    document.frmProspect.dfProspectDocumentId.value=id;
    document.frmProspect.formAction.value="DISPLAY_DELETE_PROSPECT_DOCUMENT";
    document.frmProspect.submit();
}

function fOnAddContact()
{
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndContacts', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndContacts';
    document.frmProspect.action='prospect';

    document.frmProspect.formAction.value="DISPLAY_INSERT_CONTACT";
    document.frmProspect.submit();
}

function fOnUpdateContact(id)
{
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndContacts', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndContacts';
    document.frmProspect.action='prospect';
    document.frmProspect.dfContactId.value=id;
    document.frmProspect.formAction.value="DISPLAY_UPDATE_CONTACT";
    document.frmProspect.submit();
}

function fOnDeleteContact(id)
{
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndContacts', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndContacts';
    document.frmProspect.action='prospect';
    document.frmProspect.dfContactId.value=id;
    document.frmProspect.formAction.value="DISPLAY_DELETE_CONTACT";
    document.frmProspect.submit();
}

function fOnAddProspectSalesAction() {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndProspectSalesAction', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndProspectSalesAction';
    document.frmProspect.action='prospect';

    document.frmProspect.formAction.value="DISPLAY_INSERT_PROSPECT_SALES_ACTION";
    document.frmProspect.submit();
}

function fOnAddCampaign() {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndCampaignAction', 'status=yes,scrollbars=yes,width=800,height=480,top=0,left=0, resizable=yes');
    document.frmProspect.target='wndCampaignAction';
    document.frmProspect.action='prospect';
    document.frmProspect.formAction.value="DISPLAY_SELECT_CAMPAIGN";
    document.frmProspect.submit();
}

function fOnUpdateProspectCampaign(id) {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndCampaignAction', 'status=yes,scrollbars=yes,width=1000,height=800,top=0,left=0, resizable=yes');
    document.frmProspect.target='wndCampaignAction';
    document.frmProspect.action='prospect';
    document.frmProspect.dfProspectCampaignId.value=id;
    document.frmProspect.formAction.value="DISPLAY_UPDATE_PROSPECT_CAMPAIGN";
    document.frmProspect.submit();
}

function fOnDeleteProspectCampaign(id) {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndCampaignAction', 'status=yes,scrollbars=yes,width=800,height=480,top=0,left=0');
    document.frmProspect.target='wndCampaignAction';
    document.frmProspect.action='prospect';
    document.frmProspect.dfProspectCampaignId.value=id;
    document.frmProspect.formAction.value="DISPLAY_DELETE_PROSPECT_CAMPAIGN";
    document.frmProspect.submit();
}

function fOnUpdateProspectSalesAction(id) {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndProspectSalesAction', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndProspectSalesAction';
    document.frmProspect.action='prospect';
    document.frmProspect.dfProspectSalesActionId.value=id;
    document.frmProspect.formAction.value="DISPLAY_UPDATE_PROSPECT_SALES_ACTION";
    document.frmProspect.submit();
}

function fOnDeleteProspectSalesAction(id) {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndProspectSalesAction', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
    document.frmProspect.target='wndProspectSalesAction';
    document.frmProspect.action='prospect';
    document.frmProspect.dfProspectSalesActionId.value=id;
    document.frmProspect.formAction.value="DISPLAY_DELETE_PROSPECT_SALES_ACTION";
    document.frmProspect.submit();
}

function displayHideMoreInfo(){
	if(document.getElementById('moreInfo').style.display == 'block'){
		document.getElementById('moreInfo').style.display = 'none';
	}else{
		document.getElementById('moreInfo').style.display = 'block';
	}	 
}
